package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
public class AgendamentoTratamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agt_codigo;
	
	@ManyToOne
	@JoinColumn(name="age_codigo")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Agenda agendamento;
	
	@ManyToOne
	@JoinColumn(name="tra_codigo")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Tratamento tratamento;
	
	@Column
	private String agt_denteRegiao;
	
	@Column
	private Integer agt_quantidade;

	public AgendamentoTratamento() {
	
	}

	public Long getAgt_codigo() {
		return agt_codigo;
	}

	public void setAgt_codigo(Long agt_codigo) {
		this.agt_codigo = agt_codigo;
	}

	public Agenda getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agenda agendamento) {
		this.agendamento = agendamento;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}
	
	public String getAgt_denteRegiao() {
		return agt_denteRegiao;
	}
	
	public void setAgt_denteRegiao(String agt_denteRegiao) {
		this.agt_denteRegiao = agt_denteRegiao;
	}

	public Integer getAgt_quantidade() {
		return agt_quantidade;
	}
	
	public void setAgt_quantidade(Integer agt_quantidade) {
		this.agt_quantidade = agt_quantidade;
	}
}
