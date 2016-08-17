package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.FormaPagamento;

public class FormaPagamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<FormaPagamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<FormaPagamento>(this.em,FormaPagamento.class);
	}

	public void adiciona(FormaPagamento t) {
		dao.adiciona(t);
	}

	public void remove(FormaPagamento t) {
		dao.remove(t);
	}

	public void atualiza(FormaPagamento t) {
		dao.atualiza(t);
	}

	public List<FormaPagamento> listaTodos() {
		return dao.listaTodos();
	}

	public FormaPagamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
