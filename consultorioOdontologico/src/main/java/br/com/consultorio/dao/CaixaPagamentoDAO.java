package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.CaixaPagamento;

public class CaixaPagamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<CaixaPagamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<CaixaPagamento>(this.em,CaixaPagamento.class);
	}

	public void adiciona(CaixaPagamento t) {
		dao.adiciona(t);
	}

	public void remove(CaixaPagamento t) {
		dao.remove(t);
	}

	public void atualiza(CaixaPagamento t) {
		dao.atualiza(t);
	}

	public List<CaixaPagamento> listaTodos() {
		return dao.listaTodos();
	}

	public CaixaPagamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
