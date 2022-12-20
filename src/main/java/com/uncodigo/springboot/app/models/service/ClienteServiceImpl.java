package com.uncodigo.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uncodigo.springboot.app.models.dao.IClienteDao;
import com.uncodigo.springboot.app.models.entity.Cliente;

@Service("clienteService")
public class ClienteServiceImpl implements IClienteService {
	
	/**
	 * Patrón de diseño "Fachada". Único acceso a los DAO.
	 * Este service funciona como punto de acceso a los DAO (Data Access Object).
	 * Así queda desacoplado el Controlador, y este no accede directamente a las clases de datos o DAO.
	 * 
	 */

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {

		return clienteDao.findAll(pageable);
	}

	
	
}
