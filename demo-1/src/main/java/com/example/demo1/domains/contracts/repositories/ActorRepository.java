package com.example.demo1.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo1.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.demo1.domains.entities.Actor;

// Repositorio de la entidad de dominio
// Extiendo por todos los lados
// Solo accede su servicio de dominio
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections{
	<T> List<T> findAllBy(Class<T> type);
	
	@Query("SELECT a FROM Actor a WHERE a.actorId < :id")
	List<Actor> findConJPQL(@Param("id") int actorId);
	@Query(name = "Actor.findAll")
	List<Actor> findConJPQL();
	@Query(value = "SELECT * from actor WHERE actor_id < ?1", nativeQuery = true)
	List<Actor> findConSQL(int actorId);
}