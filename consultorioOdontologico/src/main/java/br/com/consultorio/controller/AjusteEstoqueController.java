package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.AjusteEstoqueDAO;
import br.com.consultorio.dao.EstoqueDAO;
import br.com.consultorio.modelo.AjusteEstoque;
import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AjusteEstoqueController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Estoque estoque;
	
	private AjusteEstoque ajusteEstoque;
	
	@Inject
	private EstoqueDAO dao;

	@Inject
	private AjusteEstoqueDAO ajusteEstoqueDAO;
	
	private List<Estoque> listaEstoque;
	
	private List<AjusteEstoque> listaAjusteEstoque;
	
	private List<Estoque> listaEstoqueNegativo;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	
	@PostConstruct
	 void init() {
		this.estoque = new Estoque();
		this.listaEstoqueNegativo = new ArrayList<>();
		this.listaEstoque = dao.buscarProdutoAtivo();
		this.listaAjusteEstoque = ajusteEstoqueDAO.listaTodos();
		this.ajusteEstoque = new AjusteEstoque();
		this.listaEstoque = dao.listaTodos();
		verificaEstoque();
	}
	
	public void verificaEstoque(){
		for (Estoque estoque : listaEstoque) {
			if(estoque.getEst_quantidade() != null){
				if(estoque.getEst_quantidade() < estoque.getEst_quantidadeMinima()){
					this.listaEstoqueNegativo.add(estoque);
				}
			}
		}
	}
	
	@Transacional
	public String ajustar(){
		if(validacao()){
			this.ajusteEstoque.setEje_quantidadeAntiga(this.getEstoque().getEst_quantidade());
			this.estoque.setEst_quantidade(this.ajusteEstoque.getEje_quantidade());
			this.dao.atualiza(this.estoque);
			
			this.ajusteEstoque.setEstoque(this.estoque);
			this.ajusteEstoque.setUsuario(usuario);
			this.ajusteEstoqueDAO.adiciona(this.ajusteEstoque);
			
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
			this.ajusteEstoque = new AjusteEstoque();
			init();
		}
		return null;
	}
	
	public Boolean validacao(){
		if(this.ajusteEstoque.getEje_quantidade() == null){
			FacesUtil.addErrorMessage("Deve ser informado a nova quantidade do Produto!");
			FacesContext.getCurrentInstance().validationFailed();
			return false;
		}
		return true;
	}
	
	public void limpar(){
		this.estoque = new Estoque();
	}
	
	public void buscarPorId(Long id){
		this.estoque = dao.buscaPorId(id);
	}

	public List<Estoque> getListaEstoque() {
		return listaEstoque;
	}
	
	public void setListaEstoque(List<Estoque> listaEstoque) {
		this.listaEstoque = listaEstoque;
	}
	
	public Estoque getEstoque() {
		return estoque;
	}
	
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<Estoque> getListaEstoqueNegativo() {
		return listaEstoqueNegativo;
	}
	
	public void setListaEstoqueNegativo(List<Estoque> listaEstoqueNegativo) {
		this.listaEstoqueNegativo = listaEstoqueNegativo;
	}
	
	public AjusteEstoque getAjusteEstoque() {
		return ajusteEstoque;
	}
	
	public void setAjusteEstoque(AjusteEstoque ajusteEstoque) {
		this.ajusteEstoque = ajusteEstoque;
	}
	
	public List<AjusteEstoque> getListaAjusteEstoque() {
		return listaAjusteEstoque;
	}
	
	public void setListaAjusteEstoque(List<AjusteEstoque> listaAjusteEstoque) {
		this.listaAjusteEstoque = listaAjusteEstoque;
	}
}
