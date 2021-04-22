package com.event.transaction;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.Status;
import com.common.event.TransactionEvent;
import com.event.transaction.model.Account;
import com.event.transaction.model.TransactionRequest;

@Component
@RestController
public class TransactionService {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private TransactionProcessor processor;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void sendTransaction(@RequestBody TransactionRequest request) {
		TransactionEvent event = new TransactionEvent();
		event.setUuid(UUID.randomUUID().toString());
		event.setAccount(EdaTransactionApplication.accounts.get(request.getUserId()));
		event.setStatus(Status.PENDING.name());
		event.setRequest(request);
		logger.info("===收到交易請求===");
		logger.info("Send Txn: {}, Status: {}", event.getUuid(), event.getStatus());
		processor.txnOut().send(message(event));
	};

	@StreamListener(TransactionProcessor.DEDUCTION_IN)
	public void deductBalance(TransactionEvent event) {
		logger.info("===進行扣款流程===");
		logger.info("Get Txn: {}, Status: {}", event.getUuid(), event.getStatus());

		Double price = event.getRequest().getPrice();
		Account account = EdaTransactionApplication.accounts.get(event.getAccount().getId());
		Double balance = account.getBalance();

		logger.info("扣款前: {}", account);
		
		if (price <= balance) {
			balance -= price;
			account.setBalance(balance);
			EdaTransactionApplication.accounts.put(event.getAccount().getId(), account);
			event.setStatus(Status.DEDUCTION_SUCCESS.name());
			processor.deductionSuccess().send(message(event));
		} else {
			event.setStatus(Status.DEDUCTION_FAIL.name());
			event.setFailMessage("帳戶餘額不足");
			processor.deductionFail().send(message(event));
		}
		
		logger.info("扣款後: {}", account);
		logger.info("Send Txn: {}, Status: {}", event.getUuid(),event.getStatus());
	}
	
	@StreamListener(TransactionProcessor.TRANSACTION_SUCCESS)
	public void finishTransaction(TransactionEvent event) {
		logger.info("===完成交易===");
		logger.info("Get Txn: {}, Status: {}", event.getUuid(), event.getStatus());
		event.setStatus(Status.TRANSACTION_SUCCESS.name());
		logger.info("Send Txn: {}, Status: {}", event.getUuid(), event.getStatus());
	}

	@StreamListener(TransactionProcessor.FAIL_IN)
	public void failNotice(TransactionEvent event) {
		logger.info("===交易失敗提示===");
		logger.info("Get Txn: {}, Status: {}", event.getUuid(), event.getStatus());
		logger.warn("交易失敗: {}, {}", event.getUuid(), event.getFailMessage());
	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}

}
