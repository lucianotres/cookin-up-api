package br.dev.ltres.cookin_up_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAtualizaDTO;
import br.dev.ltres.cookin_up_api.dto.receita.ReceitaDetalhadaDTO;
import br.dev.ltres.cookin_up_api.services.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    @GetMapping("/{id}")
    @Operation(summary = "Obter receita detalhada", description = "Retorna os detalhes de uma receita específica com base no ID fornecido")
    public ResponseEntity<ReceitaDetalhadaDTO> getDetalhada(
            @NonNull @PathVariable @Parameter(description = "Identificador da receita a detalhar", example = "1") Long id) {
        var receita = receitaService.buscarReceitaId(id);
        if (receita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ReceitaDetalhadaDTO(receita));
    }

    @GetMapping
    public ResponseEntity<Page<ReceitaDetalhadaDTO>> getLista(
            @PageableDefault(size = 100, sort = { "nome" }) @ParameterObject @NonNull Pageable pageable) {
        var receitas = receitaService.listarReceitas(pageable)
                .map(ReceitaDetalhadaDTO::new);
        return ResponseEntity.ok(receitas);
    }

    @PostMapping
    @Operation(summary = "Adicionar nova receita", description = "Adiciona uma nova receita ao sistema")
    public ResponseEntity<ReceitaDetalhadaDTO> postAdiciona(
            @RequestBody @Valid ReceitaAdicionaDTO receita,
            UriComponentsBuilder uriBuilder) {
        if (receita == null) {
            return ResponseEntity.badRequest().build();
        }

        var novaReceita = receitaService.salvarNovaReceita(receita);
        var uri = uriBuilder.path("/receitas/{id}").buildAndExpand(novaReceita.getId()).toUri();

        return ResponseEntity.created(uri).body(new ReceitaDetalhadaDTO(novaReceita));
    }

    @PutMapping
    @Operation(summary = "Altera receita", description = "Altera os detalhes de uma receita existente")
    public ResponseEntity<ReceitaDetalhadaDTO> putMethodName(@RequestBody ReceitaAtualizaDTO receita) {
        if (receita == null)
            return ResponseEntity.badRequest().build();

        var receitaAtualizada = receitaService.salvarAtualizaReceita(receita);
        return ResponseEntity.ok(new ReceitaDetalhadaDTO(receitaAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover receita", description = "Remove (desativa) uma receita ativa cadastrada no sistema pelo ID")
    public ResponseEntity<Void> deleteRemove(@PathVariable @NonNull Long id) {
        var desativou = receitaService.desativarReceita(id);
        return desativou ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}