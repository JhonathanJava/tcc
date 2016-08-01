package br.com.consultorio.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultorio.dao.ProgramaDAO;
import br.com.consultorio.modelo.Programa;

@FacesConverter(forClass = Programa.class)
public class ProgramaConverter implements Converter {

	@Inject
	ProgramaDAO programaDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Programa retorno = null;

		if (value != null && !value.equals("")) {
			retorno = (Programa) programaDAO.buscaPorId(Long.parseLong(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value != null) {
			return ((Programa) value).getPro_codigo().toString();
		}
		return null;
	}

}
