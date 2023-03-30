package com.example.demo1.domains.entities.dtos;

import com.example.demo1.domains.entities.Actor;
import com.example.demo1.domains.entities.Film;
import com.example.demo1.domains.entities.FilmActor;
import com.example.demo1.domains.entities.FilmActorPK;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
@Value @AllArgsConstructor @NoArgsConstructor
public class FilmActorDTO {
	@JsonProperty("id")
	private FilmActorPK id;

	@JsonProperty("actor")
	private Actor actor;

	@JsonProperty("pelicula")
	private Film film;
	
	
	public static FilmActorDTO from1(FilmActor target) {
		return new FilmActorDTO(target.getId(),target.getActor(), target.getFilm());
	}
	
	public static FilmActor from(FilmActorDTO target) {
		return new FilmActor(target.getId(),target.getActor(), target.getFilm());
	}
	
}
