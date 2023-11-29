package com.ms.product.controller;

import java.math.BigDecimal;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ms.product.dto.ProductRequest;
import com.ms.product.dto.ProductResponse;
import com.ms.product.service.ProductService;

@Testcontainers
@WebMvcTest(ProductController.class)
@Disabled
class ProductControllerTest {

	@Container
	private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.3");

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProductService productService;

	// Add a property dynamically. Can also be statically specified in
	// application-test.properties file also
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl());
	}

	@Test
	void shouldCreateProduct() {
		ProductRequest productRequest = new ProductRequest("Shirt", "Blue Shirt", BigDecimal.valueOf(1000.0));
		ProductResponse productResponse = new ProductResponse("1", "Shirt", "Blue Shirt", BigDecimal.valueOf(1000.0));

		String strProductRequest = "";
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		try {
			strProductRequest = ow.writeValueAsString(productRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Mockito.when(productService.createProduct(productRequest)).thenReturn(productResponse);

		try {
			MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders
							.post("/api/product")
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON)
							.content(strProductRequest)
				)
				.andExpectAll(
						MockMvcResultMatchers.status().isCreated(),
						MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Shirt")),
						MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("Blue Shirt")),
						MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(1000.0))
				)
				.andReturn();
			System.out.println(mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
