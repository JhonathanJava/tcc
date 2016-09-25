package br.com.consultorio.lazy;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.sorter.PacienteSorter;
 
/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class PacienteLazy extends LazyDataModel<Paciente> {
     
    private List<Paciente> datasource;
     
    public PacienteLazy(List<Paciente> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Paciente getRowData(String rowKey) {
        for(Paciente paciente : datasource) {
            if(paciente.getPac_codigo().equals(rowKey))
                return paciente;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Paciente paciente) {
        return paciente.getPac_codigo();
    }
 
    @Override
    public List<Paciente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Paciente> data = new ArrayList<Paciente>();
 
        //filter
        for(Paciente paciente : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(paciente.getClass().getField(filterProperty).get(paciente));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(paciente);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new PacienteSorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}