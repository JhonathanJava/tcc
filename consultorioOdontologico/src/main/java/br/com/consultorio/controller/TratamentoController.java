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
	
	private Tratamento tratamentoEditar;
	
	@Inject
	private TratamentoDAO dao;

	@Inject
	private PlanoPaiDAO planoDAO;
	
	private Tratamento filterTratamento;
	
	private List<Tratamento> listaTratamentos;
	
	private List<PlanoPai> listaPlanos;

	
	@PostConstruct
	 void init() {
		this.tratamento = new Tratamento();
		this.tratamentoEditar = new Tratamento();
		this.filterTratamento = new Tratamento();
		this.listaTratamentos = dao.listaTodos();
	}
	
	public void buscaEditarPorId(Long id){
		tratamentoEditar = dao.buscaPorId(id);
		System.out.println(id+"-"+tratamentoEditar);
	}
	
	
	@Transacional
	public void inativarSelecionados(Tratamento tratamento){
			if(tratamento.getTra_status().equals("A")){
				tratamento.setTra_status('I');
			}else{
				tratamento.setTra_status('A');
			}
			this.dao.atualiza(tratamento);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String editar(){
		this.dao.atualiza(this.tratamentoEditar);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.tratamentoEditar = new Tratamento();
		init();
		this.listaTratamentos = dao.listaTodos();
		return null;
	}
	
	@Transacional
	public String gravar(){
		this.dao.adiciona(this.tratamento);
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.tratamento = new Tratamento();
		init();
		return null;
	}
	
	@Transacional
	public void remover(){
		System.out.println("Chamando Remover()");
		this.dao.remove(tratamentoEditar);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void pesquisaPorFiltro(){
		System.out.println(this.filterTratamento.toString());
		this.listaTratamentos = dao.pesquisaPorFiltro(this.filterTratamento); 
	}
	
	public void limparTratamento(){
		this.tratamento = new Tratamento();
	}
	
	public void buscarPorId(Long id){
		tratamento = dao.buscaPorId(id);
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public Tratamento getFilterTratamento() {
		return filterTratamento;
	}
	
	public void setFilterTratamento(Tratamento filterTratamento) {
		this.filterTratamento = filterTratamento;
	}
	
	public List<Tratamento> getListaTratamentos() {
		return listaTratamentos;
	}
	
	public void setListaTratamentos(List<Tratamento> listaTratamentos) {
		this.listaTratamentos = listaTratamentos;
	}

	public Tratamento getTratamentoEditar() {
		return tratamentoEditar;
	}
	
	public void setTratamentoEditar(Tratamento tratamentoEditar) {
		this.tratamentoEditar = tratamentoEditar;
	}
	
	public List<PlanoPai> getListaPlanos() {
		return listaPlanos = planoDAO.listaTodos();
	}

	public void setListaPlanos(List<PlanoPai> listaPlanos) {
		this.listaPlanos = listaPlanos;
	}
}
