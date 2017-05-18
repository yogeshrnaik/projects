package com.retail.store.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.dto.ProductDto;
import com.retail.store.exception.NotFoundException;
import com.retail.store.model.Product;
import com.retail.store.model.ProductCategory;
import com.retail.store.repository.ProductRepository;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductCategoryService categoryService;

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product find(Long id) {
        return productRepo.findOne(id);
    }

    public Long save(ProductDto productDto) {
        ProductCategory category = categoryService.getProductCategory(productDto.getCategoryId());

        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.setCategory(category);
        productRepo.save(product);

        return product.getId();
    }

    public void save(Long id, ProductDto productDto) {
        ProductCategory category = categoryService.getProductCategory(productDto.getCategoryId());
        Product existingProduct = getProduct(id);

        BeanUtils.copyProperties(productDto, existingProduct);
        existingProduct.setCategory(category);

        productRepo.save(existingProduct);
    }

    public Product getProduct(Long id) {
        Product product = find(id);
        if (product == null) {
            throw new NotFoundException("NotFound.product", null);
        }
        return product;
    }
}
