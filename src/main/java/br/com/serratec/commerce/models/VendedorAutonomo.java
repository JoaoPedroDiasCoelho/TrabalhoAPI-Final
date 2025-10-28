package br.com.serratec.commerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class VendedorAutonomo extends Vendedor {

    @NotNull
    @PositiveOrZero(message = "A comissão deve ser um valor positivo.")
    @Column(nullable = false)
    private Double comissao;
}