package com.example.demo1.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo1.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.demo1.domains.entities.FilmActor;
import com.example.demo1.domains.entities.FilmActorPK;

// Repositorio de la entidad de dominio
// Extiendo por todos los lados
// Solo accede su servicio de dominio
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorPK>, JpaSpecificationExecutor<FilmActor>, RepositoryWithProjections{
	<T> List<T> findAllBy(Class<T> type);
	
	@Query("SELECT fa FROM FilmActor fa WHERE fa.id.actorId < :id")
	List<FilmActor> findConJPQL(@Param("id") int actorId);

	@Query(name = "FilmActor.findAll")
	List<FilmActor> findConJPQL();

	@Query(value = "SELECT * FROM film_actor WHERE actor_id < ?1", nativeQuery = true)
	List<FilmActor> findConSQL(int actorId);

}
