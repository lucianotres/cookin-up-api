package br.dev.ltres.cookin_up_api.dto.publico;

import java.util.List;

import br.dev.ltres.cookin_up_api.model.Receita;

public record PublicoReceitaDTO(
        String nome,
        List<String> ingredientes,
        String imagem) {

    public PublicoReceitaDTO(Receita receita) {
        this(
                receita.getNome(),
                receita.getIngredientes().stream()
                        .map(m -> m.getNome())
                        .toList(),
                receita.getImagem());
    }
}
