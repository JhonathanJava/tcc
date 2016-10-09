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

import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.modelo.Tratamento;

public class TratamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Tratamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Tratamento>(this.em,Tratamento.class);
	}

	public void adiciona(Tratamento t) {
		dao.adiciona(t);
	}

	public void remove(Tratamento t) {
		dao.remove(t);
	}

	public void atualiza(Tratamento t) {
		dao.atualiza(t);
	}

	public List<Tratamento> listaTodos() {
		return dao.listaTodos();
	}

	public Tratamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Tratamento> pesquisaPorFiltro(Tratamento filterTratamento) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tratamento> query = builder.createQuery(Tratamento.class);
		Root<Tratamento> from = query.from(Tratamento.class);
		
		
		
		Predicate predicate = builder.and();
		if (filterTratamento.getTra_descricao() != null && !filterTratamento.getTra_descricao().equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("tra_descricao"), "%"+filterTratamento.getTra_descricao()+"%"));
		}
		
		if (filterTratamento.getTra_status() != null && !filterTratamento.getTra_status().equals("")){
		    predicate = builder.and(predicate, 
		        builder.equal(from.<String>get("tra_status"), filterTratamento.getTra_status()));
		}
		
		TypedQuery<Tratamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("tra_descricao"))));
		List<Tratamento> tratamentos = typedQuery.getResultList();
		
		return tratamentos;
	}

	public List<Tratamento> buscarPorNome(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tratamento> query = builder.createQuery(Tratamento.class);
		Root<Tratamento> from = query.from(Tratamento.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("tra_descricao"), "%"+consulta+"%"));
		}
		
		 predicate = builder.and(predicate, builder.equal(from.<String>get("tra_status"), "A"));
		
		TypedQuery<Tratamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("tra_descricao"))));
		List<Tratamento> tratamentos = typedQuery.getResultList();
		
		return tratamentos;
	}


}
