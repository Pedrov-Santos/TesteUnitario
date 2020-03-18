package com.example.demo.service;

import java.util.Date;

import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;
import com.example.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao()); 
		
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		return locacao;
		
	}
}
