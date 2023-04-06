package com.example.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.CategoryDTO;
import com.example.domains.entities.dtos.CategoryShort;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/peliculas")
public class FilmController {

	@Autowired
	FilmService filmService;

	@GetMapping("")
	public String index() {
		return "Hola esto es Film Controller";
	}

	// http://localhost:8001/peliculas/get
	@GetMapping(path = "/get")
	public @ResponseBody List<FilmShortDTO> getActors() throws JsonProcessingException {
		return filmService.getByProjection(FilmShortDTO.class);
	}

	// http://localhost:8001/peliculas/get/2
	@GetMapping(path = "/get/{id}")
	public FilmDTO getOneFilmDTO(@PathVariable int id) throws NotFoundException {
		var item = filmService.getOne(id);
		if (item.isEmpty()) {
			throw new NotFoundException();
		}
		return FilmDTO.from(item.get());
	}
	
	

}
