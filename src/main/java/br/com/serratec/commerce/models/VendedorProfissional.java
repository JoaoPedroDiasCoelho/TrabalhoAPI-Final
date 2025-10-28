package br.com.serratec.commerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class VendedorProfissional extends Vendedor {

    @NotBlank(message = "O CNPJ é obrigatório")
    @Column(unique = true, length = 18, nullable = false)
    private String cnpj;
}