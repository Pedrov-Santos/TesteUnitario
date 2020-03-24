package com.example.demo.service;

import static com.example.demo.builder.FilmeBuilder.umFilme;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.daos.LocacaoDAOS;
import com.example.demo.exception.FilmeSemEstoqueException;
import com.example.demo.exception.LocadoraException;
import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;

@RunWith(Parameterized.class)
public class CalcularValorLocacaoTest {
	
	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAOS dao;
	
	@Parameter
	public List<Filme> filme;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;
	
	@InjectMocks
	private LocacaoService service;
	
	@Before
	public void antes() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static Filme filme1 = umFilme().agora();
	private static Filme filme2 = umFilme().agora();
	private static Filme filme3 = umFilme().agora();
	private static Filme filme4 = umFilme().agora();
	private static Filme filme5 = umFilme().agora();
	private static Filme filme6 = umFilme().agora();
	private static Filme filme7 = umFilme().agora();
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1 , filme2 , filme3) , 11.0 , "25% DSC"},
			{Arrays.asList(filme1 , filme2 , filme3 , filme4), 13.0 , "50% DSC"},
			{Arrays.asList(filme1 , filme2 , filme3 , filme4 , filme5), 14.0 , "75% DSC"},
			{Arrays.asList(filme1 , filme2 , filme3 , filme4 , filme5, filme6), 14.0 , "100% DSC"},
			{Arrays.asList(filme1 , filme2 , filme3 , filme4 , filme5, filme6, filme7) , 18.0 , "Sem DSC"}
		});
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoOsDescontos() throws FilmeSemEstoqueException, LocadoraException {
		
		Usuario usuario = new Usuario("Usuario 1");
		
		Locacao resul = service.alugarFilme(usuario, filme);
		
		assertThat(resul.getValor(), is(valorLocacao));
	}
	
}
