package com.event.transaction.model;

public class Account {
	
	public Account() {
		super();
	}

	public Account(String id, Boolean binded, Double balance) {
		super();
		this.id = id;
		this.binded = binded;
		this.balance = balance;
	}

	private String id;
	private Boolean binded;
	private Double balance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getBinded() {
		return binded;
	}

	public void setBinded(Boolean binded) {
		this.binded = binded;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(id)
				.append("'s Account Balacne: ")
				.append(balance)
				.toString();
	}

}
