package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Fornecedor;

public class FornecedorDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Fornecedor> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Fornecedor>(this.em,Fornecedor.class);
	}

	public void adiciona(Fornecedor t) {
		dao.adiciona(t);
	}

	public void remove(Fornecedor t) {
		dao.remove(t);
	}

	public void atualiza(Fornecedor t) {
		dao.atualiza(t);
	}

	public List<Fornecedor> listaTodos() {
		return dao.listaTodos();
	}

	public Fornecedor buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
