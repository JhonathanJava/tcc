package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Permissao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long per_codigo;
	
	@Column
	private Boolean per_inserir = Boolean.FALSE;
	
	@Column
	private Boolean per_alterar = Boolean.FALSE;
	
	@Column
	private Boolean per_excluir = Boolean.FALSE;
	
	@Column
	private Boolean per_imprimir = Boolean.FALSE;
	
	@Column
	private Boolean per_ver = Boolean.FALSE;
	
	@Column
	private Boolean per_status = Boolean.FALSE;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar per_dataCadastro = Calendar.getInstance();
	
	@JoinColumn(name = "perfil_codigo")
	@ManyToOne
	private Perfil perfil;
	
	@JoinColumn(name = "perfilPermissao")
	@ManyToOne
	private PerfilPermissao perfilPermissao;
	
	@ManyToOne
	private Programa programa;

	public Long getPer_codigo() {
		return per_codigo;
	}

	public void setPer_codigo(Long per_codigo) {
		this.per_codigo = per_codigo;
	}

	public Boolean getPer_inserir() {
		return per_inserir;
	}

	public void setPer_inserir(Boolean per_inserir) {
		this.per_inserir = per_inserir;
	}

	public Boolean getPer_alterar() {
		return per_alterar;
	}

	public void setPer_alterar(Boolean per_alterar) {
		this.per_alterar = per_alterar;
	}

	public Boolean getPer_excluir() {
		return per_excluir;
	}

	public void setPer_excluir(Boolean per_excluir) {
		this.per_excluir = per_excluir;
	}

	public Boolean getPer_imprimir() {
		return per_imprimir;
	}

	public void setPer_imprimir(Boolean per_imprimir) {
		this.per_imprimir = per_imprimir;
	}

	public Boolean getPer_ver() {
		return per_ver;
	}

	public void setPer_ver(Boolean per_ver) {
		this.per_ver = per_ver;
	}

	public Boolean getPer_status() {
		return per_status;
	}

	public void setPer_status(Boolean per_status) {
		this.per_status = per_status;
	}

	public Calendar getPer_dataCadastro() {
		return per_dataCadastro;
	}

	public void setPer_dataCadastro(Calendar per_dataCadastro) {
		this.per_dataCadastro = per_dataCadastro;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Programa getPrograma() {
		return programa;
	}
	
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	
	public PerfilPermissao getPerfilPermissao() {
		return perfilPermissao;
	}
	
	public void setPerfilPermissao(PerfilPermissao perfilPermissao) {
		this.perfilPermissao = perfilPermissao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((per_codigo == null) ? 0 : per_codigo.hashCode());
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
		Permissao other = (Permissao) obj;
		if (per_codigo == null) {
			if (other.per_codigo != null)
				return false;
		} else if (!per_codigo.equals(other.per_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permissao [per_codigo=" + per_codigo + ", per_inserir=" + per_inserir + ", per_alterar=" + per_alterar
				+ ", per_excluir=" + per_excluir + ", per_imprimir=" + per_imprimir + ", per_ver=" + per_ver
				+ ", per_status=" + per_status + ", per_dataCadastro=" + per_dataCadastro + ", perfil=" + perfil
				+ ", programa=" + programa + "]";
	}

}
