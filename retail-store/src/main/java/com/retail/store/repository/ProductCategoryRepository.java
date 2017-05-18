package com.retail.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.retail.store.model.ProductCategory;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
