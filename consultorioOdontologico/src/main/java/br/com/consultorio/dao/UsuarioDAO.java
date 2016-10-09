package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.util.jsf.FacesUtil;

public class UsuarioDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Usuario> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Usuario>(this.em,Usuario.class);
	}
	
	public void adiciona(Usuario t) {
		dao.adiciona(t);
	}

	public void remove(Usuario t) {
		dao.remove(t);
	}

	public void atualiza(Usuario t) {
		dao.atualiza(t);
	}

	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	public Usuario buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Usuario> pesquisaPorFiltro(Usuario usuario){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> from = query.from(Usuario.class);
		
		Predicate predicate = builder.and();
		if (usuario.getUsu_nome() != null && !usuario.getUsu_nome().equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("usu_nome"), "%"+usuario.getUsu_nome()+"%"));
		}
		
		TypedQuery<Usuario> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("usu_nome"))));
		List<Usuario> usuarios = typedQuery.getResultList();
		
		return usuarios;
		
		
	}
	
	public Usuario existe(Usuario usuario){
		Usuario resultado = new Usuario();
		TypedQuery<Usuario> query = em.createQuery(" select u from Usuario u where u.usu_ativo = 'Ativo' and u.usu_login = :uLogin and u.usu_senha = :uSenha ", Usuario.class);
		
		query.setParameter("uLogin",usuario.getUsu_login());
		query.setParameter("uSenha",usuario.getUsu_senha());
		
		try{
			System.out.println("Senha = "+usuario.getUsu_senha());
			resultado = query.getSingleResult();
			System.out.println("Resultado Usuario Nome: "+resultado.getUsu_login());
			if(resultado != null){
				FacesUtil.addSuccessMessage("Logado com Sucesso!");
			}
		}catch(NoResultException ex){
			FacesUtil.addWarnMessage("Nenhum Usuário Encontrado!");
			return null;
		}
		return resultado;
	}

	public List<Usuario> buscarProfissional(String consulta) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> from = query.from(Usuario.class);
		
		Predicate predicate = builder.and();
		if (consulta != null && !consulta.equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("usu_nome"), "%"+consulta+"%"));
		}
		
		 predicate = builder.and(predicate, builder.equal(from.<String>get("usu_status"), "A"));
		 predicate = builder.and(predicate, builder.equal(from.<String>get("usu_profissional"), "Sim"));
		
		TypedQuery<Usuario> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("usu_nome"))));
		List<Usuario> usuarios = typedQuery.getResultList();
		
		return usuarios;
	}
}
