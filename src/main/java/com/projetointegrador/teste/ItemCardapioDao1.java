package com.projetointegrador.teste;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCardapioDao1 extends JpaRepository<ItemDoCardapio, Integer>{
    
}
