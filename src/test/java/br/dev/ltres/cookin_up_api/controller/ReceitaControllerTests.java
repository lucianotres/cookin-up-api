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
                new Ingrediente(1l, "farinha de trigo"),
                new Ingrediente(2l, "açúcar"),
                new Ingrediente(3l, "chocolate em pó"),
                new Ingrediente(4l, "fermento em pó"),
                new Ingrediente(5l, "ovo"),
                new Ingrediente(6l, "leite")));
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
                        return new Ingrediente(1l, ing);
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
    void testGetDetalhadaWithValidId() throws Exception {
        when(receitaService.buscarReceitaId(1L)).thenReturn(receita);

        mockMvc.perform(get("/receitas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Bolo de Chocolate"));

        verify(receitaService, times(1)).buscarReceitaId(1L);
    }

    @Test
    void testGetDetalhadaWithNonExistentId() throws Exception {
        when(receitaService.buscarReceitaId(999L)).thenReturn(null);

        mockMvc.perform(get("/receitas/999"))
                .andExpect(status().isNotFound());

        verify(receitaService, times(1)).buscarReceitaId(999L);
    }
}
