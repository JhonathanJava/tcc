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

import br.com.consultorio.modelo.CondicaoPagamento;
import br.com.consultorio.modelo.Estoque;

public class CondicaoPagamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<CondicaoPagamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<CondicaoPagamento>(this.em,CondicaoPagamento.class);
	}

	public void adiciona(CondicaoPagamento t) {
		dao.adiciona(t);
	}

	public void remove(CondicaoPagamento t) {
		dao.remove(t);
	}

	public void atualiza(CondicaoPagamento t) {
		dao.atualiza(t);
	}

	public List<CondicaoPagamento> listaTodos() {
		return dao.listaTodos();
	}

	public CondicaoPagamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<CondicaoPagamento> buscarCondicaoPorNomeAtivo(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<CondicaoPagamento> query = builder.createQuery(CondicaoPagamento.class);
		Root<CondicaoPagamento> from = query.from(CondicaoPagamento.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, builder.like(from.<String>get("con_descricao"), "%"+consulta+"%"));
		}
		
		predicate = builder.and(predicate, builder.equal(from.<String>get("con_status"), "A"));
		
		TypedQuery<CondicaoPagamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("con_descricao"))));
		List<CondicaoPagamento> condicaoPagamento = typedQuery.getResultList();
		
		return condicaoPagamento;
	}


}
