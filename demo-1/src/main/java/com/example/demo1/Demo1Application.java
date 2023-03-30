package com.example.demo1;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.example.demo1.domains.contracts.services.ActorService;
import com.example.demo1.domains.contracts.services.CategoryService;
import com.example.demo1.domains.contracts.services.FilmActorService;
import com.example.demo1.domains.entities.Category;
import com.example.demo1.domains.entities.FilmCategory;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.demo1", "com.example.demo1.domains.entities" })
public class Demo1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

	@Autowired
	CategoryService srv;

	@Autowired(required = true)
	FilmCategory srv2;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Arrancando aplicación...");

		// COMPROBAR TODA LA PARTE DE LA ENTITY @Category
		// FUNCIONA
		//List<FilmCategory> listaCategories = (List<FilmCategory>) srv2.getCategory();
		//srv.add(new Category(0, "Españolisima", listaCategories));

	}

}
