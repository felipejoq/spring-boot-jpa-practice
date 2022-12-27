package com.uncodigo.springboot.app.models.service;

import java.util.List;

import com.uncodigo.springboot.app.models.dao.IFacturaDao;
import com.uncodigo.springboot.app.models.dao.IProductoDao;
import com.uncodigo.springboot.app.models.entity.Factura;
import com.uncodigo.springboot.app.models.entity.Producto;
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
     */
    private final IClienteDao clienteDao;

    private final IProductoDao productoDao;

    private final IFacturaDao facturaDao;

    public ClienteServiceImpl(IClienteDao clienteDao, IProductoDao productoDao, IFacturaDao facturaDao) {
        this.clienteDao = clienteDao;
        this.productoDao = productoDao;
        this.facturaDao = facturaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteDao.findAll();
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

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase(term);
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchByIdWithFacturas(Long id) {
        return clienteDao.fetchByIdWithFacturas(id);
    }

}
