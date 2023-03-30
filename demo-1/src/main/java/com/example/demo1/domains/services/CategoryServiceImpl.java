package com.example.demo1.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo1.domains.contracts.repositories.CategoryRepository;
import com.example.demo1.domains.contracts.services.CategoryService;
import com.example.demo1.domains.entities.Category;
import com.example.demo1.exceptions.InvalidDataException;
import com.example.demo1.exceptions.NotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryRepository dao;

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort,type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Category> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Category> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Category> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Category> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
		{
			throw new InvalidDataException("[ERROR] El item no puede ser nulo");
		}
		
		if(item.isInvalid())
		{
			throw new InvalidDataException(item.getErrorsMessage());
		}
		
		if(dao.existsById(item.getCategoryId()))
		{
			throw new DuplicateKeyException(item.getErrorsMessage());
		}
		return dao.save(item);
	}

	@Override
	public Category modify(Category item) throws NotFoundException, InvalidDataException {
		if(item == null)
		{
			throw new InvalidDataException("[ERROR] El item no puede ser nulo");
		}
		
		if(item.isInvalid())
		{
			throw new InvalidDataException(item.getErrorsMessage());
		}
		
		if(!dao.existsById(item.getCategoryId()))
		{
			throw new NotFoundException();
		}
		return dao.save(item);
	}

	@Override
	public void delete(Category item) throws InvalidDataException {
		if(item == null)
		{
			throw new InvalidDataException("[ERROR] El item no puede ser nulo");
		}
		
		deleteById(item.getCategoryId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	
}
