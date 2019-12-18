package com.banksavingaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.banksavingaccount.model.BankAccountModel;
import com.banksavingaccount.model.BankSavingAccountModel;
import com.banksavingaccount.service.BankSavingAccountService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bank-saving-account")
public class BankSavingAccountController {

	WebClient client = WebClient.builder().baseUrl("http://localhost:8007/bank-account")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

	@Autowired
	BankSavingAccountService bankSavingAccountService;

	@PostMapping("/insert")
	public Mono<BankSavingAccountModel> insertBankSavingAccount(
			@RequestBody BankSavingAccountModel bankSavingAccountModel) {

		return bankSavingAccountService.insertBankSavingAccount(bankSavingAccountModel);

	}

	@GetMapping("/get/{document}")
	public Mono<BankSavingAccountModel> getByDocument(@PathVariable String document) {

		return bankSavingAccountService.findByDocument(document);
	}

	@PutMapping("/update-amount/{accountNumber}/{typeOperation}/{accountNumberAmount}")
	public Mono<BankSavingAccountModel> updateAmountBankSavingAccount(
			@RequestBody BankSavingAccountModel bankSavingAccountModel, @PathVariable String accountNumber,
			@PathVariable String typeOperation, @PathVariable Double accountNumberAmount) {
		
		return bankSavingAccountService.updateAmountBankSavingAccount(bankSavingAccountModel, accountNumber,
				typeOperation, accountNumberAmount);
	}

}
