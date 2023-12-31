package com.kaio.apivendas.resources;

import com.kaio.apivendas.domain.Categoria;
import com.kaio.apivendas.domain.Pedido;
import com.kaio.apivendas.dto.CategoriaDTO;
import com.kaio.apivendas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> find(@PathVariable Integer id) {
        Pedido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    //@GetMapping
    //public ResponseEntity<List<Pedido>> findAll() {
      //  List<Pedido> list = service.findAll();
        //return ResponseEntity.ok().body(list);
    //}

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "sortDirection", defaultValue = "DESC") String direction) {
        Page<Pedido> list = service.findPage(page, linesPerPage, direction, orderBy);
        return ResponseEntity.ok().body(list);
    }

}
