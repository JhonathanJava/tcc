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

@Entity
public class EstoqueSaida implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ets_codigo;
	
	@Column
	private Integer ets_quantidade;
	
	@JoinColumn(name = "des_codigo")
	@ManyToOne
	private DestinoProdutoSaida destinoProdutoSaida = new DestinoProdutoSaida();
	
	@Column
	private String ets_status;
	
	@Column
	private Date ets_data = new Date();
	
	@Column
	private Date ets_dataInativacao;
	
	@Column
	private BigDecimal ets_valorVenda;

	@JoinColumn(name = "usu_codigo")
	@ManyToOne
	private Usuario usuario = new Usuario();
	
	@JoinColumn(name = "est_codigo")
	@ManyToOne
	private Estoque estoque = new Estoque();
	
	
	public EstoqueSaida() {
		
	}


	public Long getEts_codigo() {
		return ets_codigo;
	}


	public void setEts_codigo(Long ets_codigo) {
		this.ets_codigo = ets_codigo;
	}


	public Integer getEts_quantidade() {
		return ets_quantidade;
	}


	public void setEts_quantidade(Integer ets_quantidade) {
		this.ets_quantidade = ets_quantidade;
	}

	public String getEts_status() {
		return ets_status;
	}
	
	public void setEts_status(String ets_status) {
		this.ets_status = ets_status;
	}

	public Date getEts_data() {
		return ets_data;
	}


	public void setEts_data(Date ets_data) {
		this.ets_data = ets_data;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Estoque getEstoque() {
		return estoque;
	}


	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public DestinoProdutoSaida getDestinoProdutoSaida() {
		return destinoProdutoSaida;
	}
	
	public void setDestinoProdutoSaida(DestinoProdutoSaida destinoProdutoSaida) {
		this.destinoProdutoSaida = destinoProdutoSaida;
	}
	
	public BigDecimal getEts_valorVenda() {
		return ets_valorVenda;
	}
	
	public void setEts_valorVenda(BigDecimal ets_valorVenda) {
		this.ets_valorVenda = ets_valorVenda;
	}
	
	public Date getEts_dataInativacao() {
		return ets_dataInativacao;
	}
	
	public void setEts_dataInativacao(Date ets_dataInativacao) {
		this.ets_dataInativacao = ets_dataInativacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = prime * result + ((ets_quantidade == null) ? 0 : ets_quantidade.hashCode());
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
		EstoqueSaida other = (EstoqueSaida) obj;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (ets_quantidade == null) {
			if (other.ets_quantidade != null)
				return false;
		} else if (!ets_quantidade.equals(other.ets_quantidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EstoqueSaida [ets_codigo=" + ets_codigo + ", ets_quantidade=" + ets_quantidade + ", ets_data="
				+ ets_data + ", usuario=" + usuario + ", estoque=" + estoque + "]";
	}

}
