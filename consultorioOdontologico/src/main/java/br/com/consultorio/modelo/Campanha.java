package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Campanha implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cae_codigo;
	
	@Column
	private String cae_descricao;
	
	@Column
	private String cae_titulo;
	
	@Column
	private String cae_destinatario;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar cae_dtCadastro = Calendar.getInstance();
	
	public Campanha() {
	
	}

	public Long getCae_codigo() {
		return cae_codigo;
	}

	public void setCae_codigo(Long cae_codigo) {
		this.cae_codigo = cae_codigo;
	}

	public String getCae_descricao() {
		return cae_descricao;
	}

	public void setCae_descricao(String cae_descricao) {
		this.cae_descricao = cae_descricao;
	}

	public String getCae_titulo() {
		return cae_titulo;
	}

	public void setCae_titulo(String cae_titulo) {
		this.cae_titulo = cae_titulo;
	}

	public String getCae_destinatario() {
		return cae_destinatario;
	}
	
	public void setCae_destinatario(String cae_destinatario) {
		this.cae_destinatario = cae_destinatario;
	}
	
	public Calendar getCae_dtCadastro() {
		return cae_dtCadastro;
	}

	public void setCae_dtCadastro(Calendar cae_dtCadastro) {
		this.cae_dtCadastro = cae_dtCadastro;
	}

	@Override
	public String toString() {
		return "Campanha [cae_codigo=" + cae_codigo + ", cae_descricao=" + cae_descricao + ", cae_titulo=" + cae_titulo
				+ ", cae_imagem=" + cae_destinatario + ", cae_dtCadastro=" + cae_dtCadastro + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cae_codigo == null) ? 0 : cae_codigo.hashCode());
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
		Campanha other = (Campanha) obj;
		if (cae_codigo == null) {
			if (other.cae_codigo != null)
				return false;
		} else if (!cae_codigo.equals(other.cae_codigo))
			return false;
		return true;
	}
	
}
