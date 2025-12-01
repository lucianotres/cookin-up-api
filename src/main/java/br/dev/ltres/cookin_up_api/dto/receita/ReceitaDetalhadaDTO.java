package br.dev.ltres.cookin_up_api.dto.receita;

import java.util.List;

import br.dev.ltres.cookin_up_api.model.Receita;

public record ReceitaDetalhadaDTO(
        Long id,
        String nome,
        List<String> ingredientes,
        String imagem) {

    public ReceitaDetalhadaDTO(Receita receita) {
        this(receita.getId(),
                receita.getNome(),
                receita.getIngredientes().stream().map(ingrediente -> ingrediente.getNome()).toList(),
                receita.getImagem());
    }
}
