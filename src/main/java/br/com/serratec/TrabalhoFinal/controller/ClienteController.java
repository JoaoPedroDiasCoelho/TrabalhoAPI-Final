package br.com.serratec.TrabalhoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.TrabalhoFinal.dto.ClienteDTO;
import br.com.serratec.TrabalhoFinal.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(description = "Listar Clientes")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "Retorna o Cliente"),
    		@ApiResponse(responseCode = "400",description = "Erro ao retornar os clientes"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll(Pageable pageable) {
        Page<ClienteDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }
    
    @Operation(description = "Buscar por Id")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "Cliente encontrado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao encontrar o Cliente"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
    		@ApiResponse(responseCode = "404", description = "Cliente nao encontrado"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        ClienteDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @Operation(description = "Inserir Cliente")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201",description = "Cliente criado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao criar Cliente"),
    		@ApiResponse(responseCode = "409",description = "Cliente ja existe"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> insert(@RequestBody ClienteDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
    @Operation(description = "Atualizar Cliente")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = " Cliente atualizado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao atualizar Cliente"),
    		@ApiResponse(responseCode = "404", description = "Cliente não encontrado!"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO dto) {

        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    
    @Operation(description = "Deletar Cliente")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204",description = "Cliente Deletado!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao Deletar o  Cliente"),
    		@ApiResponse(responseCode = "404", description = "Cliente não encontrado!"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
