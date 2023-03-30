package com.example.demo1.domains.entities.dtos;

import java.util.List;
import com.example.demo1.domains.entities.Film;
import com.example.demo1.domains.entities.Language;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
@Value @AllArgsConstructor @NoArgsConstructor
public class LanguageDTO {
	
	private int languageId;
	private String name;
	private List<Film> films;
	private List<Film> filmsVO;
	
	public static LanguageDTO from(Language target) {
		return new LanguageDTO(target.getLanguageId(),target.getName(),target.getFilms(),target.getFilmsVO());
	}
	
	public static Language from(LanguageDTO target) {
		return new Language(target.getLanguageId(),target.getName(),target.getFilms(),target.getFilmsVO());
	}
	
}
