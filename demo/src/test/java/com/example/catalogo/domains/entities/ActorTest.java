package com.example.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.example.domains.entities.*;

class ActorTest {

	@Test
	void testIsValid() {
		var fixture = new Actor(0, "Pepito", "GRILLO");
		assertTrue(fixture.isValid());
	}

	@Nested
	class Name {
		@Nested
		class OK {
			@DisplayName("El nombre tiene entre 2 y 45 caracteres, y no está blanco")
			@ParameterizedTest(name = "nombre: {0}")
			@CsvSource(value = { "'Pepito'", "'Manolo'", "'Pepe'", "'Juan'" })
			void testName(String valor) {
				var fixture = new Actor(0, valor, "GRILLO");
				assertNotNull(fixture);
				assertTrue(fixture.isValid());
			}
		}

		@Nested
		class KO {
			@DisplayName("El nombre de tener entre 2 y 45 caracteres, y no puede estar en blanco")
			@ParameterizedTest(name = "nombre: -{0}- -> {1}")
			@CsvSource(value = {
					"'','ERRORES: firstName: el tamaño debe estar entre 2 y 45. firstName: no debe estar vacío.'",
					"' ','ERRORES: firstName: el tamaño debe estar entre 2 y 45. firstName: no debe estar vacío.'",
					"'   ','ERRORES: firstName: no debe estar vacío.'",
					"A,'ERRORES: firstName: el tamaño debe estar entre 2 y 45.'",
					"12345678901234567890123456789012345678901234567890,'ERRORES: firstName: el tamaño debe estar entre 2 y 45.'" })
			void testName(String valor, String error) {
				var fixture = new Actor(0, valor, "GRILLO");
				assertNotNull(fixture);
				assertTrue(fixture.isInvalid());
				assertEquals(error, fixture.getErrorsMessage());
			}
		}
	}

	@Nested
	class LastName {
		@Nested
		class OK {
			@DisplayName("El apellido tiene entre 2 y 45 caracteres, y no está blanco")
			@ParameterizedTest(name = "nombre: {0}")
			@CsvSource(value = { "'Ball'", "'Dukakis'", "'Carry'", "'Curry'" })
			void testLastName(String valor) {
				var fixture = new Actor(0, "Pepito", valor);
				assertNotNull(fixture);
				assertFalse(fixture.isValid());
			}
		}

		@Nested
		class KO {
			@DisplayName("El apellido deben tener entre 2 y 45 caracteres, y no pueden estar en blanco")
			@ParameterizedTest(name = "lastName: -{0}- -> {1}")
			@CsvSource(value = {
					"'','ERRORES: lastName: Tiene que estar en mayusculas. lastName: el tamaño debe estar entre 2 y 45.'",
					"' ','ERRORES: lastName: Tiene que estar en mayusculas. lastName: el tamaño debe estar entre 2 y 45.'",
					"'   ','ERRORES: lastName: Tiene que estar en mayusculas.'",
					"A,'ERRORES: lastName: el tamaño debe estar entre 2 y 45.'",
					"12345678901234567890123456789012345678901234567890,'ERRORES: lastName: Tiene que estar en mayusculas. lastName: el tamaño debe estar entre 2 y 45.'" })
			void testLastName(String valor, String error) {
				var fixture = new Actor(0, "Pepito", valor);
				assertNotNull(fixture);
				assertTrue(fixture.isInvalid());
				assertEquals(error, fixture.getErrorsMessage());
			}
		}
	}

}