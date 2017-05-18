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
import com.retail.store.dto.ResponseDto;
import com.retail.store.model.ProductCategory;
import com.retail.store.service.ProductCategoryService;

@RestController
public class ProductCategoryController {

    @Autowired
    private ResponseBuilder response;

    @Autowired
    private ProductCategoryService service;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<ProductCategory>> listCategories() {
        return response.ok(service.findAll());
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductCategory> get(@PathVariable Long id) {
        return response.ok(service.getProductCategory(id));
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> add(@RequestBody @Validated ProductCategory category) {
        service.save(category);
        return response.created("Success.productcategory.added", category.getId());
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> update(@PathVariable Long id, @RequestBody @Validated ProductCategory category) {
        service.save(id, category);
        return response.ok("Success.productcategory.updated");
    }
}
