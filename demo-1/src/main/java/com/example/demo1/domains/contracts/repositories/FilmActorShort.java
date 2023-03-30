package com.example.demo1.domains.contracts.repositories;

import com.example.demo1.domains.entities.Actor;
import com.example.demo1.domains.entities.Film;

// Cojo los atributos "simples" del Actor 
// Sin Timestamp o cosas raras
public interface FilmActorShort {

	int getId();
	Film getFilm();
	Actor getActor();
		
}
