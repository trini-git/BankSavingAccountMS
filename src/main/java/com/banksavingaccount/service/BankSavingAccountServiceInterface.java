package com.banksavingaccount.service;

import com.banksavingaccount.model.BankSavingAccountModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankSavingAccountServiceInterface {
	
	Mono<BankSavingAccountModel> insertBankSavingAccount(BankSavingAccountModel bankSavingAccountModel);
	Flux<BankSavingAccountModel> findAll();
	Mono<BankSavingAccountModel> findByDocument(String document);		
	Mono<BankSavingAccountModel> updateAmountRetire(BankSavingAccountModel bankSavingAccountModel);	
	Mono<BankSavingAccountModel> updateAmountDeposite(BankSavingAccountModel bankSavingAccountModel);
}
