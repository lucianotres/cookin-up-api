package br.dev.ltres.cookin_up_api.services;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAdicionaDTO;
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
        var listaIngredientes = obterIngredientesPeloNome(receita);

        var novaReceita = new Receita(null, receita.nome(), receita.imagem(), true, listaIngredientes);
        var receitaAdicionada = receitaRepository.save(novaReceita);

        return receitaAdicionada;
    }

    private List<Ingrediente> obterIngredientesPeloNome(ReceitaAdicionaDTO receita) {
        var ingredientes = receita.ingredientes();
        if (ingredientes == null || ingredientes.isEmpty())
            throw new RequisicaoInvalidaException("A receita deve conter ao menos um ingrediente");

        var listaIngredientes = ingredienteRepository.findAllByNomeIn(ingredientes);
        if (!ingredientes.stream()
                .allMatch(i -> listaIngredientes.stream().anyMatch(li -> li.getNome().equalsIgnoreCase(i))))
            throw new RequisicaoInvalidaException("Um ou mais ingredientes não foram encontrados");

        return listaIngredientes;
    }

    public Receita buscarReceitaId(@NonNull Long id) {
        return receitaRepository.findById(id).orElse(null);
    }
}
