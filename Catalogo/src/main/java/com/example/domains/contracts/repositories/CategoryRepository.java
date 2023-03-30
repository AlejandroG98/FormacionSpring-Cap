package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Category;

// Repositorio de la entidad de dominio
// Extiendo por todos los lados
// Solo accede su servicio de dominio
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>, RepositoryWithProjections{
	//<T> List<T> findAllBy(Class<T> type);
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	/*
	@Query("SELECT a FROM Category a WHERE a.categoryId < :id")
	List<Category> findConJPQL(@Param("id") int categoryId);
	
	@Query(name = "Category.findAll")
	List<Category> findConJPQL();
	
	@Query(value = "SELECT * from Category WHERE category_id < ?1", nativeQuery = true)
	List<Category> findConSQL(int categoryId);*/
}
