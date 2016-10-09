package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PerfilDAO;
import br.com.consultorio.modelo.Perfil;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PerfilController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Perfil Perfil;
	
	@Inject
	private PerfilDAO dao;
	
	private List<Perfil> Perfils;
	
	@PostConstruct
	 void init() {
		this.Perfils = dao.listaTodos();
		this.Perfil = new Perfil();
	}
	
	public void carregaPeloId(Perfil Perfil){
		System.out.println(Perfil.getPer_codigo());
		this.Perfil = this.dao.buscaPorId(Perfil.getPer_codigo());
	}
	
	public void limpar(){
		this.Perfil = new Perfil();
	}
	
	@Transacional
	public String gravar() {
		if(this.Perfil.getPer_codigo() != null){
			this.dao.atualiza(this.Perfil);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.Perfil);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	
	public void limparPerfil(){
		this.Perfil = new Perfil();
	}
	
	public Perfil getPerfil() {
		return Perfil;
	}
	
	public void setPerfil(Perfil Perfil) {
		this.Perfil = Perfil;
	}
	
	public List<Perfil> getPerfils() {
		return Perfils;
	}
	
	public void setPerfils(List<Perfil> Perfils) {
		this.Perfils = Perfils;
	}
	
	
}
