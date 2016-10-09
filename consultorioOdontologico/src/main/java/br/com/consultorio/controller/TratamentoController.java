package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.dao.TratamentoDAO;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.modelo.Tratamento;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TratamentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Tratamento tratamento;
	
	@Inject
	private TratamentoDAO dao;

	@Inject
	private PlanoPaiDAO planoDAO;
	
	private List<Tratamento> listaTratamentos;
	
	private List<PlanoPai> listaPlanos;
	
	
	@PostConstruct
	 void init() {
		this.tratamento = new Tratamento();
		this.listaTratamentos = dao.listaTodos();
		this.listaPlanos = planoDAO.listaTodos();
	}
	
	@Transacional
	public void inativarSelecionados(Tratamento tratamento){
			if(tratamento.getTra_status().equals("A")){
				tratamento.setTra_status("I");
			}else{
				tratamento.setTra_status("A");
			}
			this.dao.atualiza(tratamento);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar(){
		System.out.println(this.tratamento.toString());
		this.tratamento.setTra_status("A");
		if(this.tratamento.getTra_codigo() != null){
			this.dao.atualiza(this.tratamento);
			FacesUtil.addSuccessMessage("Atualizado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.tratamento);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		this.tratamento = new Tratamento();
		init();
		return null;
	}
	
	@Transacional
	public void remover(){
		this.tratamento.setTra_status("I");
		this.dao.atualiza(tratamento);
		init();
		FacesUtil.addSuccessMessage("Registro Inativado Com Sucesso!!");
	}
	
	public void limparTratamento(){
		this.tratamento = new Tratamento();
	}
	
	public void buscarPorId(Long id){
		tratamento = dao.buscaPorId(id);
	}

	public List<PlanoPai> getListaPlanos() {
		return listaPlanos;
	}
	
	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}
	
	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public List<Tratamento> getListaTratamentos() {
		return listaTratamentos;
	}
	
	public void setListaTratamentos(List<Tratamento> listaTratamentos) {
		this.listaTratamentos = listaTratamentos;
	}

}
