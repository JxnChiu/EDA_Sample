package com.event.quotacheck;

import java.util.HashMap;
import java.util.Map;

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
public class QuotaChecker {
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	
	private static final Map<String, Double> typeQuotaLimitations = new HashMap<String, Double>();
	
	static {
		typeQuotaLimitations.put("A", 10000D);
		typeQuotaLimitations.put("B", 30000D);
		typeQuotaLimitations.put("C", 50000D);
	}
	
	@Autowired
	private QuotaCheckProcessor processor;

	@StreamListener(QuotaCheckProcessor.APPLICATIONS_IN)
	public void getBindChecked(TransactionEvent event) {
		logger.info("===額度檢查===");
		logger.info("Get Txn: {}, Status: {}", event.getUuid(),event.getStatus());
		
		String type = event.getRequest().getType();
		Double price = event.getRequest().getPrice();

		if (price <= typeQuotaLimitations.get(type)) {
			event.setStatus(Status.QUOTA_CHECK_APPROVED.name());
			processor.quotaApproved().send(message(event));
		} else {
			event.setStatus(Status.QUOTA_CHECK_DECLINED.name());
			event.setFailMessage("額度檢查未通過，請重新確認交易種類額度上限");
			processor.quotaDeclined().send(message(event));
		}
		
		logger.info("Send Txn: {}, Status: {}", event.getUuid(),event.getStatus());
	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}
}
