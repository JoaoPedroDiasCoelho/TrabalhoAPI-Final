package br.com.serratec.TrabalhoFinal.dto;

import br.com.serratec.TrabalhoFinal.entity.ItemPedido;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPedidoDTO {

    private Long id;
    private Long produtoId;
    private String produtoNome;

    private BigDecimal valorVenda;
    private BigDecimal desconto;
    private Integer quantidade;
    private Double subTotal;

    public ItemPedidoDTO(ItemPedido entity) {
        this.id = entity.getId();
        this.produtoId = entity.getProduto().getId();
        this.produtoNome = entity.getProduto().getNome();
        this.valorVenda = entity.getValorVenda();
        this.desconto = entity.getDesconto();
        this.quantidade = entity.getQuantidade();
        this.subTotal = entity.getSubTotal();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
    
}