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

import br.com.consultorio.modelo.Agenda;
import br.com.consultorio.modelo.Paciente;

public class AgendaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Agenda> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Agenda>(this.em,Agenda.class);
	}

	public void adiciona(Agenda t) {
		dao.adiciona(t);
	}

	public void remove(Agenda t) {
		dao.remove(t);
	}

	public void atualiza(Agenda t) {
		dao.atualiza(t);
	}

	public List<Agenda> listaTodos() {
		return dao.listaTodos();
	}

	public Agenda buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Agenda> buscarAgendamentoAberto(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Agenda> query = builder.createQuery(Agenda.class);
		Root<Agenda> from = query.from(Agenda.class);
		
		Predicate predicate = builder.and();
		 predicate = builder.and(predicate, builder.notEqual(from.<String>get("age_status"), "Consultado"));
		 predicate = builder.and(predicate, builder.notEqual(from.<String>get("age_status"), "Ausente"));
		
		TypedQuery<Agenda> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("age_dataConsulta"))));
		List<Agenda> agendamento = typedQuery.getResultList();
		
		return agendamento;
	}
	


}
