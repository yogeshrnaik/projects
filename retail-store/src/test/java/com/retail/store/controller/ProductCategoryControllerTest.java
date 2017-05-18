package com.retail.store.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.retail.store.RetailStoreApplication;
import com.retail.store.model.ProductCategory;
import com.retail.store.repository.ProductCategoryRepository;
import com.retail.store.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailStoreApplication.class)
@WebAppConfiguration
public class ProductCategoryControllerTest {

    @Autowired
    private ProductCategoryRepository categoryRepo;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private List<ProductCategory> categories = new ArrayList<>();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        productRepo.deleteAllInBatch();
        categoryRepo.deleteAllInBatch();

        categories.add(categoryRepo.save(new ProductCategory(1l, "A", 0.0)));
        categories.add(categoryRepo.save(new ProductCategory(2l, "B", 10.0)));
        categories.add(categoryRepo.save(new ProductCategory(3l, "C", 20.0)));
    }

    @Test
    public void test_getAllProductCategories() throws Exception {
        mockMvc.perform(get("/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void readProductCategory() throws Exception {
        for (ProductCategory cat : categories) {
            mockMvc.perform(get("/categories/" + cat.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(cat.getId().intValue())))
                .andExpect(jsonPath("$.category", is(cat.getCategory())))
                .andExpect(jsonPath("$.salesTaxPercentage", is(cat.getSalesTaxPercentage())));
        }
    }
}
