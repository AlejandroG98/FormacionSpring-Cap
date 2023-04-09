package com.example.catalogo.application.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.application.controllers.CategoryController;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.CategoryShort;
import com.example.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Value
	static class CategoryShortMock implements CategoryShort {
		int categoryId;
		String name;
		List<FilmCategory> filmCategories;
	}

	@Nested
	class oneCategory {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Accion", "2,Violencia", "3,Muerte" })
			void testGetOneCategory(int id, String nombre) throws Exception {
				var category = new Category(id, nombre);
				var categoryDTO = CategoryDTO.from(category);
				when(srv.getOne(id)).thenReturn(Optional.of(category));
				try {
					mockMvc.perform(get("/categorias/get/{id}", id)).andExpect(status().isOk())
							.andExpect(jsonPath("$.categoryId").value(categoryDTO.getCategoryId()))
							.andExpect(jsonPath("$.nombre").value(categoryDTO.getName())).andDo(print());
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1,A", "-2,",
					"-3,Muerteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" })
			void testGetOneCategory(int id, String nombre) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				try {
					mockMvc.perform(get("/categorias/get/{id}", id)).andExpect(status().is4xxClientError())
							.andExpect(jsonPath("$.title").value(null));
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
			@CsvSource({ "1", "2", "3" })
			void testGetOne404(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				try {
				mockMvc.perform(get("/categorias/get/{id}", id))
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
			void testGetOne404(int id) throws Exception {
				when(srv.getOne(id)).thenReturn(Optional.empty());
				try {
				mockMvc.perform(get("/categorias/get/{id}", id))
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
	class addCategory {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Guerra", "2,Muerte", "3,Destrucción" })
			void testAddCategory(int id, String nombre) throws DuplicateKeyException, InvalidDataException, Exception {
				var ele = new Category(id, nombre);
				when(srv.add(ele)).thenReturn(ele);
				try {
					mockMvc.perform(post("/categorias/addCategory").contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(CategoryDTO.from(ele)))
							.param("id", String.valueOf(ele.getCategoryId())).param("name", ele.getName()))
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
			@CsvSource({ "1,-A", "-2,", "3,ABCDEFGHIJKLMNOPQRaASDSSTUVWXYZabcdefghiASDjklmnopqrstuvwxyz0123456789" })
			void testAddCategory(int id, String nombre) throws DuplicateKeyException, InvalidDataException, Exception {
				var ele = new Category(id, nombre);
				when(srv.add(ele)).thenReturn(ele);
				try {
					mockMvc.perform(post("/categorias/addCategory").contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(CategoryDTO.from(ele)))
							.param("id", String.valueOf(ele.getCategoryId())).param("nombre", ele.getName()))
							.andExpect(status().is4xxClientError()).andDo(print());
				} catch (JsonProcessingException e) {
					e.getMessage();
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	@Nested
	class updateCategory {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1,Guerra", "2,Muerte", "3,Destrucción" })
			void testUpdateCategory(int categoryId, String nombre) throws Exception {
				Category category = new Category(categoryId, nombre);
				mockMvc.perform(put("/categorias/{id}", categoryId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(category))).andExpect(status().is2xxSuccessful())
						.andDo(print());
				verify(srv, times(1)).modify(category);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "1,-A", "-2,", "-3,Des" })
			void testUpdateCategory(int categoryId, String name) throws Exception {
				Category category = new Category(categoryId, name);
				when(srv.modify(category)).thenReturn(category);
				mockMvc.perform(put("/categorias/{id}", categoryId).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(category))).andExpect(status().is2xxSuccessful())
						.andExpect(content().string("")).andDo(print());
				verify(srv, times(1)).modify(category);
			}
		}
	}

	@Nested
	class deleteCategory {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testDeleteCategory(int id) throws Exception {
				try {
					mockMvc.perform(delete("/categorias/{id}", id)).andExpect(status().isOk()).andDo(print());
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
			public void testDeleteCategory(int id) throws InvalidDataException {
				try {
					doNothing().when(srv).deleteById(id);
					mockMvc.perform(delete("/categorias/{id}", id)).andExpect(status().isOk()).andDo(print());
					verify(srv, times(1)).deleteById(id);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
}