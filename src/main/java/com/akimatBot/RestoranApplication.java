package com.akimatBot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableJpaRepositories
//@Component
@Slf4j
public class RestoranApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestoranApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

    }



}
