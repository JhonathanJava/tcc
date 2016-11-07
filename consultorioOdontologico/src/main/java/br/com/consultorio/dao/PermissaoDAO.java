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

import br.com.consultorio.modelo.PerfilPermissao;
import br.com.consultorio.modelo.Permissao;
import br.com.consultorio.modelo.Titulo;

public class PermissaoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Permissao> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Permissao>(this.em,Permissao.class);
	}

	public void adiciona(Permissao p) {
		dao.adiciona(p);
	}

	public void remove(Permissao p) {
		dao.remove(p);
	}

	public void atualiza(Permissao p) {
		dao.atualiza(p);
	}

	public List<Permissao> listaTodos() {
		return dao.listaTodos();
	}

	public Permissao buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<PerfilPermissao> carregaPerfilComPermissoes(){
		TypedQuery<PerfilPermissao> query = em.createQuery("select pe from PerfilPermissao pe",PerfilPermissao.class);
		List<PerfilPermissao> permissao = query.getResultList();
		return permissao;
	}

	public List<Permissao> carregaPermissaoPorPerfil(Long per_codigo) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Permissao> query = builder.createQuery(Permissao.class);
		Root<Permissao> from = query.from(Permissao.class);
		
		Predicate predicate = builder.and();
		
		predicate = builder.and(predicate, builder.equal(from.join("perfil").get("per_codigo"), per_codigo));

		TypedQuery<Permissao> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("per_codigo"))));
		List<Permissao> permissoes = typedQuery.getResultList();
		
		return permissoes;
	}
}
