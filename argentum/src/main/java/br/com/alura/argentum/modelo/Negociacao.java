package br.com.alura.argentum.modelo;

import java.time.LocalDateTime;

public class Negociacao {

	private double preco;
	
	private int quantidade;
	
	private LocalDateTime data;

	public double getPreco() {
		return preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public LocalDateTime getData() {
		return data;
	}
	
	
}
