package com.example.catalogo.application.controllers;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.application.controllers.ActorController;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorController.class)
class ActorControllerTest {
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
				mockMvc.perform(get("/actores/get").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,M,Garcia", "2,Benito,F", "3, ,Jamon" })
			void testGetAll(int id, String nombre, String apellido) throws Exception {
				List<ActorShort> lista = new ArrayList<>(Arrays.asList(new ActorShortMock(id, nombre, apellido)));
				when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
				mockMvc.perform(get("/actores/get").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
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
				mockMvc.perform(get("/actores/get/{id}", id)).andExpect(status().isOk())
						.andExpect(jsonPath("$.actorId").value(actorDTO.getActorId()))
						.andExpect(jsonPath("$.nombre").value(actorDTO.getFirstName()))
						.andExpect(jsonPath("$.apellidos").value(actorDTO.getLastName())).andDo(print());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,M,Garcia", "2,Benito,F", "-3,,Jamon" })
			void testGetOneActor(int id, String nombre, String apellido) throws Exception {
				Actor actor = new Actor(id, nombre, apellido);
				var actorDTO = ActorDTO.from(actor);
				when(srv.getOne(id)).thenReturn(Optional.ofNullable(actor));
				mockMvc.perform(get("/actores/get/{id}", id)).andExpect(status().isOk())
						.andExpect(jsonPath("$.actorId").value(actorDTO.getActorId()))
						.andExpect(jsonPath("$.nombre").value(actorDTO.getFirstName()))
						.andExpect(jsonPath("$.apellidos").value(actorDTO.getLastName())).andDo(print());
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
				mockMvc.perform(get("/actores/get/{id}", id))
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
				mockMvc.perform(get("/actores/get/{id}", id))
					.andExpect(status().isNotFound())
					.andExpect(jsonPath("$.title").value("Not Found"))
			        .andDo(print());
			}
		}
	}

	@Nested
	class addActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			void testAddActor(int id, String nombre, String apellido) throws Exception {
				var ele = new Actor(id, nombre, apellido);
				when(srv.add(ele)).thenReturn(ele);
				mockMvc.perform(post("/actores/addActor").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
						.param("firstname", ele.getFirstName()).param("lastname", ele.getLastName()))
						.andExpect(status().isOk()).andDo(print());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1,,", "-2,,b", "-3,a," })
			void testAddActor(int id, String nombre, String apellido) throws Exception {
				var ele = new Actor(id, nombre, apellido);
				when(srv.add(ele)).thenReturn(ele);
				mockMvc.perform(post("/actores/addActor").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
						.param("firstname", ele.getFirstName()).param("lastname", ele.getLastName()))
						.andExpect(status().isBadRequest()).andDo(print());
			}
		}
	}

	@Nested
	class updateActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			void testUpdateActor(int actorId) throws Exception {
				Actor actor = new Actor(actorId, "Pepito", "Grillo");
				mockMvc.perform(put("/actores/{id}", actorId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actor))).andExpect(status().is2xxSuccessful())
						.andDo(print());
				verify(srv, times(1)).modify(actor);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,M,Gar", "2,Pol,V", "-3,Macar,Rones" })
			void testUpdateActor(int actorId, String nombre, String apellido) throws Exception {
				ActorDTO actorDto = new ActorDTO(actorId, nombre, apellido);
				Actor actor = ActorDTO.from(actorDto);
				mockMvc.perform(put("/actores/{id}", actorId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actor))).andReturn();
				verify(srv, times(1)).modify(actor);
			}
		}
	}

	@Nested
	class deleteActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testDeleteActor(int id) throws Exception {
				mockMvc.perform(delete("/actores/{id}", id)).andExpect(status().isOk()).andDo(print());
				verify(srv, times(1)).deleteById(id);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			public void testDeleteActor(int id) throws Exception {
				mockMvc.perform(delete("/actores/{id}", id)).andExpect(status().isOk()).andDo(print());
				verify(srv, times(1)).deleteById(id);
			}
		}
	}

}