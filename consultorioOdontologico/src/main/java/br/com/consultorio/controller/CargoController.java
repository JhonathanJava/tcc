package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.CargoDAO;
import br.com.consultorio.modelo.Cargo;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CargoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Cargo cargo;
	
	@Inject
	private CargoDAO dao;
	
	private List<Cargo> cargos;
	
	private List<Cargo> filterCargos;
	
	private List<Cargo> cargosSelecionados = new ArrayList<>();
	
	private Long cargoId;
	
	@PostConstruct
	 void init() {
		System.out.println("sokosko");
		//this.cargos = dao.listaTodos();
		this.cargo = new Cargo();
		//this.cargo.setCar_status("A");
	}
	

	
	public void carregaPeloId(Cargo cargo){
		System.out.println(cargo.getCar_codigo());
		this.cargo = this.dao.buscaPorId(cargo.getCar_codigo());
	}
	
	@Transacional
	public void inativarSelecionados(Cargo cargo){
			if(cargo.getCar_status().equals("A")){
				cargo.setCar_status("I");
				cargo.setCar_dtInativacao(Calendar.getInstance());
			}else{
				cargo.setCar_status("A");
			}
			this.dao.atualiza(cargo);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar() {
		System.out.println("Gravando Cargo " + this.cargo.getCar_descricao());
		System.out.println("ToString = "+ this.cargo.toString());
		if(this.cargo.getCar_codigo() != null){
			System.out.println("Salvando");
			this.dao.atualiza(this.cargo);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Alterando");
			this.dao.adiciona(this.cargo);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		init();
		return null;
	}
	
	@Transacional
	public void excluirSelecionados(){
		for (Cargo cargo : cargosSelecionados) {
			this.dao.remove(cargo);
			cargo = null;
		}
		init();
		FacesUtil.addSuccessMessage("Registros Excluidos Com Sucesso!!");
	}
	
	@Transacional
	public void remover(Cargo cargo){
		System.out.println("Chamando Remover()");
		this.dao.remove(cargo);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limparCargo(){
		this.cargo = new Cargo();
	}
	
	public Long getCargoId() {
		return cargoId;
	}
	
	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}
	
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public List<Cargo> getCargos() {
		return cargos;
	}
	
	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}
	
	public List<Cargo> getCargosSelecionados() {
		return cargosSelecionados;
	}
	
	public void setCargosSelecionados(List<Cargo> cargosSelecionados) {
		this.cargosSelecionados = cargosSelecionados;
	}
	
	public List<Cargo> getFilterCargos() {
		return filterCargos;
	}
	
	public void setFilterCargos(List<Cargo> filterCargos) {
		this.filterCargos = filterCargos;
	}
	
}
