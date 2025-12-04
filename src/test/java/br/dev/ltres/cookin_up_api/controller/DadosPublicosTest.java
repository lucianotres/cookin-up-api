package br.dev.ltres.cookin_up_api.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import br.dev.ltres.cookin_up_api.model.Categoria;
import br.dev.ltres.cookin_up_api.model.Ingrediente;
import br.dev.ltres.cookin_up_api.model.Receita;
import br.dev.ltres.cookin_up_api.repository.CategoriaRepository;
import br.dev.ltres.cookin_up_api.repository.ReceitaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class DadosPublicosTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaRepository categoriaRepository;

    @MockitoBean
    private ReceitaRepository receitaRepository;

    @Test
    void testGetCategoriasVazio() throws Exception {
        when(categoriaRepository.findByAtivoTrue()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/publico/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetCategoriasRetornaListaValida() throws Exception {
        var listaCategorias = List.of(
                new Categoria(1l, "Bebidas", "imagem.jpg", true, List.of(
                        new Ingrediente(1L, "Água"))),
                new Categoria(2l, "Sobremesas", "imagem.jpg", true, List.of(
                        new Ingrediente(2L, "Açúcar"))));

        when(categoriaRepository.findByAtivoTrue()).thenReturn(listaCategorias);

        var httpPerform = mockMvc.perform(get("/publico/categorias"));

        verify(categoriaRepository, times(1)).findByAtivoTrue();
        httpPerform.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Bebidas"))
                .andExpect(jsonPath("$[0].ingredientes[0]").value("Água"))
                .andExpect(jsonPath("$[1].nome").value("Sobremesas"))
                .andExpect(jsonPath("$[1].ingredientes[0]").value("Açúcar"));
    }

    @Test
    void testGetReceitasVazio() throws Exception {
        when(receitaRepository.findByAtivoTrue()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/publico/receitas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetReceitasRetornaListaValida() throws Exception {
        var listaReceitas = List.of(
                new Receita(1l, "Suco de Laranja", "imagem.jpg", true, List.of(
                        new Ingrediente(1L, "Laranja"))),
                new Receita(2l, "Pudim", "imagem.jpg", true, List.of(
                        new Ingrediente(2L, "Leite"),
                        new Ingrediente(3L, "Ovo"))));

        when(receitaRepository.findByAtivoTrue()).thenReturn(listaReceitas);

        var httpPerform = mockMvc.perform(get("/publico/receitas"));

        verify(receitaRepository, times(1)).findByAtivoTrue();
        httpPerform.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Suco de Laranja"))
                .andExpect(jsonPath("$[0].ingredientes[0]").value("Laranja"))
                .andExpect(jsonPath("$[1].nome").value("Pudim"))
                .andExpect(jsonPath("$[1].ingredientes[1]").value("Ovo"));
    }
}