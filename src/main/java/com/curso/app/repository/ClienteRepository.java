package com.curso.app.repository;

import com.curso.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

// Aula 3: a lista em memoria da Aula 2 virou uma interface JpaRepository de verdade.
// O Spring Data JPA gera a implementacao sozinho, so por causa desta assinatura.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
