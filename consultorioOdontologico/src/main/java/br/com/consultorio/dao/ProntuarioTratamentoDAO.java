package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.ProntuarioTratamento;

public class ProntuarioTratamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<ProntuarioTratamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<ProntuarioTratamento>(this.em,ProntuarioTratamento.class);
	}

	public void adiciona(ProntuarioTratamento t) {
		dao.adiciona(t);
	}

	public void remove(ProntuarioTratamento t) {
		dao.remove(t);
	}

	public void atualiza(ProntuarioTratamento t) {
		dao.atualiza(t);
	}

	public List<ProntuarioTratamento> listaTodos() {
		return dao.listaTodos();
	}

	public ProntuarioTratamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}


}
