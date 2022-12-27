package com.uncodigo.springboot.app.models.dao;

import com.uncodigo.springboot.app.models.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaDao extends JpaRepository<Factura, Long> {
}
