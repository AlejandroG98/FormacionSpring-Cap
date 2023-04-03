package com.example.catalogo.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.Film.Rating;

class FilmDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	List<String> filmActorList = new ArrayList<String>();
	List<String> filmCategoryList = new ArrayList<String>();

	@Test
	void testFromFilm() {
		var film = new FilmDTO(0, "The revenge of the test part 3", "Description of the movie", 60,
				Rating.GENERAL_AUDIENCES, new Short("2023"), (byte) 5, new BigDecimal(10.0), new BigDecimal(30),
				new Language(1), new Language(2), filmActorList, filmCategoryList);
		var filmDTO = FilmDTO.from(film);
		assertEquals(Film.class, filmDTO.getClass());

		assertAll("Attributes", () -> assertEquals(0, filmDTO.getFilmId()),
				() -> assertEquals("Description of the movie", filmDTO.getDescription()),
				() -> assertEquals(60, filmDTO.getLength()),
				() -> assertEquals(Rating.GENERAL_AUDIENCES, filmDTO.getRating()),
				() -> assertEquals(new Short("2023"), filmDTO.getReleaseYear()),
				() -> assertEquals((byte) 5, filmDTO.getRentalDuration()),
				() -> assertEquals(new BigDecimal(10.0), filmDTO.getRentalRate()),
				() -> assertEquals(new BigDecimal(30), filmDTO.getReplacementCost()),
				() -> assertEquals("The revenge of the test part 3", filmDTO.getTitle()),
				() -> assertEquals(new Language(1), filmDTO.getLanguage()),
				() -> assertEquals(new Language(2), filmDTO.getLanguageVO()));
	}

	@Test
	void testFromFilmDTO() {
		var film = new Film(0, "The revenge of the test part 3", "Description of the movie", new Short("2023"),
				new Language(1), new Language(2), (byte) 5, new BigDecimal(10.0), 60, new BigDecimal(30),
				Rating.GENERAL_AUDIENCES);
		var filmDTO = FilmDTO.from(film);
		assertEquals(Film.class, film.getClass());
		assertAll("Attributes", () -> assertEquals(0, filmDTO.getFilmId()),
				() -> assertEquals("Description of the movie", filmDTO.getDescription()),
				() -> assertEquals(60, filmDTO.getLength()),
				() -> assertEquals(Rating.GENERAL_AUDIENCES, filmDTO.getRating()),
				() -> assertEquals(new Short("2023"), filmDTO.getReleaseYear()),
				() -> assertEquals((byte) 5, filmDTO.getRentalDuration()),
				() -> assertEquals(new BigDecimal(10.0), filmDTO.getRentalRate()),
				() -> assertEquals(new BigDecimal(30), filmDTO.getReplacementCost()),
				() -> assertEquals("The revenge of the test part 3", filmDTO.getTitle()), // changed to correct title
				() -> assertEquals(new Language(1), filmDTO.getLanguage()),
				() -> assertEquals(new Language(2), filmDTO.getLanguageVO()),
				() -> assertEquals(filmActorList, filmDTO.getActors()),
				() -> assertEquals(filmCategoryList, filmDTO.getCategories()));
	}

}