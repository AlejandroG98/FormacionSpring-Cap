package com.example.catalogo.application.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.controllers.LanguageController;
import com.example.catalogo.application.controllers.ActorControllerTest.ActorShortMock;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.domains.entities.dtos.LanguageShort;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(LanguageController.class)
public class LanguageControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LanguageService srv;

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

	@Nested
	class getAll {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Aleman", "2,Ingles", "3,Castellano" })
			void testGetAll(int id, String nombre) throws Exception {
				List<LanguageShort> lista = new ArrayList<>(Arrays.asList(new LanguageShortMock(id, nombre)));
				when(srv.getByProjection(LanguageShort.class)).thenReturn(lista);
				mockMvc.perform(get("/idiomas/get").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1, ", "2,123456789012345678901", "3,    " })
			void testGetAll(int id, String nombre) throws Exception {
				if (nombre == null || nombre.length() > 20 || id < 0) {
					mockMvc.perform(get("/idiomas/get")).andExpect(status().is2xxSuccessful());
				}
			}
		}
	}

	// NOTA MENTAL:
	// ERROR No value at JSON path "$.nombre" -> Solución: cambiar por el nombre que
	// esta en su DTO
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
						.andExpect(jsonPath("$.name").value(languageDTO.getName())).andDo(print());
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
						.andExpect(jsonPath("$.name").value(languageDTO.getName())).andDo(print());
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
					mockMvc.perform(get("/idiomas/get/{id}", id)).andExpect(status().isNotFound())
							.andExpect(jsonPath("$.title").value("Not Found")).andDo(print());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			void testGetOne404(int id) throws Exception {
					when(srv.getOne(id)).thenReturn(Optional.empty());
					mockMvc.perform(get("/idiomas/get/{id}", id)).andExpect(status().isNotFound())
							.andExpect(jsonPath("$.title").value("Not Found")).andDo(print());
			}
		}
	}

	@Nested
	class addLanguage {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Guerra", "2,Muerte", "3,Destrucción" })
			void testAddLanguage(int id, String nombre) throws Exception {
				var ele = new Language(id, nombre);
				when(srv.add(ele)).thenReturn(ele);
				mockMvc.perform(post("/idiomas/addLanguage").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(LanguageDTO.from(ele)))
						.param("id", String.valueOf(ele.getLanguageId())).param("name", ele.getName()))
						.andExpect(status().isOk()).andDo(print());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1, ", "-2,", "3,  " })
			void testAddLanguage(int id, String nombre) throws Exception {
				var ele = new Language(id, nombre);
				when(srv.add(ele)).thenReturn(ele);
				mockMvc.perform(post("/idiomas/addLanguage").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(LanguageDTO.from(ele)))
						.param("id", String.valueOf(ele.getLanguageId())).param("name", ele.getName()))
						.andExpect(status().is4xxClientError()).andDo(print());
			}
		}
	}

	@Nested
	class updateLanguage {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Galego", "2,Aragones", "3,Valenciano" })
			void testUpdateLanguage(int languageId, String nombre) throws Exception, Exception {
				Language language = new Language(languageId, nombre);
				mockMvc.perform(put("/idiomas/{id}", languageId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(language))).andExpect(status().is2xxSuccessful())
						.andDo(print());
				verify(srv, times(1)).modify(language);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,        ", "2,-",
					"3,Valencianooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" })
			void testUpdateLanguage(int languageId, String nombre) throws Exception, Exception {
				LanguageDTO languageDto = new LanguageDTO(languageId, nombre);
				Language language = LanguageDTO.from(languageDto);
				when(srv.modify(language)).thenReturn(language);
				mockMvc.perform(put("/idiomas/{id}", languageId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(language))).andExpect(status().is2xxSuccessful())
						.andDo(print());
				verify(srv, times(1)).modify(language);
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
			@CsvSource({ "-1", "-2", "-3" })
			public void testDeleteLanguage(int id) throws Exception {
				if (id < 0) {
					assertThrows(AssertionError.class, () -> {
						mockMvc.perform(delete("/idiomas/{id}", id)).andExpect(status().is5xxServerError());
					});
				}
			}
		}
	}

}
