package br.com.consultorio.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultorio.dao.PlanoPaiDAO;
import br.com.consultorio.modelo.PlanoPai;

@FacesConverter(forClass=PlanoPai.class)
public class PlanoConverter implements Converter{

	@Inject
	PlanoPaiDAO planoPaiDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		PlanoPai retorno = null;
		
		if(value != null && !value.equals("")){
			retorno = (PlanoPai) planoPaiDAO.buscaPorId(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
			if(value != null){
				return ((PlanoPai) value).getPlp_codigo().toString();
			}
		return null;
	}

}
