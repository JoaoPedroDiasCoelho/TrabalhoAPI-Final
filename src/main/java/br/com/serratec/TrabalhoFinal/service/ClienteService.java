package br.com.serratec.TrabalhoFinal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.serratec.TrabalhoFinal.dto.ClienteDTO;
import br.com.serratec.TrabalhoFinal.dto.EnderecoResponseDTO;
import br.com.serratec.TrabalhoFinal.entity.Cliente;
import br.com.serratec.TrabalhoFinal.exception.DatabaseException;
import br.com.serratec.TrabalhoFinal.exception.ResourceNotFoundException;
import br.com.serratec.TrabalhoFinal.repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNome(dto.getNome());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setCep(dto.getCep());
    }

    public Page<ClienteDTO> findAllPaged(Pageable pageable) {
        Page<Cliente> page = repository.findAll(pageable);
        return page.map(ClienteDTO::new);
    }

    public ClienteDTO findById(Long id) {
        Optional<Cliente> obj = repository.findById(id);
        Cliente entity = obj.orElseThrow(() -> new ResourceNotFoundException("cliente não encontrado"));
        return new ClienteDTO(entity);
    }

    public ClienteDTO insert(ClienteDTO dto) {
        Cliente entity = new Cliente();

        copyDtoToEntity(dto, entity);

        EnderecoResponseDTO cepData = enderecoService.buscarCep(dto.getCep());

        entity.setLogradouro(cepData.logradouro());
        entity.setBairro(cepData.bairro());
        entity.setLocalidade(cepData.localidade());
        entity.setUf(cepData.uf());
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        entity.setSenha(senhaCriptografada);
        
        
        if (dto.getSenha() == null || dto.getSenha().toString().trim().isEmpty()) {
            throw new IllegalArgumentException("senha obrigatória");
        }
        entity = repository.save(entity);
        
        emailService.enviarEmail(
            entity.getEmail(),
            "Cadastro realizado",
            "Olá " + entity.getNome() + ",\n\nCadastro concluído."
        );

        return new ClienteDTO(entity);
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        try {
            Cliente entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClienteDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("id não encontrado");
        }
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("id não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("possui pedidos");
        }
    }
}