package br.com.serratec.commerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    @Email(message = "Email invalido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "O salário é obrigatório")
    @Column(nullable = false)
    private Double salario;


    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private Set<LancamentoVendas> lancamentos;
}