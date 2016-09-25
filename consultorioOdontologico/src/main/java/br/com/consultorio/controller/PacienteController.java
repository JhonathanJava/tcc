package br.com.consultorio.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.PlanoDAO;
import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.lazy.PacienteLazy;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PacienteController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Paciente paciente;
	
	private Paciente pacienteEditar;
	//oncomplete="PF('carDialog').show()"
	@Inject
	private Paciente pacienteFiltro;
	
	private long diferencaAnos;
	
	private String filtroCpf;
	
	private boolean diferencaAnosBoolean = false;
	
	@Inject
	private PacienteDAO dao;
	
	@Inject
	private PlanoPaiDAO planoDAO;
	
	@Inject
	private PlanoDAO planosDAO;
	
	private List<Paciente> listaPacientes;
	
	private PacienteLazy pacienteLazy;
	
	private List<PlanoPai> listaPlanos;
	
	@PostConstruct
	 void init() {
		System.out.println("Init Paciente ----------------------------------------------------------------");
		this.paciente = new Paciente();
		this.pacienteEditar = new Paciente();
		this.listaPacientes =  new ArrayList<>();
		this.paciente.setPac_indicacao("-1");
		this.listaPacientes = dao.listaTodos();
		//this.pacienteLazy = new PacienteLazy(listaPacientes);
	}
	
	public void limpar(){
		System.out.println("deu certo Salvando !!");
	}
	
	public void novoPaciente(){
		this.paciente = new Paciente();
		this.pacienteEditar = new Paciente();
		this.listaPacientes =  new ArrayList<>();
		this.paciente.setPac_indicacao("-1");
		this.listaPacientes = dao.listaTodos();
	}
	
	@Transacional
	public String salvar(){
		System.out.println("ToString = "+ this.paciente.toString());
		this.planosDAO.adiciona(this.paciente.getPlano());

		this.dao.adiciona(this.paciente);
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.paciente = new Paciente();
		init();
		return null;
	}
	
	@Transacional
	public void editar(){
		System.out.println("Chamou Metodo");
		this.dao.atualiza(this.paciente);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.paciente = new Paciente();
		init();
	}
	
	@Transacional
	public void excluirRegistro(){
		System.out.println("Paciente: "+paciente.toString());
		this.dao.remove(paciente);
		listaTodos();
		this.paciente = new Paciente();
		FacesUtil.addSuccessMessage("Excluído Com Sucesso!!");
	}
	
	public void buscaPorId(Long id){
		paciente = dao.buscaPorId(id);
		System.out.println("ID = "+paciente.toString());
	}
	
	public void buscaEditarPorId(Long id){
		pacienteEditar = dao.buscaPorId(id);
		System.out.println("ID = "+pacienteEditar.toString());
		calculaIdade();
	}
	
	public void calculaIdade(){
			LocalDate hoje = LocalDate.now();
			LocalDate dataNascimento;
			if(this.paciente.getPac_dataNascimento() != null && !this.paciente.getPac_dataNascimento().equals("")){
				dataNascimento = this.paciente.getPac_dataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				System.out.println("diferenca "+ChronoUnit.YEARS.between(dataNascimento,hoje));
				setDiferencaAnos(ChronoUnit.YEARS.between(dataNascimento,hoje));
				if(getDiferencaAnos() < 18){
					diferencaAnosBoolean = true;
				}else{
					diferencaAnosBoolean = false;
				}
			}
		System.out.println("Idade -> "+getDiferencaAnos());
		System.out.println("verifica = "+ diferencaAnosBoolean);
	}
	
	public List<PlanoPai> getListaPlanos() {
		return listaPlanos = planoDAO.listaTodos();
	}

	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}

	public String getFiltroCpf() {
		return filtroCpf;
	}
	
	public void setFiltroCpf(String filtroCpf) {
		this.filtroCpf = filtroCpf;
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
	
	public void listaTodos(){
		this.listaPacientes = dao.listaTodos();
	}
	
	public void pesquisaPorFiltro(){
		this.pacienteFiltro.setPac_cpf(getFiltroCpf());
		System.out.println(this.pacienteFiltro.toString());
		this.listaPacientes = dao.pesquisaPorFiltro(this.pacienteFiltro);
	}
	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public Paciente getPacienteEditar() {
		return pacienteEditar;
	}
	
	public void setPacienteEditar(Paciente pacienteEditar) {
		this.pacienteEditar = pacienteEditar;
	}
	
	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}
	
	public void setListaPacientes(List<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}
	
	public Paciente getPacienteFiltro() {
		return pacienteFiltro;
	}
	
	public void setPacienteFiltro(Paciente pacienteFiltro) {
		this.pacienteFiltro = pacienteFiltro;
	}
	
	public PacienteLazy getPacienteLazy() {
		return pacienteLazy;
	}
	
	public void setPacienteLazy(PacienteLazy pacienteLazy) {
		this.pacienteLazy = pacienteLazy;
	}
}
