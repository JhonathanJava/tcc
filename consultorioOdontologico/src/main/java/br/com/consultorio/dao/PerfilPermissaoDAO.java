package br.com.consultorio.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.consultorio.modelo.PerfilPermissao;

public class PerfilPermissaoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;
	
	private GenericDAO<PerfilPermissao> dao;
	
	@PostConstruct
	void init(){
		this.dao = new GenericDAO<PerfilPermissao>(this.em,PerfilPermissao.class);
	}

	public void adiciona(PerfilPermissao p) {
		dao.adiciona(p);
	}

	public void remove(PerfilPermissao p) {
		dao.remove(p);
	}

	public void atualiza(PerfilPermissao p) {
		dao.atualiza(p);
	}

	public List<PerfilPermissao> listaTodos() {
		return dao.listaTodos();
	}

	public PerfilPermissao buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}
	


}
