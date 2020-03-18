package com.example.demo.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;
import com.example.utils.DataUtils;

public class LocacaoServiceTeste {

	@Test
	public void teste() {
		
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1" , 2 , 5.0);
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}

	
}
