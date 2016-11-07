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

import br.com.consultorio.modelo.AgendamentoTratamento;
import br.com.consultorio.modelo.Paciente;

public class AgendaTratamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<AgendamentoTratamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<AgendamentoTratamento>(this.em,AgendamentoTratamento.class);
	}

	public void adiciona(AgendamentoTratamento t) {
		dao.adiciona(t);
	}

	public void remove(AgendamentoTratamento t) {
		dao.remove(t);
	}

	public void atualiza(AgendamentoTratamento t) {
		dao.atualiza(t);
	}

	public List<AgendamentoTratamento> listaTodos() {
		return dao.listaTodos();
	}

	public AgendamentoTratamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<AgendamentoTratamento> buscarPorAgenda(Long age_codigo){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AgendamentoTratamento> query = builder.createQuery(AgendamentoTratamento.class);
		Root<AgendamentoTratamento> from = query.from(AgendamentoTratamento.class);
		
		Predicate predicate = builder.and();
		
		 predicate = builder.and(predicate, builder.equal(from.join("agendamento").get("age_codigo"), age_codigo));
		
		TypedQuery<AgendamentoTratamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("agt_codigo"))));
		List<AgendamentoTratamento> agendamentoTratamento = typedQuery.getResultList();
		
		return agendamentoTratamento;
	}
	
	public List<AgendamentoTratamento> buscarPorPaciente(Long pac_codigo){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AgendamentoTratamento> query = builder.createQuery(AgendamentoTratamento.class);
		Root<AgendamentoTratamento> from = query.from(AgendamentoTratamento.class);
		
		Predicate predicate = builder.and();
		
		 predicate = builder.and(predicate, builder.equal(from.join("paciente").get("pac_codigo"), pac_codigo));
		
		TypedQuery<AgendamentoTratamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("agt_codigo"))));
		List<AgendamentoTratamento> agendamentoTratamento = typedQuery.getResultList();
		
		return agendamentoTratamento;
	}


}
