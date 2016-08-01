package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.consultorio.modelo.EstoqueEntrada;

public class EstoqueEntradaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<EstoqueEntrada> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<EstoqueEntrada>(this.em,EstoqueEntrada.class);
	}

	public void adiciona(EstoqueEntrada t) {
		dao.adiciona(t);
	}

	public void remove(EstoqueEntrada t) {
		dao.remove(t);
	}

	public void atualiza(EstoqueEntrada t) {
		dao.atualiza(t);
	}

	public List<EstoqueEntrada> listaTodos() {
		return dao.listaTodos();
	}

	public EstoqueEntrada buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<EstoqueEntrada> pesquisaEstoqueEntradaPorCompra (EstoqueEntrada estoque){
		Session session = (Session) em.getDelegate();
		Query query = session.createQuery("from Stock where stockCode = :code ");
		query.setParameter("code", "7277");
		List<EstoqueEntrada> list = query.list();
		return list;
	}

}
