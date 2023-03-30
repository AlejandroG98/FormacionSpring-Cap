package com.example.demo1.domains.entities.dtos;

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

	
	public static LanguageDTO from(Language target) {
		return new LanguageDTO(target.getLanguageId(),target.getName());
	}
	
	public static Language from(LanguageDTO target) {
		return new Language(target.getLanguageId(),target.getName());
	}
	
}
