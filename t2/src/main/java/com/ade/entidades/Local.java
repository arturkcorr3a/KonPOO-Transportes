package com.ade.entidades;

public class Local {

	private final String cidade;
	private final int codigo;
	private static int codigoAUX = 1;
	private final String nome;
	private final int latitude;
	private final int longitude;

	public Local(String cidade, String nome, int latitude, int longitude) {
		this.nome = nome;
		this.cidade = cidade;
		this.latitude = latitude;
		this.longitude = longitude;

		this.codigo = codigoAUX;
		codigoAUX++;
	}

	public Local(String cidade, int codigo, String nome, int latitude, int longitude) {
		this.nome = nome;
		this.cidade = cidade;
		this.latitude = latitude;
		this.longitude = longitude;

		this.codigo = codigo;
		codigoAUX++;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public static double distancia(Local local1, Local local2) {
		return Math.sqrt(Math.pow((double) local1.latitude - local2.latitude, 2) +
				Math.pow((double) local1.longitude - local2.longitude, 2));
	}

	@Override
	public String toString() {
		return String.format("Codigo: %d\tNome: %s\tCidade: %s\t%d %d", codigo, nome, cidade, latitude, longitude);
	}

	public String csvString() {
		return cidade + ";" + codigo + ";" + nome + ";" + latitude + ";" + longitude;
	}

	public static void resetCodigo() {
		codigoAUX = 1;
	}

	public static int getCodigoAUX() {
		return codigoAUX;
	}

}
