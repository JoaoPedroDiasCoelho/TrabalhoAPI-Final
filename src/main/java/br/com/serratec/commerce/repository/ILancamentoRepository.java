package br.com.serratec.commerce.repository;

import br.com.serratec.commerce.models.LancamentoVendas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILancamentoRepository extends JpaRepository<LancamentoVendas, Long> {
    @Query("SELECT lv FROM LancamentoVendas lv JOIN FETCH lv.vendedor WHERE lv.id = :id")
    Optional<LancamentoVendas> findByIdFetchingVendedor(Long id);
}