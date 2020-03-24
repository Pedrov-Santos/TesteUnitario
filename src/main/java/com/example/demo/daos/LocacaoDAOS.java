package com.example.demo.daos;

import java.util.List;

import com.example.demo.model.Locacao;

public interface LocacaoDAOS {

public void salvar(Locacao locacao);

public List<Locacao> obterLocacoesPendentes();
}
