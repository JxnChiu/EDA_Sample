package com.common.event;

import com.event.transaction.model.Account;
import com.event.transaction.model.TransactionRequest;

public class TransactionEvent {

	private String uuid;
	private Account account;
	private TransactionRequest request;
	private String status;
	private String failMessage;

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public TransactionRequest getRequest() {
		return request;
	}

	public void setRequest(TransactionRequest request) {
		this.request = request;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
