package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.AgendaDAO;
import br.com.consultorio.modelo.Agenda;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AtendimentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Agenda agenda;
	
	@Inject
	private AgendaDAO agendaDAO;
	
	private List<Agenda> listaAgenda;
	
	
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

}
