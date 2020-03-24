package com.example.demo.builder;

import com.example.demo.model.Filme;

public class FilmeBuilder {

	
	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder umFilme() {
		
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setEstoque(2);
		builder.filme.setFilme("filme");
		builder.filme.setPrecoLocacao(4.0);
		return builder;
	}
	
	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder comValor(Double valor) {
		filme.setPrecoLocacao(valor);
		return this;
	}
	
	public Filme  agora() {
		return filme;
	}
}
