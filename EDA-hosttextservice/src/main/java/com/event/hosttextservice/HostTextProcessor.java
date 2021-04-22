package com.event.hosttextservice;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface HostTextProcessor {
	String APPLICATION_IN = "deduction_success";
	String APPLICATION_OUT = "host_text_sended";
	
	@Input(APPLICATION_IN)
	SubscribableChannel sendHostTxet();
	
	@Output(APPLICATION_OUT)
	MessageChannel sendSuccess();

}
