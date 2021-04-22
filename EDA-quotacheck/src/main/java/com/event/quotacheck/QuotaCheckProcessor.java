package com.event.quotacheck;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface QuotaCheckProcessor {
	
	String APPLICATIONS_IN = "bind_check_approved";
	String APPROVED_OUT = "quota_check_approved";
	String DECLIEND_OUT = "fail_notification";
	
	@Input(APPLICATIONS_IN)
	SubscribableChannel getBindChecked();
	
	@Output(APPROVED_OUT)
	MessageChannel quotaApproved();
	
	@Output(DECLIEND_OUT)
	MessageChannel quotaDeclined();

}
