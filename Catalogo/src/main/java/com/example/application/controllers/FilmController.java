package com.example.application.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
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

	@Autowired
	LanguageRepository languageRepository;

	@GetMapping("")
	public String index() {
		return "Hola esto es Film Controller";
	}

	// http://localhost:8001/peliculas/get
	@GetMapping(path = "/get")
	public List<Film> getAllFilms() {
		return filmRepository.findAll();
	}

	// http://localhost:8001/peliculas/get/1
	@GetMapping("/get/{id}")
	public Film getFilmById(@PathVariable int id) {
		return filmRepository.findById(id).orElse(null);
	}

	// http://localhost:8001/peliculas/1
	/*
	 * { "id": 1, "title": "ACADEMY DINOSAUR", "descripcion":
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

	// http://localhost:8001/peliculas/225
	/*
	  { "filmId": 0, "description": "Pato", "lastUpdate":
	  "2023-04-05T15:41:26.000+00:00", "length": 81, "rating": "GENERAL_AUDIENCES",
	  "releaseYear": "2006", "rentalDuration": 7, "rentalRate": 4.99,
	  "replacementCost": 29.99, "title": "ACADEMY DINOSAUR", "language": { "id": 4,
	  "idioma": "Mandarin", "ultimaModificacion": "2006-02-15 04:02:19" },
	  "languageVO": { "id": 3, "idioma": "Japanese", "ultimaModificacion":
	  "2006-02-15 04:02:19" }, "actors": [ { "actorId": 77, "firstName": "CARY",
	  "lastName": "MCCONAUGHEY", "lastUpdate": "2006-02-15T03:34:33.000+00:00" }, {
	  "actorId": 117, "firstName": "RENEE", "lastName": "TRACY", "lastUpdate":
	  "2006-02-15T03:34:33.000+00:00" }, { "actorId": 137, "firstName": "MORGAN",
	  "lastName": "WILLIAMS", "lastUpdate": "2006-02-15T03:34:33.000+00:00" }, {
	  "actorId": 187, "firstName": "RENEE", "lastName": "BALL", "lastUpdate":
	  "2006-02-15T03:34:33.000+00:00" } ], "categories": [ { "categoryId": 16,
	  "name": "Travel" } ] }
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

	// ERROR: DataIntegrityViolationException
	// http://localhost:8001/peliculas/1
	// {"id":1,"name":"ACADEMY DINOSAUR"}
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable int id) throws InvalidDataException {
		filmService.deleteById(id);
	}

}
