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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;

@Entity
public class Fornecedor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fun_codigo;
	
	@Column
	private String fun_nome;
	
	@Column
	private String fun_status;
	
	@Column
	private String fun_nomeFantasia;
	
	@Column
	private String fun_telefone;
	
	@Column
	private String fun_fax;
	
	@Column
	private String fun_endereco;
	
	@Column
	private String fun_bairro;
	
	@Column
	private String fun_cidade;
	
	@Column
	private String fun_estado;
	
	@Column
	private String fun_cep;
	
	@Column
	private String fun_cnpj;
	
	@Column
	private String fun_inscricaoEstadual;
	
	@Column
	private String fun_contato;
	
	@Column
	@URL
	private String fun_site;
	
	@Column
	@Email
	private String fun_email;
	
	@Column
	private String fun_obs;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Calendar fun_dataCadastro = Calendar.getInstance();
	
	public Fornecedor() {
	}

	public Long getFun_codigo() {
		return fun_codigo;
	}

	public void setFun_codigo(Long fun_codigo) {
		this.fun_codigo = fun_codigo;
	}

	public String getFun_nome() {
		return fun_nome;
	}

	public void setFun_nome(String fun_nome) {
		this.fun_nome = fun_nome;
	}

	public String getFun_status() {
		return fun_status;
	}

	public void setFun_status(String fun_status) {
		this.fun_status = fun_status;
	}

	public String getFun_telefone() {
		return fun_telefone;
	}

	public void setFun_telefone(String fun_telefone) {
		this.fun_telefone = fun_telefone;
	}

	public String getFun_fax() {
		return fun_fax;
	}

	public void setFun_fax(String fun_fax) {
		this.fun_fax = fun_fax;
	}

	public String getFun_endereco() {
		return fun_endereco;
	}

	public void setFun_endereco(String fun_endereco) {
		this.fun_endereco = fun_endereco;
	}

	public String getFun_bairro() {
		return fun_bairro;
	}

	public void setFun_bairro(String fun_bairro) {
		this.fun_bairro = fun_bairro;
	}

	public String getFun_cidade() {
		return fun_cidade;
	}

	public void setFun_cidade(String fun_cidade) {
		this.fun_cidade = fun_cidade;
	}

	public String getFun_estado() {
		return fun_estado;
	}

	public void setFun_estado(String fun_estado) {
		this.fun_estado = fun_estado;
	}

	public String getFun_cep() {
		return fun_cep;
	}

	public void setFun_cep(String fun_cep) {
		this.fun_cep = fun_cep;
	}

	public String getFun_cnpj() {
		return fun_cnpj;
	}

	public void setFun_cnpj(String fun_cnpj) {
		this.fun_cnpj = fun_cnpj;
	}

	public String getFun_inscricaoEstadual() {
		return fun_inscricaoEstadual;
	}

	public void setFun_inscricaoEstadual(String fun_inscricaoEstadual) {
		this.fun_inscricaoEstadual = fun_inscricaoEstadual;
	}

	public String getFun_contato() {
		return fun_contato;
	}

	public void setFun_contato(String fun_contato) {
		this.fun_contato = fun_contato;
	}

	public String getFun_site() {
		return fun_site;
	}

	public void setFun_site(String fun_site) {
		this.fun_site = fun_site;
	}

	public String getFun_email() {
		return fun_email;
	}

	public void setFun_email(String fun_email) {
		this.fun_email = fun_email;
	}

	public String getFun_obs() {
		return fun_obs;
	}

	public void setFun_obs(String fun_obs) {
		this.fun_obs = fun_obs;
	}

	public Calendar getFun_dataCadastro() {
		return fun_dataCadastro;
	}

	public void setFun_dataCadastro(Calendar fun_dataCadastro) {
		this.fun_dataCadastro = fun_dataCadastro;
	}

	public String getFun_nomeFantasia() {
		return fun_nomeFantasia;
	}
	public void setFun_nomeFantasia(String fun_nomeFantasia) {
		this.fun_nomeFantasia = fun_nomeFantasia;
	}
	
	@Override
	public String toString() {
		return "Funcionario [fun_codigo=" + fun_codigo + ", fun_nome=" + fun_nome + ", fun_status=" + fun_status
				+ ", fun_telefone=" + fun_telefone + ", fun_fax=" + fun_fax + ", fun_endereco=" + fun_endereco
				+ ", fun_bairro=" + fun_bairro + ", fun_cidade=" + fun_cidade + ", fun_estado=" + fun_estado
				+ ", fun_cep=" + fun_cep + ", fun_cnpj=" + fun_cnpj + ", fun_inscricaoEstadual=" + fun_inscricaoEstadual
				+ ", fun_contato=" + fun_contato + ", fun_site=" + fun_site + ", fun_email=" + fun_email + ", fun_obs="
				+ fun_obs + ", fun_dataCadastro=" + fun_dataCadastro + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fun_codigo == null) ? 0 : fun_codigo.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (fun_codigo == null) {
			if (other.fun_codigo != null)
				return false;
		} else if (!fun_codigo.equals(other.fun_codigo))
			return false;
		return true;
	}
	
}
