package br.dev.ltres.cookin_up_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAtualizaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaDetalhadaDTO;
import br.dev.ltres.cookin_up_api.dto.ingrediente.IngredienteAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.ingrediente.IngredienteDetalhaDTO;
import br.dev.ltres.cookin_up_api.model.Categoria;
import br.dev.ltres.cookin_up_api.model.Ingrediente;
import br.dev.ltres.cookin_up_api.repository.CategoriaRepository;
import br.dev.ltres.cookin_up_api.repository.IngredienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar Ingredientes", description = "Lista todos os ingrediente cadastrados no sistema")
    public ResponseEntity<Page<IngredienteDetalhaDTO>> getListar(
            @PageableDefault(size = 100, sort = { "nome" }) @ParameterObject @NonNull Pageable paginacao) {
        var listaIngredientes = ingredienteRepository
                .findAll(paginacao)
                .map(IngredienteDetalhaDTO::new);

        return ResponseEntity.ok(listaIngredientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar ingrediente", description = "Detalha um ingrediente cadastrado no sistema pelo ID")
    public ResponseEntity<IngredienteDetalhaDTO> getDetalhe(
            @PathVariable @Parameter(description = "ID do ingrediente", example = "3") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        var ingrediente = ingredienteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente não encontrado"));

        return ResponseEntity.ok(new IngredienteDetalhaDTO(ingrediente));
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Adicionar ingrediente", description = "Adiciona um novo ingrediente dentro da categoria ao sistema")
    public ResponseEntity<IngredienteDetalhaDTO> postAdiciona(
            @RequestBody @Valid IngredienteAdicionaDTO ingrediente,
            UriComponentsBuilder uriBuilder) {
        if (ingrediente == null) {
            return ResponseEntity.badRequest().build();
        }

        var categoriaIngrediente = categoriaRepository
                .findById(ingrediente.id_categoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para o ingrediente"));

        var novoIngrediente = new Ingrediente(ingrediente);
        novoIngrediente.setCategoria(categoriaIngrediente);

        novoIngrediente = ingredienteRepository.save(novoIngrediente);

        var uri = uriBuilder.path("/ingredientes/{id}").buildAndExpand(novoIngrediente.getId()).toUri();
        return ResponseEntity.created(uri).body(new IngredienteDetalhaDTO(novoIngrediente));
    }

    /*
     * @PutMapping
     * 
     * @Transactional
     * 
     * @Operation(summary = "Atualizar categoria", description =
     * "Atualiza uma categoria ativa cadastrada no sistema")
     * public ResponseEntity<CategoriaDetalhadaDTO> putAtualiza(
     * 
     * @RequestBody @Valid CategoriaAtualizaDTO categoria) {
     * if (categoria == null) {
     * return ResponseEntity.badRequest().build();
     * }
     * 
     * var categoriaExistente = ingredienteRepository
     * .findByIdAndAtivoTrue(categoria.id())
     * .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
     * 
     * categoriaExistente.atualizaDados(categoria);
     * 
     * return ResponseEntity.ok(new CategoriaDetalhadaDTO(categoriaExistente));
     * }
     * 
     * @DeleteMapping("/{id}")
     * 
     * @Transactional
     * 
     * @Operation(summary = "Remover categoria", description =
     * "Remove (desativa) uma categoria ativa cadastrada no sistema pelo ID")
     * public ResponseEntity<Void> deleteRemove(@PathVariable Long id) {
     * var desativouQtd = ingredienteRepository.desativar(id);
     * if (desativouQtd == 0) {
     * return ResponseEntity.notFound().build();
     * }
     * 
     * return ResponseEntity.noContent().build();
     * }
     */
}