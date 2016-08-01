package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Cargo;

public class CargoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Cargo> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Cargo>(this.em,Cargo.class);
	}

	public void adiciona(Cargo t) {
		dao.adiciona(t);
	}

	public void remove(Cargo t) {
		dao.remove(t);
	}

	public void atualiza(Cargo t) {
		dao.atualiza(t);
	}

	public List<Cargo> listaTodos() {
		return dao.listaTodos();
	}

	public Cargo buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
