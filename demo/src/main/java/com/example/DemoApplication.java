package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorService srv;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
	
		// ERROR Porque la id=1 ya existe
		srv.add(new Actor(1, "KK", "KKK"));
		
		//srv.add(new Actor(202, "4", "d"));
	}
}
