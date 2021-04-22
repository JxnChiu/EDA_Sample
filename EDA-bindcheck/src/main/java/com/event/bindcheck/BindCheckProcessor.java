package com.event.bindcheck;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface BindCheckProcessor {
	String APPLICATIONS_IN = "txn_http_source";
	String APPROVED_OUT = "bind_check_approved";
	String DECLIEND_OUT = "fail_notification";
	
	@Input(APPLICATIONS_IN)
	SubscribableChannel getTxn();
	
	@Output(APPROVED_OUT)
	MessageChannel bindApproved();
	
	@Output(DECLIEND_OUT)
	MessageChannel bindDeclined();
}
