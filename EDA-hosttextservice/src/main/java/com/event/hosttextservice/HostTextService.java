package com.event.hosttextservice;

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
public class HostTextService {
	
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	@Autowired
	private HostTextProcessor processor;

	@StreamListener(HostTextProcessor.APPLICATION_IN)
	public void sendHostTxet(TransactionEvent event) {
		logger.info("===發送主機電文===");
		logger.info("Get Txn: {}, Status: {}", event.getUuid(), event.getStatus());
		event.setStatus(Status.HOST_TEXT_SENDED.name());
		logger.info("Send Txn: {}, Status: {}", event.getUuid(), event.getStatus());
		processor.sendSuccess().send(message(event));
	}
	
	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}


}
