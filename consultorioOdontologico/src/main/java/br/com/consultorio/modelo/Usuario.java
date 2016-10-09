package br.com.consultorio.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usu_codigo;
	
	@Column
	@Email(message = "E-mail Inválido informe no formato: teste@teste.com")
	private String usu_email;
	
	@Column
	private String usu_senha;
	
	@Column
	private String usu_status = "A";
	
	@Column
	private String usu_telefone;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date usu_dataCadastro = new Date();
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date usu_dataInativacao;
	
	@Column
	@Temporal(TemporalType.DATE)
	@Past(message = "A Data de Nascimento: não pode ser depois da data atual")
	private Date usu_dataNascimento;
	
	@Column
	private String usu_login;
	
	@Column
	private String usu_nome;
	
	@Column
	private String usu_cro;
	
	@Column
	@CPF(message = "CPF Inválido: Informe novamente")
	private String usu_cpf;
	
	@Column
	private String usu_ativo;
	
	@Column
	private String usu_celular;
	
	@Column 
	private String usu_endereco;
	
	@Column 
	private String usu_cidade;
	
	@Column
	private String usu_estado;
	
	@Column
	private String usu_complemento;
	
	@Column
	private String usu_numeroEndereco;
	
	@Column String usu_bairro;
	
	@Column String usu_profissional = "Não";
	
	@JoinColumn(name = "per_codigo")
	@ManyToOne
	private Perfil perfil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_codigo")
	private Cargo cargo;
	
	public Long getUsu_codigo() {
		return usu_codigo;
	}

	public void setUsu_codigo(Long usu_codigo) {
		this.usu_codigo = usu_codigo;
	}

	public String getUsu_email() {
		return usu_email;
	}

	public void setUsu_email(String usu_email) {
		this.usu_email = usu_email;
	}
	
	public String getUsu_senha() {
		return usu_senha;
	}

	public void setUsu_senha(String usu_senha) {
		this.usu_senha = usu_senha;
	}
	
	public String getUsu_status() {
		return usu_status;
	}

	public void setUsu_status(String usu_status) {
		this.usu_status = usu_status;
	}

	public String getUsu_telefone() {
		return usu_telefone;
	}

	public void setUsu_telefone(String usu_telefone) {
		this.usu_telefone = usu_telefone;
	}

	public Date getUsu_dataCadastro() {
		return usu_dataCadastro;
	}

	public void setUsu_dataCadastro(Date usu_dataCadastro) {
		this.usu_dataCadastro = usu_dataCadastro;
	}

	public String getUsu_login() {
		return usu_login;
	}

	public void setUsu_login(String usu_login) {
		this.usu_login = usu_login;
	}

	public String getUsu_nome() {
		return usu_nome;
	}

	public void setUsu_nome(String usu_nome) {
		this.usu_nome = usu_nome;
	}
	
	public Date getUsu_dataInativacao() {
		return usu_dataInativacao;
	}
	
	public void setUsu_dataInativacao(Date usu_dataInativacao) {
		this.usu_dataInativacao = usu_dataInativacao;
	}
	
	public Date getUsu_dataNascimento() {
		return usu_dataNascimento;
	}
	
	public void setUsu_dataNascimento(Date usu_dataNascimento) {
		this.usu_dataNascimento = usu_dataNascimento;
	}

	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public String getUsu_cpf() {
		return usu_cpf;
	}

	public void setUsu_cpf(String usu_cpf) {
		this.usu_cpf = usu_cpf;
	}

	public String getUsu_ativo() {
		return usu_ativo;
	}

	public void setUsu_ativo(String usu_ativo) {
		this.usu_ativo = usu_ativo;
	}

	public String getUsu_celular() {
		return usu_celular;
	}

	public void setUsu_celular(String usu_celular) {
		this.usu_celular = usu_celular;
	}

	public String getUsu_endereco() {
		return usu_endereco;
	}

	public void setUsu_endereco(String usu_endereco) {
		this.usu_endereco = usu_endereco;
	}

	public String getUsu_cidade() {
		return usu_cidade;
	}

	public void setUsu_cidade(String usu_cidade) {
		this.usu_cidade = usu_cidade;
	}

	public String getUsu_estado() {
		return usu_estado;
	}

	public void setUsu_estado(String usu_estado) {
		this.usu_estado = usu_estado;
	}

	public String getUsu_complemento() {
		return usu_complemento;
	}

	public void setUsu_complemento(String usu_complemento) {
		this.usu_complemento = usu_complemento;
	}

	public String getUsu_numeroEndereco() {
		return usu_numeroEndereco;
	}

	public Cargo getCargo() {
		return cargo;
	}
	
	public void setUsu_numeroEndereco(String usu_numeroEndereco) {
		this.usu_numeroEndereco = usu_numeroEndereco;
	}
	
	public String getUsu_cro() {
		return usu_cro;
	}
	
	public void setUsu_cro(String usu_cro) {
		this.usu_cro = usu_cro;
	}
	
	public String getUsu_bairro() {
		return usu_bairro;
	}
	
	public void setUsu_bairro(String usu_bairro) {
		this.usu_bairro = usu_bairro;
	}

	public String getUsu_profissional() {
		return usu_profissional;
	}
	
	public void setUsu_profissional(String usu_profissional) {
		this.usu_profissional = usu_profissional;
	}
	
	@Override
	public String toString() {
		return "Usuario [usu_codigo=" + usu_codigo + ", usu_email=" + usu_email + ", usu_senha=" + usu_senha
				+ ", usu_status=" + usu_status + ", usu_telefone=" + usu_telefone + ", usu_dataCadastro="
				+ usu_dataCadastro + ", usu_dataInativacao=" + usu_dataInativacao + ", usu_dataNascimento="
				+ usu_dataNascimento + ", usu_login=" + usu_login + ", usu_nome=" + usu_nome + ", usu_cro=" + usu_cro
				+ ", usu_cpf=" + usu_cpf + ", usu_ativo=" + usu_ativo + ", usu_celular=" + usu_celular
				+ ", usu_endereco=" + usu_endereco + ", usu_cidade=" + usu_cidade + ", usu_estado=" + usu_estado
				+ ", usu_complemento=" + usu_complemento + ", usu_numeroEndereco=" + usu_numeroEndereco + ", perfil="
				+ perfil + ", cargo=" + cargo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usu_codigo == null) ? 0 : usu_codigo.hashCode());
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
		Usuario other = (Usuario) obj;
		if (usu_codigo == null) {
			if (other.usu_codigo != null)
				return false;
		} else if (!usu_codigo.equals(other.usu_codigo))
			return false;
		return true;
	}
	
	
}
