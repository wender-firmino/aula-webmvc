package com.curso.app.repository;

import com.curso.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Aula 3: a lista em memoria da Aula 2 virou uma interface JpaRepository de verdade.
// O Spring Data JPA gera a implementacao sozinho, so por causa desta assinatura.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Aula 5 (2.4): HQL fala de entidades e atributos Java (Cliente, c.nome),
    // nunca de tabelas e colunas (cliente, nome_completo) - por isso a consulta
    // continua igual mesmo com @Column(name = "nome_completo") na Aula 4.
    // :trecho e um parametro nomeado; @Param liga o valor do metodo a ele.
    // O % antes e depois faz a busca funcionar como um "contem" (equivalente ao LIKE do SQL).
    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:trecho%")
    List<Cliente> buscarPorTrechoDoNome(@Param("trecho") String trecho);

    // Aula 7 (slide 17): Query Method - o Spring Data monta a consulta a
    // partir do nome do metodo (existsBy + Email), sem precisar de @Query aqui.
    boolean existsByEmail(String email);
}
