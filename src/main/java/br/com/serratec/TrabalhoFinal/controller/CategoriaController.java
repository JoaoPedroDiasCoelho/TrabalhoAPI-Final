package br.com.serratec.TrabalhoFinal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.serratec.TrabalhoFinal.dto.CategoriaDTO;
import br.com.serratec.TrabalhoFinal.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(description = "Lista todas as categorias")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "Retorna as categorias"),
    		@ApiResponse(responseCode = "400",description = "Erro ao retornar as categorias"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    
    @Operation(description = "Buscar por id")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "Retorna a categoria por Id"),
    		@ApiResponse(responseCode = "404",description = "Essa categoria nao existe !"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        CategoriaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(description = "Inserir categoria")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201",description = "Categoria criada!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao criar categoria"),
    		@ApiResponse(responseCode = "409",description = "Categoria ja existe"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<CategoriaDTO> insert(@RequestBody CategoriaDTO dto) {
        dto = service.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(description = "Atualizar categoria")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200",description = "Categoria atualizada!"),
    		@ApiResponse(responseCode = "400",description = "Erro ao atualizar categoria"),
    		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
