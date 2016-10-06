package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.AjusteEstoque;

public class AjusteEstoqueDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<AjusteEstoque> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<AjusteEstoque>(this.em,AjusteEstoque.class);
	}

	public void adiciona(AjusteEstoque t) {
		dao.adiciona(t);
	}

	public void remove(AjusteEstoque t) {
		dao.remove(t);
	}

	public void atualiza(AjusteEstoque t) {
		dao.atualiza(t);
	}

	public List<AjusteEstoque> listaTodos() {
		return dao.listaTodos();
	}

	public AjusteEstoque buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
