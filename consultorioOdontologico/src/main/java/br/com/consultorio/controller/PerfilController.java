package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	private List<Perfil> filterPerfils;
	
	private List<Perfil> PerfilsSelecionados = new ArrayList<>();
	
	@PostConstruct
	 void init() {
		this.Perfils = dao.listaTodos();
		this.Perfil = new Perfil();
	}
	
	public void carregaPeloId(Perfil Perfil){
		System.out.println(Perfil.getPer_codigo());
		this.Perfil = this.dao.buscaPorId(Perfil.getPer_codigo());
	}
	
	@Transacional
	public void inativarSelecionados(Perfil Perfil){
			if(Perfil.getPer_status().equals("A")){
				Perfil.setPer_status("I");
			}else{
				Perfil.setPer_status("A");
			}
			this.dao.atualiza(Perfil);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar() {
		System.out.println("Gravando Perfil " + this.Perfil.getPer_descricao());
		System.out.println("ToString = "+ this.Perfil.toString());
		if(this.Perfil.getPer_codigo() != null){
			System.out.println("Salvando");
			this.dao.atualiza(this.Perfil);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Alterando");
			this.dao.adiciona(this.Perfil);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void excluirSelecionados(){
		for (Perfil Perfil : PerfilsSelecionados) {
			this.dao.remove(Perfil);
			Perfil = null;
		}
		init();
		FacesUtil.addSuccessMessage("Registros Excluidos Com Sucesso!!");
	}
	
	@Transacional
	public void remover(Perfil Perfil){
		System.out.println("Chamando Remover()");
		this.dao.remove(Perfil);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
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
	
	public List<Perfil> getPerfilsSelecionados() {
		return PerfilsSelecionados;
	}
	
	public void setPerfilsSelecionados(List<Perfil> PerfilsSelecionados) {
		this.PerfilsSelecionados = PerfilsSelecionados;
	}
	
	public List<Perfil> getFilterPerfils() {
		return filterPerfils;
	}
	
	public void setFilterPerfils(List<Perfil> filterPerfils) {
		this.filterPerfils = filterPerfils;
	}
	
}
