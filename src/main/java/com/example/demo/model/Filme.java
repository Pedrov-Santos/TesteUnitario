package com.example.demo.model;

public class Filme {

	private String filme;
	private Integer estoque;
	private Double precoLocacao;
	
	public Filme() {
		
	}
	
	public Filme(String filme, Integer estoque , Double precoLocacao) {
		this.filme = filme; 
		this.estoque = estoque;
		this.precoLocacao = precoLocacao;
	}

	public String getFilme() {
		return filme;
	}

	public void setFilme(String filme) {
		this.filme = filme;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public Double getPrecoLocacao() {
		return precoLocacao;
	}

	public void setPrecoLocacao(Double precoLocacao) {
		this.precoLocacao = precoLocacao;
	}
	
}
