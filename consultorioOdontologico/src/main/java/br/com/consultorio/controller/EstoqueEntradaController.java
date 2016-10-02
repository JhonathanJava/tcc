package br.com.consultorio.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.consultorio.dao.CompraDAO;
import br.com.consultorio.dao.EstoqueDAO;
import br.com.consultorio.dao.EstoqueEntradaDAO;
import br.com.consultorio.dao.FornecedorDAO;
import br.com.consultorio.modelo.Compra;
import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.EstoqueEntrada;
import br.com.consultorio.modelo.Fornecedor;
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
	
	@Inject
	private EstoqueDAO dao;
	
	@Inject
	private FornecedorDAO fornecedorDAO;
	
	@Inject
	private EstoqueEntradaDAO entradaDAO;
	
	@Inject
	private CompraDAO compraDAO;
	
	private List<EstoqueEntrada> listaProduto;
	private List<Compra> listaCompra;
	private List<Fornecedor> listaFornecedores;
	private List<Estoque> results = new ArrayList<Estoque>();
	private List<Fornecedor> resultsFornecedor = new ArrayList<Fornecedor>();
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
				p.setEte_status("A");
				p.setCompra(this.getCompra());
				this.entradaDAO.adiciona(p);
			}
			
			FacesUtil.addSuccessMessage("Entrada Efetuada com Sucesso!!");
			init();
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
