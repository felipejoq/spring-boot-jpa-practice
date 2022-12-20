package com.uncodigo.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.uncodigo.springboot.app.models.entity.Cliente;
/**
 * 
 * No es necesario anotarla, porque como hereda de CrudRepository
 * y esta interfaz ya es un componente de Spring la convierte también en un componente.
 *
 */
public interface IClienteDao extends CrudRepository<Cliente, Long> {}
