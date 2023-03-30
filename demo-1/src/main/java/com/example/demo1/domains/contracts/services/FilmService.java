package com.example.demo1.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.demo1.domains.core.services.contracts.ProjectionDomainService;
import com.example.demo1.domains.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer> {
	List<Film> novedades(Timestamp fecha);
}
