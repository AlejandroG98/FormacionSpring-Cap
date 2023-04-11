package com.example.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Language;
import com.example.domains.services.LanguageServiceImpl;
import com.example.exceptions.*;

public class LanguageServiceImplTest {

	private LanguageRepository languageRepositoryMock;
	private LanguageServiceImpl languageService;

	@BeforeEach
	public void setup() {
		languageRepositoryMock = mock(LanguageRepository.class);
		languageService = new LanguageServiceImpl();
	}

	@Test
	public void testAddInvalidLanguage() {
		Language languageToAdd = new Language(0, "");
		when(languageRepositoryMock.existsById(0)).thenReturn(false);

		assertThatThrownBy(() -> languageService.add(languageToAdd)).isInstanceOf(InvalidDataException.class)
				.hasMessage("ERRORES: name: no debe estar vacío.");
	}
	
    @Test
    public void testAddNullLanguage() {
        assertThatThrownBy(() -> languageService.add(null))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("[ERROR] El item no puede ser nulo");
    }
    
}
