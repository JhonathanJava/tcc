package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.consultorio.modelo.Anamnese;
import br.com.consultorio.modelo.ProntuarioTratamento;
import br.com.consultorio.modelo.Titulo;

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

	public List<ProntuarioTratamento> buscaTratatamentoPorPaciente(Long codigo){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ProntuarioTratamento> query = builder.createQuery(ProntuarioTratamento.class);
		Root<ProntuarioTratamento> from = query.from(ProntuarioTratamento.class);
		
		Predicate predicate = builder.and();
		
		predicate = builder.equal(from.join("paciente").get("pac_codigo"), codigo);
		
		TypedQuery<ProntuarioTratamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("prt_codigo"))));
		List<ProntuarioTratamento> titulos = typedQuery.getResultList();
		
		return titulos;
	}
	

}
