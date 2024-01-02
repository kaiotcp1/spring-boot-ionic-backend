package com.kaio.apivendas.services;

import com.kaio.apivendas.domain.*;
import com.kaio.apivendas.domain.enums.EstadoPagamento;
import com.kaio.apivendas.repositories.ItemPedidoRepository;
import com.kaio.apivendas.repositories.PagamentoRepository;
import com.kaio.apivendas.repositories.PedidoRepository;
import com.kaio.apivendas.security.UserSS;
import com.kaio.apivendas.services.exceptions.AuthorizationException;
import com.kaio.apivendas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        UserSS user = UserService.authenticated();
        if(user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC; // Valor padrão

        if ("DESC".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, sortDirection, orderBy);
        Cliente cliente = clienteService.find(user.getId());
        return repository.findByCliente(cliente, pageRequest);
    }
}
