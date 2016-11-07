package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PerfilDAO;
import br.com.consultorio.dao.PermissaoDAO;
import br.com.consultorio.modelo.Perfil;
import br.com.consultorio.modelo.Permissao;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PerfilController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Perfil perfil;
	
	private Permissao permissao;
	
	@Inject
	private PerfilDAO dao;
	
	@Inject
	private PermissaoDAO permissaoDAO;
	
	private List<Perfil> perfils;
	
	private List<Permissao> lstPermissao;
	
	private String txtPermissao;
	
	@PostConstruct
	 void init() {
		this.perfils = dao.listaTodos();
		this.perfil = new Perfil();
		this.permissao = new Permissao();
		this.lstPermissao = new ArrayList<>();
	}
	
	public void carregaPeloId(Perfil perfil){
		System.out.println(perfil.getPer_codigo());
		this.perfil = this.dao.buscaPorId(perfil.getPer_codigo());
		
		this.lstPermissao = this.permissaoDAO.carregaPermissaoPorPerfil(perfil.getPer_codigo());
		
	}
	
	
	public void limpar(){
		this.perfil = new Perfil();
	}
	
	public void salvarPermissao(){
		for (Permissao p : lstPermissao) {
			System.out.println(p);
		}
		init();
	}
	
	public void removerPermissao(){
		lstPermissao.remove(permissao);
	}
	
	public Boolean validaAdicionaPermissao(){
//		for (Permissao p : lstPermissao) {
//			if(p.getPrograma().equals(permissao.getPrograma() && txtPermissao.equals(p.))){
//				FacesUtil.addErrorMessage("");
//				return Boolean.FALSE;
//			}
//		}
		return Boolean.TRUE;
	}
	
	public void adicionarPermissao(){
		if(validaAdicionaPermissao()){
			if(txtPermissao.equals("I")){
				permissao.setPer_inserir(Boolean.TRUE);
			}else if(txtPermissao.equals("V")){
				permissao.setPer_ver(Boolean.TRUE);
			}else if(txtPermissao.equals("M")){
				permissao.setPer_imprimir(Boolean.TRUE);
			}else if(txtPermissao.equals("E")){
				permissao.setPer_excluir(Boolean.TRUE);
			}else if(txtPermissao.equals("A")){
				permissao.setPer_alterar(Boolean.TRUE);
			}else if(txtPermissao.equals("T")){
				permissao.setPer_inserir(Boolean.TRUE);
				permissao.setPer_ver(Boolean.TRUE);
				permissao.setPer_imprimir(Boolean.TRUE);
				permissao.setPer_excluir(Boolean.TRUE);
				permissao.setPer_alterar(Boolean.TRUE);
			}
			lstPermissao.add(permissao);
			permissao = new Permissao();
		}
	}
	
	@Transacional
	public String gravar() {
		if(this.perfil.getPer_codigo() != null){
			this.dao.atualiza(this.perfil);
			for (Permissao p : lstPermissao) {
				p.setPerfil(perfil);
				permissaoDAO.atualiza(p);
			}
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.perfil);
			for (Permissao p : lstPermissao) {
				p.setPerfil(perfil);
				permissaoDAO.adiciona(p);
			}
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		
		init();
		return null;
	}
	
	public String getTxtPermissao() {
		return txtPermissao;
	}
	
	public void setTxtPermissao(String txtPermissao) {
		this.txtPermissao = txtPermissao;
	}
	
	public void limparPerfil(){
		this.perfil = new Perfil();
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public List<Permissao> getLstPermissao() {
		return lstPermissao;
	}

	public void setLstPermissao(List<Permissao> lstPermissao) {
		this.lstPermissao = lstPermissao;
	}
	
}
