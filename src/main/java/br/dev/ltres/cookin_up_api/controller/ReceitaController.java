package br.dev.ltres.cookin_up_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.receita.ReceitaDetalhadaDTO;
import br.dev.ltres.cookin_up_api.services.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

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

}