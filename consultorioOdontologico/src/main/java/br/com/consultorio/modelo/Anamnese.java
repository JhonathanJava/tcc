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

@Entity
public class Anamnese implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long anm_codigo;
	
	@Column
	private String anm_descricao;
	
	@Column
	private String anm_alerta;
	
	@Column 
	private String anm_tipoPergunta;
	
	@Column
	private String anm_descricaoAuxiliar;
	
	@Column
	private String anm_descricaoAlerta;
	
	@Column 
	private String anm_tipoAlerta;
	
	@Column
	private Date anm_dataInativacao;
	
	@Column 
	String anm_status = "A";
	
	@JoinColumn(name = "moa_codigo")
	@ManyToOne
	private ModeloAnamnese modeloAnamnese = new ModeloAnamnese();
	
	public Anamnese() {
	}

	public Long getAnm_codigo() {
		return anm_codigo;
	}

	public void setAnm_codigo(Long anm_codigo) {
		this.anm_codigo = anm_codigo;
	}

	public String getAnm_descricao() {
		return anm_descricao;
	}

	public void setAnm_descricao(String anm_descricao) {
		this.anm_descricao = anm_descricao;
	}

	public String getAnm_alerta() {
		return anm_alerta;
	}

	public void setAnm_alerta(String anm_alerta) {
		this.anm_alerta = anm_alerta;
	}

	public String getAnm_tipoPergunta() {
		return anm_tipoPergunta;
	}

	public void setAnm_tipoPergunta(String anm_tipoPergunta) {
		this.anm_tipoPergunta = anm_tipoPergunta;
	}

	public String getAnm_descricaoAuxiliar() {
		return anm_descricaoAuxiliar;
	}

	public void setAnm_descricaoAuxiliar(String anm_descricaoAuxiliar) {
		this.anm_descricaoAuxiliar = anm_descricaoAuxiliar;
	}

	public String getAnm_descricaoAlerta() {
		return anm_descricaoAlerta;
	}

	public void setAnm_descricaoAlerta(String anm_descricaoAlerta) {
		this.anm_descricaoAlerta = anm_descricaoAlerta;
	}

	public String getAnm_tipoAlerta() {
		return anm_tipoAlerta;
	}

	public void setAnm_tipoAlerta(String anm_tipoAlerta) {
		this.anm_tipoAlerta = anm_tipoAlerta;
	}
	
	public ModeloAnamnese getModeloAnamnese() {
		return modeloAnamnese;
	}
	
	public void setModeloAnamnese(ModeloAnamnese modeloAnamnese) {
		this.modeloAnamnese = modeloAnamnese;
	}
	
	public Date getAnm_dataInativacao() {
		return anm_dataInativacao;
	}

	public void setAnm_dataInativacao(Date anm_dataInativacao) {
		this.anm_dataInativacao = anm_dataInativacao;
	}

	public String getAnm_status() {
		return anm_status;
	}

	public void setAnm_status(String anm_status) {
		this.anm_status = anm_status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anm_codigo == null) ? 0 : anm_codigo.hashCode());
		result = prime * result + ((anm_descricao == null) ? 0 : anm_descricao.hashCode());
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
		Anamnese other = (Anamnese) obj;
		if (anm_codigo == null) {
			if (other.anm_codigo != null)
				return false;
		} else if (!anm_codigo.equals(other.anm_codigo))
			return false;
		if (anm_descricao == null) {
			if (other.anm_descricao != null)
				return false;
		} else if (!anm_descricao.equals(other.anm_descricao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Anamnese [anm_codigo=" + anm_codigo + ", anm_descricao=" + anm_descricao + ", anm_alerta=" + anm_alerta
				+ ", anm_tipoPergunta=" + anm_tipoPergunta + ", anm_descricaoAuxiliar=" + anm_descricaoAuxiliar
				+ ", anm_descricaoAlerta=" + anm_descricaoAlerta + ", anm_tipoAlerta=" + anm_tipoAlerta + "]";
	}
	
}
