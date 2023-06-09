package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class ActorServiceImpl implements ActorService{

	@Autowired
	ActorRepository dao;

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return (List<T>) dao.findAllBy(type);
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
	public Iterable<Actor> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Actor> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Actor> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Actor> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		if(dao.existsById(item.getActorId()))
			throw new DuplicateKeyException(item.getErrorsMessage());
		
		return dao.save(item);
	}

	@Override
	public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage());
		if(!dao.existsById(item.getActorId()))
			throw new NotFoundException();
		
		return dao.save(item);
	}

	@Override
	public void delete(Actor item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(item.getActorId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Actor> novedades(@NonNull Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}
	
	@Transactional
	public String updateActor(Integer id,String firstname,String lastname){
		Actor act=dao.getReferenceById(id);
		if(dao.existsById(id)) {
			act.setFirstName(firstname.toUpperCase());
			act.setLastName(lastname.toUpperCase());
			dao.save(act);
			return "Actualización éxitosa";
		}else {
			return "Actualizacion sin éxito";
		}
	}
	
}
