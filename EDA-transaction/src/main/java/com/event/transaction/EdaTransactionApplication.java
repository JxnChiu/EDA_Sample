package com.event.transaction;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.event.transaction.model.Account;

@SpringBootApplication
@EnableBinding(TransactionProcessor.class)
public class EdaTransactionApplication {

	private static final Logger logger = LogManager.getLogger(EdaTransactionApplication.class.getName());

	public static Map<String, Account> accounts = new HashMap<String, Account>();

	public static void createDemoAccount() {
		accounts.put("jason", new Account("jason", true, 50000d));
		accounts.put("bing", new Account("bing", false, 0d));
		accounts.put("nick", new Account("nick", true, 500d));
	}

	public static void main(String[] args) {
		SpringApplication.run(EdaTransactionApplication.class, args);
		logger.info("The EDA Transaction Application has started...");
		createDemoAccount();
	}

//	BlockingQueue<TransactionEvent> unbounded = new LinkedBlockingQueue<>();

//	@Bean
//	public Supplier<TransactionEvent> supplyTxn() {
//		return () -> unbounded.poll();
//	}

//	@Bean
//	public Supplier<String> supplyTxn() {
//		logger.info("此處不會執行");
//		Supplier<String> txnSupplier = () -> {
//			String helloString = "Hello World: " + System.currentTimeMillis();
//			logger.info("Sending: " + helloString);
//
//			return helloString;
//		};
//		logger.info("此處不會執行");
//		return txnSupplier;
//	}

}
