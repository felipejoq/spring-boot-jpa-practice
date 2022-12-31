package com.uncodigo.springboot.app.models.dao;

import com.uncodigo.springboot.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
    
}
