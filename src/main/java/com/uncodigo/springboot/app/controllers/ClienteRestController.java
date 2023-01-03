package com.uncodigo.springboot.app.controllers;

import com.uncodigo.springboot.app.models.entity.Cliente;
import com.uncodigo.springboot.app.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    @Qualifier("clienteService")
    private IClienteService clienteService;

    @GetMapping(value = "/listar")
    public List<Cliente> listar() {

        return clienteService.findAll();
    }

}
