package com.tutorial.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.login.bean.UsuarioBean;

public interface UsuarioRepository extends JpaRepository<UsuarioBean, Long> {

    Optional<UsuarioBean> findByEmail(String email);
}
