package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.DestinoProdutoSaidaDAO;
import br.com.consultorio.modelo.DestinoProdutoSaida;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DestinoProdutoSaidaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private DestinoProdutoSaida destino;
	
	@Inject
	private DestinoProdutoSaidaDAO dao;
	
	private List<DestinoProdutoSaida> lstDestinoProduto;
	
	@PostConstruct
	 void init() {
		this.destino = new DestinoProdutoSaida();
		this.lstDestinoProduto = dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		destino = dao.buscaPorId(id);
	}
	
	@Transacional
	public String gravar() {
		this.destino.setDes_status("A");
		if(this.destino.getDes_codigo() != null){
			this.dao.atualiza(this.destino);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.destino);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void inativarDestino(){
		this.destino.setDes_status("I");
		this.dao.atualiza(destino);
		FacesUtil.addSuccessMessage("Registro Inativado Com Sucesso!!");
		init();
	}
	
	public void limpar(){
		this.destino = new DestinoProdutoSaida();
	}

	public DestinoProdutoSaida getDestino() {
		return destino;
	}

	public void setDestino(DestinoProdutoSaida destino) {
		this.destino = destino;
	}

	public List<DestinoProdutoSaida> getLstDestinoProduto() {
		return lstDestinoProduto;
	}

	public void setLstDestinoProduto(List<DestinoProdutoSaida> lstDestinoProduto) {
		this.lstDestinoProduto = lstDestinoProduto;
	}

}
