package com.app;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Book;
import com.github.javafaker.Faker;

@SpringBootApplication
@EnableBinding(Source.class)
@RestController
public class SpringCloudStreamPublisherApplication {

	@Autowired
    private MessageChannel output;

    @GetMapping("/publish")
    public Book publishEvent() {
    	Book book = new Book();
    	Integer i = new Random().nextInt();
    	book.setId(i);
    	Faker faker = new Faker();
    	String name = faker.name().fullName();
    	book.setName(name);
        output.send(MessageBuilder.withPayload(book).build());
        return book;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamPublisherApplication.class, args);
    }

}
