package com.event.quotacheck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(QuotaCheckProcessor.class)
public class EdaQuotacheckApplication {
	private static final Logger logger = LogManager.getLogger(EdaQuotacheckApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(EdaQuotacheckApplication.class, args);
		logger.info("The EDA Balancecheck Application has started...");
	}

}
