package com.retail.store.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.retail.store.controller.response.ResponseBuilder;
import com.retail.store.repository.CartItemRepository;
import com.retail.store.repository.CartRepository;
import com.retail.store.repository.ProductCategoryRepository;
import com.retail.store.repository.ProductRepository;
import com.retail.store.repository.UserRepository;

public class RetailStoreControllerTest {

    @Autowired
    protected ResponseBuilder response;

    @Autowired
    protected ProductCategoryRepository categoryRepo;

    @Autowired
    protected ProductRepository productRepo;

    @Autowired
    protected UserRepository userRepo;

    @Autowired
    protected CartRepository cartRepo;

    @Autowired
    protected CartItemRepository cartItemRepo;

    @Autowired
    protected WebApplicationContext ctx;

    protected MockMvc mockMvc;

    protected MediaType contentType = new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @After
    public void cleanup() {
        userRepo.deleteAllInBatch();
        cartItemRepo.deleteAllInBatch();
        cartRepo.deleteAllInBatch();
        productRepo.deleteAllInBatch();
        categoryRepo.deleteAllInBatch();
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
