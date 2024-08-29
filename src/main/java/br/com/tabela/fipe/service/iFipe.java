package br.com.tabela.fipe.service;

import java.util.List;

public interface iFipe {
    <T> T ObterdadosFipe(String json, Class<T> classe);

    <T> List<T> ObterdadosFipeLista(String json, Class<T> classe);
}
