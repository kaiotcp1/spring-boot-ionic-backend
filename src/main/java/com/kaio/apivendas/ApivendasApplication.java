package com.kaio.apivendas;

import com.kaio.apivendas.domain.Categoria;
import com.kaio.apivendas.domain.Cidade;
import com.kaio.apivendas.domain.Estado;
import com.kaio.apivendas.domain.Produto;
import com.kaio.apivendas.repositories.CategoriaRepository;
import com.kaio.apivendas.repositories.CidadeRepository;
import com.kaio.apivendas.repositories.EstadoRepository;
import com.kaio.apivendas.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ApivendasApplication  implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;



    public static void main(String[] args) {
        SpringApplication.run(ApivendasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Imressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);

        // Acessando os atríbutos e os atualizando de forma bidirecional

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));


        //


        Estado est1 = new Estado(null, "Mineras Gerais");
        Estado est2 = new Estado(null, "São paulo");

        Cidade c1 = new Cidade(null, "Uberlandia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
    }
}
