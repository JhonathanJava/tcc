package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.CampanhaPaciente;

public class CampanhaPacienteDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<CampanhaPaciente> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<CampanhaPaciente>(this.em,CampanhaPaciente.class);
	}

	public void adiciona(CampanhaPaciente t) {
		dao.adiciona(t);
	}

	public void remove(CampanhaPaciente t) {
		dao.remove(t);
	}

	public void atualiza(CampanhaPaciente t) {
		dao.atualiza(t);
	}

	public List<CampanhaPaciente> listaTodos() {
		return dao.listaTodos();
	}

	public CampanhaPaciente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
