package com.example.demo1.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo1.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.demo1.domains.entities.Language;

// Repositorio de la entidad de dominio
// Extiendo por todos los lados
// Solo accede su servicio de dominio
public interface LanguageRepository extends JpaRepository<Language, Integer>, JpaSpecificationExecutor<Language>, RepositoryWithProjections{
	<T> List<T> findAllBy(Class<T> type);
	
	@Query("SELECT a FROM Language a WHERE a.languageId < :id")
	List<Language> findConJPQL(@Param("id") int languageId);
	@Query(name = "Language.findAll")
	List<Language> findConJPQL();
	@Query(value = "SELECT * from language WHERE language_id < ?1", nativeQuery = true)
	List<Language> findConSQL(int languageId);
}
