package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	Calculadora calc;

	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	// Para saber que esta clase son pruebas @Nested
	// @DisplayName = nombre
	@Nested
	@DisplayName("Pruebas de suma")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class Suma {

		@Nested
		class OK {
			@Test
			void test_Suma_Positivo_Positivo() {
				var rslt = calc.suma(2, 2);
				assertEquals(4, rslt);
			}

			@Test
			void test_Suma_Positivo_Negativo() {
				var rslt = calc.suma(2, -1);
				assertEquals(1, rslt);
			}

			@Test
			void test_Suma_Negativo_Positivo() {

				assertEquals(1, calc.suma(-1, 2));
			}
		}

		@Nested
		class KO {

		}
	}

	@Nested
	@DisplayName("Pruebas de división")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class Divide {

		@Nested
		class OK {
			@Test
			void test_Dividir_Cero() {
				assertEquals(Double.POSITIVE_INFINITY, calc.divide(1, 0.0));
			}
			@Test
			void test_No_Dividir_Cero() {
				var rslt = ;
				assertEquals(Double.POSITIVE_INFINITY, calc.divide(1, 0.0));
			}
		}

		@Nested
		class KO {
			@Test
			void test_Dividir_Cero() {
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0.0));
			}
		}
	}
}
