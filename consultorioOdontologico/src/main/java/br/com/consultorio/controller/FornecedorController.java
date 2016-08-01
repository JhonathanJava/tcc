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
	
	private Fornecedor fornecedorEditar;
	
	@Inject
	private FornecedorDAO dao;
	
	private List<Fornecedor> fornecedores;
	
	private Long FuncionarioId;
	
	@PostConstruct
	 void init() {
		this.fornecedor = new Fornecedor();
		this.fornecedorEditar = new Fornecedor();
		this.fornecedores = dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		fornecedor = dao.buscaPorId(id);
		System.out.println("ID = "+fornecedor.toString());
	}
	
	public void buscaEditarPorId(Long id){
		fornecedorEditar = dao.buscaPorId(id);
		System.out.println("ID = "+fornecedorEditar.toString());
	}
	
	@Transacional
	public void inativarSelecionados(Fornecedor fornecedor){
			if(fornecedor.getFun_status().equals("A")){
				fornecedor.setFun_status("I");
			}else{
				fornecedor.setFun_status("A");
			}
			this.dao.atualiza(fornecedor);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar() {
		System.out.println("Gravando Funcionario " + this.fornecedor.getFun_nome());
		System.out.println("ToString = "+ this.fornecedor.toString());
		if(this.fornecedor.getFun_codigo() != null){
			System.out.println("Salvando");
			this.dao.atualiza(this.fornecedor);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Alterando");
			this.dao.adiciona(this.fornecedor);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public String editar(){
		System.out.println("ToString = "+ this.fornecedorEditar.toString());
		this.dao.atualiza(this.fornecedorEditar);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.fornecedorEditar = new Fornecedor();
		init();
		this.fornecedores = dao.listaTodos();
		return null;
	}
	
	@Transacional
	public void remover(){
		System.out.println("Chamando Remover()");
		this.dao.remove(fornecedor);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
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

	public Long getFuncionarioId() {
		return FuncionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		FuncionarioId = funcionarioId;
	}
	
	public Fornecedor getFornecedorEditar() {
		return fornecedorEditar;
	}
	
	public void setFornecedorEditar(Fornecedor fornecedorEditar) {
		this.fornecedorEditar = fornecedorEditar;
	}
	
}
