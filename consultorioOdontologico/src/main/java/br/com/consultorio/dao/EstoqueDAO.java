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

import br.com.consultorio.modelo.Estoque;

public class EstoqueDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Estoque> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Estoque>(this.em,Estoque.class);
	}

	public void adiciona(Estoque t) {
		dao.adiciona(t);
	}

	public void remove(Estoque t) {
		dao.atualiza(t);
	}

	public void atualiza(Estoque t) {
		dao.atualiza(t);
	}

	public List<Estoque> listaTodos() {
		return dao.listaTodos();
	}

	public Estoque buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Estoque> buscarPorNome(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Estoque> query = builder.createQuery(Estoque.class);
		Root<Estoque> from = query.from(Estoque.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, builder.like(from.<String>get("est_produto"), "%"+consulta+"%"));
		}
		
		predicate = builder.and(predicate, builder.equal(from.<String>get("est_status"), "A"));
		
		TypedQuery<Estoque> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("est_produto"))));
		List<Estoque> estoque = typedQuery.getResultList();
		
		return estoque;
	}
	
	public List<Estoque> buscarProdutoAtivo(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Estoque> query = builder.createQuery(Estoque.class);
		Root<Estoque> from = query.from(Estoque.class);
		
		Predicate predicate = builder.and();
		
		predicate = builder.and(predicate, builder.equal(from.<String>get("est_status"), "A"));
		
		TypedQuery<Estoque> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("est_produto"))));
		List<Estoque> estoque = typedQuery.getResultList();
		
		return estoque;
	}
	
}
