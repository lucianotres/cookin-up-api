package br.dev.ltres.cookin_up_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaAdicionaDTO;
import br.dev.ltres.cookin_up_api.dto.categoria.CategoriaDetalhadaDTO;
import br.dev.ltres.cookin_up_api.repository.CategoriaRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<Page<CategoriaDetalhadaDTO>> getListar(
            @PageableDefault(size = 100, sort = { "nome" }) @NonNull Pageable paginacao) {
        var listaCategorias = categoriaRepository.findAll(paginacao).map(CategoriaDetalhadaDTO::new);

        return ResponseEntity.ok(listaCategorias);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaDetalhadaDTO> postAdiciona(@RequestBody CategoriaAdicionaDTO categoria,
            UriComponentsBuilder uriBuilder) {
        var novaCategoria = categoriaRepository.save(categoria.toEntity());
        var uri = uriBuilder.path("/categorias/{id}").buildAndExpand(novaCategoria.getId()).toUri();

        return ResponseEntity.created(uri).body(new CategoriaDetalhadaDTO(novaCategoria));
    }

}