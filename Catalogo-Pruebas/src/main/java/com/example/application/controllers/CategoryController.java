package com.example.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.CategoryShort;
import com.example.domains.entities.dtos.ElementoDto;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@ResponseBody
@RequestMapping("/categorias")
public class CategoryController {

	@Autowired
	CategoryService catService;

	@GetMapping("")
	public String index() {
		return "Hola esto es Category Controller";
	}

	// http://localhost:8001/categorias/get
	@GetMapping(path = "/get")
	public @ResponseBody List<CategoryShort> getActors() throws JsonProcessingException {
		return catService.getByProjection(CategoryShort.class);
	}

	// http://localhost:8001/categorias/get/2
	@GetMapping(path = "/get/{id}")
	public CategoryDTO getOneCategoryDTO(@PathVariable int id) throws NotFoundException {
		var item = catService.getOne(id);
		if (item.isEmpty()) {
			throw new NotFoundException();
		}
		return CategoryDTO.from(item.get());
	}

	// http://localhost:8001/categorias/peliculasDeLaCategoria/2
	@GetMapping(path = "/peliculasDeLaCategoria/{id}")
	public List<ElementoDto<Integer, String>> getPeliculasFromCategory(@PathVariable int id) throws NotFoundException {
		return catService.getOne(id).get().getFilmCategories().stream()
				.map(f -> new ElementoDto<>(f.getFilm().getFilmId(), f.getFilm().getTitle())).toList();
	}

	// http://localhost:8001/categorias/addCategory?name=Italiana
	@PostMapping(path = "/addCategory")
	public @ResponseBody Category addNewCategory(@RequestParam String name)
			throws InvalidDataException, org.springframework.dao.DuplicateKeyException, DuplicateKeyException {

		var categoryAux = new Category();
		categoryAux.setCategoryId(0);
		categoryAux.setName(name);

		try {
			return catService.add(categoryAux);
		} catch (InvalidDataException ex) {
			throw new InvalidDataException("ERROR. El nombre de la Categoria no puede ser mayor de 25");
		}
	}

	// NO FUNCIONA PORQUE TOCA PK DE FILM_CATEGORY
	// http://localhost:8001/categorias/delete?id=2
	@DeleteMapping("/delete")
	public void delete(@RequestParam int id) {
		catService.deleteById(id);
	}

}
