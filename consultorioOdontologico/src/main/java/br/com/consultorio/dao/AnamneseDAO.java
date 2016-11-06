package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.Anamnese;

public class AnamneseDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Anamnese> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Anamnese>(this.em,Anamnese.class);
	}

	public void adiciona(Anamnese t) {
		dao.adiciona(t);
	}

	public void remove(Anamnese t) {
		dao.remove(t);
	}

	public void atualiza(Anamnese t) {
		dao.atualiza(t);
	}

	public List<Anamnese> listaTodos() {
		return dao.listaTodos();
	}

	public Anamnese buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Anamnese> listaAnamnesePorModelo(Long codigo){
		String hql = "from Anamnese a where a.modeloAnamnese.moa_codigo = "+codigo;
		System.out.println(hql);
		return dao.createQuery(hql);
	}

}
