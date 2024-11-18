package com.alura.literalura.service;

import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional
    public void eliminarDuplicados() {
        libroRepository.eliminarDuplicados();
        System.out.println("Duplicados eliminados correctamente.");
    }
}
