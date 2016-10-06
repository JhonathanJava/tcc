package br.com.consultorio.controller;

import java.io.Serializable;
import java.math.BigDecimal;
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

import br.com.consultorio.dao.CompraDAO;
import br.com.consultorio.dao.CondicaoPagamentoDAO;
import br.com.consultorio.dao.EstoqueDAO;
import br.com.consultorio.dao.EstoqueEntradaDAO;
import br.com.consultorio.dao.FormaPagamentoDAO;
import br.com.consultorio.dao.FornecedorDAO;
import br.com.consultorio.dao.TituloDAO;
import br.com.consultorio.modelo.Compra;
import br.com.consultorio.modelo.CondicaoPagamento;
import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.EstoqueEntrada;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.modelo.Fornecedor;
import br.com.consultorio.modelo.Titulo;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstoqueEntradaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Estoque estoque; // Produto
	
	private EstoqueEntrada estoqueEntrada; // Entrada do Produto
	
	private Compra compra; // Cabeça da Nota de Entrada
	
	private Fornecedor fornecedor;
	
	private Titulo titulo;
	
	@Inject
	private EstoqueDAO dao;
	
	@Inject
	private TituloDAO tituloDAO;
	
	@Inject
	private FornecedorDAO fornecedorDAO;
	
	@Inject
	private CondicaoPagamentoDAO condicaoDAO;
	
	@Inject
	private FormaPagamentoDAO formaPagamentoDAO;
	
	@Inject
	private EstoqueEntradaDAO entradaDAO;
	
	@Inject
	private CompraDAO compraDAO;
	
	private List<EstoqueEntrada> listaProduto;
	private List<Titulo> listaTitulo;
	private List<Compra> listaCompra;
	private List<Fornecedor> listaFornecedores;
	private List<Estoque> results = new ArrayList<Estoque>();
	private List<Fornecedor> resultsFornecedor = new ArrayList<Fornecedor>();
	private List<CondicaoPagamento> resultsPagamento = new ArrayList<CondicaoPagamento>();
	private List<FormaPagamento> resultsFormaPagamento = new ArrayList<FormaPagamento>();
	private String destinoSaida;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

	
	@PostConstruct
	 void init() {
		System.out.println("Init Paciente ----------------------------------------------------------------");
		this.estoque = new Estoque();
		this.fornecedor = new Fornecedor();
		this.compra = new Compra();
		this.estoqueEntrada = new EstoqueEntrada();
		this.listaFornecedores = fornecedorDAO.listaTodos();
		this.listaProduto =  new ArrayList<>();
		this.listaProduto.clear();
		this.listaCompra = compraDAO.listaTodos();
		this.listaTitulo = new ArrayList<>();
		this.titulo = new Titulo();
	}
	
	public void limpar(){
		init();
	}
	
	public Boolean validaFecharNota(){
		System.out.println(this.getCompra().getCom_valorTotal());
		if(this.getCompra().getCom_valorTotal() == null || this.getCompra().getCom_valorTotal().equals(BigDecimal.ZERO)){
			FacesUtil.addErrorMessage("Não Pode inserir uma Nota de Entrada com Valor 0,00");
			FacesContext.getCurrentInstance().validationFailed();
			return false;
		}
			return true;
	}
	
	@Transacional
	public void fecharNota(){
		if(validaFecharNota()){
			this.getCompra().setCom_status("A");
			this.getCompra().setUsuario(usuario);
			this.compraDAO.adiciona(this.getCompra());
			
			for (EstoqueEntrada p : listaProduto) {
				
				/* ATUALIZA A QUANTIDADE DE ESTOQUE DO PRODUTO */
				
				Estoque produto = dao.buscaPorId(p.getEstoque().getEst_codigo());
				produto.setEst_quantidade(produto.getEst_quantidade() + p.getEte_quantidade());
				dao.atualiza(produto);
				
				/* FAZ ENTRADA DO PRODUTO NO ESTOQUE */
				p.setEte_status("A");
				p.setCompra(this.getCompra());
				this.entradaDAO.adiciona(p);
			}
			
			for (Titulo t : listaTitulo) {
				t.setCompra(this.compra);
				System.out.println(t);
				tituloDAO.adiciona(t);
			}
			
			FacesUtil.addSuccessMessage("Entrada Efetuada com Sucesso!!");
			init();
		}
	}
	
	public void geraParcelas(){
		this.titulo.setTit_valor(getCompra().getCom_valorTotal());
		System.out.println(titulo);
		Integer numeroParcela = titulo.getCondicaoPagamento().getCon_numeroParcela();
		BigDecimal valorTemp = titulo.getTit_valor().divide(new BigDecimal(numeroParcela));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		for(int i = 1; i <= numeroParcela; i++){
			Titulo temp = new Titulo();
			temp.setCondicaoPagamento(titulo.getCondicaoPagamento());
			temp.setFormaPagamento(titulo.getFormaPagamento());
			temp.setFornecedor(compra.getFornecedor());
			temp.setTit_parcela(i);
			temp.setTit_tipo("D");
			temp.setTit_valor(valorTemp);
			temp.setUsuario(usuario);
			c.add(Calendar.DAY_OF_MONTH, (30*i));
			temp.setTit_vencimento(c.getTime());
			this.listaTitulo.add(temp);
		}
		
	}
	
	public void atualizaValorJuro(){
		this.getCompra().setCom_valorTotal(this.getCompra().getCom_valorTotal().add(this.getCompra().getCom_juros()));
	}
	
	public void atualizaValorFrete(){
		this.getCompra().setCom_valorTotal(this.getCompra().getCom_valorTotal().add(this.getCompra().getCom_frete()));
	}
	
	public void atualizaValorDesconto(){
		this.getCompra().setCom_valorTotal(this.getCompra().getCom_valorTotal().subtract(this.getCompra().getCom_desconto()));
	}
	
	public Boolean validaAdicionaProduto(){
		
		if(this.estoqueEntrada.getEte_valor() == null || this.estoqueEntrada.getEte_valor().equals(0)){
			FacesUtil.addErrorMessage("Não Pode inserir um Produto com Valor 0,00");
			return false;
		}
		
		if(this.estoqueEntrada.getEte_quantidade() == null || this.estoqueEntrada.getEte_quantidade() == 0){
			FacesUtil.addErrorMessage("Não Pode inserir um Produto com Quantidade 0");
			return false;
		}
		return true;
	}
	
	public void adicionaProduto(){
		if(validaAdicionaProduto()){
			this.estoqueEntrada.setEte_status("A");
			this.listaProduto.add(this.estoqueEntrada);
			this.estoqueEntrada = new EstoqueEntrada();
			calculaTotal();
		}
	}
	
	public void removeProduto(EstoqueEntrada e){
		System.out.println(e);
		this.listaProduto.remove(e);
		this.estoqueEntrada = new EstoqueEntrada();
		calculaTotal();
	}
	
	public void calculaTotal(){
		this.getCompra().setCom_valorTotal(BigDecimal.ZERO);
		for (EstoqueEntrada p : listaProduto) {
			BigDecimal multiplicado = p.getEte_valor().multiply(new BigDecimal(p.getEte_quantidade()));
			this.getCompra().setCom_valorTotal(this.getCompra().getCom_valorTotal().add(multiplicado));
		}

	}
	
	public void visualizarCompra(Compra compra){
		this.compra = compraDAO.buscaPorId(compra.getCom_codigo());
		this.listaProduto = entradaDAO.pesquisaEstoqueEntradaPorCompra(compra);
	}
	
	public List<Fornecedor> buscaFornecedor(String query){
		resultsFornecedor = fornecedorDAO.buscarPorNome(query);
        return resultsFornecedor;
	}
	
	public List<CondicaoPagamento> buscaCondicaoPagamento(String query){
		resultsPagamento = condicaoDAO.buscarCondicaoPorNomeAtivo(query);
        return resultsPagamento;
	}
	
	public List<FormaPagamento> buscaFormaPagamento(String query){
		resultsFormaPagamento = formaPagamentoDAO.buscarFormaPorNomeAtivo(query);
        return resultsFormaPagamento;
	}
	
	public List<Estoque> buscaProduto(String query){
        results = dao.buscarPorNome(query);
        return results;
	}
	
	@Transacional
	public void inativarCompra(){
		this.compra.setCom_status("I");
		this.compra.setCom_dataCancelamento(new Date());
		compraDAO.atualiza(this.compra);
		
		for (EstoqueEntrada entrada : listaProduto) {
			entrada.setEte_status("I");
			entradaDAO.atualiza(entrada);
		}
		
		FacesUtil.addSuccessMessage("Nota cancelada, e entrada dos produtos da nota retirado do estoque!");
	}
	
	public List<Titulo> getListaTitulo() {
		return listaTitulo;
	}
	
	public void setListaTitulo(List<Titulo> listaTitulo) {
		this.listaTitulo = listaTitulo;
	}
	
	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
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

	public List<Fornecedor> getResultsFornecedor() {
		return resultsFornecedor;
	}
	
	public void setResultsFornecedor(List<Fornecedor> resultsFornecedor) {
		this.resultsFornecedor = resultsFornecedor;
	}
	
	public List<Estoque> getResults() {
		return results;
	}
	
	public void setResults(List<Estoque> results) {
		this.results = results;
	}
	
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<EstoqueEntrada> getListaProduto() {
		return listaProduto;
	}
	
	public void setListaProduto(List<EstoqueEntrada> listaProduto) {
		this.listaProduto = listaProduto;
	}
	
	public Compra getCompra() {
		return compra;
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}
	
	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}
	
	public EstoqueEntrada getEstoqueEntrada() {
		return estoqueEntrada;
	}
	
	public void setEstoqueEntrada(EstoqueEntrada estoqueEntrada) {
		this.estoqueEntrada = estoqueEntrada;
	}
	
	public String getDestinoSaida() {
		return destinoSaida;
	}
	
	public void setDestinoSaida(String destinoSaida) {
		this.destinoSaida = destinoSaida;
	}
	
	public List<Compra> getListaCompra() {
		return listaCompra;
	}
	
	public void setListaCompra(List<Compra> listaCompra) {
		this.listaCompra = listaCompra;
	}
	
}
