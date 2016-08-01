package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Plano;

public class PlanoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Plano> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Plano>(this.em,Plano.class);
	}

	public void adiciona(Plano t) {
		dao.adiciona(t);
	}

	public void remove(Plano t) {
		dao.remove(t);
	}

	public void atualiza(Plano t) {
		dao.atualiza(t);
	}

	public List<Plano> listaTodos() {
		return dao.listaTodos();
	}

	public Plano buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

}
