package com.kaio.apivendas;

import com.kaio.apivendas.domain.*;
import com.kaio.apivendas.domain.enums.EstadoPagamento;
import com.kaio.apivendas.domain.enums.TipoCliente;
import com.kaio.apivendas.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class ApivendasApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApivendasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
