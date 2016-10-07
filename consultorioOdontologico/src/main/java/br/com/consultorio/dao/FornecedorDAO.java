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

import br.com.consultorio.modelo.Fornecedor;

public class FornecedorDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Fornecedor> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Fornecedor>(this.em,Fornecedor.class);
	}

	public void adiciona(Fornecedor t) {
		dao.adiciona(t);
	}

	public void remove(Fornecedor t) {
		dao.remove(t);
	}

	public void atualiza(Fornecedor t) {
		dao.atualiza(t);
	}

	public List<Fornecedor> listaTodos() {
		return dao.listaTodos();
	}

	public Fornecedor buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Fornecedor> buscarPorNome(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Fornecedor> query = builder.createQuery(Fornecedor.class);
		Root<Fornecedor> from = query.from(Fornecedor.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("fun_nome"), "%"+consulta+"%"));
		}
		
		 predicate = builder.and(predicate, builder.equal(from.<String>get("fun_status"), "A"));
		
		TypedQuery<Fornecedor> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("fun_nome"))));
		List<Fornecedor> fornecedores = typedQuery.getResultList();
		
		return fornecedores;
	}

}
