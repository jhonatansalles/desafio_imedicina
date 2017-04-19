package com.desafioimedicina.crudpessoa.service;

import java.util.List;

import com.desafioimedicina.crudpessoa.model.Pessoa;

public interface PessoaService {
	
	Pessoa obter(long id);
	
	Pessoa obterPorNome(String nome);
	
	void incluir(Pessoa pessoa);
	
	void alterar(Pessoa pessoa);
	
	void excluir(long id);

	List<Pessoa> obterTodasPessoas(); 
	
	void excluirTodasPessoas();
	
	public boolean isPessoaExistente(Pessoa pessoa);
	
}
