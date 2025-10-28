package br.com.serratec.commerce.repository;

import br.com.serratec.commerce.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendedorRepository extends JpaRepository<Vendedor, Long> {
}