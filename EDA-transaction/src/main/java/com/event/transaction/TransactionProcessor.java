package com.event.transaction;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface TransactionProcessor {
	String TxnOut = "txn_http_source";

	String FAIL_IN = "fail_notification";

	String DEDUCTION_IN = "quota_check_approved";
	String DEDUCTION_SUCCESS = "deduction_success";
	String DEDUCTION_FAIL = "fail_notification";

	String TRANSACTION_SUCCESS = "host_text_sended";

	@Output(TxnOut)
	MessageChannel txnOut();

	@Input(FAIL_IN)
	SubscribableChannel failNotice();

	@Input(DEDUCTION_IN)
	SubscribableChannel deductBalance();

	@Output(DEDUCTION_SUCCESS)
	MessageChannel deductionSuccess();

	@Output(DEDUCTION_FAIL)
	MessageChannel deductionFail();

	@Input(TRANSACTION_SUCCESS)
	SubscribableChannel transactionSuccess();

}
