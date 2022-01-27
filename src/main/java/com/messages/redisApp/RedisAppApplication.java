package com.messages.redisApp;

import com.messages.redisApp.model.Message;
import com.messages.redisApp.repository.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisAppApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(MessageRepository repository) {
		return (args) -> {
			repository.save(new Message("hola"));
			repository.save(new Message("como va?"));
			repository.save(new Message("todo bien?"));
		};
	}
}
