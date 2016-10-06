package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.CondicaoPagamentoDAO;
import br.com.consultorio.modelo.CondicaoPagamento;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CondicaoPagamentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CondicaoPagamento condicaoPagamento;
	
	@Inject
	private CondicaoPagamentoDAO dao;
	
	private List<CondicaoPagamento> listaCondicao;
	
	@PostConstruct
	 void init() {
		this.condicaoPagamento = new CondicaoPagamento();
		this.listaCondicao = dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		this.condicaoPagamento = dao.buscaPorId(id);
	}
	
	@Transacional
	public String gravar() {
		if(this.condicaoPagamento.getCon_codigo() != null){
			this.dao.atualiza(this.condicaoPagamento);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Alterando");
			this.dao.adiciona(this.condicaoPagamento);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void inativarCondicaoPagamento(){
		this.condicaoPagamento.setCon_status("I");
		this.dao.atualiza(condicaoPagamento);
		init();
		FacesUtil.addSuccessMessage("Registro Inativado Com Sucesso!!");
	}
	
	public void limpar(){
		this.condicaoPagamento = new CondicaoPagamento();
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public List<CondicaoPagamento> getListaCondicao() {
		return listaCondicao;
	}

	public void setListaCondicao(List<CondicaoPagamento> listaCondicao) {
		this.listaCondicao = listaCondicao;
	}

}
