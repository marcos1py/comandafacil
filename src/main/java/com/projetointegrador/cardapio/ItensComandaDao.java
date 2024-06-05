package com.projetointegrador.cardapio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensComandaDao extends JpaRepository<ItemCardapio, Integer>{
    
}

