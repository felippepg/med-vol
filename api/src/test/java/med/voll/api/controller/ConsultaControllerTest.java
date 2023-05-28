package med.voll.api.controller;

import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import med.voll.api.dominio.consulta.DadosDetalhamentoConsulta;
import med.voll.api.dominio.consulta.service.AgendaDeConsultas;
import med.voll.api.dominio.medico.Especialidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest //trás o contexto completo do Spring
@AutoConfigureMockMvc //para poder injetar o MockMVC
@AutoConfigureJsonTesters //para poder injetar o JacksonTester
class ConsultaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;

    @Autowired
    JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    @MockBean
    AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria retornar o erro 400 ao agendar consulta e informar parâmetros vazios")
    @WithMockUser // avisa ao Spring para ignorar autenticação
    void agendarConsultaCenario1() throws Exception {
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consulta").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar 200 ao agendar consulta e não informar parâmetros")
    @WithMockUser
    void agendarConsultaCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
        Mockito.when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consulta")
                        .content(dadosAgendamentoConsultaJacksonTester.write(
                                new DadosAgendamentoConsulta(2l, 5l, especialidade, data)
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        var responseJson = dadosDetalhamentoConsultaJacksonTester.write(
            dadosDetalhamento
        ).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(responseJson);
    }
}