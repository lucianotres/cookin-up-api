package br.dev.ltres.cookin_up_api.dto.receita;

import java.util.List;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record ReceitaAtualizaDTO(
        @NotNull @NonNull Long id,
        String nome,
        List<String> ingredientes,
        String imagem) {
}
