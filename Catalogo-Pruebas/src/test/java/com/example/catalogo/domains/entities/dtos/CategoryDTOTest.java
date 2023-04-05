package com.example.catalogo.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.*;

public class CategoryDTOTest {

	@Test
	void testFrom() {
	    Category category = new Category(1, "Action", new ArrayList<>());
	    CategoryDTO categoryDTO = CategoryDTO.from(category);
	    assertEquals(category.getCategoryId(), categoryDTO.getCategoryId());
	    assertEquals(category.getName(), categoryDTO.getName());
	    assertEquals(category.getFilmCategories(), categoryDTO.getFilmCategories());
	}

	@Test
	void testTo() {
	    CategoryDTO categoryDTO = new CategoryDTO(1, "Action", new ArrayList<>());
	    Category category = CategoryDTO.from(categoryDTO);
	    assertEquals(categoryDTO.getCategoryId(), category.getCategoryId());
	    assertEquals(categoryDTO.getName(), category.getName());
	    assertEquals(categoryDTO.getFilmCategories(), category.getFilmCategories());
	}

}
