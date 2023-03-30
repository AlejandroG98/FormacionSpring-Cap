package com.example.demo1.domains.contracts.repositories;

import java.util.List;

import com.example.demo1.domains.entities.Film;

public interface LanguageShort {

	int getLanguageId();
	String getName();
	List<Film> getFilms();
	List<Film> getFilmsVO();

}
