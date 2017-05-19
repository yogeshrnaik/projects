package com.retail.store.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.dto.ProductDto;
import com.retail.store.exception.NotFoundException;
import com.retail.store.model.Product;
import com.retail.store.model.ProductCategory;
import com.retail.store.model.User;
import com.retail.store.repository.ProductRepository;
import com.retail.store.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User find(Long id) {
        return userRepo.findOne(id);
    }

    public Long save(User user) {
        userRepo.save(user);
        return user.getId();
    }

    public void save(Long id, User user) {
        User existingUser = getUser(id);
        existingUser.setName(user.getName());
        userRepo.save(existingUser);
    }

    public User getUser(Long id) {
        User user = find(id);
        if (user == null) {
            throw new NotFoundException("NotFound.user", null);
        }
        return user;
    }
}
