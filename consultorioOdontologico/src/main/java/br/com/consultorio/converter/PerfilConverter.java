package br.com.consultorio.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultorio.dao.PerfilDAO;
import br.com.consultorio.modelo.Perfil;

@FacesConverter(forClass=Perfil.class)
public class PerfilConverter implements Converter{

	@Inject
	PerfilDAO perfilDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Perfil retorno = null;
		
		if(value != null && !value.equals("")){
			retorno = (Perfil) perfilDAO.buscaPorId(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
			if(value != null){
				return ((Perfil) value).getPer_codigo().toString();
			}
		return null;
	}

}
