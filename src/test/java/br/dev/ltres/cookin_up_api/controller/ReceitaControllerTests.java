package br.dev.ltres.cookin_up_api.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.dev.ltres.cookin_up_api.dto.receita.ReceitaAdicionaDTO;
import br.dev.ltres.cookin_up_api.model.Ingrediente;
import br.dev.ltres.cookin_up_api.model.Receita;
import br.dev.ltres.cookin_up_api.services.ReceitaService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser(roles = { "AccPadrao" }, username = "luciano")
@SuppressWarnings("null")
public class ReceitaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ReceitaAdicionaDTO> receitaAdicionaDTOJacksonTester;

    @MockitoBean
    private ReceitaService receitaService;

    private Receita receita;

    @BeforeEach
    void setUp() {
        receita = new Receita(1l, "Bolo de Chocolate", "bolo.png", true, List.of(
                new Ingrediente(1l, "farinha de trigo", null),
                new Ingrediente(2l, "açúcar", null),
                new Ingrediente(3l, "chocolate em pó", null),
                new Ingrediente(4l, "fermento em pó", null),
                new Ingrediente(5l, "ovo", null),
                new Ingrediente(6l, "leite", null)));
    }

    @Test
    @DisplayName("Deve cadastrar uma nova receita com sucesso")
    void cadastrarReceitaTest() throws Exception {
        var ingredientes = List.of(
                "farinha de trigo",
                "açúcar",
                "chocolate em pó",
                "fermento em pó",
                "ovo",
                "leite");
        var receita = new ReceitaAdicionaDTO("Bolo de Chocolate", ingredientes, "bolo.png");

        when(receitaService.salvarNovaReceita(any()))
                .thenAnswer(invocation -> {
                    var dto = invocation.getArgument(0, ReceitaAdicionaDTO.class);
                    var ingredientesRetorno = dto.ingredientes().stream().map(ing -> {
                        return new Ingrediente(1l, ing, null);
                    }).toList();
                    return new Receita(
                            1l,
                            dto.nome(),
                            dto.imagem(),
                            true,
                            ingredientesRetorno);
                });

        var performResult = mockMvc.perform(post("/receitas")
                .contentType("application/json")
                .content(receitaAdicionaDTOJacksonTester.write(receita).getJson()));

        performResult.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Bolo de Chocolate"))
                .andExpect(jsonPath("$.imagem").value("bolo.png"));
    }

    @Test
    @DisplayName("Deve retornar receita detalhada com ID existente")
    void testGetReceitaDetalhadaComId() throws Exception {
        when(receitaService.buscarReceitaId(1L)).thenReturn(receita);

        mockMvc.perform(get("/receitas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Bolo de Chocolate"));

        verify(receitaService, times(1)).buscarReceitaId(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar receita detalhada com ID inexistente")
    void testGetReceitaDetalhadaComIdInexistente() throws Exception {
        when(receitaService.buscarReceitaId(999L)).thenReturn(null);

        mockMvc.perform(get("/receitas/999"))
                .andExpect(status().isNotFound());

        verify(receitaService, times(1)).buscarReceitaId(999L);
    }

    private void mockaListaDeReceitas(int tamanhoDaPagina) {
        List<Receita> receitas = List.of(
                new Receita(1l, "Bolo de Chocolate", "", true, List.of()),
                new Receita(2l, "Torta de Limão", "", true, List.of()),
                new Receita(3l, "Torta de Limão Azedo", "", true, List.of()));

        var pageable = PageRequest.of(0, tamanhoDaPagina);
        var page = new PageImpl<>(receitas.subList(0, Math.min(tamanhoDaPagina, receitas.size())), pageable,
                receitas.size());

        when(receitaService.listarReceitas(any(Pageable.class)))
                .thenReturn(page);
    }

    @Test
    @DisplayName("Deve retornar paginação correta na lista de receitas")
    void testPaginacaoDaListaDeReceitas() throws Exception {
        mockaListaDeReceitas(2);

        mockMvc.perform(get("/receitas")
                .param("page", "0")
                .param("size", "2")
                .param("sort", "nome"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver receitas cadastradas")
    void testRetornaListaVaziaDeReceitas() throws Exception {
        when(receitaService.listarReceitas(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 100), 0));

        mockMvc.perform(get("/receitas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Deve retornar lista normal de receitas")
    void testRetornaListaNormalDeReceitas() throws Exception {
        mockaListaDeReceitas(100);

        mockMvc.perform(get("/receitas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Bolo de Chocolate"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Torta de Limão"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nome").value("Torta de Limão Azedo"));
    }
}
