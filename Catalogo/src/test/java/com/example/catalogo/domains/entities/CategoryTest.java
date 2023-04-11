package com.example.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.example.domains.entities.*;

class CategoryTest {

	@Test
	void testIsValid() {
		var fixture = new Category(0, "Comedy");
		assertTrue(fixture.isValid());
	}
	
	@Nested
	class Name {
		@Nested
		class OK {
			@DisplayName("El nombre no está en blanco")
			@ParameterizedTest(name = "Name: {0}, Error: {1}")
			@CsvSource(value = { 
				"'Western'",
				"'Acción'",
				"'Policias" 
				})
			void testName(String valor) {
				var fixture = new Category(0, valor);
				assertNotNull(fixture);
				assertTrue(fixture.isValid());
			}
		}

		@Nested
		class KO {
			@DisplayName("El nombre no puede estar en blanco")
			@ParameterizedTest(name = "Name: {0}, Error: {1}")
			@CsvSource(value = { 
				"'','ERRORES: name: no debe estar vacío.'",
				"' ','ERRORES: name: no debe estar vacío.'",
				"'   ','ERRORES: name: no debe estar vacío.'", 
				"12345678901234567890123456789012345678901234567890,'ERRORES: name: el tamaño debe estar entre 0 y 25.'" 
				})
			void testName(String valor, String error) {
				var fixture = new Category(0, valor);
				assertNotNull(fixture);
				assertTrue(fixture.isInvalid());
				assertEquals(error, fixture.getErrorsMessage());
			}
		}
	}

}