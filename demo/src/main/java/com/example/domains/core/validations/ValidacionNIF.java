package com.example.domains.core.validations;

public class ValidacionNIF {
	public boolean NIFcheck(String NIF) {
		return NIF.length() != 9 ? false : !NIF.matches("\\d{8}[a-zA-Z]") ? false : true;
	}
}
