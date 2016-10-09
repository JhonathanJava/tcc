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

import br.com.consultorio.modelo.Perfil;

public class PerfilDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Perfil> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Perfil>(this.em,Perfil.class);
	}

	public void adiciona(Perfil p) {
		dao.adiciona(p);
	}

	public void remove(Perfil p) {
		dao.remove(p);
	}

	public void atualiza(Perfil p) {
		dao.atualiza(p);
	}

	public List<Perfil> listaTodos() {
		return dao.listaTodos();
	}

	public Perfil buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Perfil> buscaPerfilSemPermissao() {
		TypedQuery<Perfil> query = em.createQuery("select p from Perfil p left join Permissao pe on pe.perfil.per_codigo = p.per_codigo where pe.perfil.per_codigo is null",Perfil.class);
		List<Perfil> perfils = query.getResultList();
		return perfils;
	}

	public List<Perfil> buscarPorNome(String consulta){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Perfil> query = builder.createQuery(Perfil.class);
		Root<Perfil> from = query.from(Perfil.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("per_descricao"), "%"+consulta+"%"));
		}
		
		 predicate = builder.and(predicate, builder.equal(from.<String>get("per_status"), "Ativo"));
		
		TypedQuery<Perfil> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("per_descricao"))));
		List<Perfil> perfil = typedQuery.getResultList();
		
		return perfil;
	}
}
