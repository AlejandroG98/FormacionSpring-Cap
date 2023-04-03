package com.example.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.example.domains.entities.*;
import com.example.domains.entities.Film.Rating;

class FilmTest {

	private static Language firstLang = new Language(0, "Armenio");
    private static Language secondLang = new Language(0, "Serbio");
    
	@Test
	void testIsValidShort() {
		var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994,
				new Language(0, "Armenio"), new Language(0, "Serbio"), (byte) 3, BigDecimal.valueOf(2.99), 142,
				BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
		assertTrue(item.isValid());
	}

	@DisplayName("Validar Titulo y Descripción - Valido")
	@ParameterizedTest(name = "{index} => Title: {0}, Description: {1}, Expected Result: {2}")
	@CsvSource({
	        "Joker, In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society, true",
	        " , Description is empty, false",
	        "The Lord of the Rings: The Fellowship of the Ring, This is the first part of the Lord of the Rings trilogy, which tells the story of the fateful power of the One Ring., true",
	        "A, Title is too short, false",
	        "'', Title is empty, false",
	        "The Shawshank Redemption, Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency., true",
	        "The Hobbit: An Unexpected Journey, A reluctant hobbit, Bilbo Baggins, sets out to the Lonely Mountain with a spirited group of dwarves to reclaim their mountain home, which was stolen by the dragon Smaug., true",
	        "Z, Title is too short, false"
	})
	void testTitleAndDescriptionValid(String title, String description, boolean expectedResult) {
	    var fixture = new Film(0, description, title);
	    assertEquals(expectedResult, fixture.isValid());
	}
	
	@DisplayName("Validar Titulo y Descripción - Invalido")
	@ParameterizedTest(name = "{index} => Title: {0}, Description: {1}, Expected Result: {2}")
	@CsvSource({
	        "The Lion King, A young lion prince is cast out of his pride by his cruel uncle, true",
	        "Avengers, A group of superheroes comes together to stop a powerful villain from destroying the world, true",
	        "The Silence of the Lambs, An FBI agent seeks the help of a cannibalistic killer to catch a serial killer, true",
	        "Inception, A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO., true",
	})
	void testTitleAndDescriptionInvalid(String title, String description, boolean expectedResult) {
	    var fixture = new Film(0, description, title);
	    assertEquals(expectedResult, fixture.isInvalid());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"'1895','ERRORES: releaseYear: must be greater than or equal'",
			"'2050','ERRORES: releaseYear: must be greater than or equal'",
			})
    void testReleaseYear(Short releaseYear) {
        var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", releaseYear, firstLang, secondLang,
                (byte) 3, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
        assertNotNull(item);
        assertFalse(item.isInvalid());
    }
//
//    @ParameterizedTest
//    @ValueSource(bytes = { 1, 5, 10 })
//    void testRentalDuration(Byte rentalDuration) {
//        var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, firstLang, secondLang,
//                rentalDuration, BigDecimal.valueOf(2.99), 142, BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
//        assertNotNull(item);
//        assertFalse(item.isValid());
//        assertEquals("ERRORES: rentalDuration: must be greater than 0.", item.getErrorsMessage());
//    }
//
//    @ParameterizedTest
//    @ValueSource(doubles = { 0.0, 2.99, 100.99 })
//    void testRentalRate(BigDecimal rentalRate) {
//        var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, firstLang, secondLang,
//                (byte) 3, rentalRate, 142, BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
//        assertNotNull(item);
//        assertFalse(item.isValid());
//        assertEquals("ERRORES: rentalRate: must be greater than 0.00.", item.getErrorsMessage());
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = { 0, 50, 200 })
//    void testLength(Integer length) {
//        var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, firstLang, secondLang,
//                (byte) 3, BigDecimal.valueOf(2.99), length, BigDecimal.valueOf(20.99), Rating.ADULTS_ONLY);
//        assertNotNull(item);
//        assertFalse(item.isValid());
//        assertEquals("ERRORES: length: must be greater than 0.", item.getErrorsMessage());
//    }
//
//    @ParameterizedTest
//    @ValueSource(doubles = { -1.0, 0.0, 20.99 })
//    void testReplacementCost(BigDecimal replacementCost) {
//        var item = new Film(0, "Este es el nombre", "Y esta es la descripción.", (short) 1994, firstLang, secondLang,
//                (byte) 3, BigDecimal.valueOf(2.99), 142, replacementCost, Rating.ADULTS_ONLY);
//        assertNotNull(item);
//        assertFalse(item.isValid());
//        assertEquals("ERRORES: replacementCost: must be greater than 0.00.", item.getErrorsMessage());
//    }





}