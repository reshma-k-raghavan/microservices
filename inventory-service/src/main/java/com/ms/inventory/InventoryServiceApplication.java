package com.ms.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ms.inventory.model.Inventory;
import com.ms.inventory.repository.InventoryRepository;

@SpringBootApplication
//@EnableEurekaClient. Based on pom dependency, the Main Application will be auto-configured as Eureka Client. 
//No need to add annotation: @EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	// To prevent the table from inserting multiple duplicate records, set property:
	// spring.jpa.hibernate.ddl-auto=create-drop instead of update
	// Use Liquibase or Flyway to load data to DB in Prod
	@Bean
	public CommandLineRunner loadDatatoDB(InventoryRepository inventoryRepository) {
		// CommanLineRunner is a Consumer
		return args -> {
			inventoryRepository.save(new Inventory(1L, "SH", 2, true));
			inventoryRepository.save(new Inventory(2L, "FL", 10, true));
		};

	}

}
