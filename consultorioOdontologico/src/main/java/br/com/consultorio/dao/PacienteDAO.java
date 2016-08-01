package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.Date;
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

public class PacienteDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<Paciente> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<Paciente>(this.em,Paciente.class);
	}

	public void adiciona(Paciente t) {
		dao.adiciona(t);
	}

	public void remove(Paciente t) {
		dao.remove(t);
	}

	public void atualiza(Paciente t) {
		dao.atualiza(t);
	}

	public List<Paciente> listaTodos() {
		return dao.listaTodos();
	}

	public Paciente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	
	public List<Paciente> pesquisaPorFiltro (Paciente paciente){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Paciente> query = builder.createQuery(Paciente.class);
		Root<Paciente> from = query.from(Paciente.class);
		
		Predicate predicate = builder.and();
		if (paciente.getPac_nome() != null && !paciente.getPac_nome().equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("pac_nome"), "%"+paciente.getPac_nome()+"%"));
		}
		
		if (paciente.getPac_sexo() != null && !paciente.getPac_sexo().equals("") && !paciente.getPac_sexo().equals('0')){
		    predicate = builder.and(predicate, 
		        builder.equal(from.<Character>get("pac_sexo"), paciente.getPac_sexo()));
		}
		
		if (paciente.getPac_cpf() != null && !paciente.getPac_cpf().equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("pac_cpf"), "%"+paciente.getPac_cpf()+"%"));
		}
		
		if (paciente.getPac_rg() != null && !paciente.getPac_rg().equals("")){
		    predicate = builder.and(predicate, 
		        builder.like(from.<String>get("pac_rg"), "%"+paciente.getPac_rg()+"%"));
		}
		
		if (paciente.getPac_dataNascimento() != null && !paciente.getPac_dataNascimento().equals("")){
		    predicate = builder.and(predicate, 
		        builder.equal(from.<Date>get("pac_dataNascimento"), paciente.getPac_dataNascimento()));
		}
		
		TypedQuery<Paciente> typedQuery = em.createQuery(query.select(from ).where( predicate ).orderBy(builder.asc(from.get("pac_nome"))));
		List<Paciente> pacientes = typedQuery.getResultList();
		
		return pacientes;
	}



}
