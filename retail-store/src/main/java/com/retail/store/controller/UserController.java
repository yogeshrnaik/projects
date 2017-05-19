package com.retail.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.retail.store.controller.response.ResponseBuilder;
import com.retail.store.dto.ProductDto;
import com.retail.store.dto.ResponseDto;
import com.retail.store.model.Product;
import com.retail.store.model.User;
import com.retail.store.service.ProductService;
import com.retail.store.service.UserService;

@RestController
public class UserController {

    @Autowired
    private ResponseBuilder response;

    @Autowired
    private UserService service;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listUsers() {
        return response.ok(service.findAll());
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = service.getUser(id);
        return response.ok(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> add(@RequestBody @Validated User user) {
        Long id = service.save(user);
        return response.created("Success.user.added", id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> update(@PathVariable Long id, @RequestBody @Validated User user) {
        service.save(user);
        return response.ok("Success.user.updated");
    }
}
