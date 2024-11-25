package com.ade.entidades;

public class Cliente {

	private final int codigo;
	private final String nome;
	private final String telefone;

	public Cliente(String nome, String telefone, int codigo) {
		this.nome = nome;
		this.telefone = telefone;
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return String.format("Codigo: %d\tNome: %s\tTelefone: %s", codigo, nome, telefone);
	}

	public String csvString() {
		return codigo + ";" + nome + ";" + telefone;
	}
}