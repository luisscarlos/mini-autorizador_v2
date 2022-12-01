package com.miniautorizador.repository;

import com.miniautorizador.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, UUID> {

    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
