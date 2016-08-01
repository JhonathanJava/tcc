package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Programa;

public class ProgramaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Programa> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Programa>(this.em,Programa.class);
	}

	public void adiciona(Programa p) {
		dao.adiciona(p);
	}

	public void remove(Programa p) {
		dao.remove(p);
	}

	public void atualiza(Programa p) {
		dao.atualiza(p);
	}

	public List<Programa> listaTodos() {
		return dao.listaTodos();
	}

	public Programa buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
