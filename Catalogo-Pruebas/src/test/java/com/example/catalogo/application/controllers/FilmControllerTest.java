package com.example.catalogo.application.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmShort;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@ComponentScan(basePackages = "com.example.")
@WebMvcTest(FilmController.class)
public class FilmControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private FilmController filmController;

	@Autowired 
	private FilmService filmService;

	private List<Film> filmList;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
		filmList = new ArrayList<Film>(
				Arrays.asList(new Film(1, "ACADEMIA DINOSAURIO", "Descripción de la peli"), new Film(2, "Pato mareado", "Descripción de la peli")));
	}

	@Test
	public void testGetAllFilms() throws Exception {
		when(filmController.getAllFilms()).thenReturn(filmList);
		mockMvc.perform(MockMvcRequestBuilders.get("/peliculas/get").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", is("ACADEMY DINOSAUR")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", is("description1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Pato mareado")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].description", is("description2")));
	}

	@Test
	public void testGetFilmById() throws Exception {
		when(filmController.getFilmById(anyInt())).thenReturn(filmList.get(0));
		mockMvc.perform(MockMvcRequestBuilders.get("/peliculas/get/1").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", is("ACADEMY DINOSAUR")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", is("description1")));
	}


	
	

}