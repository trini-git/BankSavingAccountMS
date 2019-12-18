package com.banksavingaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.banksavingaccount.model.BankAccountModel;
import com.banksavingaccount.model.BankSavingAccountModel;
import com.banksavingaccount.repository.BankSavingAccountRepositoryInterface;

import reactor.core.publisher.Mono;

@Service
public class BankSavingAccountService implements BankSavingAccountServiceInterface {

	@Autowired
	WebClient client;

	@Autowired
	BankSavingAccountRepositoryInterface bankSavingAccountRepositoryInterface;

	BankAccountModel bankAccountModel = new BankAccountModel();

	@Override
	public Mono<BankSavingAccountModel> insertBankSavingAccount(BankSavingAccountModel bankSavingAccountModel) {

		bankAccountModel.setAccountNumber(bankSavingAccountModel.getAccountNumber());
		bankAccountModel.setAmount(bankSavingAccountModel.getAmount());
		bankAccountModel.setTypeAccountNumber(bankSavingAccountModel.getTypeAccountNumber());
		bankAccountModel.setStatus(bankSavingAccountModel.getStatus());

		insertBankAccount(bankAccountModel).subscribe();
		return bankSavingAccountRepositoryInterface.save(bankSavingAccountModel);
	}

	@Override
	public Mono<BankSavingAccountModel> findByDocument(String document) {

		return bankSavingAccountRepositoryInterface.findByDocument(document);

	}

	/* to update the amount depends of BankAccount */
	@Override
	public Mono<BankSavingAccountModel> updateAmountBankSavingAccount(BankSavingAccountModel bankSavingAccountModel,
			String accountNumber, String typeOperation, Double accountNumberAmount) {

		return bankSavingAccountRepositoryInterface.findByAccountNumber(accountNumber)
				.flatMap(newbankSavingAccountModel -> {
					double currentAmount = newbankSavingAccountModel.getAmount();

					if (typeOperation.equalsIgnoreCase("D")) {
						newbankSavingAccountModel.setAmount(currentAmount + bankSavingAccountModel.getAmount());
						return bankSavingAccountRepositoryInterface.save(newbankSavingAccountModel).flatMap(account -> {
							bankAccountModel.setAmount(account.getAmount());
							updateAmountBankAccount(bankAccountModel, accountNumber, accountNumberAmount).subscribe();
							return Mono.just(account);
						});

					} else if (typeOperation.equalsIgnoreCase("R")) {
						if (currentAmount >= bankSavingAccountModel.getAmount()) {
							newbankSavingAccountModel.setAmount(currentAmount - bankSavingAccountModel.getAmount());
							return bankSavingAccountRepositoryInterface.save(newbankSavingAccountModel)
									.flatMap(account -> {
										bankAccountModel.setAmount(account.getAmount());
										updateAmountBankAccount(bankAccountModel, accountNumber, accountNumberAmount)
												.subscribe();
										return Mono.just(account);
									});
						} else {
							return Mono.empty();
						}

					}
					return Mono.empty();
				});
	}

	/* Microservice that connects insertBankSavingAccount */
	public Mono<BankAccountModel> insertBankAccount(BankAccountModel bankAccountModel) {
		return client.post().uri("/insert").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(bankAccountModel))
				.retrieve().bodyToMono(BankAccountModel.class);
	}

	/* Microservice that connects insertBankSavingAccount */
	public Mono<BankAccountModel> updateAmountBankAccount(BankAccountModel bankAccountModel, String accountNumber,
			Double amount) {
		return client.put().uri("/update-amount/" + accountNumber + "/" + amount)
				.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
				.syncBody(bankAccountModel).retrieve().bodyToMono(BankAccountModel.class);
	}

}
