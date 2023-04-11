package com.example.application.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/peliculas")
public class FilmController {

	@Autowired
	FilmService filmService;

	@Autowired
	FilmRepository filmRepository;

	@GetMapping("")
	public String index() {
		return "Hola esto es Film Controller";
	}

	// http://localhost:8001/peliculas/get
	@GetMapping(path = "/get")
	public List<Film> getAllFilms() {
		return filmService.getAll();
	}

	// http://localhost:8001/peliculas/get/1
	@GetMapping("/get/{id}")
	public Film getFilmById(@PathVariable int id) {
		return filmService.getOne(id).orElse(null);
	}

	// http://localhost:8001/peliculas/225
	/*
	 * { "filmId": 1, "description": "Pato mareado", "lastUpdate":
	 * "2023-04-10T09:15:17.000+00:00", "length": 86, "rating":
	 * "PARENTAL_GUIDANCE_SUGGESTED", "releaseYear": "2006", "rentalDuration": 6,
	 * "rentalRate": 0.99, "replacementCost": 20.99, "title": "ACADEMY DINOSAUR",
	 * "language": { "id": 1, "idioma": "Ingles", "ultimaModificacion":
	 * "2023-04-06 08:38:49" }, "languageVO": { "id": 5, "idioma": "French",
	 * "ultimaModificacion": "2006-02-15 04:02:19" }, "actors": [ { "actorId": 1,
	 * "firstName": "Accion", "lastName": "ASEGURADA" }, { "actorId": 10,
	 * "firstName": "CHRISTIAN", "lastName": "GABLE" }, { "actorId": 20,
	 * "firstName": "IVANKA", "lastName": "TRUMP" }, { "actorId": 30, "firstName":
	 * "SANDRA", "lastName": "PECK" }, { "actorId": 40, "firstName": "JOHNNY",
	 * "lastName": "CAGE" }, { "actorId": 53, "firstName": "MENA", "lastName":
	 * "TEMPLE" }, { "actorId": 108, "firstName": "WARREN", "lastName": "NOLTE" }, {
	 * "actorId": 162, "firstName": "OPRAH", "lastName": "KILMER" }, { "actorId":
	 * 188, "firstName": "ROCK", "lastName": "DUKAKIS" }, { "actorId": 198,
	 * "firstName": "MARY", "lastName": "KEITEL" } ], "categories": [ {
	 * "categoryId": 6, "name": "Documentary" } ] }
	 */
	@PostMapping(path = "/{id}")
	public @ResponseBody Film postFilm(@PathVariable Integer id, @Valid @RequestBody Film film)
			throws NotFoundException, InvalidDataException {
		Film existingFilm = filmRepository.getById(id);
		existingFilm.setTitle(film.getTitle());
		existingFilm.setDescription(film.getDescription());
		if (existingFilm == null)
			throw new InvalidDataException("ERROR. Está vacio de datos...");
		return filmService.modify(existingFilm);
	}

	// http://localhost:8001/peliculas/1
	/*
	 * { "title": "ACADEMY DINOSAUR", "descripcion":
	 * "A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"
	 * , "duracion": 86, "valoracion": "GENERAL_AUDIENCES", "release_year": 2006,
	 * "rental_duration": 6, "rental_rate": 0.99, "replacement_cost": 20.99,
	 * "language": { "id": 1, "nombre": "Ingles" }, "languageVO": { "id": 5,
	 * "nombre": "French" }, "actors": [ { "id":1, "firstName": "John", "lastName":
	 * "Doe" } ], "categories": [ { "id":6, "name":"Documentary" } ] }
	 */
	@PutMapping(path = "/{id}")
	public @ResponseBody Film putFilm(@PathVariable Integer id, @Valid @RequestBody Film film)
			throws NotFoundException, InvalidDataException {
		Film existingFilm = filmRepository.getById(id);
		existingFilm.setTitle(film.getTitle());
		existingFilm.setDescription(film.getDescription());
		if (existingFilm == null)
			throw new InvalidDataException("ERROR. Está vacio de datos...");
		return filmService.modify(existingFilm);
	}

	// ERROR: DataIntegrityViolationException
	// http://localhost:8001/peliculas/1
	// {"id":1,"name":"ACADEMY DINOSAUR"}
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable int id) {
		filmService.deleteById(id);
	}

}
