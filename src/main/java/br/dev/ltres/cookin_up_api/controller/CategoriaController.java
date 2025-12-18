package br.dev.ltres.cookin_up_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAtualizaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaDetalhadaDTO;
import br.dev.ltres.cookin_up_api.model.Categoria;
import br.dev.ltres.cookin_up_api.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

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
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias ativas cadastradas no sistema")
    public ResponseEntity<Page<CategoriaDetalhadaDTO>> getListar(
            @PageableDefault(size = 100, sort = { "nome" }) @ParameterObject @NonNull Pageable paginacao) {
        var listaCategorias = categoriaRepository
                .findByAtivoTrue(paginacao)
                .map(CategoriaDetalhadaDTO::new);

        return ResponseEntity.ok(listaCategorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar categoria", description = "Detalha uma categoria ativa cadastrada no sistema pelo ID")
    public ResponseEntity<CategoriaDetalhadaDTO> getDetalhe(
            @PathVariable @Parameter(description = "ID da categoria", example = "3") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        var categoria = categoriaRepository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        return ResponseEntity.ok(new CategoriaDetalhadaDTO(categoria));
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Adicionar categoria", description = "Adiciona uma nova categoria ao sistema")
    public ResponseEntity<CategoriaDetalhadaDTO> postAdiciona(
            @RequestBody @Valid CategoriaAdicionaDTO categoria,
            UriComponentsBuilder uriBuilder) {
        if (categoria == null) {
            return ResponseEntity.badRequest().build();
        }

        var novaCategoria = categoriaRepository.save(new Categoria(categoria));
        var uri = uriBuilder.path("/categorias/{id}").buildAndExpand(novaCategoria.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoriaDetalhadaDTO(novaCategoria));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria ativa cadastrada no sistema")
    public ResponseEntity<CategoriaDetalhadaDTO> putAtualiza(
            @RequestBody @Valid CategoriaAtualizaDTO categoria) {
        if (categoria == null) {
            return ResponseEntity.badRequest().build();
        }

        var categoriaExistente = categoriaRepository
                .findByIdAndAtivoTrue(categoria.id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoriaExistente.atualizaDados(categoria);
        categoriaExistente = categoriaRepository.save(categoriaExistente);

        return ResponseEntity.ok(new CategoriaDetalhadaDTO(categoriaExistente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Remover categoria", description = "Remove (desativa) uma categoria ativa cadastrada no sistema pelo ID")
    public ResponseEntity<Void> deleteRemove(@PathVariable Long id) {
        var desativouQtd = categoriaRepository.desativar(id);
        if (desativouQtd == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrado")
    public ResponseEntity<List<CategoriaDetalhadaDTO>> getFiltrado(
            @RequestParam @Parameter(description = "Termo a ser pesquisado", example = "Bebidas") @NonNull String termo) {

        var listaFiltrada = categoriaRepository.findTop10ByAtivoTrueAndNomeContainingIgnoreCase(termo);
        return ResponseEntity.ok(listaFiltrada.stream().map(CategoriaDetalhadaDTO::new).toList());
    }
}