package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Estado;
import com.kaio.apivendas.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<Estado> findAll() {
        return repository.findAllByOrderByNome();
    }
}
