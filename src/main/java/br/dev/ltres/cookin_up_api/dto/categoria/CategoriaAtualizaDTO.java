package br.dev.ltres.cookin_up_api.dto.categoria;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;

public record CategoriaAtualizaDTO(
                @NotNull @NonNull Long id,
                String nome,
                String imagem) {
}
