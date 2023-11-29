package com.ms.product.service;

import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import com.ms.product.dto.ProductRequest;
import com.ms.product.dto.ProductResponse;
import com.ms.product.model.Product;
import com.ms.product.repository.ProductRepository;

@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository productRepository;

	public ProductResponse createProduct(ProductRequest productRequest) {
		Product product = new Product(productRequest.getName(), productRequest.getDescription(),
				productRequest.getPrice());
		Product savedProduct = productRepository.save(product);
		LOGGER.info("Created {}", product);
		return productToProductResponse(savedProduct);
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> productList = productRepository.findAll();
		List<ProductResponse> productResponseList = productList.stream().map(p -> productToProductResponse(p)).toList();
		return productResponseList;
	}

	private ProductResponse productToProductResponse(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
	}

}
