package br.dev.ltres.cookin_up_api.dto.ingrediente;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record IngredienteAtualizaDTO(
        @NotNull @NonNull Long id,
        String nome,
        Long id_categoria) {
}
