package br.com.serratec.commerce.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import br.com.serratec.commerce.models.LancamentoVendas;


@Data
public class LancamentoVendasResponseDTO {
    
    private LocalDate dataVenda;
    private BigDecimal valorVenda;
    private String nomeVendedor;
    
    public LancamentoVendasResponseDTO(LancamentoVendas vendas) {
        this.dataVenda = vendas.getData();
        this.valorVenda = vendas.getValor();
        this.nomeVendedor = vendas.getVendedor().getNome();
    }
}