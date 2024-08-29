package br.com.tabela.fipe.dadosGerais;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelo(List<Dados> modelos) {
}
