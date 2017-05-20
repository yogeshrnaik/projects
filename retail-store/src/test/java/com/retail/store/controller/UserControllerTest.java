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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.retail.store.RetailStoreApplication;
import com.retail.store.dto.ResponseType;
import com.retail.store.model.User;
import com.retail.store.repository.ProductRepository;
import com.retail.store.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailStoreApplication.class)
@WebAppConfiguration
public class UserControllerTest extends RetailStoreControllerTest {

    private List<User> users = new ArrayList<>();

    @Override
    @Before
    public void setUp() {
        super.setUp();

        users.add(userRepo.saveAndFlush(new User(1l, "Name 1", null)));
        users.add(userRepo.saveAndFlush(new User(2l, "Name 2", null)));
        users.add(userRepo.saveAndFlush(new User(3l, "Name 3", null)));
    }

    @Test
    public void test_getAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void readUser() throws Exception {
        for (User user : userRepo.findAll()) {
            mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(header().string("Location", containsString("/users/" + user.getId())));
        }
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/users/0"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.type", is(ResponseType.ERROR.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("NotFound.user"))));
    }

    @Test
    public void createUser() throws Exception {
        String userJson = json(new User(null, "New user", null));

        mockMvc.perform(post("/users")
            .contentType(contentType)
            .content(userJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.user.added"))))
            .andExpect(header().string("Location", containsString("/users/")));
    }

    @Test
    public void updateUser() throws Exception {
        User user = users.get(0);
        String userJson = json(new User(user.getId(), "New User Name", null));

        mockMvc.perform(put("/users/" + user.getId())
            .contentType(contentType)
            .content(userJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.user.updated"))))
            .andExpect(header().string("Location", containsString("/users/" + user.getId())));
    }

}
