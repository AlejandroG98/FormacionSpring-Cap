package com.example.catalogo.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.services.ActorServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

@TestPropertySource(locations = "/application-test.properties")
@DataJpaTest
@ComponentScan(basePackages = "com.example")
@DisplayName("ActorService Tests")
public class ActorServiceImplTest {

    @Mock
    ActorRepository actorRepositoryMock;

    @InjectMocks
    ActorServiceImpl actorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test general")
    void testGetAll() {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "Mario", "Cansas"));
        actors.add(new Actor(2, "Angel", "Diablo"));
        actors.add(new Actor(3, "Tom", "Tam"));
        when(actorRepositoryMock.findAll()).thenReturn(actors);
        List<Actor> actualActors = actorService.getAll();
        assertEquals(actors.size(), actualActors.size());
        assertTrue(actualActors.containsAll(actors));
    }

    @Test
    @DisplayName("Test getOne()")
    void testGetOne() {
        Actor expectedActor = new Actor(1, "Mario", "Cansas");
        when(actorRepositoryMock.findById(1)).thenReturn(Optional.of(expectedActor));
        assertTrue(actorService.getOne(1).isPresent());
        assertEquals(expectedActor, actorService.getOne(1).get());
    }

    @Test
    @DisplayName("Test add() con actor nulo")
    void testAddNullActor() {
        assertThrows(InvalidDataException.class, () -> {
            actorService.add(null);
        });
        verify(actorRepositoryMock, never()).save(any(Actor.class));
    }

    @Test
    @DisplayName("Test add() con actor invalido ")
    void testAddInvalidActor() {
        assertThrows(InvalidDataException.class, () -> {
            actorService.add(new Actor());
        });
        verify(actorRepositoryMock, never()).save(any(Actor.class));
    }

    @Test
    @DisplayName("Test add() con key duplicada")
    void testAddDuplicateActor() {
        Actor existingActor = new Actor(1, "Mario", "Cansas");
        when(actorRepositoryMock.existsById(existingActor.getActorId())).thenReturn(true);
        assertThrows(InvalidDataException.class, () -> {
            actorService.add(existingActor);
        });
        verify(actorRepositoryMock, never()).save(any(Actor.class));
    }

    @Test
    @DisplayName("Test add()")
    void testAdd() throws DuplicateKeyException, InvalidDataException {
        Actor newActor = new Actor(4, "Paco", "Mer".toUpperCase());
        when(actorRepositoryMock.existsById(newActor.getActorId())).thenReturn(false);
        when(actorRepositoryMock.save(newActor)).thenReturn(newActor);
        assertEquals(newActor, actorService.add(newActor));
        verify(actorRepositoryMock, times(1)).save(actorService.add(newActor));
    }


    @Test
    @DisplayName("Test modify() con actor nulo")
    void testModifyNullActor() {
        assertThrows(InvalidDataException.class, () -> {
            actorService.modify(null);
        });
        verify(actorRepositoryMock, never()).save(any(Actor.class));
    }

    @Test
    @DisplayName("Test modify() con actor invalido")
    void testModifyInvalidActor() {
        assertThrows(InvalidDataException.class, () -> {
            actorService.modify(new Actor());
        });
        verify(actorRepositoryMock, never()).save(any(Actor.class));
    }
    
    @Test
    @DisplayName("Test delete()")
    void testDelete() throws InvalidDataException {
        Actor actorToDelete = new Actor(1, "Benito", "Benita");
        when(actorRepositoryMock.existsById(actorToDelete.getActorId())).thenReturn(true);
        doNothing().when(actorRepositoryMock).deleteById(actorToDelete.getActorId());
        actorService.delete(actorToDelete);
        verify(actorRepositoryMock, times(1)).deleteById(actorToDelete.getActorId());
    }

    @Test
    @DisplayName("Test delete() con parametro nulo")
    void testDeleteWithNullParameter() {
        assertThrows(InvalidDataException.class, () -> {
            actorService.delete(null);
        });
    }

    @Test
    @DisplayName("Test delete() con actor que no existe")
    void testDeleteWithNonExistingActor() {
        when(actorRepositoryMock.existsById(1)).thenReturn(true);
        assertThrows(InvalidDataException.class, () -> {
            actorService.delete(null);
        });
    }

}