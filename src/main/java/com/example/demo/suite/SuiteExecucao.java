package com.example.demo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.example.demo.service.CalcularValorLocacaoTest;
import com.example.demo.service.LocacaoServiceTeste;

@RunWith(Suite.class)
@SuiteClasses({
	CalcularValorLocacaoTest.class,
	LocacaoServiceTeste.class
	

})
public class SuiteExecucao {
// Apaga
}
