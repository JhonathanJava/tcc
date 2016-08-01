package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Compra;

public class CompraDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Compra> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Compra>(this.em,Compra.class);
	}

	public void adiciona(Compra t) {
		dao.adiciona(t);
	}

	public void remove(Compra t) {
		dao.remove(t);
	}

	public void atualiza(Compra t) {
		dao.atualiza(t);
	}

	public List<Compra> listaTodos() {
		return dao.listaTodos();
	}

	public Compra buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
