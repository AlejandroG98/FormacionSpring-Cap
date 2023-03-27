package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

class ValidacionNIFTest {

	ValidacionNIF nif;

	@BeforeEach
	void setUp() throws Exception {
		nif = new ValidacionNIF();
	}

	@Nested
	@DisplayName("Pruebas para saber si es valido o no un NIF")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class isNIF {

		@ParameterizedTest
		@ValueSource(strings = {"12345678A", "12345679F"})
		void casosValidos(String NIF) {
			assertAll("Validar propiedades", 
					() -> assertNotNull(nif.NIFcheck(NIF)),
					() -> assertTrue(nif.NIFcheck(NIF))
					);
		}
		
		@ParameterizedTest
		@ValueSource(strings = {"12345678", "1234567A","12345678AA","1234567AA"})
		void casosInvalidos(String NIF) {
			assertAll("Validar propiedades", 
					() -> assertNotNull(nif.NIFcheck(NIF)), 
					() -> assertFalse(nif.NIFcheck(NIF)));
		}
	}
}
