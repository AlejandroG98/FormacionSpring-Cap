package com.example.demo1.domains.entities.dtos;

import java.util.List;

import com.example.demo1.domains.entities.Category;
import com.example.demo1.domains.entities.FilmCategory;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
@Value @AllArgsConstructor @NoArgsConstructor
public class CategoryDTO {
	@JsonProperty("id")
	private int categoryId;
	
	@JsonProperty("nombre")
	private String name;
	
	@JsonProperty("categoriaPeliculas")
	private List<FilmCategory> filmCategories;
	
	public static CategoryDTO from(Category target) {
		return new CategoryDTO(target.getCategoryId(),target.getName(),target.getFilmCategories());
	}
	
	public static Category from(CategoryDTO target) {
		return new Category(target.getCategoryId(),target.getName(),target.getFilmCategories());
	}
	
}
