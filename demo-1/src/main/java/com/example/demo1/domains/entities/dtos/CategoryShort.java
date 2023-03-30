package com.example.demo1.domains.entities.dtos;

import java.util.List;

import com.example.demo1.domains.entities.FilmCategory;

// Cojo los atributos "simples" del Actor 
// Sin Timestamp o cosas raras
public interface CategoryShort {

	int getCategoryId();
	String getName();
	List<FilmCategory> getFilmCategories();
	

}
