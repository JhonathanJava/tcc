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
public class Compra implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long com_codigo;
	
	@Column
	private BigDecimal com_frete; 
	
	@Column
	private BigDecimal com_desconto;
	
	@Column
	private BigDecimal com_valorTotal;
	
	@Column
	private String com_nf; 
	
	@Column
	private Date com_data = new Date(); // data de Entrada
	
	@Column
	private Date com_dataCompra = new Date(); // data de Compra
	
	@Column
	private Date com_dataVencimento = new Date(); // data de Pagamento da Compra
	
	@JoinColumn(name = "usu_codigo")
	@ManyToOne
	private Usuario usuario = new Usuario();
	
	@JoinColumn(name = "for_codigo")
	@ManyToOne
	private Fornecedor fornecedor = new Fornecedor();

	public Long getCom_codigo() {
		return com_codigo;
	}

	public void setCom_codigo(Long com_codigo) {
		this.com_codigo = com_codigo;
	}

	public BigDecimal getCom_frete() {
		return com_frete;
	}

	public void setCom_frete(BigDecimal com_frete) {
		this.com_frete = com_frete;
	}

	public BigDecimal getCom_desconto() {
		return com_desconto;
	}

	public void setCom_desconto(BigDecimal com_desconto) {
		this.com_desconto = com_desconto;
	}

	public BigDecimal getCom_valorTotal() {
		return com_valorTotal;
	}

	public void setCom_valorTotal(BigDecimal com_valorTotal) {
		this.com_valorTotal = com_valorTotal;
	}

	public String getCom_nf() {
		return com_nf;
	}

	public void setCom_nf(String com_nf) {
		this.com_nf = com_nf;
	}

	public Date getCom_data() {
		return com_data;
	}

	public void setCom_data(Date com_data) {
		this.com_data = com_data;
	}

	public Date getCom_dataCompra() {
		return com_dataCompra;
	}

	public void setCom_dataCompra(Date com_dataCompra) {
		this.com_dataCompra = com_dataCompra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public Date getCom_dataVencimento() {
		return com_dataVencimento;
	}
	
	public void setCom_dataVencimento(Date com_dataVencimento) {
		this.com_dataVencimento = com_dataVencimento;
	}

	@Override
	public String toString() {
		return "Compra [com_codigo=" + com_codigo + ", com_frete=" + com_frete + ", com_desconto=" + com_desconto
				+ ", com_valorTotal=" + com_valorTotal + ", com_nf=" + com_nf + ", com_data=" + com_data
				+ ", com_dataCompra=" + com_dataCompra + ", com_dataVencimento=" + com_dataVencimento + ", usuario="
				+ usuario + ", fornecedor=" + fornecedor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((com_codigo == null) ? 0 : com_codigo.hashCode());
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
		Compra other = (Compra) obj;
		if (com_codigo == null) {
			if (other.com_codigo != null)
				return false;
		} else if (!com_codigo.equals(other.com_codigo))
			return false;
		return true;
	}
	
	
}
