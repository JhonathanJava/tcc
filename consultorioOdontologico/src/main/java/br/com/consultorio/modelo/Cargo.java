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
public class Cargo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long car_codigo;
	
	@Column
	private String car_descricao;
	
	@Column
	private String car_status;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar car_dtCadastro = Calendar.getInstance();
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar car_dtInativacao;
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar car_dtReativacao;

	public Cargo() {
	}
	
	public Long getCar_codigo() {
		return car_codigo;
	}

	public void setCar_codigo(Long car_codigo) {
		this.car_codigo = car_codigo;
	}

	public String getCar_descricao() {
		return car_descricao;
	}

	public void setCar_descricao(String car_descricao) {
		this.car_descricao = car_descricao;
	}

	public String getCar_status() {
		return car_status;
	}

	public void setCar_status(String car_status) {
		this.car_status = car_status;
	}

	public Calendar getCar_dtCadastro() {
		return car_dtCadastro;
	}
	
	public void setCar_dtCadastro(Calendar car_dtCadastro) {
		this.car_dtCadastro = car_dtCadastro;
	}
	
	public Calendar getCar_dtReativacao() {
		return car_dtReativacao;
	}
	
	public void setCar_dtReativacao(Calendar car_dtReativacao) {
		this.car_dtReativacao = car_dtReativacao;
	}
	
	@Override
	public String toString() {
		return "Cargo [car_codigo=" + car_codigo + ", car_descricao=" + car_descricao + ", car_status=" + car_status
				+ "]";
	}
	
	public Calendar getCar_dtInativacao() {
		return car_dtInativacao;
	}
	
	public void setCar_dtInativacao(Calendar car_dtInativacao) {
		this.car_dtInativacao = car_dtInativacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_codigo == null) ? 0 : car_codigo.hashCode());
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
		Cargo other = (Cargo) obj;
		if (car_codigo == null) {
			if (other.car_codigo != null)
				return false;
		} else if (!car_codigo.equals(other.car_codigo))
			return false;
		return true;
	}
	
}
