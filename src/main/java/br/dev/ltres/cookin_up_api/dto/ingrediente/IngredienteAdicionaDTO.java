package br.dev.ltres.cookin_up_api.dto.ingrediente;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotBlank;

public record IngredienteAdicionaDTO(
        @NotBlank(message = "O nome do ingrediente é obrigatório") String nome,
        @NonNull Long id_categoria) {
}
