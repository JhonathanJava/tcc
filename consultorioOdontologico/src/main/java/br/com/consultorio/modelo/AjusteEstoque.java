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
public class AjusteEstoque implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eje_codigo;
	
	@JoinColumn(name = "est_codigo")
	@ManyToOne
	private Estoque estoque = new Estoque();
	
	@Column
	private Integer eje_quantidade = 0;
	
	@Column
	private Integer eje_quantidadeAntiga = 0;
	
	@JoinColumn(name = "usu_codigo")
	@ManyToOne
	private Usuario usuario = new Usuario();
	
	@Column
	private Date eje_data = new Date();
	
	@Column
	private String eje_status = "A";
	
	@Column
	private Date eje_dataInativado;
	
	public AjusteEstoque() {
		
	}

	public Long getEje_codigo() {
		return eje_codigo;
	}

	public void setEje_codigo(Long eje_codigo) {
		this.eje_codigo = eje_codigo;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Integer getEje_quantidade() {
		return eje_quantidade;
	}

	public void setEje_quantidade(Integer eje_quantidade) {
		this.eje_quantidade = eje_quantidade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getEje_data() {
		return eje_data;
	}

	public void setEje_data(Date eje_data) {
		this.eje_data = eje_data;
	}

	public String getEje_status() {
		return eje_status;
	}

	public void setEje_status(String eje_status) {
		this.eje_status = eje_status;
	}

	public Date getEje_dataInativado() {
		return eje_dataInativado;
	}

	public void setEje_dataInativado(Date eje_dataInativado) {
		this.eje_dataInativado = eje_dataInativado;
	}

	public Integer getEje_quantidadeAntiga() {
		return eje_quantidadeAntiga;
	}
	
	public void setEje_quantidadeAntiga(Integer eje_quantidadeAntiga) {
		this.eje_quantidadeAntiga = eje_quantidadeAntiga;
	}
	
	@Override
	public String toString() {
		return "AjusteEstoque [eje_codigo=" + eje_codigo + ", estoque=" + estoque + ", eje_quantidade=" + eje_quantidade
				+ ", usuario=" + usuario + ", eje_data=" + eje_data + ", eje_status=" + eje_status
				+ ", eje_dataInativado=" + eje_dataInativado + "]";
	}

}
