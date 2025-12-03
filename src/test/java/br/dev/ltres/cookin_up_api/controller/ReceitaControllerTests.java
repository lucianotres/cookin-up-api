package br.dev.ltres.cookin_up_api.controller;

import java.util.List;

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
import static org.mockito.Mockito.when;

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
}
