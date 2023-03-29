package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
@Value @AllArgsConstructor @NoArgsConstructor
public class ActorDTO {
	private int actorId;
	private String firstName;
	private String lastName;
	
	public static ActorDTO from(Actor target) {
		return new ActorDTO(target.getActorId(),target.getFirstName(),target.getLastName());
	}
	
	public static Actor from(ActorDTO target) {
		return new Actor(target.getActorId(),target.getFirstName(),target.getLastName());
	}
	
}
