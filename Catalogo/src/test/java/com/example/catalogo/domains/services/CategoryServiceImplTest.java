package com.example.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import com.example.domains.entities.Category;
import com.example.exceptions.InvalidDataException;
import com.example.domains.contracts.repositories.*;
import com.example.domains.services.*;

public class CategoryServiceImplTest {

    private CategoryRepository categoryRepositoryMock = mock(CategoryRepository.class);
    private CategoryServiceImpl categoryService = new CategoryServiceImpl();

    static Collection<Arguments> duplicateCategoryDataProvider() {
        return Arrays.asList(
            Arguments.of("Electronica", "Electronica"),
            Arguments.of("Moda", "Moda"),
            Arguments.of("Libros", "Libros")
        );
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void testAddCategoryInvalidName(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        assertThrows(InvalidDataException.class, () -> {
            categoryService.add(category);
        });
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "Electronica", "Moda", "Libros" })
    void testAddCategoryInvalidArguments(String categoryName) {
        assertThrows(InvalidDataException.class, () -> {
            categoryService.add(null);
        });
    }
    
    @Test
    void testGetOneCategoryNotFound() {
        when(categoryRepositoryMock.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.getOne(1))
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    void testAddCategoryDuplicateKey() {
        when(categoryRepositoryMock.existsById(new Category(0, "Humor Amarillo").getCategoryId())).thenReturn(true);
        assertThatThrownBy(() -> categoryService.add(new Category(0, "Humor Amarillo")))
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    void testAddCategoryInvalidData() {
        assertThatThrownBy(() -> categoryService.add(new Category(0, "")))
                .isInstanceOf(InvalidDataException.class);
    }

}
