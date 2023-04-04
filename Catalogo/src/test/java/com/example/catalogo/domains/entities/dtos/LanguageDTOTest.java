package com.example.catalogo.domains.entities.dtos;
import com.example.domains.entities.dtos.LanguageDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.*;

public class LanguageDTOTest {


    @Test
    @DisplayName("Test from()")
    void testFrom() {
        LanguageDTO languageDTO = LanguageDTO.from(new Language(1, "Espaninglish"));
        assertEquals(LanguageDTO.class, languageDTO.getClass());

        assertEquals(1, languageDTO.getLanguageId());
        assertEquals("Espaninglish", languageDTO.getName());
    }

    @Test
    @DisplayName("Test to()")
    void testTo() {
        Language language = LanguageDTO.from(new LanguageDTO(0,"Armenio"));
        assertEquals(Language.class, language.getClass());

        assertEquals(0, language.getLanguageId());
        assertEquals("Armenio", language.getName());
    }

}
