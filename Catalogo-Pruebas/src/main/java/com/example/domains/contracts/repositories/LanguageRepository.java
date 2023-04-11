package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Language;

// Repositorio de la entidad de dominio
// Extiendo por todos los lados
// Solo accede su servicio de dominio

public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>, RepositoryWithProjections{
	//<T> List<T> findAllBy(Class<T> type);
	List<Language> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	/*@Query("SELECT a FROM Language a WHERE a.languageId < :id")
	List<Language> findConJPQL(@Param("id") int languageId);
	@Query(name = "Language.findAll")
	List<Language> findConJPQL();
	@Query(value = "SELECT * from language WHERE language_id < ?1", nativeQuery = true)
	List<Language> findConSQL(int languageId);*/
}
