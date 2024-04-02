package com.tiimi1.petshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;

@SpringBootApplication
public class PetshopApplication {
	private static final Logger log = LoggerFactory.getLogger(PetshopApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
	}

	@Bean
	public CommandLineRunner productDemo(ProductRepository prodRepo, ManufacturerRepository manuRepo) {
		return (args) -> {
			log.info("couple demo manufacturers");
			Manufacturer manufacturer1 = new Manufacturer("Doggy Stuff");
			Manufacturer manufacturer2 = new Manufacturer("Catty Stuff");
			Manufacturer manufacturer3 = new Manufacturer("Fishy Stuff");

			manuRepo.save(manufacturer1);
			manuRepo.save(manufacturer2);
			manuRepo.save(manufacturer3);

			log.info("couple demo products");
			prodRepo.save(new Product("Dog booties", "Apparel", "Green", "Medium", 59.95D, manufacturer1));
			prodRepo.save(new Product("Cat Collar", "Apparel", "Red", "Small", 20.00D, manufacturer2));
			prodRepo.save(new Product("Fish Tank", "Living Space", "Black", "Large", 500.50D, manufacturer3));
		};
	}

}
