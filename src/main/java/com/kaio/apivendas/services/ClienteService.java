package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Cliente;
import com.kaio.apivendas.dto.ClienteDTO;
import com.kaio.apivendas.repositories.ClienteRepository;
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
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    public Cliente find(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        Sort.Direction sortDirection = Sort.Direction.ASC; // Valor padrão

        if ("DESC".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
        return repository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
