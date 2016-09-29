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
	
	@PostConstruct
	 void init() {
		this.cargo = new Cargo();
		this.cargos = dao.listaTodos();
	}
	
	public void limpar(){
		this.cargo = new Cargo();
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
		this.cargo.setCar_status("A");
		this.cargo.setCar_dtCadastro(Calendar.getInstance());
		if(this.cargo.getCar_codigo() != null){
			System.out.println("Salvando");
			this.dao.atualiza(this.cargo);
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			System.out.println("Alterando");
			this.dao.adiciona(this.cargo);
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		this.cargo = new Cargo();
		init();
		return null;
	}
	
	@Transacional
	public void remover(){
		System.out.println("Chamando Remover()");
		this.dao.remove(cargo);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void limparCargo(){
		this.cargo = new Cargo();
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
	
}
