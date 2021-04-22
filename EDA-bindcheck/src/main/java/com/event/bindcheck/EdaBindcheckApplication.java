package com.event.bindcheck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(BindCheckProcessor.class)
public class EdaBindcheckApplication {
	private static final Logger logger = LogManager.getLogger(EdaBindcheckApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(EdaBindcheckApplication.class, args);
		logger.info("The EDA Bindcheck Application has started...");
	}

}
