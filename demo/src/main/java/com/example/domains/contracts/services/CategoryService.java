package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.services.contracts.ProjectionDomainService;
import com.example.domains.entities.Category;

public interface CategoryService extends ProjectionDomainService<Category, Integer>{
	List<Category> novedades(Timestamp fecha);
}
