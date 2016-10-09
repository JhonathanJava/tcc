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

import br.com.consultorio.modelo.Orcamento;
import br.com.consultorio.modelo.OrcamentoItem;

public class OrcamentoItemDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<OrcamentoItem> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<OrcamentoItem>(this.em,OrcamentoItem.class);
	}

	public void adiciona(OrcamentoItem t) {
		dao.adiciona(t);
	}

	public void remove(OrcamentoItem t) {
		dao.remove(t);
	}

	public void atualiza(OrcamentoItem t) {
		dao.atualiza(t);
	}

	public List<OrcamentoItem> listaTodos() {
		return dao.listaTodos();
	}

	public OrcamentoItem buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<OrcamentoItem> buscaPorOrcamento(Orcamento orcamento) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<OrcamentoItem> query = builder.createQuery(OrcamentoItem.class);
		Root<OrcamentoItem> from = query.from(OrcamentoItem.class);
		
		Predicate predicate = builder.and();
		
		predicate = builder.and(predicate, builder.equal(from.join("orcamento").get("orc_codigo"),orcamento.getOrc_codigo()));

		TypedQuery<OrcamentoItem> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("ori_codigo"))));
		List<OrcamentoItem> itens = typedQuery.getResultList();
		
		return itens;
	}

}
