package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.EstoqueDAO;
import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstoqueController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Estoque estoque; // Produto
	
	@Inject
	private EstoqueDAO dao;
	
	private List<Estoque> listaEstoque; // Lista de Produtos
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	
	@PostConstruct
	 void init() {
		System.out.println("Init EstoqueController ----------------------------------------------------------------");
		this.estoque = new Estoque();
		this.listaEstoque =  dao.listaTodos();
	}
	
	@Transacional
	public void inativarProduto(){
		this.estoque.setEst_status("I");
		dao.atualiza(estoque);
		FacesUtil.addSuccessMessage("Produto Inativado!");
		init();
	}
	
	@Transacional
	public void gravar(){
		System.out.println(estoque);
		if(this.estoque.getEst_codigo() != null){
			dao.atualiza(estoque);
			FacesUtil.addSuccessMessage("Registro Alterado Com Sucesso!");
		}else{
			dao.adiciona(estoque);
			FacesUtil.addSuccessMessage("Registro Adicionado Com Sucesso!");
		}
		
		init();
	}
	
	public void limpar(){
		this.estoque = new Estoque();
		this.listaEstoque =  dao.listaTodos();
	}
	
	public void buscaPorId(Long id){
		estoque = dao.buscaPorId(id);
	}
	
	public void listaTodos(){
		this.listaEstoque = dao.listaTodos();
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<Estoque> getListaEstoque() {
		return listaEstoque;
	}

	public void setListaEstoque(List<Estoque> listaEstoque) {
		this.listaEstoque = listaEstoque;
	}
	
}
