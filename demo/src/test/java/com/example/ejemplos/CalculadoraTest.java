package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Ordena los test por nombre si no hay espacios
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
class CalculadoraTest {

	Calculadora calc;

	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	// @Nested Para saber que esta clase son pruebas 
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

			// Con @Disabled hago que no funcione este test
			// Por eso aparece skipped
			@Disabled
			@Test
			void test_Suma_Negativo_Positivo() {
				assertEquals(1, calc.suma(-1, 2));
			}
			
			// {0} Operando 1
			// {1} Operando 2
			// = Resultado
			@ParameterizedTest(name = "{displayName}->{0}+{1} = {2}")
			// Tengo dos cadenas: op1 op2 y resultado
			@CsvSource(value = {"1,1,2","0.1,0.2,0.3","1,2,3","0,0,0","-1,-1,-2"})
			void test_Suma_Parametrizada(double op1, double op2, double rslt) {
				assertEquals(Math.floor(rslt), Math.floor(calc.suma(op1, op2)));
			}
			
			// Test con Mock
			// Lo deshabilito para que no falle en todos los test
			@Disabled
			@Test
			void testSumaMock(){
				Calculadora calc = mock(Calculadora.class);
				// Cuando mockeo (when) si luego pongo otro valor peta
				when(calc.suma(2, 2)).thenReturn(3.0);
				assertEquals(3, calc.suma(1, 1));
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
				assertEquals(1, calc.divide(1, 1.0));
			}
			@Test
			void test_No_Dividir_Cero() {
				assertEquals(1, calc.divide(1, 1.0));
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
