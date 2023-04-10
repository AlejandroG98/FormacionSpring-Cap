package com.example.application.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.ElementoDto;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import jakarta.validation.Valid;

// Ejemplo en: demo-web -> com.example.application.resources -> ActorResource
@RestController
@RequestMapping("/actores")
public class ActorController {

	@Autowired
	ActorService actService;

	@GetMapping("")
	public String index() {
		return "Hola esto es Actor Controller";
	}

	// http://localhost:8001/actores/get
	@GetMapping(path = "/get")
	public @ResponseBody List<ActorShort> getActors(@RequestParam(required = false) String sort)
			throws JsonProcessingException {
		if (sort != null)
			return (List<ActorShort>) actService.getByProjection(Sort.by(sort), ActorShort.class);
		return actService.getByProjection(ActorShort.class);
	}

	// http://localhost:8001/actores/get/2
	@GetMapping(path = "/get/{id}")
	public ActorDTO getOneActor(@PathVariable int id) throws NotFoundException {
		var item = actService.getOne(id);
		if (item.isEmpty()) {
			throw new NotFoundException();
		}
		return ActorDTO.from(item.get());
	}

	// http://localhost:8001/actores/peliculasDelActor/2
	@GetMapping(path = "/peliculasDelActor/{id}")
	public List<ElementoDto<Integer, String>> getPeliculasFromActor(@PathVariable int id) throws NotFoundException {
		Optional<Actor> actor = actService.getOne(id);
		if (!actor.isPresent()) {
			throw new NotFoundException("Actor not found");
		}
		return actService.getOne(id).get().getFilmActors().stream()
				.map(f -> new ElementoDto<>(f.getFilm().getFilmId(), f.getFilm().getTitle())).toList();
	}

	// http://localhost:8001/actores/addActor?firstname=Alex&lastname=Gar
	@PostMapping(path = "/addActor")
	public @ResponseBody Actor addNewActor(@RequestParam String firstname, @RequestParam String lastname)
			throws InvalidDataException, org.springframework.dao.DuplicateKeyException, DuplicateKeyException {

		var actorAux = new Actor();
		actorAux.setActorId(0);
		actorAux.setFirstName(firstname.toUpperCase());
		actorAux.setLastName(lastname.toUpperCase());

		try {
			return actService.add(actorAux);
		} catch (InvalidDataException ex) {
			throw new InvalidDataException("ERROR. El nombre y apellido no puede ser menor a 2 o mayor a 45");
		}
	}

	// http://localhost:8001/actores/1
	// CUIDADO: El nombre de las variables vienen predeterminadas por el DTO
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item)
			throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getActorId())
			throw new BadRequestException("Nooooooo coinciden los identificadores");
		actService.modify(ActorDTO.from(item));
	}

	// http://localhost:8001/actores/201
	// {"id":201,"nombre":"KK","apellidos": "KKK"}
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable int id) {
		actService.deleteById(id);
	}

}
