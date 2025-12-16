package br.dev.ltres.cookin_up_api.dto.ingrediente;

import br.dev.ltres.cookin_up_api.model.Ingrediente;

public record IngredienteDetalhaDTO(
        Long id,
        String nome,
        IngredienteCategoriaDTO categoria) {

    public IngredienteDetalhaDTO(Ingrediente ingrediente) {
        this(ingrediente.getId(), ingrediente.getNome(),
                new IngredienteCategoriaDTO(ingrediente.getCategoria().getId(), ingrediente.getCategoria().getNome()));
    }
}