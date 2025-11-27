package br.dev.ltres.cookin_up_api.dto.categoria;

import br.dev.ltres.cookin_up_api.model.Categoria;

public record CategoriaDetalhadaDTO(
        Long id,
        String nome,
        String imagem) {

    public CategoriaDetalhadaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNome(), categoria.getImagem());
    }
}
