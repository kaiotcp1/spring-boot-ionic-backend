package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Produto;
import com.kaio.apivendas.repositories.ProdutoRepository;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto find(Integer id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

}
