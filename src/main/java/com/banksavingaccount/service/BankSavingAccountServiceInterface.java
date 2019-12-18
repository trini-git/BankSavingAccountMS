package com.banksavingaccount.service;

import com.banksavingaccount.model.BankSavingAccountModel;

import reactor.core.publisher.Mono;

public interface BankSavingAccountServiceInterface {
	
	Mono<BankSavingAccountModel> insertBankSavingAccount(BankSavingAccountModel bankSavingAccountModel);
	
	Mono<BankSavingAccountModel> findByDocument(String document);
	
	Mono<BankSavingAccountModel> updateAmountBankSavingAccount(BankSavingAccountModel bankSavingAccountModel, String accountNumber, String typeOperation, Double accountNumberAmountr);

}
