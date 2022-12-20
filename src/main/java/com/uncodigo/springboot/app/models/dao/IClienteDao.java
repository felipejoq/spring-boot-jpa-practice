package com.uncodigo.springboot.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.repository.PagingAndSortingRepository;

import com.uncodigo.springboot.app.models.entity.Cliente;
/**
 * 
 * No es necesario anotarla, porque como hereda de CrudRepository
 * y esta interfaz ya es un componente de Spring la convierte también en un componente.
 * 
 * PagingAndSortingRepository hereda de CrudRepository pero además sirve para paginar
 * los resultados JPA.
 *
 */
public interface IClienteDao extends JpaRepository<Cliente, Long> {}
