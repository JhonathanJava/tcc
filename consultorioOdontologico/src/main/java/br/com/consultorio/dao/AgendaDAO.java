package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Agenda;

public class AgendaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Agenda> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Agenda>(this.em,Agenda.class);
	}

	public void adiciona(Agenda t) {
		dao.adiciona(t);
	}

	public void remove(Agenda t) {
		dao.remove(t);
	}

	public void atualiza(Agenda t) {
		dao.atualiza(t);
	}

	public List<Agenda> listaTodos() {
		return dao.listaTodos();
	}

	public Agenda buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
