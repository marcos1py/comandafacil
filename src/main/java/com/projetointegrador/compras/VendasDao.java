package com.projetointegrador.compras;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendasDao extends JpaRepository<Vendas, Integer> {
}
