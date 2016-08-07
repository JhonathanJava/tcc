package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Prontuario;

public class ProntuarioDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Prontuario> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Prontuario>(this.em,Prontuario.class);
	}

	public void adiciona(Prontuario t) {
		dao.adiciona(t);
	}

	public void remove(Prontuario t) {
		dao.remove(t);
	}

	public void atualiza(Prontuario t) {
		dao.atualiza(t);
	}

	public List<Prontuario> listaTodos() {
		return dao.listaTodos();
	}

	public Prontuario buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
