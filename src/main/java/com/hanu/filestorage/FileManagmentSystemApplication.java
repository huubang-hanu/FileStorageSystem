package com.hanu.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FileManagmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagmentSystemApplication.class, args);
	}

}
