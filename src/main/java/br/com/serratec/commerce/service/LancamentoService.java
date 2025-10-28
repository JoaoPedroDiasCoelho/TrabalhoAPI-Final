package br.com.serratec.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.serratec.commerce.dto.LancamentoVendasResponseDTO;
import br.com.serratec.commerce.models.LancamentoVendas;
import br.com.serratec.commerce.repository.ILancamentoRepository;

@Service
public class LancamentoService {
    
    @Autowired
    private ILancamentoRepository lancamentoRepository;
    @Transactional(readOnly = true)
    public LancamentoVendasResponseDTO listarPorId(Long id) {
        LancamentoVendas vendas = lancamentoRepository.findByIdFetchingVendedor(id).orElseThrow();
        return new LancamentoVendasResponseDTO(vendas);
    }

    public LancamentoVendasResponseDTO salvarLancamento(LancamentoVendas lancamento) {
        LancamentoVendas vendasSalvo = lancamentoRepository.save(lancamento);
        return new LancamentoVendasResponseDTO(vendasSalvo);
    }
}
