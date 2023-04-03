package com.example.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import com.example.domains.entities.*;
import com.example.domains.entities.Film.Rating;

class FilmTest {

	@Nested
	class TitleDescription {
		@Nested
		class OK {
			@ParameterizedTest(name = "{index} => Title: {0}, Description: {1}, Expected Result: {2}")
			@CsvSource({
					"Joker, In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society, true",
					" , Description is empty, false",
					"The Lord of the Rings: The Fellowship of the Ring, This is the first part of the Lord of the Rings trilogy, which tells the story of the fateful power of the One Ring., true",
					"A, Title is too short, false", "'', Title is empty, false",
					"The Shawshank Redemption, Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency., true",
					"The Hobbit: An Unexpected Journey, A reluctant hobbit, Bilbo Baggins, sets out to the Lonely Mountain with a spirited group of dwarves to reclaim their mountain home, which was stolen by the dragon Smaug., true",
					"Z, Title is too short, false" })
			void testTitleAndDescription(String title, String description, boolean expectedResult) {
				var fixture = new Film(0, description, title);
				assertEquals(expectedResult, fixture.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest(name = "{index} => Title: {0}, Description: {1}, Expected Result: {2}")
			@CsvSource({ "The Lion King, A young lion prince is cast out of his pride by his cruel uncle, true",
					"Avengers, A group of superheroes comes together to stop a powerful villain from destroying the world, true",
					"The Silence of the Lambs, An FBI agent seeks the help of a cannibalistic killer to catch a serial killer, true",
					"Inception, A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO., true", })
			void testTitleAndDescription(String title, String description, boolean expectedResult) {
				var fixture = new Film(0, description, title);
				assertEquals(expectedResult, fixture.isInvalid());
			}
		}
	}

	@Nested
	class ReleaseYear {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'1999'", "'2020'", "'2001'" })
			void testReleaseYear(Short releaseYear) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", releaseYear, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "'0011','ERRORES: releaseYear: must be greater than or equal'",
					"'01','ERRORES: releaseYear: must be greater than or equal'",
					"'0000','ERRORES: releaseYear: must be greater than or equal'" })
			void testReleaseYear(Short releaseYear) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", releaseYear, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isInvalid());
			}
		}
	}

	@Nested
	class RentalDuration {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'1'", "'5'", "'10'" })
			void testRentalDuration(Byte rentalDuration) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), rentalDuration, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "'-1','ERRORES: rentalDuration: must be greater than 0.'",
					"'-100','ERRORES: rentalDuration: must be greater than 0.'",
					"'-10','ERRORES: rentalDuration: must be greater than 0.'" })
			void testRentalDuration(Byte rentalDuration) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), rentalDuration, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isInvalid());
			}
		}
	}

	@Nested
	class RentalRate {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'0.01'", "'0.99'", "'1.99'", })
			void testRentalRate(BigDecimal rentalRate) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, rentalRate, 142, BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "'-0.0','ERRORES: rentalRate: must be greater than 0.00.'",
					"'-2.99','ERRORES: rentalRate: must be greater than 0.00.'",
					"'-100.99','ERRORES: rentalRate: must be greater than 0.00.'", })
			void testRentalRate(BigDecimal rentalRate) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, rentalRate, 142, BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertFalse(item.isValid());
			}
		}
	}

	@Nested
	class Length {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'1'", "'50'", "'200'" })
			void testLength(Integer length) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), length, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "'-1','ERRORES: length: must be greater than 0.'",
					"'-50','ERRORES: length: must be greater than 0.'",
					"'-200','ERRORES: length: must be greater than 0.'" })
			void testLength(Integer length) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), length, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isInvalid());
			}
		}
	}

	@Nested
	class ReplacementCost {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'0.01'", "'1.10'", "'20.99'" })
			void testReplacementCost(BigDecimal replacementCost) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, replacementCost, Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "'-0.01','ERRORES: replacementCost: must be greater than 0.00.'",
					"'-1.10','ERRORES: replacementCost: must be greater than 0.00.'",
					"'-20.99','ERRORES: replacementCost: must be greater than 0.00.'" })
			void testReplacementCost(BigDecimal replacementCost) {
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, replacementCost, Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertFalse(item.isValid());
			}
		}
	}

	@Nested
	class FirstLanguage {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'Español'", "'Inglés'", "'Catalán'" })
			void testFirstLanguage(String language) {
				var langAux = new Language(0, language);
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, langAux,
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "' ','ERRORES: must not be blank.'", "'','ERRORES: must not be blank.'",
					"'  ','ERRORES: must not be blank.'",
					"12345678901234567890123456789012345678901234567890,'ERRORES: size must be between 0 and 20.'" })
			void testFirstLanguage(String language) {
				var langAux = new Language(0, language);
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, langAux,
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertFalse(item.isInvalid());
			}
		}
	}

	@Nested
	class LanguageVO {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'Español'", "'Inglés'", "'Catalán'" })
			void testFirstLanguage(String language) {
				var langAux = new Language(0, language);
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, langAux,
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "' ','ERRORES: must not be blank.'", "'','ERRORES: must not be blank.'",
					"'  ','ERRORES: must not be blank.'",
					"12345678901234567890123456789012345678901234567890,'ERRORES: size must be between 0 and 20.'" })
			void testFirstLanguage(String language) {
				var langAux = new Language(0, language);
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						langAux, (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99),
						Rating.ADULTS_ONLY);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}
	}

	@Nested
	class Ratings {
		@Nested
		class OK {
			@ParameterizedTest
			@CsvSource(value = { "'G'", "'PG'", "'PG-13'" })
			void testRating(String ratingString) {
				Film.Rating rating = Film.Rating.getEnum(ratingString);
				var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, new Language(0),
						new Language(1), (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99), rating);
				assertNotNull(item);
				assertTrue(item.isValid());
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = { "'G1'", "'PG2'", "'PG-133'" })
			void testInvalidRating(String ratingString) {
				assertThrows(IllegalArgumentException.class, () -> Film.Rating.getEnum(ratingString));
			}
		}
	}

}