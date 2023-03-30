package com.example.demo1.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo1.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.demo1.domains.entities.Actor;
import com.example.demo1.domains.entities.Category;

// Repositorio de la entidad de dominio
// Extiendo por todos los lados
// Solo accede su servicio de dominio
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>, RepositoryWithProjections{
	<T> List<T> findAllBy(Class<T> type);
	
	@Query("SELECT a FROM Category a WHERE a.categoryId < :id")
	List<Category> findConJPQL(@Param("id") int categoryId);
	
	@Query(name = "Category.findAll")
	List<Category> findConJPQL();
	
	@Query(value = "SELECT * from Category WHERE category_id < ?1", nativeQuery = true)
	List<Category> findConSQL(int categoryId);
}
