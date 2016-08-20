package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Caixa;

public class CaixaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Caixa> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Caixa>(this.em,Caixa.class);
	}

	public void adiciona(Caixa t) {
		dao.adiciona(t);
	}

	public void remove(Caixa t) {
		dao.remove(t);
	}

	public void atualiza(Caixa t) {
		dao.atualiza(t);
	}

	public List<Caixa> listaTodos() {
		return dao.listaTodos();
	}

	public Caixa buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
