package com.example.ejemplos;

public class ValidacionNIF {

	// Tenemos que comprobar un NIF 8 numeros y 1 letra
	public boolean NIFcheck(String NIF) {
		return NIF.length() != 9 ? false : !NIF.matches("\\d{8}[a-zA-Z]") ? false : true;
	}
}