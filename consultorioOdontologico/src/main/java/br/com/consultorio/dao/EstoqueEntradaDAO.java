package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.consultorio.modelo.Compra;
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
	
	public List<EstoqueEntrada> pesquisaEstoqueEntradaPorCompra (Compra compra){
		Query query = em.createQuery("from EstoqueEntrada where compra.com_codigo = :codigo ");
		query.setParameter("codigo", compra.getCom_codigo());
		List<EstoqueEntrada> list = query.getResultList();
		return list;
	}

}
