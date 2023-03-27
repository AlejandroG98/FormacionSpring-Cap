package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import lombok.experimental.var;

public class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	// @Smoke -> Para filtrar para la ejecución
	@Smoke
	void testCreate() {
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();

		// Comprobar si no es nulo
		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades", () -> assertEquals(1, p.getId(), "id"),
				() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
				() -> assertEquals("Grillo", p.getApellidos(), "getApellidos"));
	}

	// Test con repetidor
	// Poner las mismas propiedades de los atributos tanto en var
	// como en assert
	@RepeatedTest(value = 5, name = "{displayName}{currentRepetition}/{totalRepetitions}")
	// Con repetidoTest podemos filtrar para que se utilize (o no)
	@Tag("repetidoTest")
	void repeatedTest(RepetitionInfo repetitionInfo) {
		var p = Persona.builder().id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito"
						+ (repetitionInfo.getCurrentRepetition() % 3 == 0 ? "" : repetitionInfo.getCurrentRepetition()))
				.apellidos("Grillo").build();

		// Comprobar si no es nulo
		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades", () -> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "id"),
				() -> assertEquals("Pepito"
						+ (repetitionInfo.getCurrentRepetition() % 3 == 0 ? "" : repetitionInfo.getCurrentRepetition()),
						p.getNombre(), "getNombre"),
				() -> assertEquals("Grillo", p.getApellidos(), "getApellidos"));
	}


}
