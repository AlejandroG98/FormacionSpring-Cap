package com.example.catalogo.application.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.controllers.LanguageController;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.domains.entities.dtos.LanguageShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(LanguageController.class)
public class LanguageControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LanguageService srv;

	private final LanguageController controller = new LanguageController();

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Value
	static class LanguageShortMock implements LanguageShort {
		int languageId;
		String name;
	}
	
	// NOTA MENTAL:
	// ERROR No value at JSON path "$.nombre" -> Solución: cambiar por el nombre que esta en su DTO
	@Nested
	class oneLanguage {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Castellano", "2,Catalán", "3,Euskera" })
			void testGetOneLanguage(int id, String nombre) throws Exception {
				var language = new Language(id, nombre);
				var languageDTO = LanguageDTO.from(language);
				when(srv.getOne(id)).thenReturn(Optional.of(language));
				mockMvc.perform(get("/idiomas/get/{id}", id)).andExpect(status().isOk())
						.andExpect(jsonPath("$.id").value(languageDTO.getLanguageId()))
						.andExpect(jsonPath("$.name").value(languageDTO.getName()))
						.andDo(print());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,C", "2,", "-3,E" })
			void testGetOneLanguage(int id, String nombre) throws Exception {
				var language = new Language(id, nombre);
				var languageDTO = LanguageDTO.from(language);
				when(srv.getOne(id)).thenReturn(Optional.of(language));
				mockMvc.perform(get("/idiomas/get/{id}", id)).andExpect(status().isOk())
						.andExpect(jsonPath("$.id").value(languageDTO.getLanguageId()))
						.andExpect(jsonPath("$.name").value(languageDTO.getName()))
						.andDo(print());
			}
		}
	}
	
	@Nested
	class GetOne404 {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			void testGetOne404(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/idiomas/get/{id}", id))
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
				mockMvc.perform(get("/idiomas/get/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}
	}
	
	@Nested
	class deleteLanguage {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testDeleteLanguage(int id) throws Exception {
				mockMvc.perform(delete("/idiomas/{id}", id)).andExpect(status().isOk()).andDo(print());
				verify(srv, times(1)).deleteById(id);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-55", "-3" })
			public void testDeleteLanguage(int id) throws Exception {
				mockMvc.perform(delete("/idiomas/{id}", id)).andExpect(status().isOk()).andDo(print());
				verify(srv, times(1)).deleteById(id);
			}
		}
	}

}
