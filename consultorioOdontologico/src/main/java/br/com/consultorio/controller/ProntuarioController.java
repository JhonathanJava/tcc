package br.com.consultorio.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.dao.ProntuarioDAO;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.modelo.Prontuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProntuarioController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Prontuario prontuario;
	
	private Paciente paciente;
	
	private Prontuario prontuarioEditar;
	
	@Inject
	private ProntuarioDAO dao;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Inject
	private PlanoPaiDAO planoDAO;
	
	private List<Prontuario> prontuarios;
	
	private List<Prontuario> filterProntuarios;
	
	private List<PlanoPai> listaPlanos;
	
	private List<Paciente> lstPaciente = new ArrayList<Paciente>();
	
	private boolean diferencaAnosBoolean = false;
	
	private long diferencaAnos;
	
	@PostConstruct
	 void init() {
		this.prontuario = new Prontuario();
		this.paciente = new Paciente();
	}
	

	public void calculaIdade(){
		LocalDate hoje = LocalDate.now();
		LocalDate dataNascimento = null;
		if(this.paciente.getPac_dataNascimento() != null && !this.paciente.getPac_dataNascimento().equals("")){
			 dataNascimento = this.paciente.getPac_dataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		setDiferencaAnos(ChronoUnit.YEARS.between(dataNascimento,hoje));
		if(getDiferencaAnos() < 18){
			diferencaAnosBoolean = true;
		}else{
			diferencaAnosBoolean = false;
		}
	}
	
	public void carregaPeloId(Long id){
		this.paciente = this.pacienteDAO.buscaPorId(id);
		calculaIdade();
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
	
	public Paciente getPaciente() {
		return paciente;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public List<PlanoPai> getListaPlanos() {
		return listaPlanos = planoDAO.listaTodos();
	}

	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}

	public boolean isDiferencaAnosBoolean() {
		return diferencaAnosBoolean;
	}

	public void setDiferencaAnosBoolean(boolean diferencaAnosBoolean) {
		this.diferencaAnosBoolean = diferencaAnosBoolean;
	}

	public void setDiferencaAnos(long diferencaAnos) {
		this.diferencaAnos = diferencaAnos;
	}
	
	public long getDiferencaAnos() {
		return diferencaAnos;
	}
	
}	
