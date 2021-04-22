package com.event.bindcheck;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.common.Status;
import com.common.event.TransactionEvent;

@Component
public class BindChecker {
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private BindCheckProcessor processor;

	@StreamListener(BindCheckProcessor.APPLICATIONS_IN)
	public void getTxn(TransactionEvent event) {
		logger.info("===綁定檢查===");
		logger.info("Get Txn: {}, Status: {}", event.getUuid(),event.getStatus());
		Boolean binded = event.getAccount().getBinded();

		if (binded) {
			event.setStatus(Status.BIND_CHECK_APPROVED.name());
			processor.bindApproved().send(message(event));
		} else {
			event.setStatus(Status.BIND_CHECK_DECLINED.name());
			event.setFailMessage("綁定檢查未通過，請重新確認帳戶綁定狀況");
			processor.bindDeclined().send(message(event));
		}
		
		logger.info("Send Txn: {}, Status: {}", event.getUuid(),event.getStatus());
	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}
}
