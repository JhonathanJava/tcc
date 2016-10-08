package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.AnamneseDAO;
import br.com.consultorio.dao.ModeloAnamneseDAO;
import br.com.consultorio.modelo.Anamnese;
import br.com.consultorio.modelo.ModeloAnamnese;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AnamneseController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Anamnese anamnese;
	
	private ModeloAnamnese modelo;
	
	@Inject
	private AnamneseDAO dao;
	
	@Inject
	private ModeloAnamneseDAO modeloDAO;
	
	private List<Anamnese> anamneses;
	
	private List<ModeloAnamnese> modeloAnamneses;
	
	public void onChangeAlerta(){
	}
	
	@PostConstruct
	 void init() {
		this.anamnese = new Anamnese();
		this.modelo = new ModeloAnamnese();
		this.anamneses = new ArrayList<>();
		this.modeloAnamneses = modeloDAO.listaTodos();
	}
	
	public void buscarModelo(Long codigo){
		this.modelo = this.modeloDAO.buscaPorId(codigo);
		this.anamneses = this.dao.listaAnamnesePorModelo(modelo.getMoa_codigo());
		for (Anamnese anamnese : anamneses) {
			System.out.println(this.anamnese);
		}
		System.out.println(modelo.getMoa_codigo());
	}
	
	public void carregaPeloId(Anamnese anamnese){
		this.anamnese = this.dao.buscaPorId(anamnese.getAnm_codigo());
	}
	
	@Transacional
	public void removeAnamnese(Anamnese anamnese){
		this.dao.remove(anamnese);
		this.anamnese = new Anamnese();
		this.anamneses = this.dao.listaAnamnesePorModelo(modelo.getMoa_codigo());
		FacesUtil.addSuccessMessage("Pergunta Removida do Modelo");
	}
	
	@Transacional
	public void adicionaAnamnese(){
		modeloDAO.atualiza(modelo);
		this.anamnese.setModeloAnamnese(modelo);
		if(this.anamnese.getAnm_codigo() != null){
			this.dao.atualiza(anamnese);
		}else{
			this.dao.adiciona(anamnese);
		}
		this.anamnese = new Anamnese();
		this.anamneses = this.dao.listaAnamnesePorModelo(modelo.getMoa_codigo());
		FacesUtil.addSuccessMessage("Pergunta Adicionada no Modelo");
	}
	

	@Transacional
	public String gravarModelo() {
		System.out.println(modelo);
		if(this.modelo.getMoa_codigo() != null){
			this.modeloDAO.atualiza(this.modelo);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.modeloDAO.adiciona(this.modelo);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		return null;
	}
	
	
	@Transacional
	public String gravar() {
		if(this.anamnese.getAnm_codigo() != null){
			this.dao.atualiza(this.anamnese);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.anamnese);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void alterarStatus(){
		if(this.anamnese.getAnm_status().equals("A")){
			this.anamnese.setAnm_status("I");
			this.anamnese.setAnm_dataInativacao(new Date());
		}else{
			this.anamnese.setAnm_status("A");
		}
		this.dao.atualiza(anamnese);
		init();
		FacesUtil.addSuccessMessage("Registro Status Alterado!!");
	}
	
	public void limparAnamnese(){
		this.anamnese = new Anamnese();
	}
	
	public void limparModelo(){
		this.modelo = new ModeloAnamnese();
	}

	public Anamnese getAnamnese() {
		return anamnese;
	}

	public void setAnamnese(Anamnese anamnese) {
		this.anamnese = anamnese;
	}

	public AnamneseDAO getDao() {
		return dao;
	}

	public void setDao(AnamneseDAO dao) {
		this.dao = dao;
	}

	public List<Anamnese> getAnamneses() {
		return anamneses;
	}

	public void setAnamneses(List<Anamnese> anamneses) {
		this.anamneses = anamneses;
	}
	
	public ModeloAnamnese getModelo() {
		return modelo;
	}
	
	public void setModelo(ModeloAnamnese modelo) {
		this.modelo = modelo;
	}
	
	public List<ModeloAnamnese> getModeloAnamneses() {
		return modeloAnamneses;
	}
	
	public void setModeloAnamneses(List<ModeloAnamnese> modeloAnamneses) {
		this.modeloAnamneses = modeloAnamneses;
	}
	
	public ModeloAnamneseDAO getModeloDAO() {
		return modeloDAO;
	}
	
	public void setModeloDAO(ModeloAnamneseDAO modeloDAO) {
		this.modeloDAO = modeloDAO;
	}
}
