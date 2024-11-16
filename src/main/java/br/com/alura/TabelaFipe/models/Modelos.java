package br.com.alura.TabelaFipe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos(List<DadosFipe> modelos) {
}
