package com.example.domains.entities.dtos;

import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

// MUTABLE = @Data (Get&Set)
// INMUTABLE = SOLO SALIDA (Get)
// Entidades = Interior / DTO = Exterior
@AllArgsConstructor @NoArgsConstructor(force = true) @Value 
public class LanguageDTO {
	@NotBlank
	@NotNull
	@Positive
	@JsonProperty("languageId")
	private int languageId;
	
	@NotNull
	@NotBlank
	@Size(min = 2, max=20)
	@JsonProperty("name")
	private String name;

	
	public static LanguageDTO from(Language target) {
		return new LanguageDTO(target.getLanguageId(),target.getName());
	}
	
	public static Language from(LanguageDTO target) {
		return new Language(target.getLanguageId(),target.getName());
	}
	
}
