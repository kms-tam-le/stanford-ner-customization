package com.kms.aplus.createTrainedModel;

import com.kms.aplus.createTrainedModel.properties.StorageProperties;
import com.kms.aplus.createTrainedModel.service.trainModel.CreateTrainedModelService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CreateTrainedModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateTrainedModelApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CreateTrainedModelService createTrainedModelService) {
		return (args) -> {
			createTrainedModelService.init();
		};
	}
}
