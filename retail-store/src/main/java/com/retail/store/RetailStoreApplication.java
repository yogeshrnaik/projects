package com.retail.store;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.retail.store.dto.CartUpdateDto;
import com.retail.store.model.Product;
import com.retail.store.model.ProductCategory;
import com.retail.store.model.User;
import com.retail.store.repository.ProductCategoryRepository;
import com.retail.store.repository.ProductRepository;
import com.retail.store.repository.UserRepository;
import com.retail.store.service.CartService;

@SpringBootApplication
public class RetailStoreApplication implements CommandLineRunner {

    @Autowired
    private ProductCategoryRepository categoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartService cartService;

    public static void main(String[] args) {
        SpringApplication.run(RetailStoreApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadSampleData();
    }

    private void loadSampleData() {
        ProductCategory cat1 = new ProductCategory(1l, "A", 10.0);
        ProductCategory cat2 = new ProductCategory(2l, "B", 20.0);
        ProductCategory cat3 = new ProductCategory(3l, "C", 0.0);

        categoryRepo.save(cat1);
        categoryRepo.save(cat2);
        categoryRepo.save(cat3);

        Product product1 = new Product(1l, "Product 1A", 10.0, cat1);
        Product product2 = new Product(2l, "Product 2B", 20.0, cat2);
        productRepo.save(product1);
        productRepo.save(product2);
        productRepo.save(new Product(3l, "Product 3C", 30.0, cat3));

        User user1 = new User("Yogesh");
        User user2 = new User("John");
        userRepo.save(user1);
        userRepo.save(user2);

        cartService.updateCart(new CartUpdateDto(user1.getId(), 1l, 1));
        cartService.updateCart(new CartUpdateDto(user1.getId(), 2l, 2));
        cartService.updateCart(new CartUpdateDto(user1.getId(), 3l, 3));

        cartService.updateCart(new CartUpdateDto(user2.getId(), 1l, 3));
        cartService.updateCart(new CartUpdateDto(user2.getId(), 2l, 2));
        cartService.updateCart(new CartUpdateDto(user2.getId(), 3l, 1));
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}
