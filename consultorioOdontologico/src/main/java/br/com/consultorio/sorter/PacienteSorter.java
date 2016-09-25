package br.com.consultorio.sorter;
 
import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.consultorio.modelo.Paciente;
 
public class PacienteSorter implements Comparator<Paciente> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public PacienteSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(Paciente car1, Paciente car2) {
        try {
            Object value1 = Paciente.class.getField(this.sortField).get(car1);
            Object value2 = Paciente.class.getField(this.sortField).get(car2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

}