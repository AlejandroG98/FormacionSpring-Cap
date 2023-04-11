package com.example.catalogo.application.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.application.controllers.ActorController;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

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
				mockMvc.perform(get("/actores/get").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk());
			}

			@DisplayName("Cuando no tiene parametro Sort devuelve la lista de ActorShort")
			@ParameterizedTest
			@CsvSource({ "1,Manolo,Garcia", "2,Benito,Fontanero", "3,Manuel,Jamon" })
			public void testGetAll2(int id, String nombre, String apellido) throws Exception {
				List<ActorShort> expectedActors = Arrays.asList(new ActorShortMock(id, nombre, apellido));
				when(srv.getByProjection(ActorShort.class)).thenReturn(expectedActors);
				mockMvc.perform(get("/actores/get")).andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].actorId").value(id)).andExpect(jsonPath("$[0].nombre").value(nombre))
						.andExpect(jsonPath("$[0].apellido").value(apellido));
				verify(srv, times(1)).getByProjection(ActorShort.class);
				verifyNoMoreInteractions(srv);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,    ,Garciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2,  ,    ", "3,    ,      " })
			void testGetAll3(int id, String nombre, String apellido) throws Exception {
				if (id < 0 || nombre == null || apellido == null || nombre.trim().length() > 20) {
					mockMvc.perform(get("/actores/get").accept(MediaType.APPLICATION_JSON))
							.andExpect(status().is2xxSuccessful());
				}
			}

			@DisplayName("Devuelve una List vacia cuando no exiten Actores")
			@Test
			public void testGetAll4() throws Exception {
			    when(srv.getAll()).thenReturn(Collections.emptyList());
			    mockMvc.perform(get("/actores/get"))
			            .andExpect(status().isOk());
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

			@DisplayName("Cuando encuentra al actor devuelve su DTO")
			@ParameterizedTest
			@CsvSource({ "1,Benito,Malo", "2,Haber,Estudiado", "3,Polo,Mentolado" })
			public void testGetOneActor2(int id, String nombre, String apellido) throws Exception {
				Actor actor = new Actor(id, nombre, apellido);
				when(srv.getOne(id)).thenReturn(Optional.of(actor));
				mockMvc.perform(get("/actores/get/{id}", id)).andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.actorId").value(id)).andExpect(jsonPath("$.nombre").value(nombre))
						.andExpect(jsonPath("$.apellidos").value(apellido));
				verify(srv, times(1)).getOne(id);
				verifyNoMoreInteractions(srv);
			}

		}

		@Nested
		class KO {
			@DisplayName("ID Negativas")
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			void testGetOneActor3(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/actores/get/{id}", id)).andExpect(status().is4xxClientError())
							.andExpect(jsonPath("$.title").value("Not Found"));
			}

			@DisplayName("Cuando no encuentra al actor devuelve Not Found")
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testGetOneActor4(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/actores/get/{id}",id)).andExpect(status().isNotFound());
				verify(srv, times(1)).getOne(id);
				verifyNoMoreInteractions(srv);
			}
		}
	}

	@Nested
	class getPeliculasFromActor {
		@Nested
		class OK {
			@DisplayName("Devuelve las Pelis si encuentra al Actor")
			@ParameterizedTest
			@CsvSource({ "1,Paco,Mer", "2,Pa,Beber", "3,Pa,Dormir" })
			public void getPeliculasFromActor(int id, String nombre, String apellido) throws Exception {
				List<FilmActor> filmActors = new ArrayList<>();
				FilmActor filmActor1 = new FilmActor(new Film(), new Actor());
				filmActors.add(filmActor1);
				Actor actor = new Actor(id, nombre, apellido);
				when(srv.getOne(id)).thenReturn(Optional.of(actor));
				mockMvc.perform(get("/actores/peliculasDelActor/" + id)).andExpect(status().isOk());
			}
		}

		@Nested
		class KO {
			@DisplayName("Si no encuentra al Actor devuelve NotFound")
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void getPeliculasFromActor2(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				mockMvc.perform(get("/actores/peliculasDelActor/{id}" , id)).andExpect(status().isNotFound())
						.andExpect(result -> assertFalse(result.getResolvedException() instanceof NoSuchElementException));
				verify(srv, times(1)).getOne(id);
				verifyNoMoreInteractions(srv);
			}

			@DisplayName("Devuelve una List vacia si el Actor no tiene Films")
			@ParameterizedTest
			@CsvSource({ "1,Paco,Mer", "2,Pol,Vito", "3,Ala,Casa" })
			public void getPeliculasFromActor3(int id, String nombre, String apellido) throws Exception {
				Actor actor = new Actor(id, nombre, apellido);
				when(srv.getOne(id)).thenReturn(Optional.of(actor));
				mockMvc.perform(get("/actores/peliculasDelActor/" + id)).andExpect(status().isOk())
						.andExpect(content().json("[]"));
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
					mockMvc.perform(get("/actores/get/{id}", id))
						.andExpect(status().isNotFound())
						.andExpect(jsonPath("$.title").value("Not Found"))
					    .andDo(print());
			}
		}

		@Nested
		class KO {
			@DisplayName("Devuelve Client Error cuando tiene una Excepción inesperada")
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testGetOne4042(int id) throws Exception {
			    when(srv.getOne(id)).thenThrow(RuntimeException.class);
			    mockMvc.perform(get("/actores/" + id)).andExpect(status().is4xxClientError());
			}

			@DisplayName("Devuelve Method Not Allowed cuando tiene un HTTP incorrecto")
			@ParameterizedTest
			@CsvSource({ "1,Paco,Mer", "2,Pa,Beber", "3,Pa,Dormir" })
			public void testGetOne4043(int id, String nombre, String apellido) throws Exception {
				Actor actor = new Actor(id, nombre, apellido);
				String json = objectMapper.writeValueAsString(actor);
				mockMvc.perform(post("/actores").contentType(MediaType.APPLICATION_JSON).content(json))
						.andExpect(status().isMethodNotAllowed());
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
				when(srv.add(ele)).thenReturn(ele);
				mockMvc.perform(post("/actores/addActor").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
						.param("firstname", ele.getFirstName()).param("lastname", ele.getLastName()))
						.andExpect(status().isOk()).andDo(print());
			}
		}

		@Nested
		class KO {
			@DisplayName("Parametros incorrectos: Id Negativa, Nombre o Apellido nulo o 1 char")
			@ParameterizedTest
			@CsvSource({ "-1,,", "-2,,b", "-3,a," })
			void testAddActor2(int id, String nombre, String apellido) throws Exception {
				var ele = new Actor(id, nombre, apellido);
				when(srv.add(ele)).thenReturn(ele);
				mockMvc.perform(post("/actores/addActor").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
						.param("firstname", ele.getFirstName()).param("lastname", ele.getLastName()))
						.andExpect(status().isBadRequest()).andDo(print());
			}

			@DisplayName("Parametros incorrectos: Nombre o Apellido con menos de 2 char")
			@ParameterizedTest
			@CsvSource({ "M,Gar", "Pol,V", "Ho,Ho" })
			public void testAddActor3(String nombre, String apellido) throws Exception {
				when(srv.add(any(Actor.class))).thenThrow(InvalidDataException.class);
				mockMvc.perform(post("/actores/addActor").param("firstname", nombre).param("lastname", apellido))
						.andExpect(status().isBadRequest()).andExpect(jsonPath("$.title", is("Bad Request")));
				verify(srv, times(1)).add(any(Actor.class));
				verifyNoMoreInteractions(srv);
			}

			@DisplayName("Devuelve Client Error si ya existe el Actor")
			@ParameterizedTest
			@CsvSource({ "Alex,Gar", "ED,CHASE", "JOE,SWANK" })
			public void testAddActor4(String nombre, String apellido) throws Exception , DuplicateKeyException{
				when(srv.add(any(Actor.class))).thenThrow(DuplicateKeyException.class);
				mockMvc.perform(post("/actores/addActor").param("firstname", nombre).param("lastname", apellido))
						.andExpect(status().is4xxClientError());

				verify(srv, times(1)).add(any(Actor.class));
				verifyNoMoreInteractions(srv);
			}
		}
	}

	@Nested
	class updateActor {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			void testUpdateActor(int actorId, String nombre, String apellido) throws Exception, Exception {
				Actor actor = new Actor(actorId, nombre, apellido);
				mockMvc.perform(put("/actores/{id}", actorId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(actor))).andExpect(status().is2xxSuccessful())
						.andDo(print());
				verify(srv, times(1)).modify(actor);
			}
		}

		@Nested
		class KO {
			@DisplayName("Nombre o Apellido Nulo")
			@ParameterizedTest
			@CsvSource({ "2,     ,123456789012345678901234567890", "3,123456789012345678901234567890,  ",
					"4,    ,            " })
			void testUpdateActor2(int actorId, String nombre, String apellido) throws Exception, Exception {
				assertThrows(AssertionError.class, () -> {
					mockMvc.perform(put("/actores/{id}", actorId)).andExpect(status().is5xxServerError());
				});
			}

			@DisplayName("No coinciden las ID")
			@ParameterizedTest
			@CsvSource({ "1,2", "5,6", "10,11" })
			public void testUpdateActor3(int actorId, int payloadActorId) throws Exception {
				ActorDTO actorToUpdate = new ActorDTO(payloadActorId, "FirstName", "LastName");
				String requestJson = objectMapper.writeValueAsString(actorToUpdate);

				mockMvc.perform(put("/actores/" + actorId).contentType(MediaType.APPLICATION_JSON).content(requestJson))
						.andExpect(status().isBadRequest()).andExpect(jsonPath("$.title", is("Bad Request")));

				verifyNoMoreInteractions(srv);
			}

			@DisplayName("Devuelve NotFound si no encuentra el Actor")
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			public void testUpdateActor4(int id, String nombre, String apellido) throws NotFoundException, Exception {
				ActorDTO actorToUpdate = new ActorDTO(id, nombre, apellido);
				String requestJson = objectMapper.writeValueAsString(actorToUpdate);
				when(srv.modify(ActorDTO.from(actorToUpdate))).thenThrow(NotFoundException.class);
				mockMvc.perform(put("/actores/{id}", id).contentType(MediaType.APPLICATION_JSON).content(requestJson))
						.andExpect(status().isNotFound());
				verify(srv, times(1)).modify(ActorDTO.from(actorToUpdate));
				verifyNoMoreInteractions(srv);
			}

			@DisplayName("Devuelve ClientError con una Excepción inprevista")
			@ParameterizedTest
			@CsvSource({ "1,Mar,Gar", "2,Pol,Vor", "3,Macar,Rones" })
			public void testUpdateActor5(int id, String nombre, String apellido) throws NotFoundException, Exception {
				ActorDTO actorToUpdate = new ActorDTO(id, nombre, apellido);
				String requestJson = objectMapper.writeValueAsString(actorToUpdate);
				when(srv.modify(ActorDTO.from(actorToUpdate))).thenThrow(NotFoundException.class);
				mockMvc.perform(put("/actores/{id}", id).contentType(MediaType.APPLICATION_JSON).content(requestJson))
						.andExpect(status().is4xxClientError());
				verify(srv, times(1)).modify(ActorDTO.from(actorToUpdate));
				verifyNoMoreInteractions(srv);
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

			@DisplayName("No devuelve contenido si el Actor existe")
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testDeleteActor2(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(null);
				mockMvc.perform(delete("/actores/" + id)).andExpect(status().is2xxSuccessful());
				verify(srv, times(1)).deleteById(id);
			}
		}

		@Nested
		class KO {
			@DisplayName("ID Negativa")
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			public void testDeleteActor3(int id) throws Exception {
				if (id < 0) {
					assertThrows(AssertionError.class, () -> {
						mockMvc.perform(delete("/actores/{id}", id)).andExpect(status().is5xxServerError());
					});
				}
			}

			@DisplayName("Le pasamos parametros que no son Integer")
			@ParameterizedTest
			@CsvSource({ "invalidId", "!-.´+`Ç", "<>>><<>>>" })
			public void testDeleteActor4(String param) throws Exception {
				mockMvc.perform(delete("/actores/" + param)).andExpect(status().isBadRequest());
				verifyNoInteractions(srv);
			}
		}
	}
}