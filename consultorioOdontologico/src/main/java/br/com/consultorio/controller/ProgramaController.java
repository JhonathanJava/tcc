package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.ProgramaDAO;
import br.com.consultorio.modelo.Programa;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProgramaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Programa programa;
	
	@Inject
	private ProgramaDAO dao;
	
	private List<Programa> programas;
	
	private List<Programa> filterProgramas;
	
	private List<Programa> programasSelecionados = new ArrayList<>();
	
	@PostConstruct
	 void init() {
		this.programas = dao.listaTodos();
		this.programa = new Programa();
		this.programa.setPro_status("A");
	}
	
	public void carregaPeloId(Programa programa){
		System.out.println(programa.getPro_codigo());
		this.programa = this.dao.buscaPorId(programa.getPro_codigo());
	}
	
	@Transacional
	public void inativarSelecionados(Programa programa){
			if(programa.getPro_status().equals("A")){
				programa.setPro_status("I");
			}else{
				programa.setPro_status("A");
			}
			this.dao.atualiza(programa);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar() {
		System.out.println("Gravando Programa " + this.programa.getPro_tela());
		System.out.println("ToString = "+ this.programa.toString());
		if(this.programa.getPro_codigo() != null){
			System.out.println("Alterando");
			this.programa.setPro_tela(this.programa.getPro_tela()+".xhtml");
			this.dao.atualiza(this.programa);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Salvando");
			this.dao.adiciona(this.programa);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void excluirSelecionados(){
		for (Programa programa : programasSelecionados) {
			this.dao.remove(programa);
			programa = null;
		}
		init();
		FacesUtil.addSuccessMessage("Registros Excluidos Com Sucesso!!");
	}
	
	@Transacional
	public void remover(Programa programa){
		System.out.println("Chamando Remover()");
		this.dao.remove(programa);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limparPrograma(){
		this.programa = new Programa();
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	public List<Programa> getFilterProgramas() {
		return filterProgramas;
	}

	public void setFilterProgramas(List<Programa> filterProgramas) {
		this.filterProgramas = filterProgramas;
	}

	public List<Programa> getProgramasSelecionados() {
		return programasSelecionados;
	}

	public void setProgramasSelecionados(List<Programa> programasSelecionados) {
		this.programasSelecionados = programasSelecionados;
	}

	
}
