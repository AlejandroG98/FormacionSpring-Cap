package com.example.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Film;
import com.example.domains.services.FilmServiceImpl;
import com.example.exceptions.InvalidDataException;

public class FilmServiceImplTest {
    
    @Mock
    private FilmRepository filmRepositoryMock;
    
    @InjectMocks
    private FilmServiceImpl filmService;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFilmValid() throws Exception {
        Film filmToAdd = new Film();
        filmToAdd.setTitle("Titulazo");
        
        try {
            filmService.add(filmToAdd);
        } catch (InvalidDataException e) {
            assertThat(e).hasMessageContaining(filmToAdd.getErrorsMessage());
        }
    }
    
    @Test
    void testAddFilmInvalid() throws Exception {
        Film filmToAdd = new Film();
        filmToAdd.setTitle("");
        
        try {
            filmService.add(filmToAdd);
        } catch (InvalidDataException e) {
            assertThat(e).hasMessageContaining(filmToAdd.getErrorsMessage());
        }
    }
    
    @Test
    void testGetAll() {
        List<Film> films = Arrays.asList(new Film(), new Film());
        when(filmRepositoryMock.findAll()).thenReturn(films);

        List<Film> returnedFilms = filmService.getAll();

        assertThat(returnedFilms).isEqualTo(films);
    }
  
    @Test
    void testDeleteFilm() {
        Film filmToDelete = new Film();
        filmToDelete.setFilmId(1);
        doNothing().when(filmRepositoryMock).deleteById(filmToDelete.getFilmId());

        assertDoesNotThrow(() -> filmService.delete(filmToDelete));

        verify(filmRepositoryMock).deleteById(filmToDelete.getFilmId());
    }
    
    @Test
    void testDeleteFilmInvalidDataException() {
        Film filmToDelete = null;

        assertThrows(InvalidDataException.class, () -> filmService.delete(filmToDelete));
    }
    
    @Test
    void testDeleteFilmById() {
        doNothing().when(filmRepositoryMock).deleteById(1);
        assertDoesNotThrow(() -> filmService.deleteById(1));
        verify(filmRepositoryMock).deleteById(1);
    }
    
    @Test
    void testNovedades() {
        Timestamp fecha = Timestamp.valueOf(LocalDateTime.now());
        List<Film> films = Arrays.asList(new Film(), new Film());
        when(filmRepositoryMock.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha)).thenReturn(films);
        List<Film> returnedFilms = filmService.novedades(fecha);
        assertThat(returnedFilms).isEqualTo(films);
    }

}