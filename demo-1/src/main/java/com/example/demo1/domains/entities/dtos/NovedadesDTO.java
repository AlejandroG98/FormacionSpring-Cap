package com.example.demo1.domains.entities.dtos;

import java.util.List;

import com.example.demo1.domains.entities.Actor;
import com.example.demo1.domains.entities.Category;
import com.example.demo1.domains.entities.Film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<Film> films;
	private List<Actor> actors;
	private List<Category> categories;
	
}
