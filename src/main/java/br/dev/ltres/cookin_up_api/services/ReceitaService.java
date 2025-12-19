package br.dev.ltres.cookin_up_api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAtualizaDTO;
import br.dev.ltres.cookin_up_api.exception.RequisicaoInvalidaException;
import br.dev.ltres.cookin_up_api.model.Ingrediente;
import br.dev.ltres.cookin_up_api.model.Receita;
import br.dev.ltres.cookin_up_api.repository.IngredienteRepository;
import br.dev.ltres.cookin_up_api.repository.ReceitaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final IngredienteRepository ingredienteRepository;

    @Transactional
    public Receita salvarNovaReceita(@Valid ReceitaAdicionaDTO receita) throws RequisicaoInvalidaException {
        var ingredientes = receita.ingredientes();
        if (ingredientes == null || ingredientes.isEmpty())
            throw new RequisicaoInvalidaException("A receita deve conter ao menos um ingrediente");

        var listaIngredientes = obterIngredientesPeloNome(ingredientes);

        var novaReceita = new Receita(null, receita.nome(), receita.imagem(), true, listaIngredientes);
        var receitaAdicionada = receitaRepository.save(novaReceita);

        return receitaAdicionada;
    }

    @Transactional
    @SuppressWarnings("null")
    public Receita salvarAtualizaReceita(@Valid ReceitaAtualizaDTO receita) throws RequisicaoInvalidaException {
        var receitaEntity = receitaRepository
                .findByIdAndAtivoTrue(receita.id())
                .orElseThrow(() -> new RequisicaoInvalidaException("Receita não encontrada"));

        if (receita.nome() != null)
            receitaEntity.setNome(receita.nome());

        if (receita.imagem() != null)
            receitaEntity.setImagem(receita.imagem());

        if (receita.ingredientes() != null)
            receitaEntity.setIngredientes(obterIngredientesPeloNome(receita.ingredientes()));

        return receitaRepository.save(receitaEntity);
    }

    private List<Ingrediente> obterIngredientesPeloNome(List<String> receitaIngredientes) {
        var listaIngredientes = ingredienteRepository.findAllByNomeIn(receitaIngredientes);
        if (!receitaIngredientes.stream()
                .allMatch(i -> listaIngredientes.stream().anyMatch(li -> li.getNome().equalsIgnoreCase(i))))
            throw new RequisicaoInvalidaException("Um ou mais ingredientes não foram encontrados");

        return listaIngredientes;
    }

    @Transactional
    public boolean desativarReceita(@NonNull Long id) {
        var desativouQtd = receitaRepository.desativar(id);
        return desativouQtd > 0;
    }

    public Receita buscarReceitaId(@NonNull Long id) {
        return receitaRepository.findByIdAndAtivoTrue(id).orElse(null);
    }

    public Page<Receita> listarReceitas(@NonNull Pageable pageable) {
        return receitaRepository.findByAtivoTrue(pageable);
    }
}
