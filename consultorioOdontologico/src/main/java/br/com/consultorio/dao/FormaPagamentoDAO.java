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
import br.com.consultorio.modelo.FormaPagamento;

public class FormaPagamentoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<FormaPagamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<FormaPagamento>(this.em,FormaPagamento.class);
	}

	public void adiciona(FormaPagamento t) {
		dao.adiciona(t);
	}

	public void remove(FormaPagamento t) {
		dao.remove(t);
	}

	public void atualiza(FormaPagamento t) {
		dao.atualiza(t);
	}

	public List<FormaPagamento> listaTodos() {
		return dao.listaTodos();
	}

	public FormaPagamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<FormaPagamento> buscarFormaPorNomeAtivo(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<FormaPagamento> query = builder.createQuery(FormaPagamento.class);
		Root<FormaPagamento> from = query.from(FormaPagamento.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, builder.like(from.<String>get("for_descricao"), "%"+consulta+"%"));
		}
		
		predicate = builder.and(predicate, builder.equal(from.<String>get("for_status"), "A"));
		
		TypedQuery<FormaPagamento> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("for_descricao"))));
		List<FormaPagamento> formaPagamento = typedQuery.getResultList();
		
		return formaPagamento;
	}

}
