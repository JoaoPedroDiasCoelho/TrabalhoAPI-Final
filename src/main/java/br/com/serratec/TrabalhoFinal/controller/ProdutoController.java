package br.com.serratec.TrabalhoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.TrabalhoFinal.dto.ProdutoDTO;
import br.com.serratec.TrabalhoFinal.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(description = "Listar Produtos")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "Retorna o lista de Produtos"),
    		@ApiResponse(responseCode = "400",description = "Erro ao retornar os Produtos"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findAll(Pageable pageable) {
        Page<ProdutoDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    
    @Operation(description = "Busca um Produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado!"),
        @ApiResponse(responseCode = "404", description = "Produtor não encontrado!"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        ProdutoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    
    @Operation(description = "Inserir Produto")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201",description = "Produto criado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao criar Produto"),
    		@ApiResponse(responseCode = "409",description = "Produto ja existe"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> insert(@RequestBody ProdutoDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
    @Operation(description = "Atualizar Produto")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = " Produto atualizado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao atualizar Produto"),
    		@ApiResponse(responseCode = "404", description = "Produto não encontrado!"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    
    @Operation(description = "Deletar Produto")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204",description = "Produto Deletado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao Deletar o Produto"),
    		@ApiResponse(responseCode = "404", description = "Produto não encontrado!"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}