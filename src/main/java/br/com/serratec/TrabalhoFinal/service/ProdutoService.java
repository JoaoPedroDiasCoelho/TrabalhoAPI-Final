package br.com.serratec.TrabalhoFinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.TrabalhoFinal.dto.ProdutoDTO;
import br.com.serratec.TrabalhoFinal.entity.Categoria;
import br.com.serratec.TrabalhoFinal.entity.Produto;
import br.com.serratec.TrabalhoFinal.exception.DatabaseException;
import br.com.serratec.TrabalhoFinal.exception.ResourceNotFoundException;
import br.com.serratec.TrabalhoFinal.repository.ICategoriaRepository;
import br.com.serratec.TrabalhoFinal.repository.IProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository repository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());

        if (dto.getCategoriaId() == null) {
            throw new DatabaseException("categoria obrigatória");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("categoria não encontrada"));

        entity.setCategoria(categoria);
    }

    public Page<ProdutoDTO> findAllPaged(Pageable pageable) {
        Page<Produto> page = repository.findAll(pageable);
        return page.map(ProdutoDTO::new);
    }

    public ProdutoDTO findById(Long id) {
        Optional<Produto> obj = repository.findById(id);
        Produto entity = obj.orElseThrow(() -> new ResourceNotFoundException("produto não encontrado"));
        return new ProdutoDTO(entity);
    }

    public ProdutoDTO insert(ProdutoDTO dto) {
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        try {
            Produto entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProdutoDTO(entity);
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
            throw new DatabaseException("integridade violada");
        }
    }
}