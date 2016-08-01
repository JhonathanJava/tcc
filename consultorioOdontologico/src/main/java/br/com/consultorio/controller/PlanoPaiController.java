package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PlanoPaiController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private PlanoPai planoPai;
	
	@Inject
	private PlanoPaiDAO dao;
	
	private PlanoPai plano;
	
	private PlanoPai planoEditar;
	
	private PlanoPai planoFiltro;
	
	private List<PlanoPai> listaPlanos;

	@PostConstruct
	 void init() {
		System.out.println("Init Paciente ----------------------------------------------------------------");
		this.plano = new PlanoPai();
		this.planoEditar = new PlanoPai();
		this.planoFiltro = new PlanoPai();
		this.listaPlanos =  new ArrayList<>();
	}
	
	public void limpar(){
		init();
	}
	
	@Transacional
	public String salvar(){
		System.out.println("ToString = "+ this.plano.toString());
		this.dao.adiciona(this.plano);
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.plano = new PlanoPai();
		init();
		return null;
	}
	
	@Transacional
	public String editar(){
		System.out.println("ToString = "+ this.planoEditar.toString());
		this.dao.atualiza(this.planoEditar);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.planoEditar = new PlanoPai();
		init();
		this.listaPlanos = dao.listaTodos();
		return null;
	}
	
	@Transacional
	public void excluirRegistro(){
		System.out.println("Planos: "+planoEditar.toString());
		this.dao.remove(planoEditar);
		listaTodos();
		this.plano = new PlanoPai();
		FacesUtil.addSuccessMessage("Excluído Com Sucesso!!");
	}
	
	public void buscaPorId(Long id){
		plano = dao.buscaPorId(id);
		System.out.println("ID = "+plano.toString());
	}
	
	public void buscaEditarPorId(Long id){
		planoEditar = dao.buscaPorId(id);
		System.out.println("ID = "+planoEditar.toString());
	}
	
	public void listaTodos(){
		this.listaPlanos = dao.listaTodos();
		for (PlanoPai planos : listaPlanos) {
			System.out.println(planos);
		}
	}
	
	public void pesquisaPorFiltro(){
		System.out.println(this.planoFiltro.toString());
		this.listaPlanos = dao.pesquisaPorFiltro(this.planoFiltro);
	}

	public PlanoPai getPlanoPai() {
		return planoPai;
	}

	public void setPlanoPai(PlanoPai planoPai) {
		this.planoPai = planoPai;
	}

	public PlanoPai getPlano() {
		return plano;
	}

	public void setPlano(PlanoPai plano) {
		this.plano = plano;
	}

	public PlanoPai getPlanoEditar() {
		return planoEditar;
	}

	public void setPlanoEditar(PlanoPai planoEditar) {
		this.planoEditar = planoEditar;
	}

	public PlanoPai getPlanoFiltro() {
		return planoFiltro;
	}

	public void setPlanoFiltro(PlanoPai planoFiltro) {
		this.planoFiltro = planoFiltro;
	}

	public List<PlanoPai> getListaPlanos() {
		return listaPlanos;
	}

	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}
	
	
}
