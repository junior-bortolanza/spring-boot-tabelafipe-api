package br.com.alura.TabelaFipe.services;

public interface IConverteDados {
    <T> T obterDados (String json, Class<T> classe);
}
