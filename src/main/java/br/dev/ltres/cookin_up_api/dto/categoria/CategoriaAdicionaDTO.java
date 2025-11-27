package br.dev.ltres.cookin_up_api.dto.categoria;

import org.springframework.lang.NonNull;

import br.dev.ltres.cookin_up_api.model.Categoria;

public record CategoriaAdicionaDTO(
                String nome,
                String imagem) {

        @NonNull
        public Categoria toEntity() {
                return new Categoria(
                                null,
                                this.nome,
                                this.imagem);
        }
}
