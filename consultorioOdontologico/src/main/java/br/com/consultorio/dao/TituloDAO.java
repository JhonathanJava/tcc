package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Titulo;

public class TituloDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Titulo> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Titulo>(this.em,Titulo.class);
	}

	public void adiciona(Titulo t) {
		dao.adiciona(t);
	}

	public void remove(Titulo t) {
		dao.remove(t);
	}

	public void atualiza(Titulo t) {
		dao.atualiza(t);
	}

	public List<Titulo> listaTodos() {
		return dao.listaTodos();
	}

	public Titulo buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
