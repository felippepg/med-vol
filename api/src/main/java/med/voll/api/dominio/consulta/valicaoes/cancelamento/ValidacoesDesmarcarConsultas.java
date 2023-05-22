package med.voll.api.dominio.consulta.valicaoes.cancelamento;

import med.voll.api.dominio.consulta.DadosDesmarcarConsulta;

public interface ValidacoesDesmarcarConsultas {
    void validar(DadosDesmarcarConsulta dados);
}
