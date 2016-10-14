package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.CaixaDAO;
import br.com.consultorio.modelo.Caixa;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CaixaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Caixa caixa;
	
	@Inject
	private CaixaDAO dao;

	private List<Caixa> listaCaixa;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	
	@PostConstruct
	 void init() {
		this.caixa = new Caixa();
		this.listaCaixa = dao.listaTodos();
	}
	
	public void buscarPorId(Long id){
		caixa = dao.buscaPorId(id);
	}
	
	@Transacional
	public void fecharCaixa(){
		this.caixa.setCai_dtFechamento(new Date());
		this.dao.atualiza(this.caixa);
		this.listaCaixa = dao.listaTodos();
		FacesUtil.addSuccessMessage("Caixa Fechado!!");
	}
	
	@Transacional
	public String gravar(){
		this.caixa.setUsuario(usuario);
		if(this.caixa.getCai_codigo() != null && this.caixa.getCai_codigo() > 0){
			this.dao.atualiza(this.caixa);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.caixa);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		this.caixa = new Caixa();
		init();
		return null;
	}
	
	public void limparCaixa(){
		this.caixa = new Caixa();
	}
	
	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public List<Caixa> getListaCaixa() {
		return listaCaixa;
	}

	public void setListaCaixa(List<Caixa> listaCaixa) {
		this.listaCaixa = listaCaixa;
	}

}
