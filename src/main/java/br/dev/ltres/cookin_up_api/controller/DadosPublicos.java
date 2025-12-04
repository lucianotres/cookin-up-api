package br.dev.ltres.cookin_up_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.ltres.cookin_up_api.dto.publico.PublicoCategoriaDTO;
import br.dev.ltres.cookin_up_api.dto.publico.PublicoReceitaDTO;
import br.dev.ltres.cookin_up_api.repository.CategoriaRepository;
import br.dev.ltres.cookin_up_api.repository.ReceitaRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/publico")
public class DadosPublicos {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @GetMapping("/categorias")
    @Operation(summary = "Listar todas categorias válidas")
    public ResponseEntity<List<PublicoCategoriaDTO>> getCategorias() {
        var categorias = categoriaRepository.findByAtivoTrue();
        var categoriasDTO = categorias.stream()
                .map(PublicoCategoriaDTO::new)
                .toList();

        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("/receitas")
    @Operation(summary = "Listar todas receitas válidas")
    public ResponseEntity<List<PublicoReceitaDTO>> getReceitas() {
        var receitas = receitaRepository.findByAtivoTrue();
        var receitasDTO = receitas.stream()
                .map(PublicoReceitaDTO::new)
                .toList();

        return ResponseEntity.ok(receitasDTO);
    }

}
