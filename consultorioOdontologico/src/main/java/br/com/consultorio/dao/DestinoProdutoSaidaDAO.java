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

import br.com.consultorio.modelo.DestinoProdutoSaida;

public class DestinoProdutoSaidaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<DestinoProdutoSaida> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<DestinoProdutoSaida>(this.em,DestinoProdutoSaida.class);
	}

	public void adiciona(DestinoProdutoSaida t) {
		dao.adiciona(t);
	}

	public void remove(DestinoProdutoSaida t) {
		dao.remove(t);
	}

	public void atualiza(DestinoProdutoSaida t) {
		dao.atualiza(t);
	}

	public List<DestinoProdutoSaida> listaTodos() {
		return dao.listaTodos();
	}

	public DestinoProdutoSaida buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<DestinoProdutoSaida> buscarPorNome(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<DestinoProdutoSaida> query = builder.createQuery(DestinoProdutoSaida.class);
		Root<DestinoProdutoSaida> from = query.from(DestinoProdutoSaida.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, builder.like(from.<String>get("des_descricao"), "%"+consulta+"%"));
		}
		
		predicate = builder.and(predicate, builder.equal(from.<String>get("des_status"), "A"));
		
		TypedQuery<DestinoProdutoSaida> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("des_descricao"))));
		List<DestinoProdutoSaida> destinoSaidas = typedQuery.getResultList();
		
		return destinoSaidas;
	}

}
