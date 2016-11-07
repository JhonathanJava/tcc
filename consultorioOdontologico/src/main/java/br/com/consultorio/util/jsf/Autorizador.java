package br.com.consultorio.util.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Named;

import br.com.consultorio.modelo.Permissao;
import br.com.consultorio.modelo.Usuario;

@Named
public class Autorizador implements  PhaseListener{

	private static final long serialVersionUID = 1L;

	List<Permissao> permissao = new ArrayList<Permissao>(); 
	
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		
		String nomePagina = context.getViewRoot().getViewId();
		System.out.println("Teste");
		if("/login.xhtml".equals(nomePagina)){
			return;
		}
		
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		permissao  = (List<Permissao>) context.getExternalContext().getSessionMap().get("permissao");
		
		if(usuarioLogado != null){
			
			return;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null,"/login?faces-redirect=true");
		context.renderResponse();
	}

	public void beforePhase(PhaseEvent event) {
	}
	

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	public List<Permissao> getPermissao() {
		return permissao;
	}
	
	public void setPermissao(List<Permissao> permissao) {
		this.permissao = permissao;
	}

}
