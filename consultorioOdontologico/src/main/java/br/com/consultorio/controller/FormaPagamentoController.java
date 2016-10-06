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
	
	@Inject
	private FormaPagamentoDAO dao;
	
	private List<FormaPagamento> formaPagamentos;
	
	@PostConstruct
	 void init() {
		this.formaPagamento = new FormaPagamento();
		this.formaPagamentos = dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		formaPagamento = dao.buscaPorId(id);
	}
	
	@Transacional
	public String gravar() {
		this.formaPagamento.setFor_status("A");
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
	public void inativarFormaPagamento(){
		this.formaPagamento.setFor_status("I");
		this.dao.atualiza(formaPagamento);
		init();
		FacesUtil.addSuccessMessage("Registro Inativado Com Sucesso!!");
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

	public List<FormaPagamento> getFormaPagamentos() {
		return formaPagamentos;
	}

	public void setFormaPagamentos(List<FormaPagamento> formaPagamentos) {
		this.formaPagamentos = formaPagamentos;
	}
	
}
