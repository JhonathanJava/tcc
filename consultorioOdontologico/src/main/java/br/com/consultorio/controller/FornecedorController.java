package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.FornecedorDAO;
import br.com.consultorio.modelo.Fornecedor;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FornecedorController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Fornecedor fornecedor;
	
	@Inject
	private FornecedorDAO dao;
	
	private List<Fornecedor> fornecedores;
	
	@PostConstruct
	 void init() {
		this.fornecedor = new Fornecedor();
		this.fornecedores = dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		fornecedor = dao.buscaPorId(id);
	}
	
	@Transacional
	public String gravar() {
		this.fornecedor.setFun_status("A");
		if(this.fornecedor.getFun_codigo() != null){
			this.dao.atualiza(this.fornecedor);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.fornecedor);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void remover(){
		if(fornecedor.getFun_status().equals("A")){
			fornecedor.setFun_status("I");
		}else{
			fornecedor.setFun_status("A");
		}
		this.dao.atualiza(fornecedor);
		init();
		FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
	}
	
	public void limpar(){
		this.fornecedor = new Fornecedor();
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

}
