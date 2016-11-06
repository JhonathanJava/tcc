package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.ProntuarioDAO;
import br.com.consultorio.modelo.Prontuario;

@Named
@ViewScoped
public class ProntuarioController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Prontuario prontuario;
	
	@Inject
	private ProntuarioDAO prontuarioDAO;
	
	private List<Prontuario> listaProntuario = new ArrayList<Prontuario>();
	
	@PostConstruct
	 void init() {
		this.prontuario = new Prontuario();
		this.listaProntuario = new ArrayList<Prontuario>();
	}
	
	public void buscaPorId(Long id){
		this.prontuario = this.prontuarioDAO.buscaPorId(id);
	}
	
	public Prontuario getProntuario() {
		return prontuario;
	}
	
	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}
	
	public List<Prontuario> getListaProntuario() {
		return this.prontuarioDAO.listaTodos();
	}
	
	public void setListaProntuario(List<Prontuario> listaProntuario) {
		this.listaProntuario = listaProntuario;
	}
	
}	
