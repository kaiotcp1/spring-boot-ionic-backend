package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Categoria;
import com.kaio.apivendas.repositories.CategoriaRepository;
import com.kaio.apivendas.services.exceptions.DataIntegrityException;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;
    public Categoria find(Integer id) {
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return repository.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    };

    public List<Categoria> findAll() {
        return repository.findAll();
    }
}
