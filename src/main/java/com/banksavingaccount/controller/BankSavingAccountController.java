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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bank-saving-account")
public class BankSavingAccountController {

	@Autowired
	BankSavingAccountService bankSavingAccountService;
	
	@GetMapping("/find-all")
	public Flux<BankSavingAccountModel> findAll() {

		return bankSavingAccountService.findAll();
	}
	
	@GetMapping("/find-by/{document}")
	public Mono<BankSavingAccountModel> findByDocument(@PathVariable String document) {

		return bankSavingAccountService.findByDocument(document);
	}
	

	@PostMapping("/insert")
	public Mono<BankSavingAccountModel> insertBankSavingAccount(
			@RequestBody BankSavingAccountModel bankSavingAccountModel) {

		return bankSavingAccountService.insertBankSavingAccount(bankSavingAccountModel);

	}

	@PutMapping("/update-retire")
	public Mono<BankSavingAccountModel> updateAmountRetire(
			@RequestBody BankSavingAccountModel bankSavingAccountModel) {
		
		return bankSavingAccountService.updateAmountRetire(bankSavingAccountModel);
	}
	
	@PutMapping("/update-deposite")
	public Mono<BankSavingAccountModel> updateAmountDeposite(
			@RequestBody BankSavingAccountModel bankSavingAccountModel) {
		
		return bankSavingAccountService.updateAmountDeposite(bankSavingAccountModel);
	}

}
