package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Campanha;

public class CampanhaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Campanha> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Campanha>(this.em,Campanha.class);
	}

	public void adiciona(Campanha t) {
		dao.adiciona(t);
	}

	public void remove(Campanha t) {
		dao.remove(t);
	}

	public void atualiza(Campanha t) {
		dao.atualiza(t);
	}

	public List<Campanha> listaTodos() {
		return dao.listaTodos();
	}

	public Campanha buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
