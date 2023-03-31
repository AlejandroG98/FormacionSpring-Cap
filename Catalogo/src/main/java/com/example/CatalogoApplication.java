package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmCategory;
import jakarta.transaction.Transactional;
import java.util.List;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

//	@Autowired
//	FilmService srv; 
	
	@Autowired
	CategoryService srvCat;

	@Autowired(required = true)
	FilmCategory auxFilmCat;

	@Autowired
	LanguageService srvLang;

	@Autowired(required = true)
	Film auxFilm;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("------------------> Aplicacion iniciada");
//		var peli = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0),1, new BigDecimal(10.0));
//		peli.setRating(Rating.ADULTS_ONLY);
//		peli.addActor(1);
//		peli.addActor(2); 
//		peli.addActor(3);
//		peli.setLength(10);
//		peli.addCategory(2);
//		peli.addCategory(4);		
//		peli = srv.add(peli);
//		peli = srv.getOne(1001).get();
//		peli.removeActor(new Actor(1));
//		peli.removeActor(new Actor(2));
//		peli.addActor(4);
//		peli.removeCategory(peli.getCategories().get(0));
//		peli.addCategory(1);
//		peli.setTitle("Adios mundo");
//		srv.modify(peli);

		// COMPROBAR TODA LA PARTE DE LA ENTITY @Category
		// SI AÑADE A LA DB
		//List<FilmCategory> listaCategorias = (List<FilmCategory>) auxFilmCat.getCategory();
		//srvCat.add(new Category(0, "Alemana", listaCategorias));
		// Si quiero forzar el error: 0 -> 17

		// COMPROBAR TODA LA PARTE DE LA ENTITY @Language
		// SI AÑADE A LA DB
		// int languageId, String name, List<Film> films, List<Film> filmsVO
		// List<Film> peliOriginal = (List<Film>) auxFilm.getLanguage();
		// List<Film> peliSubtituladaFilms = (List<Film>) auxFilm.getLanguageVO();
		// srvLang.add(new Language(0, "Patuno", peliOriginal, peliSubtituladaFilms));
		// Si quiero forzar el error: 0 -> 7
	}

}
