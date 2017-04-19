package com.desafioimedicina.crudpessoa.model;

import java.util.Date;

public class Pessoa {

//	private long id;
//	private String username;
//	private String address;
//	private String email;
	
	private long id;
	private String nome;
	private Date dataNascimento;
	private String email;
	private String telefone;
	
	public Pessoa(){
		id = 0;
	}
	
	public Pessoa(long id, String nome, Date dataNascimento, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.telefone = telefone;
	}
	
//	public User(long id, String username, String address, String email){
//		this.id = id;
//		this.username = username;
//		this.address = address;
//		this.email = email;
//	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pessoa))
			return false;
		Pessoa other = (Pessoa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nome=" + nome + ", email="
				+ email + ", telefone=" + telefone + "]";
	}
}
