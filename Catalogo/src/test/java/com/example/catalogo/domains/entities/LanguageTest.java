package com.example.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.example.domains.entities.*;

class LanguageTest {

	@Test
	void testIsValid() {
		var fixture = new Language(0, "Armenio");
		assertTrue(fixture.isValid());
	}

//	@DisplayName("El nombre no puede estar en blanco")
//	@ParameterizedTest(name = "Name: {0}, Error: {1}")
//	@CsvSource(value = { 
//		"'','ERRORES: name: must not be blank.'",
//		"' ','ERRORES: name: must not be blank.'",
//		"'   ','ERRORES: name: must not be blank.'", 
//		"12345678901234567890123456789012345678901234567890,'ERRORES: name: size must be between 0 and 20.'" 
//		})
//	void testNameIsInvalid(String valor, String error) {
//		var fixture = new Language(0, valor);
//		assertNotNull(fixture);
//		assertTrue(fixture.isInvalid());
//		assertEquals(error, fixture.getErrorsMessage());
//	}
	
	@Nested
	class Name {
		@Nested
		class OK {
			@DisplayName("El nombre no está en blanco")
			@ParameterizedTest(name = "Name: {0}")
			@CsvSource(value = { 
				"'Castellano'",
				"'Catalán'",
				"'Inglés'", 
				"'Francés'" 
				})
			void testLanguage(String valor) {
				var fixture = new Language(0, valor);
				assertNotNull(fixture);
				assertTrue(fixture.isValid());
			}
		}

		@Nested
		class KO {
			@DisplayName("El nombre no puede estar en blanco")
			@ParameterizedTest(name = "Name: {0}, Error: {1}")
			@CsvSource(value = { 
				"'','ERRORES: name: must not be blank.'",
				"' ','ERRORES: name: must not be blank.'",
				"'   ','ERRORES: name: must not be blank.'", 
				"12345678901234567890123456789012345678901234567890,'ERRORES: name: size must be between 0 and 20.'" 
				})
			void testLanguage(String valor, String error) {
				var fixture = new Language(0, valor);
				assertNotNull(fixture);
				assertTrue(fixture.isInvalid());
				assertEquals(error, fixture.getErrorsMessage());
			}
		}
	}

}