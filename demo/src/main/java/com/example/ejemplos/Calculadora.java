package com.example.ejemplos;

public class Calculadora {
	public double suma(double a, double b) {
		return a + b;
	}

	public double divide(double a, double b){
		if(b == 0) {
			throw new ArithmeticException ("No puedes dividir entre 0 ");
		}
		return a / b;
	}
}
