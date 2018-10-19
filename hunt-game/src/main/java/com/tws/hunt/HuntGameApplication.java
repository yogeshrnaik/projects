package com.tws.hunt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tws.hunt.service.HuntGameService;

@SpringBootApplication
public class HuntGameApplication implements CommandLineRunner {

    @Autowired
    private HuntGameService huntService;

    public static void main(String[] args) {
        SpringApplication.run(HuntGameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        huntService.play();
    }
}
