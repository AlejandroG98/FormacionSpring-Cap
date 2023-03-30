package com.example.demo1.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.demo1.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, RepositoryWithProjections {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
