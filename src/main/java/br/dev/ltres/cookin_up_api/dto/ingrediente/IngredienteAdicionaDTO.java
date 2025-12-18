package br.dev.ltres.cookin_up_api.dto.ingrediente;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredienteAdicionaDTO(
        @NotBlank(message = "O nome do ingrediente é obrigatório") String nome,
        @NonNull @NotNull(message = "A categoria do ingrediente é obrigatória") Long id_categoria) {
}
