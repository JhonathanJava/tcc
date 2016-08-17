package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.FormaPagamentoDAO;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class FormaPagamentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private FormaPagamento formaPagamento;
	
	private FormaPagamento formaPagamentoEditar;
	
	@Inject
	private FormaPagamentoDAO dao;
	
	private List<FormaPagamento> formaPagamentos;
	
	@PostConstruct
	 void init() {
		this.formaPagamento = new FormaPagamento();
		this.formaPagamentoEditar = new FormaPagamento();
		this.formaPagamentos = dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		formaPagamento = dao.buscaPorId(id);
		System.out.println("ID = "+formaPagamento.toString());
	}
	
	public void buscaEditarPorId(Long id){
		formaPagamentoEditar = dao.buscaPorId(id);
		System.out.println("ID = "+formaPagamentoEditar.toString());
	}
	
	@Transacional
	public String gravar() {
		if(this.formaPagamento.getFor_codigo() != null){
			this.dao.atualiza(this.formaPagamento);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Alterando");
			this.dao.adiciona(this.formaPagamento);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public String editar(){
		this.dao.atualiza(this.formaPagamentoEditar);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.formaPagamentoEditar = new FormaPagamento();
		init();
		this.formaPagamentos = dao.listaTodos();
		return null;
	}
	
	@Transacional
	public void remover(){
		System.out.println("Chamando Remover()");
		this.dao.remove(formaPagamentoEditar);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limpar(){
		this.formaPagamento = new FormaPagamento();
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public FormaPagamento getFormaPagamentoEditar() {
		return formaPagamentoEditar;
	}

	public void setFormaPagamentoEditar(FormaPagamento formaPagamentoEditar) {
		this.formaPagamentoEditar = formaPagamentoEditar;
	}

	public List<FormaPagamento> getFormaPagamentos() {
		return formaPagamentos;
	}

	public void setFormaPagamentos(List<FormaPagamento> formaPagamentos) {
		this.formaPagamentos = formaPagamentos;
	}
	
}
