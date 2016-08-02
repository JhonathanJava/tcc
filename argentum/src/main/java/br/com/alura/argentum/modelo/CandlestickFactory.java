package br.com.alura.argentum.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class CandlestickFactory {

	public Candlestick geraCandlestick(List<Negociacao> negociacoes,LocalDateTime data){
		double abertura = negociacoes.get(0).getPreco();
		double fechamento = negociacoes.get(negociacoes.size() - 1).getPreco();
		
		double minimo = negociacoes.get(0).getPreco();
		double maximo = negociacoes.get(0).getPreco();;
		
		double volume = 0;
		for (Negociacao negociacao : negociacoes) {
			volume += negociacao.getVolume();
			if(negociacao.getPreco() > maximo){
				maximo = negociacao.getPreco();
			}else if(negociacao.getPreco() < minimo){
				minimo = negociacao.getPreco();
			}
		}
		
		return new Candlestick(abertura, fechamento, maximo, minimo, volume, data);
	}
}
