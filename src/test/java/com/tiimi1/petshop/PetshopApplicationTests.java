package com.tiimi1.petshop;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tiimi1.petshop.web.ManufacturerController;
import com.tiimi1.petshop.web.ProductController;

@SpringBootTest
class PetshopApplicationTests {

	@Autowired
	private ProductController pController;

	@Autowired
	private ManufacturerController mController;

	@Test
	public void controllerTests() throws Exception {
		assertThat(pController).isNotNull();
		assertThat(mController).isNotNull();
	}

}
