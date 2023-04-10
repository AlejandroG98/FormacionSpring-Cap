package com.example.catalogo.application.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.controllers.FilmController;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
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

	@MockBean
	private FilmService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Value
	static class FilmShortMock implements FilmShort {
		int filmId;
		String title;
	}
	
	@Disabled
	@Nested
	class GetOne404 {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			void testGetOne404(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/peliculas/get/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			void testGetOne404(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/peliculas/get/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}
	}

	
}