package br.com.consultorio.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultorio.dao.TratamentoDAO;
import br.com.consultorio.modelo.Tratamento;

@FacesConverter(value="tratamentoConverter",forClass=Tratamento.class)
public class TratamentoConverter implements Converter{

	@Inject
	TratamentoDAO tratamentoDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Tratamento retorno = null;
		
		if(value != null && !value.equals("")){
			retorno = (Tratamento) tratamentoDAO.buscaPorId(Long.parseLong(value));
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
			if(value != null){
				return ((Tratamento) value).getTra_codigo().toString();
			}
		return null;
	}

}
