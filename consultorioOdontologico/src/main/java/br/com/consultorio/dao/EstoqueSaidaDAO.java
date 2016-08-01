package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.EstoqueSaida;

public class EstoqueSaidaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<EstoqueSaida> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<EstoqueSaida>(this.em,EstoqueSaida.class);
	}

	public void adiciona(EstoqueSaida t) {
		dao.adiciona(t);
	}

	public void remove(EstoqueSaida t) {
		dao.remove(t);
	}

	public void atualiza(EstoqueSaida t) {
		dao.atualiza(t);
	}

	public List<EstoqueSaida> listaTodos() {
		return dao.listaTodos();
	}

	public EstoqueSaida buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
