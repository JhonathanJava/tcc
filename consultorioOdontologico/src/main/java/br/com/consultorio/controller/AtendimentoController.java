package br.com.consultorio.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.AgendaDAO;
import br.com.consultorio.dao.AgendaTratamentoDAO;
import br.com.consultorio.dao.AnamneseDAO;
import br.com.consultorio.dao.ModeloAnamneseDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.dao.ProntuarioAnamneseDAO;
import br.com.consultorio.dao.ProntuarioDAO;
import br.com.consultorio.dao.ProntuarioTratamentoDAO;
import br.com.consultorio.modelo.Agenda;
import br.com.consultorio.modelo.AgendamentoTratamento;
import br.com.consultorio.modelo.Anamnese;
import br.com.consultorio.modelo.ModeloAnamnese;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.modelo.Prontuario;
import br.com.consultorio.modelo.ProntuarioAnamnese;
import br.com.consultorio.modelo.ProntuarioTratamento;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AtendimentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Agenda agenda;
	
	@Inject
	private Prontuario prontuario;
	
	@Inject
	private AnamneseDAO anamneseDAO;
	
	@Inject
	private Paciente paciente;
	
	@Inject
	private ModeloAnamnese modeloAnamnese;
	
	@Inject
	private ProntuarioAnamneseDAO prontuarioAnamneseDAO;
	
	@Inject
	private ProntuarioAnamnese prontuarioAnamnese;
	
	@Inject
	private AgendamentoTratamento prontuarioTratamento;
	
	@Inject
	private AgendaDAO agendaDAO;
	
	@Inject
	private PlanoPaiDAO planoDAO;
	
	@Inject
	private ModeloAnamneseDAO modeloDAO;
	
	@Inject
	private ProntuarioTratamentoDAO prontuarioTratamentoDAO;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Inject
	private AgendaTratamentoDAO agtDAO;
	
	@Inject
	private ProntuarioDAO prontuarioDAO;
	
	private List<Agenda> listaAgenda;
	
	private List<PlanoPai> listaPlanos;
	
	private List<ModeloAnamnese> lstModeloAnamneses = new ArrayList<ModeloAnamnese>();
	
	private List<ProntuarioAnamnese> lstProntuarioAnamnese = new ArrayList<ProntuarioAnamnese>();
	
	private List<AgendamentoTratamento> lstProntuarioTratamento = new ArrayList<AgendamentoTratamento>();
	
	private List<Anamnese> anamneses;
	
	private long diferencaAnos;
	private boolean diferencaAnosBoolean = false;
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

	
	@PostConstruct
	public void init() {
		 this.listaAgenda = agendaDAO.buscarAgendamentoAberto();
		 this.agenda = new Agenda();
	}
	
	public void limpar() {
		 this.listaAgenda = agendaDAO.buscarAgendamentoAberto();
		 this.agenda = new Agenda();
	}
	
	public void buscarPorId(Long id){
		this.agenda = agendaDAO.buscaPorId(id);
		this.paciente = pacienteDAO.buscaPorId(this.agenda.getPaciente().getPac_codigo());
		this.lstModeloAnamneses = modeloDAO.listaTodos();
		this.lstProntuarioTratamento = agtDAO.buscarPorAgenda(this.agenda.getAge_codigo());
		modeloAnamnese = this.lstModeloAnamneses.get(0);
		calculaIdade();
		buscarAnamnese();
	}
	
	public void buscarProntuarioTratamentoPorId(Long id){
		this.prontuarioTratamento = agtDAO.buscaPorId(id);
	}
	
	public void buscarAnamnese(){
		this.anamneses = anamneseDAO.listaAnamnesePorModelo(modeloAnamnese.getMoa_codigo());
	}
	
	@Transacional
	public void salvarProntuario(){
		// Salva Prontuario
		prontuario.setPac_codigo(this.agenda.getPaciente());
		prontuario.setPro_data(new Date());
		prontuario.setUsu_codigo(usuario);
		prontuarioDAO.adiciona(prontuario);
		System.out.println(prontuario);
		// Salva Anamnese
//		for (ProntuarioAnamnese pa : lstProntuarioAnamnese) {
//			pa.setProntuario(prontuario);
//			prontuarioAnamneseDAO.adiciona(pa);
//		}
		
//		for (AgendamentoTratamento pt : lstProntuarioTratamento) {
//			pt.setProntuario(prontuario);
//			agtDAO.atualiza(pt);
//		}
		
		this.agenda.setAge_status("Consultado");
		agendaDAO.atualiza(agenda);
		init();
		
		FacesUtil.addSuccessMessage("Atendimento Finalizado");
	}
	
	public void calculaIdade(){
		LocalDate hoje = LocalDate.now();
		LocalDate dataNascimento;
		if(this.paciente.getPac_dataNascimento() != null && !this.paciente.getPac_dataNascimento().equals("")){
			dataNascimento = this.paciente.getPac_dataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			setDiferencaAnos(ChronoUnit.YEARS.between(dataNascimento,hoje));
			if(getDiferencaAnos() < 18){
				diferencaAnosBoolean = true;
			}else{
				diferencaAnosBoolean = false;
			}
		}
	}
	
	@Transacional
	public void efetivarTratamento(){
		this.prontuarioTratamento.setAgt_status("Efetivado");
		this.agtDAO.atualiza(prontuarioTratamento);
		this.prontuarioTratamento = new AgendamentoTratamento();
		FacesUtil.addSuccessMessage("Tratamento Efetivado!");
	}
	
	public void testeSalvar(Anamnese a){
		this.prontuarioAnamnese.setAnamnese(a);
		this.lstProntuarioAnamnese.add(prontuarioAnamnese);
		this.prontuarioAnamnese = new ProntuarioAnamnese();
		for (ProntuarioAnamnese s : lstProntuarioAnamnese) {
			System.out.println(s);
		}
	}
	
	@Transacional
	public void cancelarConsulta(){
		this.agenda.setAge_status("Ausente");
		agendaDAO.atualiza(this.agenda);
		FacesUtil.addSuccessMessage("Consulta Alterada Para Ausente!");
		limpar();
	}
	
	public List<Agenda> getListaAgenda() {
		return listaAgenda;
	}
	
	public void setListaAgenda(List<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}
	
	public Agenda getAgenda() {
		return agenda;
	}
	
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Paciente getPaciente() {
		return paciente;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public long getDiferencaAnos() {
		return diferencaAnos;
	}
	
	public void setDiferencaAnos(long diferencaAnos) {
		this.diferencaAnos = diferencaAnos;
	}
	
	public boolean isDiferencaAnosBoolean() {
		return diferencaAnosBoolean;
	}
	
	public void setDiferencaAnosBoolean(boolean diferencaAnosBoolean) {
		this.diferencaAnosBoolean = diferencaAnosBoolean;
	}
	
	public List<PlanoPai> getListaPlanos() {
		return listaPlanos = planoDAO.listaTodos();
	}

	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}
	
	public List<ModeloAnamnese> getLstModeloAnamneses() {
		return lstModeloAnamneses;
	}
	
	public void setAnamneses(List<Anamnese> anamneses) {
		this.anamneses = anamneses;
	}
	
	public List<Anamnese> getAnamneses() {
		return anamneses;
	}
	
	public void setLstModeloAnamneses(List<ModeloAnamnese> lstModeloAnamneses) {
		this.lstModeloAnamneses = lstModeloAnamneses;
	}
	
	public ModeloAnamnese getModeloAnamnese() {
		return modeloAnamnese;
	}
	
	public void setModeloAnamnese(ModeloAnamnese modeloAnamnese) {
		this.modeloAnamnese = modeloAnamnese;
	}
	
	public ProntuarioAnamnese getProntuarioAnamnese() {
		return prontuarioAnamnese;
	}
	
	public void setProntuarioAnamnese(ProntuarioAnamnese prontuarioAnamnese) {
		this.prontuarioAnamnese = prontuarioAnamnese;
	}
	
	public List<ProntuarioAnamnese> getLstProntuarioAnamnese() {
		return lstProntuarioAnamnese;
	}
	
	public void setLstProntuarioAnamnese(List<ProntuarioAnamnese> lstProntuarioAnamnese) {
		this.lstProntuarioAnamnese = lstProntuarioAnamnese;
	}
	
	public List<AgendamentoTratamento> getLstProntuarioTratamento() {
		return lstProntuarioTratamento;
	}
	
	public void setLstProntuarioTratamento(List<AgendamentoTratamento> lstProntuarioTratamento) {
		this.lstProntuarioTratamento = lstProntuarioTratamento;
	}
	
	public AgendamentoTratamento getProntuarioTratamento() {
		return prontuarioTratamento;
	}
	
	public void setProntuarioTratamento(AgendamentoTratamento prontuarioTratamento) {
		this.prontuarioTratamento = prontuarioTratamento;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}
	
	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}
}
