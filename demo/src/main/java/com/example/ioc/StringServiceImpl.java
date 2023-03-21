package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("Local")
public class StringServiceImpl implements StringService {
	private StringRepository dao;
	
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
		System.out.println("Creo StringServiceImpl");
	}
	
	@Override
	public String get(Integer id) {
		return dao.load() + " en local";
	}

	@Override
	public void add(String item) {
		dao.save(item);
	}

	@Override
	public void modify(String item) {
		dao.save(item);
	}

	@Override
	public void remove(Integer id) {
		dao.save(id.toString());
	}

}
