package com.projetointegrador.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MesaDao extends JpaRepository<Mesa, Integer> {
    Optional<Mesa> findByNumero(int numero);
}
