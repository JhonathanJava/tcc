package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Orcamento;

public class OrcamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Orcamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Orcamento>(this.em,Orcamento.class);
	}

	public void adiciona(Orcamento t) {
		dao.adiciona(t);
	}

	public void remove(Orcamento t) {
		dao.remove(t);
	}

	public void atualiza(Orcamento t) {
		dao.atualiza(t);
	}

	public List<Orcamento> listaTodos() {
		return dao.listaTodos();
	}

	public Orcamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

}
