package com.retail.store.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.exception.NotFoundException;
import com.retail.store.model.ProductCategory;
import com.retail.store.repository.ProductCategoryRepository;

@Component
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    public ProductCategory find(Long id) {
        return repository.findOne(id);
    }

    public void save(ProductCategory category) {
        repository.save(category);
    }

    public void save(Long id, ProductCategory category) {
        ProductCategory existingCategory = getProductCategory(id);
        existingCategory.setCategory(category.getCategory());
        existingCategory.setSalesTaxPercentage(category.getSalesTaxPercentage());
        repository.save(existingCategory);
    }

    public ProductCategory getProductCategory(Long id) {
        ProductCategory category = repository.findOne(id);
        if (category == null) {
            throw new NotFoundException("NotFound.productcategory", null);
        }
        return category;
    }
}
