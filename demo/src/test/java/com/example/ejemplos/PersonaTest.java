package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonaTest {

	@BeforeEach
	void setUp() throws Exception{
	}
	
	@Test
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
		
		//Comprobar si no es nulo
		assertNotNull(p);
		assertTrue(p instanceof Persona,"No es instancia de persona");
		assertAll("Validar propiedades",
				() -> assertEquals(1,p.getId(), "id"),
				() -> assertEquals("Pepito",p.getNombre(), "getNombre"),
				() -> assertEquals("Grillo",p.getApellidos(), "getApellidos")
				);
	}
}
