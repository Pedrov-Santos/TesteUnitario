package com.example.demo.service;

import static com.example.demo.builder.FilmeBuilder.umFilme;
import static com.example.demo.builder.LocacaoBuilder.umLocacao;
import static com.example.demo.builder.UsuarioBuilder.umUsuario;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.demo.daos.LocacaoDAOS;
import com.example.demo.exception.FilmeSemEstoqueException;
import com.example.demo.exception.LocadoraException;
import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;
import com.example.utils.DataUtils;



public class LocacaoServiceTeste {

	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private EmailService email;
	
	@Mock
	private SPCService spc;
	
	@Mock
	private LocacaoDAOS dao;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void antes() {
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testeLocacao  () throws Exception {
		
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = Arrays.asList(umFilme().comValor(5.0).agora());
		
		Locacao locacao = service.alugarFilme(usuario, filme);
		
			error.checkThat(locacao.getValor() , is(5.0));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacaoFilmeSemEstoque() throws Exception {
		
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = Arrays.asList(umFilme().semEstoque().agora());
		
		service.alugarFilme(usuario, filme);
	} 
	
	@Test
	public void testeLocacaoSemUsuario() throws FilmeSemEstoqueException  {
		
		List<Filme> filme = Arrays.asList(umFilme().agora());
		
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario invalido"));
		}
	}
	
	@Test
	public void testeLocacaoSemFilme() throws FilmeSemEstoqueException  {
		
		Usuario usuario = umUsuario().agora();
		
		try {
			service.alugarFilme(usuario, null);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("filme invalido"));
		}
	}
	
	@Test
	public void naoDeveAlugarFilmeParaQemTaDevendo() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = umUsuario().agora();
		List<Filme> filme = Arrays.asList(umFilme().agora());
		
		
		Mockito.when(spc.taDevendo(usuario)).thenReturn(true);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Vende não tá Devendo");
		
		service.alugarFilme(usuario, filme);
	
	}
	
	@Test
	public void deveEnviarEmailParaPendendcias() {
		Usuario usuario = umUsuario().agora();
		List<Locacao> locacoes = asList(umLocacao().comUsuario(usuario).atraso().agora());
		
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		service.notificaAtrasos();
		
		Mockito.verify(email).notificarAtraso(usuario);
	}
	
	@Test
	public void deveProrrogarUmaLocacao() {
		Locacao locacao = umLocacao().agora();
		
		
		service.prorrogarLocacao(locacao, 3);
		
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();
		
		error.checkThat(locacaoRetornada.getValor(), is(0.0));
	}
	
	
	
}
