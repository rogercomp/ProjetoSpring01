package com.iftm.prjreferencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
