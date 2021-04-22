package com.event.hosttextservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(HostTextProcessor.class)
public class EdaHosttextserviceApplication {
	private static final Logger logger = LogManager.getLogger(EdaHosttextserviceApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(EdaHosttextserviceApplication.class, args);
		logger.info("The EDA Host Text Application has started...");
	}

}
