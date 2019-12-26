package com.banksavingaccount.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bank_saving_account")
public class BankSavingAccountModel {
	
	@Id
	private String id;
	
	private String accountNumber;
	
	private Double amount;
	
	private String type;
	
	private String status;
	
	private List<BsaPersonalClientModel> bsaPersonalClientModel;
	
	private List<BsaSigner> bsaSigner;
	
	public List<BsaSigner> getBsaSigner() {
		return bsaSigner;
	}
	public void setBsaSigner(List<BsaSigner> bsaSigner) {
		this.bsaSigner = bsaSigner;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BsaPersonalClientModel> getBsaPersonalClientModel() {
		return bsaPersonalClientModel;
	}
	public void setBsaPersonalClientModel(List<BsaPersonalClientModel> bsaPersonalClientModel) {
		this.bsaPersonalClientModel = bsaPersonalClientModel;
	}		
}
