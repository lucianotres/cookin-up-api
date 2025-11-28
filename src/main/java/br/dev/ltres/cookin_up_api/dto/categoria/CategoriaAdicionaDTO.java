package br.dev.ltres.cookin_up_api.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record CategoriaAdicionaDTO(
                @NotBlank String nome,
                String imagem) {
}
