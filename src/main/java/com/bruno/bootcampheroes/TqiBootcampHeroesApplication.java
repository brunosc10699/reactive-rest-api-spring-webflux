package com.bruno.bootcampheroes;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamoDBRepositories
public class TqiBootcampHeroesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TqiBootcampHeroesApplication.class, args);
	}

}
