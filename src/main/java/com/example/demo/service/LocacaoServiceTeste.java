package com.example.demo.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.example.demo.exception.FilmeSemEstoqueException;
import com.example.demo.exception.LocadoraException;
import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;
import com.example.utils.DataUtils;



public class LocacaoServiceTeste {

	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Before
	public void antes() {
		 service = new LocacaoService();
	}
	
	@Test
	public void testeLocacao  () throws Exception {
		
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1" , 2 , 5.0));
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
			error.checkThat(locacao.getValor() , is(5.0));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacaoFilmeSemEstoque() throws Exception {
		
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1" , 0 , 5.0));
		
		service.alugarFilme(usuario, filme);
	} 
	
	@Test
	public void testeLocacaoSemUsuario() throws FilmeSemEstoqueException  {
		
		List<Filme> filme = Arrays.asList(new Filme("Filme 1" , 2 , 5.0));
		
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario invalido"));
		}
	}
	
	@Test
	public void testeLocacaoSemFilme() throws FilmeSemEstoqueException  {
		
		Usuario usuario = new Usuario("Usuario 1");
		
		try {
			service.alugarFilme(usuario, null);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("filme invalido"));
		}
	}
	
	
}
