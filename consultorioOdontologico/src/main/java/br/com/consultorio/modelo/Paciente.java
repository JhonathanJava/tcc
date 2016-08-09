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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Paciente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pac_codigo;
	
	@Column
	private String pac_nome;
	
	@Column
	private String pac_telefone;
	
	@Column
	private String pac_celular;
	
	@Column
	private String pac_rg;
	
	@Column
	@CPF(message = "CPF Inválido: Informe novamente")
	private String pac_cpf;
	
	@Column
	private Date pac_dataNascimento;
	
	@Column
	private Date pac_dataNascimentoResponsavel;
	
	@Column
	@Email(message="Digite um e-mail válido no formato: email@email.com")
	private String pac_email;
	
	@Column
	private String pac_endereco;
	
	@Column
	private String pac_numero;

	@Column
	private String pac_cidade;
	
	@Column
	private String pac_bairro;
	
	@Column
	private Integer pac_cep;
	
	@Column 
	private String pac_obs;
	
	@Column
	private String pac_sexo;
	
	@Column
	private String pac_estado;
	
	@Column
	private String pac_indicacao;
	
	@Column
	private Integer pac_prontuario;
	
	@Column
	private String pac_nomeResponsavel;
	
	@Column
	private String pac_cpfResponsavel;
	
	@Column
	private Character pac_status;
	
	@JoinColumn(name = "pla_codigo")
	@ManyToOne
	private Plano plano = new Plano();
	
	public Long getPac_codigo() {
		return pac_codigo;
	}

	public void setPac_codigo(Long pac_codigo) {
		this.pac_codigo = pac_codigo;
	}

	public String getPac_nome() {
		return pac_nome;
	}

	public void setPac_nome(String pac_nome) {
		this.pac_nome = pac_nome;
	}

	public String getPac_telefone() {
		return pac_telefone;
	}

	public void setPac_telefone(String pac_telefone) {
		this.pac_telefone = pac_telefone;
	}

	public String getPac_celular() {
		return pac_celular;
	}

	public void setPac_celular(String pac_celular) {
		this.pac_celular = pac_celular;
	}

	public String getPac_rg() {
		return pac_rg;
	}

	public void setPac_rg(String pac_rg) {
		this.pac_rg = pac_rg;
	}

	public String getPac_cpf() {
		return pac_cpf;
	}

	public void setPac_cpf(String pac_cpf) {
		this.pac_cpf = pac_cpf;
	}

	public Date getPac_dataNascimento() {
		return pac_dataNascimento;
	}

	public void setPac_dataNascimento(Date pac_dataNascimento) {
		this.pac_dataNascimento = pac_dataNascimento;
	}

	public String getPac_email() {
		return pac_email;
	}

	public void setPac_email(String pac_email) {
		this.pac_email = pac_email;
	}

	public String getPac_endereco() {
		return pac_endereco;
	}

	public void setPac_endereco(String pac_endereco) {
		this.pac_endereco = pac_endereco;
	}

	public String getPac_numero() {
		return pac_numero;
	}

	public void setPac_numero(String pac_numero) {
		this.pac_numero = pac_numero;
	}

	public String getPac_cidade() {
		return pac_cidade;
	}

	public void setPac_cidade(String pac_cidade) {
		this.pac_cidade = pac_cidade;
	}

	public String getPac_bairro() {
		return pac_bairro;
	}

	public void setPac_bairro(String pac_bairro) {
		this.pac_bairro = pac_bairro;
	}

	public Integer getPac_cep() {
		return pac_cep;
	}

	public void setPac_cep(Integer pac_cep) {
		this.pac_cep = pac_cep;
	}

	public String getPac_obs() {
		return pac_obs;
	}

	public void setPac_obs(String pac_obs) {
		this.pac_obs = pac_obs;
	}

	public String getPac_sexo() {
		return pac_sexo;
	}

	public void setPac_sexo(String pac_sexo) {
		this.pac_sexo = pac_sexo;
	}

	public String getPac_estado() {
		return pac_estado;
	}

	public void setPac_estado(String pac_estado) {
		this.pac_estado = pac_estado;
	}

	public String getPac_indicacao() {
		return pac_indicacao;
	}

	public void setPac_indicacao(String pac_indicacao) {
		this.pac_indicacao = pac_indicacao;
	}

	public Integer getPac_prontuario() {
		return pac_prontuario;
	}

	public void setPac_prontuario(Integer pac_prontuario) {
		this.pac_prontuario = pac_prontuario;
	}

	public String getPac_nomeResponsavel() {
		return pac_nomeResponsavel;
	}

	public void setPac_nomeResponsavel(String pac_nomeResponsavel) {
		this.pac_nomeResponsavel = pac_nomeResponsavel;
	}

	public String getPac_cpfResponsavel() {
		return pac_cpfResponsavel;
	}

	public void setPac_cpfResponsavel(String pac_cpfResponsavel) {
		this.pac_cpfResponsavel = pac_cpfResponsavel;
	}

	public Character getPac_status() {
		return pac_status;
	}

	public void setPac_status(Character pac_status) {
		this.pac_status = pac_status;
	}

	public Date getPac_dataNascimentoResponsavel() {
		return pac_dataNascimentoResponsavel;
	}
	
	public void setPac_dataNascimentoResponsavel(Date pac_dataNascimentoResponsavel) {
		this.pac_dataNascimentoResponsavel = pac_dataNascimentoResponsavel;
	}
	
	public Plano getPlano() {
		return plano;
	}
	
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pac_codigo == null) ? 0 : pac_codigo.hashCode());
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
		Paciente other = (Paciente) obj;
		if (pac_codigo == null) {
			if (other.pac_codigo != null)
				return false;
		} else if (!pac_codigo.equals(other.pac_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Paciente [pac_codigo=" + pac_codigo + ", pac_nome=" + pac_nome + ", pac_telefone=" + pac_telefone
				+ ", pac_celular=" + pac_celular + ", pac_rg=" + pac_rg + ", pac_cpf=" + pac_cpf
				+ ", pac_dataNascimento=" + pac_dataNascimento + ", pac_dataNascimentoResponsavel="
				+ pac_dataNascimentoResponsavel + ", pac_email=" + pac_email + ", pac_endereco=" + pac_endereco
				+ ", pac_numero=" + pac_numero + ", pac_cidade=" + pac_cidade + ", pac_bairro=" + pac_bairro
				+ ", pac_cep=" + pac_cep + ", pac_obs=" + pac_obs + ", pac_sexo=" + pac_sexo + ", pac_estado="
				+ pac_estado + ", pac_indicacao=" + pac_indicacao + ", pac_prontuario=" + pac_prontuario
				+ ", pac_nomeResponsavel=" + pac_nomeResponsavel + ", pac_cpfResponsavel=" + pac_cpfResponsavel
				+ ", pac_status=" + pac_status + ", plano=" + plano + "]";
	}

}