package com.desafioimedicina.crudpessoa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.desafioimedicina.crudpessoa.model.Pessoa;

@Service("pessoaService")
public class PessoaServiceImpl implements PessoaService{
	
	private static final AtomicLong contador = new AtomicLong();
	
	private static List<Pessoa> listaPessoas;
	
	static{
		listaPessoas= popularListaMock();
	}

	public List<Pessoa> obterTodasPessoas() {
		return listaPessoas;
	}
	
	public Pessoa obter(long id) {
		for(Pessoa pessoa : listaPessoas){
			if(pessoa.getId() == id){
				return pessoa;
			}
		}
		return null;
	}
	
	public Pessoa obterPorNome(String nome) {
		for(Pessoa pessoa : listaPessoas){
			if(pessoa.getNome().equalsIgnoreCase(nome)){
				return pessoa;
			}
		}
		return null;
	}
	
	public void incluir(Pessoa pessoa) {
		pessoa.setId(contador.incrementAndGet());
		listaPessoas.add(pessoa);
	}

	public void alterar(Pessoa pessoa) {
		int index = listaPessoas.indexOf(pessoa);
		listaPessoas.set(index, pessoa);
	}

	public void excluir(long id) {
		for (Iterator<Pessoa> iterator = listaPessoas.iterator(); iterator.hasNext(); ) {
		    Pessoa pessoa = iterator.next();
		    if (pessoa.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isPessoaExistente(Pessoa pessoa) {
		return obterPorNome(pessoa.getNome()) != null;
	}
	
	public void excluirTodasPessoas(){
		listaPessoas.clear();
	}

	private static List<Pessoa> popularListaMock(){
		List<Pessoa> users = new ArrayList<Pessoa>();
		users.add(new Pessoa(contador.incrementAndGet(),"Jhonatan Igor Matias Salles", new Date(), "jhonatan@hotmail.com", "31975099081"));
		users.add(new Pessoa(contador.incrementAndGet(),"Yasmin Christine Silva Santos", new Date(), "yasmin@gmail.com", "31912345678"));
		users.add(new Pessoa(contador.incrementAndGet(),"Paulo Teste", new Date(), "paulo@abc.com", "319098766543"));
		return users;
	}

}
