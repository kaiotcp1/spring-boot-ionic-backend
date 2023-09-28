package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.Categoria;
import com.kaio.apivendas.domain.Pedido;
import com.kaio.apivendas.repositories.PedidoRepository;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;
    public Pedido find(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
}
