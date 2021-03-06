package br.com.consultorio.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.com.consultorio.util.jsf.FacesUtil;

public class GenericDAO<T> {

	private final Class<T> classe;
	@Produces @RequestScoped
	private EntityManager em;

	public GenericDAO(EntityManager manager,Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	public void adiciona(T t) {
		em.persist(t);
	}

	public void remove(T t) {
		try{
			em.remove(em.merge(t));
		}catch(PersistenceException e){
			FacesUtil.addErrorMessage("Erro: Não foi Feito alteração no Registro");
		}
	}

	public void atualiza(T t) {
		em.merge(t);
	}
	
	public List<T> createQuery(String hql){
		Query query = em.createQuery(hql);
		List<T> results = query.getResultList();
		return results;
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Long id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}
	
	 public void finaliza(@Disposes EntityManager manager) {
	      manager.close();
	   }
}
