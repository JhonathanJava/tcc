package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PlanoDAO;
import br.com.consultorio.modelo.Plano;

@Named
@ViewScoped
public class PlanoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Plano plano;
	
	@Inject
	private PlanoDAO dao;
	
	private List<Plano> planos;

	
	
	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public PlanoDAO getDao() {
		return dao;
	}

	public void setDao(PlanoDAO dao) {
		this.dao = dao;
	}

	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}
	
}
