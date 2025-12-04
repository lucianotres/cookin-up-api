package br.dev.ltres.cookin_up_api.dto.publico;

import java.util.List;

import br.dev.ltres.cookin_up_api.model.Categoria;

public record PublicoCategoriaDTO(
        String nome,
        List<String> ingredientes,
        String imagem) {

    public PublicoCategoriaDTO(Categoria categoria) {
        this(
                categoria.getNome(),
                categoria.getIngredientes().stream()
                        .map(ingrediente -> ingrediente.getNome())
                        .toList(),
                categoria.getImagem());
    }
}
