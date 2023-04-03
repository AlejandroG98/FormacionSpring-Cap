package com.example.batch;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PersonasBatchConfiguration {
	@Autowired
	JobRepository jobRepository;
	@Autowired
	PlatformTransactionManager transactionManager;
}