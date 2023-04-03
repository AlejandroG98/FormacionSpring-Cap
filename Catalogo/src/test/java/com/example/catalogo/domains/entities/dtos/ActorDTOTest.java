package com.example.catalogo.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;

class ActorDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFromActorDTO() {
		var actor = ActorDTO.from(new ActorDTO(0, "Benito","CAMEL"));
		assertEquals(Actor.class,actor.getClass());
		
		assertAll("Propiedades",
				() -> assertEquals(0,actor.getActorId()),
				() -> assertEquals("Benito",actor.getFirstName()),
				() -> assertEquals("CAMEL", actor.getLastName()));
	}

	@Test
	void testFromActor() {
		var actorDTO = ActorDTO.from(new Actor(0, "Benito","CAMEL"));
		assertEquals(ActorDTO.class,actorDTO.getClass());
		
		assertAll("Propiedades",
				() -> assertEquals(0,actorDTO.getActorId()),
				() -> assertEquals("Benito",actorDTO.getFirstName()),
				() -> assertEquals("CAMEL", actorDTO.getLastName()));
	}

}