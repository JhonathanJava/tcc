package br.com.consultorio.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultorio.dao.PermissaoDAO;
import br.com.consultorio.modelo.Permissao;

@FacesConverter(forClass=Permissao.class, value="permissaoConverter")
public class PermissaoConverter implements Converter{

	@Inject
	PermissaoDAO permissaoDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null && !value.equals("")){
			return (Permissao) component.getAttributes().get(value);
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value instanceof Permissao){
			Permissao permissao = (Permissao) value;
			if(permissao != null && permissao instanceof Permissao && permissao.getPer_codigo() != null){
				component.getAttributes().put(permissao.getPer_codigo().toString(), permissao);
				return permissao.getPer_codigo().toString();
			}
		}
		return "";
	}

}
