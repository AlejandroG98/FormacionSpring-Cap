package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Film;


public interface FilmRepository extends JpaRepository<Film, Integer>,JpaSpecificationExecutor<Film> ,RepositoryWithProjections {
	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);

}
