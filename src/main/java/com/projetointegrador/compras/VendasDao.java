package com.projetointegrador.compras;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendasDao extends JpaRepository<Vendas, Integer> {
    List<Vendas> findByDataInicioBetween(Date dataInicio, Date dataFinal);
}
