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
        libroRepository.eliminarDuplicados(); // Método que ejecuta la eliminación
        System.out.println("__________-----__________-----__________-----__________-----__________");
    }

}
