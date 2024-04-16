package com.tiimi1.petshop;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductType;
import com.tiimi1.petshop.model.Manufacturer;

public class PetshopJPATests {

    @Test
    public void manufacturerCreateTest() {
        Manufacturer testManufacturer = new Manufacturer("Dharma Inc.", "The Island", "4815162342");
        assertThat(testManufacturer.getName()).isNotNull();
    }

    @Test
    public void productTypeCreateTest() {
        ProductType testProductType = new ProductType("Test type");
        assertThat(testProductType.getProductTypeValue()).isNotNull();
    }

    @Test
    public void productCreateTest() {
        Manufacturer testManufacturer = new Manufacturer("Dharma Inc.", "The Island", "4815162342");
        ProductType testProductType = new ProductType("Test type");
        Product testProduct = new Product("Test product", testProductType, "Green", "L", new BigDecimal("48.15"), testManufacturer);
        assertThat(testProduct.getName()).isNotNull();
        
    }

}
