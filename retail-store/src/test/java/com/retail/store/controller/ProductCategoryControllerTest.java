package com.retail.store.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.retail.store.RetailStoreApplication;
import com.retail.store.dto.ResponseType;
import com.retail.store.model.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailStoreApplication.class)
@WebAppConfiguration
public class ProductCategoryControllerTest extends RetailStoreControllerTest {

    private List<ProductCategory> categories = new ArrayList<>();

    @Override
    @Before
    public void setUp() {
        super.setUp();

        categories.add(categoryRepo.saveAndFlush(new ProductCategory(1l, "A", 0.0)));
        categories.add(categoryRepo.saveAndFlush(new ProductCategory(2l, "B", 10.0)));
        categories.add(categoryRepo.saveAndFlush(new ProductCategory(3l, "C", 20.0)));
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
                .andExpect(jsonPath("$.salesTaxPercentage", is(cat.getSalesTaxPercentage())))
                .andExpect(header().string("Location", containsString("/categories/" + cat.getId())));
        }
    }

    @Test
    public void productCategoryNotFound() throws Exception {
        mockMvc.perform(get("/categories/0"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.type", is(ResponseType.ERROR.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("NotFound.productcategory"))));
    }

    @Test
    public void createProductCategory() throws Exception {
        String categoryJson = json(new ProductCategory(null, "D", 15.0));

        mockMvc.perform(post("/categories")
            .contentType(contentType)
            .content(categoryJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.productcategory.added"))))
            .andExpect(header().string("Location", containsString("/categories/")));
    }

    @Test
    public void updateProductCategory() throws Exception {
        ProductCategory cat = categories.get(0);
        String categoryJson = json(new ProductCategory(cat.getId(), cat.getCategory(), 15.0));

        mockMvc.perform(put("/categories/" + cat.getId())
            .contentType(contentType)
            .content(categoryJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.productcategory.updated"))))
            .andExpect(header().string("Location", containsString("/categories/" + cat.getId())));
    }

}
