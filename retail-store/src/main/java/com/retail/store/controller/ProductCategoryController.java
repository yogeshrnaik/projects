package com.retail.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.retail.store.controller.response.ResponseBuilder;
import com.retail.store.dto.ResponseDto;
import com.retail.store.exception.RetailStoreException;
import com.retail.store.model.ProductCategory;
import com.retail.store.service.ProductCategoryService;

@RestController
public class ProductCategoryController {

    @Autowired
    private ResponseBuilder response;

    @Autowired
    private ProductCategoryService service;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<ProductCategory> listCategories() {
        return service.findAll();
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    public ProductCategory get(@PathVariable Long id) {
        ProductCategory cat = service.findOne(id);
        if (cat == null) {
            throw new RetailStoreException("NotFound.productcategory", null);
        }
        return cat;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseDto add(@RequestBody @Validated ProductCategory category) {
        service.save(category);
        return response.info("Success.productcategory.added");
    }

    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody @Validated ProductCategory category) {
        service.save(category);
        return response.info("Success.productcategory.updated");
    }

}
