package br.com.consultorio.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.AnamneseDAO;
import br.com.consultorio.dao.ModeloAnamneseDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.dao.ProntuarioAnamneseDAO;
import br.com.consultorio.dao.ProntuarioDAO;
import br.com.consultorio.dao.TratamentoDAO;
import br.com.consultorio.modelo.Anamnese;
import br.com.consultorio.modelo.ModeloAnamnese;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.PlanoPai;
import br.com.consultorio.modelo.Prontuario;
import br.com.consultorio.modelo.ProntuarioAnamnese;
import br.com.consultorio.modelo.ProntuarioTratamento;
import br.com.consultorio.modelo.Tratamento;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProntuarioController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Prontuario prontuario;
	
	private Paciente paciente;
	
	private Tratamento tratamento;
	
	private Prontuario prontuarioEditar;
	
	private ProntuarioTratamento prontuarioTratamento;
	
	private ProntuarioAnamnese prontuarioAnamnese;
	
	private ModeloAnamnese modeloAnamnese;
	
	@Inject
	private ProntuarioDAO dao;
	
	@Inject
	private AnamneseDAO anamneseDAO;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Inject
	private TratamentoDAO tratamentoDAO;
	
	@Inject
	private ModeloAnamneseDAO modeloDAO;
	
	@Inject
	private ProntuarioAnamneseDAO prontuarioAnamneseDAO;
	
	@Inject
	private PlanoPaiDAO planoDAO;
	
	private List<Prontuario> prontuarios;
	
	private List<Anamnese> anamneses;
	
	private List<Prontuario> filterProntuarios;
	
	private List<PlanoPai> listaPlanos = new ArrayList<PlanoPai>();
	
	private List<Paciente> lstPaciente = new ArrayList<Paciente>();
	
	private List<Tratamento> lstTratamento = new ArrayList<Tratamento>();
	
	private List<ModeloAnamnese> lstModeloAnamneses = new ArrayList<ModeloAnamnese>();
	
	private List<ProntuarioTratamento> listaProntuarioTratamento = new ArrayList<ProntuarioTratamento>();
	
	private boolean diferencaAnosBoolean = false;
	
	private BigDecimal textDesconto = BigDecimal.ONE;
	
	private long diferencaAnos;
	
	@PostConstruct
	 void init() {
		this.prontuario = new Prontuario();
		this.paciente = new Paciente();
		this.prontuarioTratamento = new ProntuarioTratamento();
		this.lstModeloAnamneses = modeloDAO.listaTodos();
		this.modeloAnamnese = new ModeloAnamnese();
		this.prontuarioAnamnese = new ProntuarioAnamnese();
	}
	
	public void buscarAnamnese(){
		this.anamneses = anamneseDAO.listaAnamnesePorModelo(modeloAnamnese.getMoa_codigo());
	}
	
	public void descontoMaximo(){
		prontuarioTratamento.setPlanoPai(paciente.getPlano().getPlanoPai());
		setTextDesconto(paciente.getPlano().getPlanoPai().getPla_desconto());
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
	
	public void testeSalvar(Anamnese a){
		System.out.println(a);
		System.out.println(prontuarioAnamnese.getPra_resp1());
		this.prontuarioAnamnese = new ProntuarioAnamnese();
	}
	
	public void carregaPeloId(Long id){
		this.paciente = this.pacienteDAO.buscaPorId(id);
		listaProntuarioTratamento.clear();
		prontuarioTratamento = new ProntuarioTratamento();
		calculaIdade();
	}
	
	public void buscarTratatamentoPorId(Long id){
		this.tratamento = this.tratamentoDAO.buscaPorId(id);
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
	
	public void adicionarTratamento(){
		prontuarioTratamento.setPlanoPai(paciente.getPlano().getPlanoPai());
		prontuarioTratamento.setPrt_valor(prontuarioTratamento.getTratamento().getTra_valor());
		for (ProntuarioTratamento prontuario : listaProntuarioTratamento) {
			if(prontuario.equals(prontuarioTratamento)){
				FacesUtil.addErrorMessage("Tratamento Já Adicionado!!");
				return;
			}
		}
		listaProntuarioTratamento.add(prontuarioTratamento);
		prontuarioTratamento = new ProntuarioTratamento();
		prontuarioTratamento.setTratamento(new Tratamento());
	}
	
	public List<ModeloAnamnese> getLstModeloAnamneses() {
		return lstModeloAnamneses;
	}
	
	public void setLstModeloAnamneses(List<ModeloAnamnese> lstModeloAnamneses) {
		this.lstModeloAnamneses = lstModeloAnamneses;
	}
	
	public BigDecimal getTextDesconto() {
		return textDesconto;
	}
	
	public void setTextDesconto(BigDecimal textDesconto) {
		this.textDesconto = textDesconto;
	}
	
	public void removerTratamento(ProntuarioTratamento t){
		listaProntuarioTratamento.remove(t);
		tratamento = new Tratamento();
	}
	
	public void limparProntuario(){
		this.prontuario = new Prontuario();
	}
	
	public ModeloAnamnese getModeloAnamnese() {
		return modeloAnamnese;
	}
	
	public void setModeloAnamnese(ModeloAnamnese modeloAnamnese) {
		this.modeloAnamnese = modeloAnamnese;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public List<Anamnese> getAnamneses() {
		return anamneses;
	}
	
	public void setAnamneses(List<Anamnese> anamneses) {
		this.anamneses = anamneses;
	}
	
	public Prontuario getProntuarioEditar() {
		return prontuarioEditar;
	}

	public void setProntuarioEditar(Prontuario prontuarioEditar) {
		this.prontuarioEditar = prontuarioEditar;
	}

	public ProntuarioAnamnese getProntuarioAnamnese() {
		return prontuarioAnamnese;
	}
	
	public void setProntuarioAnamnese(ProntuarioAnamnese prontuarioAnamnese) {
		this.prontuarioAnamnese = prontuarioAnamnese;
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
	
	public List<ProntuarioTratamento> getListaProntuarioTratamento() {
		return listaProntuarioTratamento;
	}
	
	public void setListaProntuarioTratamento(List<ProntuarioTratamento> listaProntuarioTratamento) {
		this.listaProntuarioTratamento = listaProntuarioTratamento;
	}
	
	public Tratamento getTratamento() {
		return tratamento;
	}
	
	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}
	
	public List<Tratamento> getLstTratamento() {
		return tratamentoDAO.listaTodos();
	}
	
	public void setLstTratamento(List<Tratamento> lstTratamento) {
		this.lstTratamento = lstTratamento;
	}
	
	public ProntuarioTratamento getProntuarioTratamento() {
		return prontuarioTratamento;
	}
	
	public void setProntuarioTratamento(ProntuarioTratamento prontuarioTratamento) {
		this.prontuarioTratamento = prontuarioTratamento;
	}
	
}	
