package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.ProntuarioAnamnese;

public class ProntuarioAnamneseDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<ProntuarioAnamnese> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<ProntuarioAnamnese>(this.em,ProntuarioAnamnese.class);
	}

	public void adiciona(ProntuarioAnamnese t) {
		dao.adiciona(t);
	}

	public void remove(ProntuarioAnamnese t) {
		dao.remove(t);
	}

	public void atualiza(ProntuarioAnamnese t) {
		dao.atualiza(t);
	}

	public List<ProntuarioAnamnese> listaTodos() {
		return dao.listaTodos();
	}

	public ProntuarioAnamnese buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
}
