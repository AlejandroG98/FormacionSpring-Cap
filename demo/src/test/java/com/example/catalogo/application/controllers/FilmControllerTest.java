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
		@Nested
		class OK {
			@Test
			public void testGetAllFilms() {
				List<Film> films = Arrays.asList(new Film(0, "Este es el nombre", "Y esta es la descripción.",  (short) 1994, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY));
				Mockito.when(filmService.getAll()).thenReturn(films);

				List<Film> response = filmService.getAll();
				assertIterableEquals(films, response);

				
				Mockito.verify(filmService, Mockito.times(1)).getAll();
			}
		}

		@Nested
		class KO {

		}
	}

//	@Test
//	public void testGetAllFilms() throws Exception {
//		when(filmService.getAll()).thenReturn(filmList);
//		mockMvc.perform(MockMvcRequestBuilders.get("/peliculas/get").accept(MediaType.APPLICATION_JSON))
//				.andDo(MockMvcResultHandlers.print())
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].filmId", is(1)))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("ACADEMY DINOSAUR")))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", is("description1")));
//	}

}