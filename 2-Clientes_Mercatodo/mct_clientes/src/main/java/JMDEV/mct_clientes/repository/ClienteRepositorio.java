package JMDEV.mct_clientes.repository;

import JMDEV.mct_clientes.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente,Integer> {
}
