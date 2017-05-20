package com.retail.store.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.retail.store.RetailStoreApplication;
import com.retail.store.dto.ProductDto;
import com.retail.store.dto.ResponseType;
import com.retail.store.model.Product;
import com.retail.store.model.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailStoreApplication.class)
@WebAppConfiguration
public class ProductControllerTest extends RetailStoreControllerTest {

    private List<ProductCategory> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @Override
    @Before
    public void setUp() {
        super.setUp();

        categories.add(categoryRepo.save(new ProductCategory(1l, "A", 0.0)));
        categories.add(categoryRepo.save(new ProductCategory(2l, "B", 10.0)));
        categories.add(categoryRepo.save(new ProductCategory(3l, "C", 20.0)));

        products.add(productRepo.save(new Product(1l, "Produce A", 100.0, categories.get(0))));
        products.add(productRepo.save(new Product(2l, "Produce B", 200.0, categories.get(1))));
        products.add(productRepo.save(new Product(3l, "Produce C", 300.0, categories.get(2))));
    }

    @Test
    public void test_getAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void readProduct() throws Exception {
        for (Product product : products) {
            mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.unitPrice", is(product.getUnitPrice())));
        }
    }

    @Test
    public void productNotFound() throws Exception {
        mockMvc.perform(get("/products/0"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.type", is(ResponseType.ERROR.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("NotFound.product"))));
    }

    @Test
    public void createProduct() throws Exception {
        String productJson = json(new ProductDto("My Product", 40.0, categories.get(0).getId()));

        mockMvc.perform(post("/products")
            .contentType(contentType)
            .content(productJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.product.added"))))
            .andExpect(header().string("Location", containsString("/products/")));
    }

    @Test
    public void updateProduct() throws Exception {
        Product product = products.get(0);
        String productJson = json(new ProductDto("MyProduct", 50.0, categories.get(0).getId()));

        mockMvc.perform(put("/products/" + product.getId())
            .contentType(contentType)
            .content(productJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.product.updated"))))
            .andExpect(header().string("Location", containsString("/products/" + product.getId())));
    }
}
