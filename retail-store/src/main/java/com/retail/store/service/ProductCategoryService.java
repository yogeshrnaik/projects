package com.retail.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.model.ProductCategory;
import com.retail.store.repository.ProductCategoryRepository;

@Component
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    public ProductCategory findOne(Long id) {
        return repository.findOne(id);
    }

    public void save(ProductCategory category) {
        repository.save(category);
    }
}
