package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.consultorio.dao.CampanhaDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.modelo.Campanha;
import br.com.consultorio.modelo.CampanhaPaciente;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CampanhaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Campanha campanha;
	
	private CampanhaPaciente campanhaPaciente;
	
	private List<Campanha> listaCampanhas;
	
	private List<Paciente> resultsPaciente;
	
	private List<Paciente> pacienteSelected;
	
	@Inject
	private CampanhaDAO dao;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	
	private String destinatario = "jhonathanjava@gmail.com";
	
	@PostConstruct
	 void init() {
		this.campanha = new Campanha();
		this.listaCampanhas = dao.listaTodos();
	}
	
	public void limpar(){
		this.campanha = new Campanha();
	}
	
	@Transacional
	public void salvar(){
		dao.adiciona(campanha);
		FacesUtil.addSuccessMessage("E-mail Enviado com Sucesso!! ");
		
		enviarEmail();
	}
	
	public void enviarEmail(){
		 Properties props = new Properties();
         /** Parâmetros de conexão com servidor Gmail */
         props.put("mail.smtp.host", "smtp.gmail.com");
         props.put("mail.smtp.socketFactory.port", "465");
         props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.port", "465");

         Session session = Session.getDefaultInstance(props,
                     new javax.mail.Authenticator() {
                          protected PasswordAuthentication getPasswordAuthentication() 
                          {
                                return new PasswordAuthentication("jhonathanjava@gmail.com", "desenv01");
                          }
                     });
         /** Ativa Debug para sessão */
         session.setDebug(true);
         try {

               Message message = new MimeMessage(session);
               message.setFrom(new InternetAddress("consultorio@gmail.com")); //Remetente
             //Destinatário(s)
               Address[] toUser = InternetAddress.parse(campanha.getCae_destinatario());  
               message.setRecipients(Message.RecipientType.TO, toUser);
               message.setSubject(campanha.getCae_titulo());//Assunto
               message.setText(campanha.getCae_descricao());
               /**Método para enviar a mensagem criada*/
               Transport.send(message);
               System.out.println("Feito!!!");
          } catch (MessagingException e) {
               throw new RuntimeException(e);
         }
	}
	
	public List<Paciente> buscaPaciente(String query){
		resultsPaciente = pacienteDAO.buscarPorNome(query);
        return resultsPaciente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Campanha getCampanha() {
		return campanha;
	}
	
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}
	
	public List<Campanha> getListaCampanhas() {
		return listaCampanhas;
	}
	
	public void setListaCampanhas(List<Campanha> listaCampanhas) {
		this.listaCampanhas = listaCampanhas;
	}

	public CampanhaPaciente getCampanhaPaciente() {
		return campanhaPaciente;
	}

	public void setCampanhaPaciente(CampanhaPaciente campanhaPaciente) {
		this.campanhaPaciente = campanhaPaciente;
	}

	public List<Paciente> getResultsPaciente() {
		return resultsPaciente;
	}

	public void setResultsPaciente(List<Paciente> resultsPaciente) {
		this.resultsPaciente = resultsPaciente;
	}

	public List<Paciente> getPacienteSelected() {
		return pacienteSelected;
	}

	public void setPacienteSelected(List<Paciente> pacienteSelected) {
		this.pacienteSelected = pacienteSelected;
	}
	
	
}
