package com.example.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ElementoDto;
import com.example.domains.entities.dtos.LanguageDTO;
import com.example.domains.entities.dtos.LanguageShort;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/idiomas")
public class LanguageController {

	@Autowired
	LanguageService langService;

	@GetMapping("")
	public String index() {
		return "Hola esto es Language Controller";
	}

	// http://localhost:8001/idiomas/get
	@GetMapping(path = "/get")
	public @ResponseBody List<LanguageShort> getLanguages() throws JsonProcessingException {
		return langService.getByProjection(LanguageShort.class);
	}

	// http://localhost:8001/idiomas/get/2
	@GetMapping(path = "/get/{id}")
	public LanguageDTO getOneLanguageDTO(@PathVariable int id) throws NotFoundException {
		var item = langService.getOne(id);
		if (item.isEmpty()) {
			throw new NotFoundException();
		}
		return LanguageDTO.from(item.get());
	}

	// http://localhost:8001/idiomas/peliculasDelIdioma/1
	@GetMapping(path = "/peliculasDelIdioma/{id}")
	public List<ElementoDto<Integer, String>> getPeliculasFromIdioma(@PathVariable int id) throws NotFoundException {
		return langService.getOne(id).orElseThrow(() -> new NotFoundException("Idioma no encontrado")).getFilms()
				.stream().map(f -> new ElementoDto<>(f.getFilmId(), f.getTitle())).toList();
	}

	// http://localhost:8001/idiomas/peliculasDelIdiomaVO/3
	@GetMapping(path = "/peliculasDelIdiomaVO/{id}")
	public List<ElementoDto<Integer, String>> getPeliculasFromIdiomaVO(@PathVariable int id) throws NotFoundException {
		return langService.getOne(id).get().getFilmsVO().stream()
				.map(f -> new ElementoDto<>(f.getFilmId(), f.getTitle())).toList();
	}

	// http://localhost:8001/idiomas/addLanguage?name=Italiana
	@PostMapping(path = "/addLanguage")
	public Language addNewLanguage(@RequestParam String name)
			throws InvalidDataException, org.springframework.dao.DuplicateKeyException, DuplicateKeyException {

		var languageAux = new Language();
		languageAux.setLanguageId(0);
		languageAux.setName(name);

		try {
			return langService.add(languageAux);
		} catch (InvalidDataException ex) {
			throw new InvalidDataException("ERROR. El nombre del Idioma no puede ser mayor de 20 carácteres");
		}
	}

	//  http://localhost:8001/idiomas/9
	// {"id":9,"name":"Marronera"}
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable int id) {
		langService.deleteById(id);
	}
	
	// http://localhost:8001/idiomas/1
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody LanguageDTO item)
			throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getLanguageId())
			throw new BadRequestException("Nooooooo coinciden los identificadores");
		langService.modify(LanguageDTO.from(item));
	}

}
