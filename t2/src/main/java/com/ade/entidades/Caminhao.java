package com.ade.entidades;

public class Caminhao {

	private final String nome;
	private final double autonomia;
	private final int codigo;
	private static int codigoAUX = 1;
	private final double velocidade;
	private double custoPorKm;
	private final int capacidadePeso;
	private static double valorGasolina = 5.32;
	private Status status;

	public Caminhao(String nome, double autonomia, double velocidade, int capacidadePeso) {
		this.nome = nome;
		this.autonomia = autonomia;
		this.velocidade = velocidade;
		this.custoPorKm = valorGasolina / autonomia;
		codigo = codigoAUX;
		this.capacidadePeso = capacidadePeso;
		codigoAUX++;
		this.status = Status.DISPONIVEL;
	}

	public Caminhao(String nome, double autonomia, int codigo, double velocidade, int capacidade, Status status) {
		this.nome = nome;
		this.autonomia = autonomia;
		this.velocidade = velocidade;
		this.custoPorKm = valorGasolina / autonomia;
		this.codigo = codigo;
		this.capacidadePeso = capacidade;
		this.status = status;
		codigoAUX++;
	}

	public String getNome() {
		return nome;
	}

	public static void setValorGasolina(double valorGasolina) {
		if (valorGasolina > 0)
			Caminhao.valorGasolina = valorGasolina;
	}

	public double getCustoPorKm() {
		return custoPorKm;
	}

	public int getCodigo() {
		return codigo;
	}

	public void atualizaCustoPorKM() {
		this.custoPorKm = valorGasolina / autonomia;
	}

	public int getCapacidadePeso() {
		return capacidadePeso;
	}

	public double getVelocidade() {
		return velocidade;
	}

	@Override
	public String toString() {
		return "[" + codigo + "] " + nome + "\n" +
				autonomia + " Km/\t" + velocidade + " Km/h\t" + custoPorKm + " R$/Km\t" + capacidadePeso;
	}

	public String csvString() {
		return nome + ";" + autonomia + ";" + codigo + ";" + velocidade + ";" + custoPorKm + ";" + capacidadePeso + ";"
				+ status.getId();
	}

	public enum Status {

		LOCADO(0), DISPONIVEL(1);

		private final int id;

		Status(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	public Status getStatus() {
		return status;
	}

	public void locarCaminhao() {
		status = Status.LOCADO;
	}

	public void disponibilizarCaminhao() {
		status = Status.DISPONIVEL;
	}
}
