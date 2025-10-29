package br.com.serratec.TrabalhoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.serratec.TrabalhoFinal.dto.PedidoDTO;
import br.com.serratec.TrabalhoFinal.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Operation(description = "Lista todos os pedidos com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de pedidos"),
        @ApiResponse(responseCode = "400", description = "Erro ao retornar os pedidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> findAll(Pageable pageable) {
        Page<PedidoDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Busca um pedido pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado!"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado!"),
        @ApiResponse(responseCode = "400", description = "Erro ao buscar o pedido"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) {
        PedidoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "Cria um novo pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro ao criar o pedido"),
        @ApiResponse(responseCode = "409", description = "Conflito: pedido já existente"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<PedidoDTO> insert(@RequestBody PedidoDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Operation(description = "Atualiza o status de um pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Erro ao atualizar o status do pedido"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado!"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<PedidoDTO> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String novoStatus = body.get("status");
        PedidoDTO dto = service.updateStatus(id, novoStatus);
        return ResponseEntity.ok().body(dto);
    }
}
