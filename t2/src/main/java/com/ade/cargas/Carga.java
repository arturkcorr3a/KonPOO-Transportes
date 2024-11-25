package com.ade.cargas;

import com.ade.entidades.Caminhao;
import com.ade.entidades.Cliente;
import com.ade.entidades.Local;

public class Carga {

	private final int codigo;

	private final int peso;
	private final int tempoMaximo;
	private final double valorDeclarado;

	private final Local destino;
	private final Local origem;
	private final Cliente cliente;
	private Status status;
	private final TipoCarga tipoCarga;

	private Caminhao caminhaoDesignado;

	public Carga(int codigo, int peso, int tempoMaximo, double valorDeclarado, Local destino, Local origem,
			Cliente cliente, TipoCarga tipoCarga) {
		this.codigo = codigo;
		this.peso = peso;
		this.tempoMaximo = tempoMaximo;
		this.valorDeclarado = valorDeclarado;
		this.destino = destino;
		this.origem = origem;
		this.cliente = cliente;
		this.status = Status.PENDENTE;
		this.tipoCarga = tipoCarga;
	}

	public void cancelar() {
		this.status = Status.CANCELADA;
	}

	public double distancia() {
		return Local.distancia(origem, destino);
	}

	public void finalizar() {
		status = Status.FINALIZADA;
	}

	public double frete() {
		return precoPorDistancia() + precoPorPeso();
	}

	public void locar() {
		status = Status.LOCADA;
	}

	public double precoPorDistancia() {
		if (caminhaoDesignado == null)
			return 0;
		return distancia() * caminhaoDesignado.getCustoPorKm();
	}

	public double precoPorPeso() {
		return peso * tipoCarga.fatorPeso;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getCodigo() {
		return codigo;
	}

	public Status getStatus() {
		return status;
	}

	public int getPeso() {
		return peso;
	}

	public int getTempoMaximo() {
		return tempoMaximo;
	}

	public void setCaminhaoDesignado(Caminhao caminhaoDesignado) {
		this.caminhaoDesignado = caminhaoDesignado;
		this.status = Status.LOCADA;
	}

	public Caminhao getCaminhaoDesignado() {
		return caminhaoDesignado;
	}

	@Override
	public String toString() {
		if (caminhaoDesignado == null) {
			return "Carga [" + codigo + "] " + status + " - " + tipoCarga + "\n" +
					"Cliente: " + cliente + "\tOrigem: " + origem + "\tDestino: " + destino + "\n" +
					peso + " t\t" + valorDeclarado + " R$\t" + tempoMaximo + " dias\n\n" + "\n" +
					"Valor Total do Frete: " + String.format("%.2f", frete()) + " R$";
		}
		return "Carga [" + codigo + "] " + status + " - " + tipoCarga + "\n" +
				"Cliente: " + cliente + "\tOrigem: " + origem + "\tDestino: " + destino + "\n" +
				peso + " t\t" + valorDeclarado + " R$\t" + tempoMaximo + " dias\n\n" +
				caminhaoDesignado.toString() + "\n\n" +
				"Valor Total do Frete: " + String.format("%.2f", frete()) + " R$";
	}

	public String csvString() {
		if (caminhaoDesignado != null)
			return codigo + ";" + peso + ";" + tempoMaximo + ";" + valorDeclarado + ";" + destino.getCodigo() + ";" +
					origem.getCodigo() + ";" + cliente.getCodigo() + ";" + status.id + ";" + tipoCarga.getNumero() + ";"
					+
					String.format("%d", caminhaoDesignado.getCodigo());
		return codigo + ";" + peso + ";" + tempoMaximo + ";" + valorDeclarado + ";" + destino.getCodigo() + ";" +
				origem.getCodigo() + ";" + cliente.getCodigo() + ";" + status.id + ";" + tipoCarga.getNumero() + ";" +
				"null";
	}

	public enum Status {

		CANCELADA(-1), FINALIZADA(0),
		LOCADA(1), PENDENTE(2);

		private final int id;

		Status(int id) {
			this.id = id;
		}
	}
}
