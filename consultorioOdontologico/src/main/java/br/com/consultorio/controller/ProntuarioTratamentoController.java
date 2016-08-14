package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.ProntuarioTratamentoDAO;
import br.com.consultorio.modelo.Cargo;
import br.com.consultorio.modelo.ProntuarioTratamento;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProntuarioTratamentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ProntuarioTratamento prontuarioTratamento;
	
	@Inject
	private ProntuarioTratamentoDAO dao;
	
	private List<ProntuarioTratamento> prontuarioTratamentos;
	
	private List<ProntuarioTratamento> filterProntuarioTratamento;
	
	private List<ProntuarioTratamento> prontuarioTratamentoSelecionados = new ArrayList<>();
	
	@PostConstruct
	 void init() {
		System.out.println("sokosko");
		this.prontuarioTratamento = new ProntuarioTratamento();
	}

	
	public void carregaPeloId(Cargo cargo){
		this.prontuarioTratamento = this.dao.buscaPorId(prontuarioTratamento.getPrt_codigo());
	}
	
	@Transacional
	public String gravar() {
		if(this.prontuarioTratamento.getPrt_codigo() != null){
			System.out.println("Alterando");
			this.dao.atualiza(this.prontuarioTratamento);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Salvando");
			this.dao.adiciona(this.prontuarioTratamento);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void excluirSelecionados(){
		for (ProntuarioTratamento t : prontuarioTratamentoSelecionados) {
			this.dao.remove(t);
			t = null;
		}
		init();
		FacesUtil.addSuccessMessage("Registros Excluidos Com Sucesso!!");
	}
	
	@Transacional
	public void remover(ProntuarioTratamento t){
		System.out.println("Chamando Remover()");
		this.dao.remove(t);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limparCargo(){
		this.prontuarioTratamento = new ProntuarioTratamento();
	}

	public ProntuarioTratamento getProntuarioTratamento() {
		return prontuarioTratamento;
	}

	public void setProntuarioTratamento(ProntuarioTratamento prontuarioTratamento) {
		this.prontuarioTratamento = prontuarioTratamento;
	}

	public ProntuarioTratamentoDAO getDao() {
		return dao;
	}

	public void setDao(ProntuarioTratamentoDAO dao) {
		this.dao = dao;
	}

	public List<ProntuarioTratamento> getProntuarioTratamentos() {
		return prontuarioTratamentos;
	}

	public void setProntuarioTratamentos(List<ProntuarioTratamento> prontuarioTratamentos) {
		this.prontuarioTratamentos = prontuarioTratamentos;
	}

	public List<ProntuarioTratamento> getFilterProntuarioTratamento() {
		return filterProntuarioTratamento;
	}

	public void setFilterProntuarioTratamento(List<ProntuarioTratamento> filterProntuarioTratamento) {
		this.filterProntuarioTratamento = filterProntuarioTratamento;
	}

	public List<ProntuarioTratamento> getProntuarioTratamentoSelecionados() {
		return prontuarioTratamentoSelecionados;
	}

	public void setProntuarioTratamentoSelecionados(List<ProntuarioTratamento> prontuarioTratamentoSelecionados) {
		this.prontuarioTratamentoSelecionados = prontuarioTratamentoSelecionados;
	}
	
}
