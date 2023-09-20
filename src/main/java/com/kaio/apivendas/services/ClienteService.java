package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Categoria;
import com.kaio.apivendas.domain.Cliente;
import com.kaio.apivendas.repositories.CategoriaRepository;
import com.kaio.apivendas.repositories.ClienteRepository;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    public Cliente find(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
}
