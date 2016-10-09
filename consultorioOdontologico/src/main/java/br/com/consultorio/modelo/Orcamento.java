package br.com.consultorio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Orcamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orc_codigo;
	
	@Column
	private Date orc_data = new Date();
	
	@Column
	private String orc_observacao;
	
	@Column
	private String orc_status = "Aguardando"; // AG - Aguardando, RE - Reprovado , AP - Aprovado
	
	@Column
	private BigDecimal orc_desconto  = BigDecimal.ZERO;
	
	@Column 
	private BigDecimal orc_total  = BigDecimal.ZERO;
	
	@ManyToOne
	@JoinColumn(name="cax_codigo")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Caixa caixa;
	
	@ManyToOne
	@JoinColumn(name="plp_codigo", nullable = true)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private PlanoPai plano = new PlanoPai();
	
	@ManyToOne
	@JoinColumn(name="usu_codigo")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Usuario usuario = new Usuario(); // Profissional do Atendimento
	
	@ManyToOne
	@JoinColumn(name="pac_codigo")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Paciente paciente = new Paciente();
	
	@ManyToOne
	@JoinColumn(name="con_codigo", nullable = true)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
	
	@ManyToOne
	@JoinColumn(name="fop_codigo", nullable = true)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private FormaPagamento formaPagamento = new FormaPagamento();
	
	@Column
	private Date orc_dataAnalise;
	
	private BigDecimal valorDesconto;
	
	public Long getOrc_codigo() {
		return orc_codigo;
	}

	public void setOrc_codigo(Long orc_codigo) {
		this.orc_codigo = orc_codigo;
	}

	public Date getOrc_data() {
		return orc_data;
	}

	public void setOrc_data(Date orc_data) {
		this.orc_data = orc_data;
	}

	public String getOrc_observacao() {
		return orc_observacao;
	}

	public void setOrc_observacao(String orc_observacao) {
		this.orc_observacao = orc_observacao;
	}

	public String getOrc_status() {
		return orc_status;
	}

	public void setOrc_status(String orc_status) {
		this.orc_status = orc_status;
	}

	public BigDecimal getOrc_desconto() {
		return orc_desconto;
	}

	public void setOrc_desconto(BigDecimal orc_desconto) {
		this.orc_desconto = orc_desconto;
	}

	public BigDecimal getOrc_total() {
		return orc_total;
	}

	public void setOrc_total(BigDecimal orc_total) {
		this.orc_total = orc_total;
	}
	
	public Caixa getCaixa() {
		return caixa;
	}
	
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Date getOrc_dataAnalise() {
		return orc_dataAnalise;
	}
	
	public void setOrc_dataAnalise(Date orc_dataAnalise) {
		this.orc_dataAnalise = orc_dataAnalise;
	}
	
	public Paciente getPaciente() {
		return paciente;
	}
	
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public PlanoPai getPlano() {
		return plano;
	}
	
	public void setPlano(PlanoPai plano) {
		this.plano = plano;
	}

	@Override
	public String toString() {
		return "Orcamento [orc_codigo=" + orc_codigo + ", orc_data=" + orc_data + ", orc_observacao=" + orc_observacao
				+ ", orc_status=" + orc_status + ", orc_desconto=" + orc_desconto + ", orc_total=" + orc_total
				+ ", caixa=" + caixa + ", usuario=" + usuario + ", paciente=" + paciente + ", condicaoPagamento="
				+ condicaoPagamento + ", formaPagamento=" + formaPagamento + ", orc_dataAnalise=" + orc_dataAnalise
				+ ", valorDesconto=" + valorDesconto + "]";
	}

}
