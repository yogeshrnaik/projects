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
import com.retail.store.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ResponseBuilder response;

    @Autowired
    private ProductService service;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listProducts() {
        return response.ok(service.findAll());
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product product = service.getProduct(id);
        return response.ok(product);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> add(@RequestBody @Validated ProductDto product) {
        Long id = service.save(product);
        return response.created("Success.product.added", id);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> update(@PathVariable Long id, @RequestBody @Validated ProductDto product) {
        service.save(id, product);
        return response.ok("Success.product.updated");
    }
}
