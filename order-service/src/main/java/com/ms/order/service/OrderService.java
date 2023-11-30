package com.ms.order.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ms.order.dto.InventoryResponse;
import com.ms.order.dto.OrderLineItemDtoRequest;
import com.ms.order.dto.OrderLineItemDtoResponse;
import com.ms.order.dto.OrderRequest;
import com.ms.order.dto.OrderResponse;
import com.ms.order.event.OrderPlacedEvent;
import com.ms.order.model.Order;
import com.ms.order.model.OrderLineItem;
import com.ms.order.repository.OrderRepository;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
//	private WebClient webClient;
	private WebClient.Builder webClientBuilder;

	@Autowired
	private Tracer tracer;

	@Autowired
	private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

	public OrderResponse placeOrder(OrderRequest orderRequest) {

		// Map from OrderLineItemDtoRequest to OrderLineItem
		List<OrderLineItemDtoRequest> orderLineItemDtoRequestList = orderRequest.getOrderLineItemDtoRequestList();
		List<OrderLineItem> orderLineItemList = orderLineItemDtoRequestList.stream()
				.map(olid -> new OrderLineItem(olid.getSkuCode(), olid.getPrice(), olid.getQuantity())).toList();

		// Call the InventoryService by sending a list of skuCodes as input, and to
		// check if the OrderLineItems are in stock based on skuCode, using WebClient.
		// If inventory in stock, make the order through synchronous communication.
		// Call: http://localhost:8082/api/inventory?sku-code=SH&sku-code=FL
		List<String> skuCodeList = orderLineItemList.stream().map(oli -> oli.getSkuCode()).toList();

		LOGGER.info("Calling Inventory Service");

		// Micrometer Tracer is used to create a new custom spanId which can be used
		// across microservices for tracing request logs with common span name
		Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
		inventoryServiceLookup.start();
		// Associate the span with a scope such that when the scope ends, the span
		// closes.
		// This also automatically adds the custom spanId in the all the below logs.
		try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup)) {

			// Service Discovery pattern: DiscoveryServer may be used to locate the
			// microservice uri instead of hard-coding the url:
			// 'http://localhost:8082/api/inventory'.

			// Instead of webClient.get(), use webClientBuilder.build().get() to get
			// multiple instances of WebClient for Client Load Balancing
			Mono<InventoryResponse[]> monoInventoryResponse = webClientBuilder.build().get()
					.uri("http://inventory-service/api/inventory",
							uriBuilder -> uriBuilder.queryParam("sku-code", skuCodeList).build())
					.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(InventoryResponse[].class);
			InventoryResponse[] inventoryResponseArr = monoInventoryResponse.block();

			if (Arrays.stream(inventoryResponseArr).allMatch(inventoryResponse -> inventoryResponse.isInStock())) {
				// Save both Order and list of OrderLineItem
				Order order = new Order(UUID.randomUUID().toString(), orderLineItemList);
				orderLineItemList.forEach(oli -> oli.setOrder(order));
				Order orderDb = orderRepository.save(order);
				
				kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(orderDb.getOrderNumber()));
				LOGGER.info("Order {} sent to Notification Service.", orderDb.getOrderNumber());

				// Map from OrderLineItem to OrderLineItemDtoResponse
				List<OrderLineItemDtoResponse> orderLineItemDtoResponseList = orderDb.getOrderLineItemList().stream()
						.map(oli -> new OrderLineItemDtoResponse(oli.getOrderLineItemId(), oli.getSkuCode(),
								oli.getPrice(), oli.getQuantity()))
						.toList();
				OrderResponse orderResponse = new OrderResponse(orderDb.getOrderId(), orderDb.getOrderNumber(),
						orderLineItemDtoResponseList);
				return orderResponse;
			} else {
				throw new IllegalArgumentException("Product is not in stock. Please try again later");
			}
		} finally {
			inventoryServiceLookup.end();
		}
	}

}
