package br.com.serratec.commerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class LancamentoVendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate data;

    @NotNull
    @PositiveOrZero(message = "O valor da venda deve ser positivo ou zero")
    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id", nullable = false, unique = true)
    private Vendedor vendedor;
}