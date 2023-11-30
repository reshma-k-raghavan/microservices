package com.ms.notify;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.ms.notify.event.OrderPlacedEvent;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@SpringBootApplication
public class NotificationServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		// built-in log from lombok
		// log.info("Received Email notification for Order {}",orderPlacedEvent.getOrderNumber());

		LOGGER.info("Received Order {}", orderPlacedEvent.getOrderNumber());
		// Logic for send email
	}
}
