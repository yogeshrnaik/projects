package com.retail.store;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.retail.store.model.ProductCategory;
import com.retail.store.repository.ProductCategoryRepository;

@SpringBootApplication
public class RetailStoreApplication implements CommandLineRunner {

    @Autowired
    private ProductCategoryRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(RetailStoreApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        repository.save(new ProductCategory(1l, "A", 20.0));
        repository.save(new ProductCategory(2l, "B", 10.0));
        repository.save(new ProductCategory(3l, "C", 0.0));
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}
