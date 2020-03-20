package com.example.demo.service;

import java.util.Date;
import java.util.List;

import com.example.demo.exception.FilmeSemEstoqueException;
import com.example.demo.exception.LocadoraException;
import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;
import com.example.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filme) throws FilmeSemEstoqueException, LocadoraException {
		
		if(usuario == null) {
			throw new LocadoraException("Usuario invalido");
		}
		
		if(filme == null || filme.isEmpty()) {
			throw new LocadoraException("filme invalido");
		}
		
		for (Filme filme2 : filme) {
			
		if(filme2.getEstoque() == 0) {
			throw new FilmeSemEstoqueException("Filme sem estoque");
		}
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0.;
		for (int i=0 ; i < filme.size(); i++) {
			
			Filme filme2 = filme.get(i);
			
			Double valorFilme = filme2.getPrecoLocacao();
			
			if(i == 2) {
				valorFilme = valorFilme * 0.75;
			} 
			if(i == 3) {
				valorFilme = valorFilme * 0.50;
			}
			if(i == 4) {
				valorFilme = valorFilme * 0.25;
			}
			if(i == 5) {
				valorFilme = 0.;
			}
			
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal); 
		
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		return locacao;
		
	}
}
