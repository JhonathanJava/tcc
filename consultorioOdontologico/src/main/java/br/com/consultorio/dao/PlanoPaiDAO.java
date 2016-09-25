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

public class PlanoPaiDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<PlanoPai> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<PlanoPai>(this.em,PlanoPai.class);
	}

	public void adiciona(PlanoPai t) {
		dao.adiciona(t);
	}

	public void remove(PlanoPai t) {
		dao.remove(t);
	}

	public void atualiza(PlanoPai t) {
		dao.atualiza(t);
	}

	public List<PlanoPai> listaTodos() {
		return dao.listaTodos();
	}

	public PlanoPai buscaPorId(Long id) {
		System.out.println("Codigo -> "+id);
		return dao.buscaPorId(id);
	}

	public List<PlanoPai> pesquisaPorFiltro(PlanoPai planoFiltro) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<PlanoPai> query = builder.createQuery(PlanoPai.class);
		Root<PlanoPai> from = query.from(PlanoPai.class);
		
		
		
		Predicate predicate = builder.and();
		if (planoFiltro.getPlp_descricao() != null && !planoFiltro.getPlp_descricao().equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("plp_descricao"), "%"+planoFiltro.getPlp_descricao()+"%"));
		}
		
		if (planoFiltro.getPlp_status() != null && !planoFiltro.getPlp_status().equals("")){
		    predicate = builder.and(predicate, 
		        builder.equal(from.<String>get("plp_status"), planoFiltro.getPlp_status()));
		}
		
		// 2 parametro dataInicial - 3 parametro dataFinal Arrumar para pesquisar por Intervalo
		//if (planoFiltro.getPlp_dataCadastro() != null && !planoFiltro.getPlp_dataCadastro().equals("")){
		    //predicate = builder.and(predicate, 
		    	//builder.between(from.<Date>get("plp_dataCadastro"), planoFiltro.getPlp_dataCadastro(), planoFiltro.getPlp_dataCadastro())); 
		//}
		
		TypedQuery<PlanoPai> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("plp_descricao"))));
		List<PlanoPai> planos = typedQuery.getResultList();
		
		return planos;
	}

}
