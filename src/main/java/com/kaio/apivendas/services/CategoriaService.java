package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Categoria;
import com.kaio.apivendas.dto.CategoriaDTO;
import com.kaio.apivendas.repositories.CategoriaRepository;
import com.kaio.apivendas.services.exceptions.DataIntegrityException;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        Categoria newObj = find(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return repository.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        Sort.Direction sortDirection = Sort.Direction.ASC; // Valor padrão

        if ("DESC".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
        return repository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO objDTO) {
        return new Categoria(objDTO.getId(), objDTO.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }
}
