package com.example.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.example.demo1.domains.contracts.services.CategoryService;
import com.example.demo1.domains.contracts.services.LanguageService;
import com.example.demo1.domains.entities.Film;
import com.example.demo1.domains.entities.FilmCategory;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.demo1", "com.example.demo1.domains.entities" })
public class Demo1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

	@Autowired
	CategoryService srvCat;

	@Autowired(required = true)
	FilmCategory auxFilmCat;
	
	@Autowired
	LanguageService srvLang;
	
	@Autowired(required = true)
	Film auxFilm;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Arrancando aplicación...");

		// COMPROBAR TODA LA PARTE DE LA ENTITY @Category
		// SI AÑADE A LA DB
		//List<FilmCategory> listaCategorias = (List<FilmCategory>) auxFilmCat.getCategory();
		//srvCat.add(new Category(0, "Españolisima", listaCategorias));
		// Si quiero forzar el error: 0 -> 17
		
		// COMPROBAR TODA LA PARTE DE LA ENTITY @Language
		// SI AÑADE A LA DB
		// int languageId, String name, List<Film> films, List<Film> filmsVO
		//List<Film> peliOriginal = (List<Film>) auxFilm.getLanguage();
		//List<Film> peliSubtituladaFilms = (List<Film>) auxFilm.getLanguageVO();
		//srvLang.add(new Language(0, "Patuno", peliOriginal, peliSubtituladaFilms));
		// Si quiero forzar el error: 0 -> 7
			

	}

}
