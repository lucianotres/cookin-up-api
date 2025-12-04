package br.dev.ltres.cookin_up_api.model;

import java.util.List;

import org.springframework.lang.NonNull;

import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAtualizaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ingrediente", joinColumns = @JoinColumn(name = "id_categoria"), inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Ingrediente> ingredientes;

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
