package com.banksavingaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.banksavingaccount.model.BankAccountModel;
import com.banksavingaccount.model.BankSavingAccountModel;
import com.banksavingaccount.model.BsaPersonalClientModel;
import com.banksavingaccount.repository.BankSavingAccountRepositoryInterface;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankSavingAccountService implements BankSavingAccountServiceInterface {

	@Autowired
	BankSavingAccountRepositoryInterface bankSavingAccountRepositoryInterface;

	BankAccountModel bankAccountModel = new BankAccountModel();
	
	@Autowired
	WebClient client;
	
	/* Microservice that connects insertBankSavingAccount */
	public Mono<BankAccountModel> insertBankAccount(BankAccountModel bankAccountModel) {
		return client.post()
				.uri("/insert")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(bankAccountModel))
				.retrieve()
				.bodyToMono(BankAccountModel.class);
	}
	
	/* Microservice that connects insertBankSavingAccount */
	public Mono<BankAccountModel> updateAmount(BankAccountModel bankAccountModel) {
		return client.put()
				.uri("/update-amount")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(bankAccountModel)
				.retrieve()
				.bodyToMono(BankAccountModel.class)
				.switchIfEmpty(Mono.empty());
	}

	@Override
	public Mono<BankSavingAccountModel> insertBankSavingAccount(BankSavingAccountModel bankSavingAccountModel) {
		
				
		bankAccountModel.setAccountNumber(bankSavingAccountModel.getAccountNumber());
		bankAccountModel.setAmount(bankSavingAccountModel.getAmount());
		bankAccountModel.setType(bankSavingAccountModel.getType());
		bankAccountModel.setStatus(bankSavingAccountModel.getStatus());

		insertBankAccount(bankAccountModel).subscribe();
		return bankSavingAccountRepositoryInterface.save(bankSavingAccountModel);
	}

	@Override
	public Mono<BankSavingAccountModel> findByDocument(String document) {

		return bankSavingAccountRepositoryInterface.findByDocument(document);

	}
	
	@Override
	public Mono<BankSavingAccountModel> updateAmountRetire(BankSavingAccountModel newBankSavingAccountModel) {
		
		return bankSavingAccountRepositoryInterface.findByAccountNumber(newBankSavingAccountModel.getAccountNumber())			
			.flatMap(oldBankSavingAccountModel -> {
				
				if (newBankSavingAccountModel.getAmount() <= oldBankSavingAccountModel.getAmount()) {
					
					oldBankSavingAccountModel.setAmount(oldBankSavingAccountModel.getAmount() - newBankSavingAccountModel.getAmount());
					bankAccountModel.setAccountNumber(oldBankSavingAccountModel.getAccountNumber());
					bankAccountModel.setAmount(oldBankSavingAccountModel.getAmount());				
					updateAmount(bankAccountModel).subscribe();
					
					return bankSavingAccountRepositoryInterface.save(oldBankSavingAccountModel);
				}
				
				return Mono.empty();
				
				});
	}
	
	@Override
	public Mono<BankSavingAccountModel> updateAmountDeposite(BankSavingAccountModel newBankSavingAccountModel) {
		
		return bankSavingAccountRepositoryInterface.findByAccountNumber(newBankSavingAccountModel.getAccountNumber())			
			.flatMap(oldBankSavingAccountModel -> {
				oldBankSavingAccountModel.setAmount(oldBankSavingAccountModel.getAmount() + newBankSavingAccountModel.getAmount());
				
				bankAccountModel.setAccountNumber(oldBankSavingAccountModel.getAccountNumber());
				bankAccountModel.setAmount(oldBankSavingAccountModel.getAmount());				
				updateAmount(bankAccountModel).subscribe();
				
				return bankSavingAccountRepositoryInterface.save(oldBankSavingAccountModel);
				});
	}



	@Override
	public Flux<BankSavingAccountModel> findAll() {
		
		return bankSavingAccountRepositoryInterface.findAll();
	}

}
