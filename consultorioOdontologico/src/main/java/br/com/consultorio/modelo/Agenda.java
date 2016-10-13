package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Agenda implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long age_codigo;
	
	@ManyToOne
	@JoinColumn(name="prf_codigo", nullable = true)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Usuario usuario;
	
	@Column
	private Date age_data = new Date();
	
	@Column
	private Date age_dataConsulta;
	
	@ManyToOne
	@JoinColumn(name="pac_codigo", nullable = true)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Paciente paciente;
	
	@Column
	private String age_motivo;
	
	@Column
	private String age_status = "Ativo";
	
	@Column  
	private String age_retorno = "N";

	public Long getAge_codigo() {
		return age_codigo;
	}

	public void setAge_codigo(Long age_codigo) {
		this.age_codigo = age_codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getAge_data() {
		return age_data;
	}

	public void setAge_data(Date age_data) {
		this.age_data = age_data;
	}

	public Date getAge_dataConsulta() {
		return age_dataConsulta;
	}

	public void setAge_dataConsulta(Date age_dataConsulta) {
		this.age_dataConsulta = age_dataConsulta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getAge_motivo() {
		return age_motivo;
	}

	public void setAge_motivo(String age_motivo) {
		this.age_motivo = age_motivo;
	}

	public String getAge_status() {
		return age_status;
	}

	public void setAge_status(String age_status) {
		this.age_status = age_status;
	}

	public String getAge_retorno() {
		return age_retorno;
	}

	public void setAge_retorno(String age_retorno) {
		this.age_retorno = age_retorno;
	}

	@Override
	public String toString() {
		return "Agenda [age_codigo=" + age_codigo + ", usuario=" + usuario + ", age_data=" + age_data
				+ ", age_dataConsulta=" + age_dataConsulta + ", paciente=" + paciente + ", age_motivo=" + age_motivo
				+ ", age_status=" + age_status + ", age_retorno=" + age_retorno + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age_codigo == null) ? 0 : age_codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (age_codigo == null) {
			if (other.age_codigo != null)
				return false;
		} else if (!age_codigo.equals(other.age_codigo))
			return false;
		return true;
	}
	
	
}
