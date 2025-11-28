package br.dev.ltres.cookin_up_api.model;

import org.springframework.lang.NonNull;

import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAtualizaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "categoria")
@Entity(name = "Categoria")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String imagem;
    private Boolean ativo = true;

    public Categoria(@NonNull CategoriaAdicionaDTO categoria) {
        this.nome = categoria.nome();
        this.imagem = categoria.imagem();
    }

    public void atualizaDados(CategoriaAtualizaDTO categoria) {
        if (categoria.nome() != null) {
            this.nome = categoria.nome();
        }
        this.imagem = categoria.imagem();
    }
}
