package br.dev.ltres.cookin_up_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAtualizaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaDetalhadaDTO;
import br.dev.ltres.cookin_up_api.model.Categoria;
import br.dev.ltres.cookin_up_api.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<Page<CategoriaDetalhadaDTO>> getListar(
            @PageableDefault(size = 100, sort = { "nome" }) @NonNull Pageable paginacao) {
        var listaCategorias = categoriaRepository
                .findByAtivoTrue(paginacao)
                .map(CategoriaDetalhadaDTO::new);

        return ResponseEntity.ok(listaCategorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDetalhadaDTO> getDetalhe(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        var categoria = categoriaRepository.getReferenceByIdAndAtivoTrue(id);

        return categoria == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new CategoriaDetalhadaDTO(categoria));
    }

    @PostMapping
    @Transactional
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
    public ResponseEntity<CategoriaDetalhadaDTO> putAtualiza(
            @RequestBody @Valid CategoriaAtualizaDTO categoria) {
        if (categoria == null) {
            return ResponseEntity.badRequest().build();
        }

        var categoriaExistente = categoriaRepository.getReferenceByIdAndAtivoTrue(categoria.id());
        if (categoriaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        categoriaExistente.atualizaDados(categoria);

        return ResponseEntity.ok(new CategoriaDetalhadaDTO(categoriaExistente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteRemove(@PathVariable Long id) {
        var desativouQtd = categoriaRepository.desativar(id);
        if (desativouQtd == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}