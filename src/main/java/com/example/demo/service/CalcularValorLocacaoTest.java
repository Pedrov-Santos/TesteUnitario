package com.example.demo.service;

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

import com.example.demo.exception.FilmeSemEstoqueException;
import com.example.demo.exception.LocadoraException;
import com.example.demo.model.Filme;
import com.example.demo.model.Locacao;
import com.example.demo.model.Usuario;

@RunWith(Parameterized.class)
public class CalcularValorLocacaoTest {
	
	@Parameter
	public List<Filme> filme;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;
	
	private LocacaoService service;
	
	@Before
	public void antes() {
		 service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("Filme 1" , 10 , 4.0);
	private static Filme filme2 = new Filme("Filme 2" , 10 , 4.0);
	private static Filme filme3 = new Filme("Filme 3" , 10 , 4.0);
	private static Filme filme4 = new Filme("Filme 4" , 10 , 4.0);
	private static Filme filme5 = new Filme("Filme 5" , 10 , 4.0);
	private static Filme filme6 = new Filme("Filme 6" , 10 , 4.0);
	private static Filme filme7 = new Filme("Filme 7" , 10 , 4.0);
	
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
