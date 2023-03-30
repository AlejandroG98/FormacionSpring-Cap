package com.example.demo1.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo1.domains.contracts.repositories.LanguageRepository;
import com.example.demo1.domains.contracts.services.LanguageService;
import com.example.demo1.domains.entities.Language;
import com.example.demo1.exceptions.InvalidDataException;
import com.example.demo1.exceptions.NotFoundException;

@Service
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	LanguageRepository dao;

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Language> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Language> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Language> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Language> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Language add(Language item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
		{
			throw new InvalidDataException("[ERROR] El item no puede ser nulo");
		}
		
		if(item.isInvalid())
		{
			throw new InvalidDataException(item.getErrorsMessage());
		}
		
		if(dao.existsById(item.getLanguageId()))
		{
			throw new DuplicateKeyException(item.getErrorsMessage());
		}
		return dao.save(item);
	}

	@Override
	public Language modify(Language item) throws NotFoundException, InvalidDataException {
		if(item == null)
		{
			throw new InvalidDataException("[ERROR] El item no puede ser nulo");
		}
		
		if(item.isInvalid())
		{
			throw new InvalidDataException(item.getErrorsMessage());
		}
		
		if(dao.existsById(item.getLanguageId()))
		{
			throw new DuplicateKeyException(item.getErrorsMessage());
		}
		return dao.save(item);
	}

	@Override
	public void delete(Language item) throws InvalidDataException {
		if(item == null)
		{
			throw new InvalidDataException("[ERROR] El item no puede ser nulo");
		}
		
		deleteById(item.getLanguageId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	
	
	
}
