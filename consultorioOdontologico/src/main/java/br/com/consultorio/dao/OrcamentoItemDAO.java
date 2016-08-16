package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.OrcamentoItem;

public class OrcamentoItemDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<OrcamentoItem> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<OrcamentoItem>(this.em,OrcamentoItem.class);
	}

	public void adiciona(OrcamentoItem t) {
		dao.adiciona(t);
	}

	public void remove(OrcamentoItem t) {
		dao.remove(t);
	}

	public void atualiza(OrcamentoItem t) {
		dao.atualiza(t);
	}

	public List<OrcamentoItem> listaTodos() {
		return dao.listaTodos();
	}

	public OrcamentoItem buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

}
