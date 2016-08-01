package br.com.consultorio.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PerfilPermissao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long perfilPermissaoCodigo;

	@JoinColumn(name="perfil_codigo")
	@ManyToOne(fetch = FetchType.LAZY)
	private Perfil perfil;
	
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getPerfilPermissaoCodigo() {
		return perfilPermissaoCodigo;
	}
	
	public void setPerfilPermissaoCodigo(Long perfilPermissaoCodigo) {
		this.perfilPermissaoCodigo = perfilPermissaoCodigo;
	}

	@Override
	public String toString() {
		return "PerfilPermissao [perfilPermissaoCodigo=" + perfilPermissaoCodigo + ", perfil=" + perfil + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perfilPermissaoCodigo == null) ? 0 : perfilPermissaoCodigo.hashCode());
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
		PerfilPermissao other = (PerfilPermissao) obj;
		if (perfilPermissaoCodigo == null) {
			if (other.perfilPermissaoCodigo != null)
				return false;
		} else if (!perfilPermissaoCodigo.equals(other.perfilPermissaoCodigo))
			return false;
		return true;
	}

	
}
