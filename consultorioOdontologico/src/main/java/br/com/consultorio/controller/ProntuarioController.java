package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.ProntuarioDAO;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.Prontuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProntuarioController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Prontuario prontuario;
	
	private Prontuario prontuarioEditar;
	
	@Inject
	private ProntuarioDAO dao;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	private List<Prontuario> prontuarios;
	
	private List<Prontuario> filterProntuarios;
	
	private List<Paciente> lstPaciente = new ArrayList<Paciente>();
	
	@PostConstruct
	 void init() {
		this.prontuario = new Prontuario();
	}
	

	
	public void carregaPeloId(Prontuario prontuario){
		this.prontuarioEditar = this.dao.buscaPorId(prontuario.getPro_codigo());
	}
	
	@Transacional
	public void inativarSelecionados(Prontuario prontuario){
			if(prontuario.getPro_status().equals("A")){
				prontuario.setPro_status('I');
			}else{
				prontuario.setPro_status('A');
			}
			this.dao.atualiza(prontuario);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar() {
		if(this.prontuario.getPro_codigo() != null){
			this.dao.atualiza(this.prontuario);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.prontuario);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void remover(){
		this.dao.remove(prontuario);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limparProntuario(){
		this.prontuario = new Prontuario();
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public Prontuario getProntuarioEditar() {
		return prontuarioEditar;
	}

	public void setProntuarioEditar(Prontuario prontuarioEditar) {
		this.prontuarioEditar = prontuarioEditar;
	}

	public ProntuarioDAO getDao() {
		return dao;
	}

	public void setDao(ProntuarioDAO dao) {
		this.dao = dao;
	}

	public List<Prontuario> getProntuarios() {
		return prontuarios;
	}

	public void setProntuarios(List<Prontuario> prontuarios) {
		this.prontuarios = prontuarios;
	}

	public List<Prontuario> getFilterProntuarios() {
		return filterProntuarios;
	}

	public void setFilterProntuarios(List<Prontuario> filterProntuarios) {
		this.filterProntuarios = filterProntuarios;
	}
	
	public List<Paciente> getLstPaciente() {
		return pacienteDAO.listaTodos();
	}
	
	public void setLstPaciente(List<Paciente> lstPaciente) {
		this.lstPaciente = lstPaciente;
	}
	
}
