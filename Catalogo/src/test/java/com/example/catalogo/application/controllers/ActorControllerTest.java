package com.example.catalogo.application.controllers;

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
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.application.controllers.ActorController;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorController.class)
public class ActorControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
		String apellido;
	}

	@Nested
	class getAll {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Manolo,Garcia", "2,Benito,Fontanero", "3,Manuel,Jamon" })
			void testGetAll(int id, String nombre, String apellido) throws Exception {
				List<ActorShort> lista = new ArrayList<>(Arrays.asList(new ActorShortMock(id, nombre, apellido)));
				when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
				try {
					mockMvc.perform(get("/actores/get").accept(MediaType.APPLICATION_JSON))
							.andExpectAll(status().isOk());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,    ,Garciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					"2,Benitooooooooooooooooooooooooooooooooooooooooooooooooooo,    ", "3,    ,      " })
			void testGetAll(int id, String nombre, String apellido) throws Exception {
				List<ActorShort> lista = new ArrayList<>(Arrays.asList(new ActorShortMock(id, nombre, apellido)));
				when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
				try {
					mockMvc.perform(get("/actores/get").accept(MediaType.APPLICATION_JSON))
							.andExpectAll(status().is4xxClientError());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	@Nested
	class oneActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Manolo,Garcia", "2,Benito,Fontanero", "3,Manuel,Jamon" })
			void testGetOneActor(int id, String nombre, String apellido) throws Exception {
				var actor = new Actor(id, nombre, apellido);
				var actorDTO = ActorDTO.from(actor);
				when(srv.getOne(id)).thenReturn(Optional.of(actor));
				try {
					mockMvc.perform(get("/actores/get/{id}", id)).andExpect(status().isOk())
							.andExpect(jsonPath("$.id").value(actorDTO.getActorId()))
							.andExpect(jsonPath("$.nombre").value(actorDTO.getFirstName()))
							.andExpect(jsonPath("$.apellidos").value(actorDTO.getLastName())).andDo(print());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			void testGetOneActor(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				try {
					mockMvc.perform(get("/actores/get/{id}", id)).andExpect(status().is4xxClientError())
							.andExpect(jsonPath("$.title").value("Not Found"));
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	@Nested
	class GetOne404 {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			void testGetOne404(int id) throws Exception  {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				try {
					mockMvc.perform(get("/actores/get/{id}", id))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.title").value("Not Found"))
					    .andDo(print());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			void testGetOne404(int id) throws Exception  {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				try {
					mockMvc.perform(get("/actores/get/{id}", id))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.title").value("Not Found"))
					    .andDo(print());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	@Nested
	class addActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			void testAddActor(int id, String nombre, String apellido)
					throws DuplicateKeyException, InvalidDataException, Exception {
				var ele = new Actor(id, nombre, apellido);
				try {
					when(srv.add(ele)).thenReturn(ele);
				} catch (DuplicateKeyException | InvalidDataException e) {
					e.getMessage();
				}
				try {
					mockMvc.perform(post("/actores/addActor").contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
							.param("firstname", ele.getFirstName()).param("lastname", ele.getLastName()))
							.andExpect(status().isOk()).andDo(print());
				} catch (JsonProcessingException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1,,", "-2,,b", "-3,a," })
			void testAddActor(int id, String nombre, String apellido) throws Exception {
				var ele = new Actor(id, nombre, apellido);
				try {
					when(srv.add(ele)).thenReturn(ele);
				} catch (DuplicateKeyException e) {
					e.getMessage();
				} catch (InvalidDataException e) {
					e.getMessage();
				}
				try {
					mockMvc.perform(post("/actores/addActor").contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
							.param("firstname", ele.getFirstName()).param("lastname", ele.getLastName()))
							.andExpect(status().isBadRequest()).andDo(print());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	@Nested
	class updateActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			void testUpdateActor(int actorId, String nombre, String apellido) {
				Actor actor = new Actor(actorId, nombre, apellido);
				try {
					mockMvc.perform(put("/actores/{id}", actorId).contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(actor))).andExpect(status().is2xxSuccessful())
							.andDo(print());
					verify(srv, times(1)).modify(actor);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "2,     ,123456789012345678901234567890", "3,123456789012345678901234567890,  ",
					"4,  ,            " })
			void testUpdateActor(int actorId, String nombre, String apellido) {
				ActorDTO actorDto = new ActorDTO(actorId, nombre, apellido);
				Actor actor = ActorDTO.from(actorDto);
				try {
					when(srv.modify(actor)).thenReturn(actor);
				} catch (NotFoundException | InvalidDataException e) {
					e.getMessage();
				}
				try {
					mockMvc.perform(put("/actores/{id}", actorId).contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(actor))).andExpect(status().is2xxSuccessful())
							.andDo(print());
					verify(srv, times(1)).modify(actor);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	@Nested
	class deleteActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testDeleteActor(int id) throws InvalidDataException {
				try {
					mockMvc.perform(delete("/actores/{id}", id)).andExpect(status().isOk()).andDo(print());
					verify(srv, times(1)).deleteById(id);
				} catch (Exception e) {
					throw new InvalidDataException(e.getMessage());
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			public void testDeleteActor(int id) throws Exception {
				if (id < 0) {
					assertThrows(AssertionError.class, () -> {
						mockMvc.perform(delete("/actores/{id}", id)).andExpect(status().is5xxServerError());
					});
				} else {
					mockMvc.perform(delete("/actores/{id}", id)).andExpect(status().isOk());
					verify(srv, times(1)).deleteById(id);
				}
			}

		}
	}
}