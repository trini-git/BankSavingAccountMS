package com.banksavingaccount.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.banksavingaccount.model.BankSavingAccountModel;

import reactor.core.publisher.Mono;

@Repository
public interface BankSavingAccountRepositoryInterface extends ReactiveMongoRepository<BankSavingAccountModel, String>{
	
	@Query(value="{'bsaPersonalClientModel.document' : ?0}", fields="{ 'bsaPersonalClientModel.document' : 1}")
	Mono<BankSavingAccountModel> findByDocument(String document);
	
	Mono<BankSavingAccountModel> findByAccountNumber(String accountNumber);
}
