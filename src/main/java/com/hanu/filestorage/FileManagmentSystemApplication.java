package com.hanu.filestorage;

import com.hanu.filestorage.configuration.FileManamentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({
		FileManamentProperties.class
})
@EnableJpaAuditing
public class FileManagmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagmentSystemApplication.class, args);
	}

}
