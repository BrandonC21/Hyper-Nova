package org.example.hypernova.persistencia.repositorios;

import org.example.hypernova.persistencia.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepo extends JpaRepository<Cliente, Integer> {
  //Buscar Cliente por RFC
    Optional<Cliente> findByrfc(String rfc);
}
