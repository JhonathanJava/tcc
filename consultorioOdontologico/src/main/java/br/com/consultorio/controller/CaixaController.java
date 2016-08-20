package br.com.consultorio.controller;

import java.io.Serializable;
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
	
	private Caixa caixaEditar;
	
	@Inject
	private CaixaDAO dao;

	private List<Caixa> listaCaixa;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	
	@PostConstruct
	 void init() {
		this.caixa = new Caixa();
		this.caixaEditar = new Caixa();
		this.listaCaixa = dao.listaTodos();
	}
	
	public void buscaEditarPorId(Long id){
		caixaEditar = dao.buscaPorId(id);
	}
	
	@Transacional
	public String editar(){
		this.dao.atualiza(this.caixaEditar);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.caixaEditar = new Caixa();
		init();
		this.listaCaixa = dao.listaTodos();
		return null;
	}
	
	@Transacional
	public String gravar(){
		this.caixa.setUsuario(usuario);
		this.dao.adiciona(this.caixa);
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.caixa = new Caixa();
		init();
		return null;
	}
	
	@Transacional
	public void remover(){
		System.out.println("Chamando Remover()");
		this.dao.remove(caixaEditar);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limparCaixa(){
		this.caixa = new Caixa();
	}
	
	public void buscarPorId(Long id){
		caixa = dao.buscaPorId(id);
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Caixa getCaixaEditar() {
		return caixaEditar;
	}

	public void setCaixaEditar(Caixa caixaEditar) {
		this.caixaEditar = caixaEditar;
	}

	public List<Caixa> getListaCaixa() {
		return listaCaixa;
	}

	public void setListaCaixa(List<Caixa> listaCaixa) {
		this.listaCaixa = listaCaixa;
	}

}
