package com.example.domains.entities.dtos;

import java.util.List;

import com.example.domains.entities.FilmCategory;

// Cojo los atributos "simples" del Actor 
// Sin Timestamp o cosas raras
public interface CategoryShort {

	int getCategoryId();
	String getName();
	List<FilmCategory> getFilmCategories();
	

}
