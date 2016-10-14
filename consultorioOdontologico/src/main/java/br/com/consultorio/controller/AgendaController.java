package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.consultorio.dao.AgendaDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.UsuarioDAO;
import br.com.consultorio.modelo.Agenda;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AgendaController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Agenda agenda;

	private ScheduleModel eventModel;
	
	private Paciente paciente;
	
	private Usuario profissional;
	
	@Inject
	private AgendaDAO dao;

	private ScheduleEvent event = new DefaultScheduleEvent();

	@Inject
	private PacienteDAO pacienteDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	private List<Agenda> listaAgenda;
	private List<Paciente> resultsPaciente = new ArrayList<Paciente>();
	private List<Usuario> resultUsuario = new ArrayList<Usuario>();

	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

	@PostConstruct
	void init() {
		 eventModel = new DefaultScheduleModel();
		 this.paciente = new Paciente();
		 this.usuario = new Usuario();
		this.agenda = new Agenda();
		this.listaAgenda = dao.listaTodos();
		for (Agenda agenda : listaAgenda) {
			eventModel.addEvent(new DefaultScheduleEvent("Paciente:"+agenda.getPaciente().getPac_nome()+"\n Médico:"+agenda.getUsuario().getUsu_nome()+"\n Motivo: "+agenda.getAge_motivo(),agenda.getAge_dataConsulta(),agenda.getAge_dataConsulta(),agenda));
		}
	}

	public void buscarPorId(Long id) {
		agenda = dao.buscaPorId(id);
	}

	public void onDateSelect(SelectEvent selectEvent) {
		System.out.println("date" + event.getId());
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		this.agenda = (Agenda) event.getData();
		this.paciente = this.getAgenda().getPaciente();
		this.usuario = this.agenda.getUsuario();
	}
	
	@Transacional
	public void addEvent(ActionEvent actionEvent) {
		this.agenda.setAge_dataConsulta(event.getStartDate());
		
		if(this.agenda.getPaciente() == null){
			this.agenda.setPaciente(paciente);
		}
		
		if(this.agenda.getUsuario() == null){
			this.agenda.setUsuario(usuario);
		}
		
		if(this.agenda.getAge_codigo() != null && this.agenda.getAge_codigo() > 0){
			dao.atualiza(agenda);
			eventModel.updateEvent(new DefaultScheduleEvent(agenda.getPaciente().getPac_nome(),event.getStartDate(),event.getStartDate()));
		}else{
			dao.adiciona(agenda);
			eventModel.addEvent(new DefaultScheduleEvent(agenda.getPaciente().getPac_nome(),event.getStartDate(),event.getStartDate()));
		}
        event = new DefaultScheduleEvent();
        
        FacesUtil.addSuccessMessage("Paciente Agendado");
    }

	public List<Paciente> buscaPaciente(String query) {
		resultsPaciente = pacienteDAO.buscarPorNome(query);
		return resultsPaciente;
	}

	public List<Usuario> buscaProfissional(String query) {
		resultUsuario = usuarioDAO.buscarProfissional(query);
		return resultUsuario;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public List<Agenda> getListaAgenda() {
		return listaAgenda;
	}

	public void setListaAgenda(List<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}

	public List<Paciente> getResultsPaciente() {
		return resultsPaciente;
	}

	public void setResultsPaciente(List<Paciente> resultsPaciente) {
		this.resultsPaciente = resultsPaciente;
	}

	public List<Usuario> getResultUsuario() {
		return resultUsuario;
	}

	public void setResultUsuario(List<Usuario> resultUsuario) {
		this.resultUsuario = resultUsuario;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	
	public ScheduleModel getEventModel() {
		return eventModel;
	}
	
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
}
