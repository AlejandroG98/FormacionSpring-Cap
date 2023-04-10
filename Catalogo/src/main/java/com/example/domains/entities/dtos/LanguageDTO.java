package com.example.domains.entities.dtos;

import com.example.domains.entities.Language;

import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
//@AllArgsConstructor @NoArgsConstructor
@Value 
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
