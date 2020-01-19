package com.example.demo.model;

import com.example.demo.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDataBase {

    @Bean
    CommandLineRunner initDataBase(ItemRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Item("T-Shirt", 15, "t-shirt-123QWE")));
            log.info("Preloading " + repository.save(new Item("Shoes", 17, "shoes-123QWE")));
        };
    }
}
