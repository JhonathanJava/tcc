package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.ModeloAnamnese;

public class ModeloAnamneseDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<ModeloAnamnese> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<ModeloAnamnese>(this.em,ModeloAnamnese.class);
	}

	public void adiciona(ModeloAnamnese t) {
		dao.adiciona(t);
	}

	public void remove(ModeloAnamnese t) {
		dao.remove(t);
	}

	public void atualiza(ModeloAnamnese t) {
		dao.atualiza(t);
	}

	public List<ModeloAnamnese> listaTodos() {
		return dao.listaTodos();
	}

	public ModeloAnamnese buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
