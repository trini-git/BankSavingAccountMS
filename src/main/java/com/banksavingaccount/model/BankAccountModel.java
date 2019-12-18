//to update the information into the BankAccountMS

package com.banksavingaccount.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bank_account")
public class BankAccountModel {

	@Id
	private String id;

	private String accountNumber;

	private Double amount;

	private String typeAccountNumber;

	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTypeAccountNumber() {
		return typeAccountNumber;
	}

	public void setTypeAccountNumber(String typeAccountNumber) {
		this.typeAccountNumber = typeAccountNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
