package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
@Value @AllArgsConstructor @NoArgsConstructor
public class ActorDTO {
	@JsonProperty("id")
	private int actorId;
	
	@JsonProperty("nombre")
	private String firstName;
	
	@JsonProperty("apellidos")
	private String lastName;
	
	public static ActorDTO from(Actor target) {
		return new ActorDTO(target.getActorId(),target.getFirstName(),target.getLastName());
	}
	
	public static Actor from(ActorDTO target) {
		return new Actor(target.getActorId(),target.getFirstName(),target.getLastName());
	}
	
}
