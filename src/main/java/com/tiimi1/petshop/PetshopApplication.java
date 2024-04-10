package com.tiimi1.petshop;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;


@SpringBootApplication
public class PetshopApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(PetshopApplication.class);
	private final ProductRepository productRepository;
	private final ManufacturerRepository manufacturerRepository;

	public PetshopApplication(ProductRepository productRepository, ManufacturerRepository manufacturerRepository) {
		this.productRepository = productRepository;
		this.manufacturerRepository = manufacturerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
		log.info("Application started");
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("couple demo manufacturers");
		Manufacturer manufacturer1 = new Manufacturer("Doggy Stuff");
		Manufacturer manufacturer2 = new Manufacturer("Catty Stuff");
		Manufacturer manufacturer3 = new Manufacturer("Fishy Stuff");

		manufacturerRepository.save(manufacturer1);
		manufacturerRepository.save(manufacturer2);
		manufacturerRepository.save(manufacturer3);

		log.info("couple demo products");
		productRepository.save(new Product("Dog booties", "Apparel", "Green", "Medium", new BigDecimal(59.95), manufacturer1));
		productRepository.save(new Product("Cat Collar", "Apparel", "Red", "Small", new BigDecimal(20.00), manufacturer2));
		productRepository.save(new Product("Fish Tank", "Living Space", "Black", "Large", new BigDecimal(500.50), manufacturer3));
	}

}
