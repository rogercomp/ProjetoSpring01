package com.iftm.prjreferencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
