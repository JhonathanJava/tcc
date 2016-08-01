package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.EstoqueEntrada;

public class EstoqueDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Estoque> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Estoque>(this.em,Estoque.class);
	}

	public void adiciona(Estoque t) {
		dao.adiciona(t);
	}

	public void remove(Estoque t) {
		dao.remove(t);
	}

	public void atualiza(Estoque t) {
		dao.atualiza(t);
	}

	public List<Estoque> listaTodos() {
		return dao.listaTodos();
	}

	public Estoque buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
}
