package com.tiimi1.petshop;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;
import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;
import com.tiimi1.petshop.model.ProductType;
import com.tiimi1.petshop.model.ProductTypeRepository;
import com.tiimi1.petshop.model.Reservation;
import com.tiimi1.petshop.model.ReservationRepository;

@SpringBootApplication
public class PetshopApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(PetshopApplication.class);
	private final ProductRepository productRepository;
	private final ManufacturerRepository manufacturerRepository;
	private final ProductTypeRepository productTypeRepository;
	private final CustomerRepository customerRepository;
	private final ReservationRepository reservationRepository;

	public PetshopApplication(ProductRepository productRepository, ManufacturerRepository manufacturerRepository,
			ProductTypeRepository productTypeRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository) {
		this.productRepository = productRepository;
		this.manufacturerRepository = manufacturerRepository;
		this.productTypeRepository = productTypeRepository;
		this.customerRepository = customerRepository;
		this.reservationRepository = reservationRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
		log.info("Application started");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("couple demo manufacturers");
		Manufacturer manufacturer1 = new Manufacturer("Doggy Stuff", "Finland", "1234567-1");
		Manufacturer manufacturer2 = new Manufacturer("Catty Stuff", "Sweden", "1234568-2");
		Manufacturer manufacturer3 = new Manufacturer("Squeaky Stuff", "Norway", "1234569-3");

		manufacturerRepository.save(manufacturer1);
		manufacturerRepository.save(manufacturer2);
		manufacturerRepository.save(manufacturer3);

		ProductType productType1 = new ProductType("Clothing");
		ProductType productType2 = new ProductType("Toy");

		productTypeRepository.save(productType1);
		productTypeRepository.save(productType2);

		log.info("couple demo products");
		productRepository
				.save(new Product("Dog Booties", productType1, "Green", "M", new BigDecimal("12.12"), manufacturer1));
		productRepository
				.save(new Product("Cat Collar", productType2, "Red", "S", new BigDecimal("12.99"), manufacturer2));
		productRepository.save(
				new Product("Squeaky Toy, Generic", productType2, "Orange", "S", new BigDecimal("3.50"),
						manufacturer3));

		log.info("couple demo appUsers");
		// Username: user, password: user
		customerRepository
				.save(new Customer("user", "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue", "USER"));
		// Username: admin, password: admin
		customerRepository
				.save(new Customer("admin", "$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW", "ADMIN"));

		
		log.info("couple demo reservations");

		Customer customerWithReservations = (new Customer("lolli", "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue", "USER"));

		customerRepository.save(customerWithReservations);

		reservationRepository.save(new Reservation( "feikki pvm",customerWithReservations));

	}

}
