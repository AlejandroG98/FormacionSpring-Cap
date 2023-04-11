package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Value;

@Value
public class FilmDTO {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("titulo")
	private String title;
	@JsonProperty("descripcion")
	private String description;
	@JsonProperty("duracion")
	private int length;
	@JsonProperty("valoracion")
	private Rating rating;
	@JsonProperty("release_year")
	private Short releaseYear;
	@JsonProperty("rental_duration")
	private byte rentalDuration;
	@JsonProperty("rental_rate")
	private BigDecimal rentalRate;
	@JsonProperty("replacement_cost")
	private BigDecimal replacementCost;
	@JsonProperty("lenguaje")
	@JsonIgnore
	private Language language;
	@JsonProperty("vo")
	@JsonIgnore
	private Language languageVO;
	@JsonProperty("actors")
	@JsonIgnore
	private List<String> actors;
	@JsonProperty("categories")
	@JsonIgnore
	private List<String> categories;
	
	public static FilmDTO from(Film target) {
		return new FilmDTO(target.getFilmId(), 
				target.getTitle(), 
				target.getDescription(), 
				target.getLength(), 
				target.getRating(), 
				target.getReleaseYear(), 
				target.getRentalDuration(), 
				target.getRentalRate(), 
				target.getReplacementCost(),
				target.getLanguage() == null ? null : target.getLanguage(),
				target.getLanguageVO() == null ? null : target.getLanguageVO(),
				target.getActors().stream().map(item-> item.getFirstName() + " " + item.getLastName()).sorted().toList(),
				target.getCategories().stream().map(item->item.getName()).sorted().toList()
				);
	}
	
	public static Film from(FilmDTO target) {
		return new Film(target.getFilmId(), target.getTitle(), target.getDescription(), target.getReleaseYear(),target.getLanguage(), target.getLanguageVO(), target.getRentalDuration(),target.getRentalRate(), target.getLength(), target.getReplacementCost(),target.getRating());
	}
	
	public static Film toEntity(FilmDTO filmDTO) {
	    Film film = new Film();
	    film.setFilmId(filmDTO.getFilmId());
	    film.setTitle(filmDTO.getTitle());
	    film.setDescription(filmDTO.getDescription());
	    film.setLength(filmDTO.getLength());
	    film.setRating(filmDTO.getRating());
	    film.setReleaseYear(filmDTO.getReleaseYear());
	    film.setRentalDuration(filmDTO.getRentalDuration());
	    film.setRentalRate(filmDTO.getRentalRate());
	    film.setReplacementCost(filmDTO.getReplacementCost());
	    film.setLanguage(filmDTO.getLanguage());
	    film.setLanguageVO(filmDTO.getLanguageVO());
	    
	    List<Actor> filmActors = new ArrayList<>();
	    for (String actorName : filmDTO.getActors()) {
	        Actor actor = new Actor();
	        actor.setFirstName(actorName);
	        FilmActor filmActor = new FilmActor();
	        filmActor.setActor(actor);
	        filmActor.setFilm(film);
	        filmActors.addAll((Collection<? extends Actor>) filmActor);
	    }
	    film.setActors(filmActors);
	    
	    List<Category> filmCategories = new ArrayList<>();
	    for (String categoryName : filmDTO.getCategories()) {
	        Category category = new Category();
	        category.setName(categoryName);
	        FilmCategory filmCategory = new FilmCategory();
	        filmCategory.setCategory(category);
	        filmCategory.setFilm(film);
	        filmCategories.addAll((Collection<? extends Category>) filmCategory);
	    }
	    film.setCategories(filmCategories);
	    
	    return film;
	}

}