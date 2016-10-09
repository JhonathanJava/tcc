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

import br.com.consultorio.modelo.Cargo;

public class CargoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Cargo> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Cargo>(this.em,Cargo.class);
	}

	public void adiciona(Cargo t) {
		dao.adiciona(t);
	}

	public void remove(Cargo t) {
		dao.remove(t);
	}

	public void atualiza(Cargo t) {
		dao.atualiza(t);
	}

	public List<Cargo> listaTodos() {
		return dao.listaTodos();
	}

	public Cargo buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Cargo> buscarCargo(String consulta) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Cargo> query = builder.createQuery(Cargo.class);
		Root<Cargo> from = query.from(Cargo.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("car_descricao"), "%"+consulta+"%"));
		}
		predicate = builder.and(predicate, builder.equal(from.<String>get("car_status"), "A"));
		
		TypedQuery<Cargo> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("car_descricao"))));
		List<Cargo> cargos = typedQuery.getResultList();
		
		return cargos;
	}

}
