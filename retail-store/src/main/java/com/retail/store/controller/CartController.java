package com.retail.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.retail.store.controller.response.ResponseBuilder;
import com.retail.store.dto.CartDto;
import com.retail.store.model.Cart;
import com.retail.store.service.CartService;

@RestController
public class CartController {

    @Autowired
    private ResponseBuilder response;

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/users/{id}/cart", method = RequestMethod.GET)
    public ResponseEntity<CartDto> getCart(@PathVariable Long id) {
        CartDto cart = cartService.getCartByUserId(id);
        return response.ok(cart);
    }
}
