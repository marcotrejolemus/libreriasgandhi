package com.literatura.libreriasgandhi.repository;

import com.literatura.libreriasgandhi.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface iLibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);

    Libro findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);



    @Query("SELECT e FROM Libro e ORDER BY e.cantidadDescargas DESC LIMIT 10")
    List<Libro> findTop10ByTituloByCantidadDescargas();


}