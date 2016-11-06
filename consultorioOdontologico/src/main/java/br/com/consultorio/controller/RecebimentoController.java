package br.com.consultorio.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.CaixaPagamentoDAO;
import br.com.consultorio.dao.CondicaoPagamentoDAO;
import br.com.consultorio.dao.FormaPagamentoDAO;
import br.com.consultorio.dao.FornecedorDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.TituloDAO;
import br.com.consultorio.modelo.CaixaPagamento;
import br.com.consultorio.modelo.CondicaoPagamento;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.modelo.Fornecedor;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.Titulo;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RecebimentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Titulo titulo;
	
	private CaixaPagamento caixaPagamento;
	
	@Inject
	private TituloDAO dao;
	
	@Inject
	private FornecedorDAO fornecedorDAO;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Inject
	private CondicaoPagamentoDAO condicaoDAO;
	
	@Inject
	private FormaPagamentoDAO formaPagamentoDAO;
	
	@Inject
	private CaixaPagamentoDAO pagamentoDAO;
	
	private List<Titulo> titulos;
	private List<CaixaPagamento> pagamentos;
	private List<Titulo> parcelas;
	private List<Fornecedor> resultsFornecedor = new ArrayList<Fornecedor>();
	private List<Paciente> resultsPaciente = new ArrayList<Paciente>();
	private List<CondicaoPagamento> resultsPagamento = new ArrayList<CondicaoPagamento>();
	private List<FormaPagamento> resultsFormaPagamento = new ArrayList<FormaPagamento>();
	
	private String txtTipoFavorecido = "O";
	private BigDecimal txtValorTitulo = BigDecimal.ZERO;
	private BigDecimal txtValorTotal = BigDecimal.ZERO;
	private BigDecimal txtValorRestante = BigDecimal.ZERO;
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	
	@PostConstruct
	 void init() {
		this.titulo = new Titulo();
		this.titulos = dao.buscarTitulo("C");
		this.parcelas = new ArrayList<>();
		this.caixaPagamento = new CaixaPagamento();
		this.pagamentos = new ArrayList<>();
	}
	
	public void limpar(){
		this.titulo = new Titulo();
		this.parcelas.clear();
	}
	
	public void carregaPeloId(Titulo t){
		this.titulo = this.dao.buscaPorId(t.getTit_codigo());
		this.txtValorTotal = this.titulo.getTit_valor();
		this.txtValorRestante = this.titulo.getTit_valor();
	}
	
	@Transacional
	public void inativarSelecionados(){
			if(titulo.getTit_status().equals("Inativo")){
				titulo.setTit_status("Aguardando");
			}else{
				titulo.setTit_dtInativacao(Calendar.getInstance());
				titulo.setTit_status("Inativo");
			}
			this.dao.atualiza(titulo);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	public void adicionaPagamento(){
		this.txtValorRestante = this.txtValorRestante.subtract(caixaPagamento.getCap_valor());
		pagamentos.add(caixaPagamento);
		this.caixaPagamento = new CaixaPagamento();
	}
	
	public void removerPagamento(CaixaPagamento c){
		this.txtValorRestante = this.getTxtValorRestante().add(c.getCap_valor());
		pagamentos.remove(c);
		this.caixaPagamento = new CaixaPagamento();
	}
	
	public void calcular(){
		this.txtValorTotal = this.titulo.getTit_valor().add(caixaPagamento.getCap_juro()).subtract(caixaPagamento.getCap_desconto());
		this.txtValorRestante = this.titulo.getTit_valor().add(caixaPagamento.getCap_juro()).subtract(caixaPagamento.getCap_desconto());
	}
	
	public Boolean validaPagamento(){
		if(this.getTxtValorRestante().compareTo(BigDecimal.ZERO) != 0){
			FacesUtil.addErrorMessage("O Valor do Título deve ser Pago o Total resta ainda: "+this.getTxtValorRestante());
			return false;
		}
		return true;
	}
	
	@Transacional
	public void pagar(){
		if(validaPagamento()){
			this.titulo.setTit_pago("S");
			this.titulo.setTit_status("Pago");
			this.titulo.setTit_pagamento(new Date());
			dao.atualiza(this.titulo);
			
			for (CaixaPagamento caixaPagamento : pagamentos) {
				caixaPagamento.setTitulo(titulo);
				pagamentoDAO.adiciona(caixaPagamento);
			}
			FacesUtil.addSuccessMessage("Título Pago com Sucesso!");
		}
		init();
	}
	
	@Transacional
	public void editar(){
		this.titulo.setTit_status("Aguardando");
		dao.atualiza(this.titulo);
		this.titulo = new Titulo();
		init();
	}
	
	@Transacional
	public String gravar() {
		if(validaGravar()){
			for (Titulo titulo : parcelas) {
				dao.adiciona(titulo);
			}
			this.titulo = new Titulo();
			this.parcelas.clear();
			init();
		}
		return null;
	}
	
	public Boolean valida(){
		if(this.titulo.getPaciente() != null && this.titulo.getPaciente().getPac_codigo() > 0){
			this.titulo.setTit_favorecido(this.titulo.getPaciente().getPac_nome());
		}
		
		if(this.titulo.getFornecedor() != null && this.titulo.getFornecedor().getFun_codigo() > 0){
			this.titulo.setTit_favorecido(this.titulo.getFornecedor().getFun_nome());
		}
		
		if(this.titulo.getTit_favorecido() != null && this.titulo.getTit_favorecido().equals("")){
			FacesUtil.addErrorMessage("Digite o nome do Favorecido!!");
			return false;
		}
		
		if(this.titulo.getTit_valor() == null || this.titulo.getTit_valor().equals(BigDecimal.ZERO)){
			FacesUtil.addErrorMessage("Digite o valor do Título");
			return false;
		}
		
		if(this.titulo.getTit_vencimento() == null){
			FacesUtil.addErrorMessage("Digite a data de Vencimento");
			return false;
		}
		
		if(this.titulo.getCondicaoPagamento() == null){
			FacesUtil.addErrorMessage("Digite a Condição de Pagamento");
			return false;
		}
		
		if(this.titulo.getFormaPagamento() == null){
			FacesUtil.addErrorMessage("Digite a Forma de Pagamento");
			return false;
		}
		
		return true;
	}
	
	public void geraTitulo(){
		if(valida()){
			this.parcelas.clear();
			Integer numeroParcela = titulo.getCondicaoPagamento().getCon_numeroParcela();
			BigDecimal t = titulo.getTit_valor().add(titulo.getTit_juros()).subtract(titulo.getTit_desconto());
			BigDecimal valorTemp = t.divide(new BigDecimal(numeroParcela),RoundingMode.HALF_UP);
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
			BigDecimal tempX = t;
			for(int i = 1; i <= numeroParcela; i++){
				Titulo temp = new Titulo();
				temp.setCondicaoPagamento(titulo.getCondicaoPagamento());
				temp.setFormaPagamento(titulo.getFormaPagamento());
				temp.setTit_parcela(i);
				temp.setTit_favorecido(titulo.getTit_favorecido());
				temp.setTit_tipo("C");
				temp.setTit_status("Aguardando");
				tempX = tempX.subtract(valorTemp);
				if(i+1 == numeroParcela){
					temp.setTit_valor(tempX);
				}else{
					temp.setTit_valor(valorTemp);
				}
				temp.setUsuario(usuario);
				c.setTime(titulo.getTit_vencimento());
				c.add(Calendar.DAY_OF_MONTH, (i*30));
				temp.setTit_vencimento(c.getTime());
				this.parcelas.add(temp);
			}
		}
	}
	
	public Boolean validaGravar(){
		BigDecimal valorTemp = this.titulo.getTit_valor().add(this.titulo.getTit_juros()).subtract(this.getTitulo().getTit_desconto());
		if(this.parcelas == null || this.parcelas.size() == 0){
			FacesUtil.addErrorMessage("Você deve primeiro Gerar as parcelas !");
			FacesContext.getCurrentInstance().validationFailed();
			return false;
		}
		
		for (Titulo a : parcelas) {
			if(a.getTit_vencimento() == null){
				FacesUtil.addErrorMessage("Você deve Informar a data de vencimento para todos os titulos !");
				FacesContext.getCurrentInstance().validationFailed();
				return false;
			}
			valorTemp = valorTemp.subtract(a.getTit_valor());
			System.out.println(valorTemp);
		}
		
		if(!valorTemp.equals(BigDecimal.ZERO)){
			FacesUtil.addErrorMessage("A Soma das Parcelas deve ter o Valor R$"+this.titulo.getTit_valor().add(this.titulo.getTit_juros()).subtract(this.getTitulo().getTit_desconto()));
			FacesContext.getCurrentInstance().validationFailed();
			return false;
		}
		
		return true;
	}
	
	public List<CondicaoPagamento> buscaCondicaoPagamento(String query){
		resultsPagamento = condicaoDAO.buscarCondicaoPorNomeAtivo(query);
        return resultsPagamento;
	}
	
	public List<FormaPagamento> buscaFormaPagamento(String query){
		resultsFormaPagamento = formaPagamentoDAO.buscarFormaPorNomeAtivo(query);
        return resultsFormaPagamento;
	}
	
	public List<Fornecedor> buscaFornecedor(String query){
		resultsFornecedor = fornecedorDAO.buscarPorNome(query);
        return resultsFornecedor;
	}
	
	public List<Paciente> buscaPaciente(String query){
		resultsPaciente = pacienteDAO.buscarPorNome(query);
        return resultsPaciente;
	}
	
	public void limparTitulo(){
		this.titulo = new Titulo();
	}
	
	public List<Titulo> getParcelas() {
		return parcelas;
	}
	
	public void setParcelas(List<Titulo> parcelas) {
		this.parcelas = parcelas;
	}

	public List<Fornecedor> getResultsFornecedor() {
		return resultsFornecedor;
	}

	public void setResultsFornecedor(List<Fornecedor> resultsFornecedor) {
		this.resultsFornecedor = resultsFornecedor;
	}

	public List<Paciente> getResultsPaciente() {
		return resultsPaciente;
	}

	public void setResultsPaciente(List<Paciente> resultsPaciente) {
		this.resultsPaciente = resultsPaciente;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public String getTxtTipoFavorecido() {
		return txtTipoFavorecido;
	}
	
	public void setTxtTipoFavorecido(String txtTipoFavorecido) {
		this.txtTipoFavorecido = txtTipoFavorecido;
	}

	public List<CondicaoPagamento> getResultsPagamento() {
		return resultsPagamento;
	}

	public void setResultsPagamento(List<CondicaoPagamento> resultsPagamento) {
		this.resultsPagamento = resultsPagamento;
	}

	public List<FormaPagamento> getResultsFormaPagamento() {
		return resultsFormaPagamento;
	}

	public void setResultsFormaPagamento(List<FormaPagamento> resultsFormaPagamento) {
		this.resultsFormaPagamento = resultsFormaPagamento;
	}
	
	public BigDecimal getTxtValorTitulo() {
		return txtValorTitulo;
	}
	
	public void setTxtValorTitulo(BigDecimal txtValorTitulo) {
		this.txtValorTitulo = txtValorTitulo;
	}
	
	public BigDecimal getTxtValorRestante() {
		return txtValorRestante;
	}
	
	public void setTxtValorRestante(BigDecimal txtValorRestante) {
		this.txtValorRestante = txtValorRestante;
	}
	
	public BigDecimal getTxtValorTotal() {
		return txtValorTotal;
	}
	
	public void setTxtValorTotal(BigDecimal txtValorTotal) {
		this.txtValorTotal = txtValorTotal;
	}

	public CaixaPagamento getCaixaPagamento() {
		return caixaPagamento;
	}

	public void setCaixaPagamento(CaixaPagamento caixaPagamento) {
		this.caixaPagamento = caixaPagamento;
	}

	public List<CaixaPagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<CaixaPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}
	
}
