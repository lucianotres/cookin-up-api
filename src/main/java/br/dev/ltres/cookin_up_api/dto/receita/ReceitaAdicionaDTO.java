package br.dev.ltres.cookin_up_api.dto.receita;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ReceitaAdicionaDTO(
        @NotBlank String nome,
        @NotEmpty List<String> ingredientes,
        String imagem) {
}
