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
	
	private List<PlanoPai> listaPlanos;

	@PostConstruct
	 void init() {
		this.plano = new PlanoPai();
		this.listaPlanos =  dao.listaTodos();
	}
	
	public void limpar(){
		init();
	}
	
	@Transacional
	public String salvar(){
		System.out.println("ToString = "+ this.plano.toString());
		if(this.plano.getPlp_codigo() != null){
			this.dao.atualiza(this.plano);
			FacesUtil.addSuccessMessage("Atualizado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.plano);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		this.plano = new PlanoPai();
		init();
		return null;
	}
	
	@Transacional
	public void excluirRegistro(){
		System.out.println("Planos: "+plano.toString());
		this.plano.setPlp_status("I");
		this.dao.atualiza(this.plano);
		listaTodos();
		this.plano = new PlanoPai();
		FacesUtil.addSuccessMessage("Excluído Com Sucesso!!");
	}
	
	public void buscaPorId(Long id){
		plano = dao.buscaPorId(id);
		System.out.println("ID = "+plano.toString());
	}
	
	public void listaTodos(){
		this.listaPlanos = dao.listaTodos();
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

	public List<PlanoPai> getListaPlanos() {
		return listaPlanos;
	}

	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}
	
}
