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
import br.com.consultorio.modelo.Titulo;

public class TituloDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Titulo> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Titulo>(this.em,Titulo.class);
	}

	public void adiciona(Titulo t) {
		dao.adiciona(t);
	}

	public void remove(Titulo t) {
		dao.remove(t);
	}

	public void atualiza(Titulo t) {
		dao.atualiza(t);
	}

	public List<Titulo> listaTodos() {
		return dao.listaTodos();
	}

	public Titulo buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Titulo> buscarTitulo(String tipo,String status){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Titulo> query = builder.createQuery(Titulo.class);
		Root<Titulo> from = query.from(Titulo.class);
		
		Predicate predicate = builder.and();
		
		predicate = builder.and(predicate, builder.equal(from.<String>get("tit_tipo"), tipo));
		predicate = builder.and(predicate, builder.equal(from.<String>get("tit_status"), status));
		
		TypedQuery<Titulo> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("tit_codigo"))));
		List<Titulo> titulos = typedQuery.getResultList();
		
		return titulos;
	}
	

}
