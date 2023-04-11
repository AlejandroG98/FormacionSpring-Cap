package com.example.catalogo.application.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.application.controllers.FilmController;
import com.example.catalogo.application.controllers.ActorControllerTest.ActorShortMock;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmShort;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

//@ComponentScan(basePackages = "com.example")
@WebMvcTest(FilmController.class)
public class FilmControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FilmService filmService;

	@MockBean
	private FilmRepository filmRepository;

	private List<Film> filmList;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	class getAll {
		@ParameterizedTest
		@CsvSource({
				"0,La película 1,Esta es la descripción de la película 1,1895,Inglés,Castellano,2,2.99,120,20.99,G",
				"1,La película 2,Esta es la descripción de la película 2,2000,Castellano,Catalán,6,5.01,180,0.99,PG",
				"3,La película 3,Esta es la descripción de la película 3,2020,Catalán,Italiano,4,3.01,121,1.99,R" })
		public void testGetAllFilms(int filmId, String title, String description, @Min(1895) Short releaseYear,
				String language, String languageVO, @Positive Byte rentalDuration,
				@Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
				@Positive Integer length,
				@DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost,
				String rating) {
			Language languageAux = new Language(1, language);
			Language languageAux2 = new Language(2, languageVO);
			Film.Rating ratingAux = Film.Rating.getEnum(rating);
			List<Film> films = Arrays.asList(new Film(filmId, title, description, releaseYear, languageAux,
					languageAux2, rentalDuration, rentalRate, length, replacementCost, ratingAux));
			Mockito.when(filmService.getAll()).thenReturn(films);
			List<Film> response = filmService.getAll();
			assertIterableEquals(films, response);
			Mockito.verify(filmService, Mockito.times(1)).getAll();
		}
	}
}