package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.consultorio.modelo.PerfilPermissao;
import br.com.consultorio.modelo.Permissao;

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
	
	public List<Permissao> carregaPermissoesPeloPerfilPermissaoId(PerfilPermissao perfilPermissao){
		TypedQuery<Permissao> query = em.createQuery("select p from Permissao p where p.perfilPermissao.perfilPermissaoCodigo =  :perfilPermissaoId",Permissao.class);
		query.setParameter("perfilPermissaoId", perfilPermissao.getPerfilPermissaoCodigo());
		List<Permissao> permissoes = query.getResultList();
		return permissoes;
	}
	
	public List<PerfilPermissao> carregaPerfilComPermissoes(){
		TypedQuery<PerfilPermissao> query = em.createQuery("select pe from PerfilPermissao pe",PerfilPermissao.class);
		List<PerfilPermissao> permissao = query.getResultList();
		return permissao;
	}
}
