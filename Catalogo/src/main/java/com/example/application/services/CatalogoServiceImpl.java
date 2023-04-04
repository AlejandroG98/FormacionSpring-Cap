package com.example.application.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.NovedadesDTO;

@Service
public class CatalogoServiceImpl {

	@Autowired
	private FilmService filmSrv;

	@Autowired
	private ActorService actorSrv;

	@Autowired
	private CategoryService catSrv;

	@Autowired
	private LanguageService langSrv;

	// DTO = Entra y sale
	public NovedadesDTO novedades(Timestamp fecha) {
		if (fecha == null) {
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		}
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item->FilmEditDTO.from(item)).toList(),
				actorSrv.novedades(fecha).stream().map(item->ActorDTO.from(item)).toList(),
				catSrv.novedades(fecha),
				langSrv.novedades(fecha));
	}

}
