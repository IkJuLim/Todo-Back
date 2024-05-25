package com.limikju.op;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OpApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpApplication.class, args);
	}

}
