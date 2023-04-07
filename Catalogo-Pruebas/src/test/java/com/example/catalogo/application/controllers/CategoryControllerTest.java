package com.example.catalogo.application.controllers;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.application.controllers.CategoryController;
import com.example.catalogo.application.controllers.ActorControllerTest.ActorShortMock;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.CategoryShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService srv;

	private final CategoryController controller = new CategoryController();

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
	class deleteCategory {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource({ "1", "2", "3" })
			public void testDeleteCategory(int id) throws Exception {
				mockMvc.perform(delete("/categorias/{id}", id)).andExpect(status().isOk()).andDo(print());
				verify(srv, times(1)).deleteById(id);
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource({ "-1", "-2", "-3" })
			public void testDeleteCategory(int id) throws Exception {
				mockMvc.perform(delete("/categorias/{id}", id)).andExpect(status().isOk()).andDo(print());
				verify(srv, times(1)).deleteById(id);
			}
		}
	}

}