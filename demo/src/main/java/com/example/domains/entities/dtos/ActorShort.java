package com.example.domains.entities.dtos;

import org.springframework.beans.factory.annotation.Value;

// Cojo los atributos "simples" del Actor 
// Sin Timestamp o cosas raras
public interface ActorShort {

	int getActorId();
	
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getNombre();
	

}
