package com.example.demo1.domains.entities.dtos;


import com.example.demo1.domains.entities.Film;

import lombok.Value;

@Value
public class FilmShortDTO {
	private int filmId;
	private String title;
	
	public static FilmShortDTO from(Film source) {
		return new FilmShortDTO(source.getFilmId(), source.getTitle());
	}
}
