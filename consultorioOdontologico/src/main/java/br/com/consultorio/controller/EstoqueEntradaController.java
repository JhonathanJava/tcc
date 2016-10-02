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
		this.listaCompra = compraDAO.listaTodos();
	}
	
	public void limpar(){
		init();
	}
	
	public void adicionaProduto(){
		this.estoqueEntrada.setEte_status("A");
		this.listaProduto.add(this.estoqueEntrada);
		this.getCompra().setCom_valorTotal(BigDecimal.ZERO);
		for (EstoqueEntrada p : listaProduto) {
			BigDecimal multiplicado = p.getEte_valor().multiply(new BigDecimal(p.getEte_quantidade()));
			this.getCompra().setCom_valorTotal(this.getCompra().getCom_valorTotal().add(multiplicado));
		}
		
		this.estoqueEntrada = new EstoqueEntrada();
	}
	
	public void removeProduto(EstoqueEntrada e){
		System.out.println(e);
		this.listaProduto.remove(e);
		this.estoqueEntrada = new EstoqueEntrada();
	}
	
	public void visualizarCompra(Compra compra){
		this.compra = compraDAO.buscaPorId(compra.getCom_codigo());
		this.listaProduto = entradaDAO.pesquisaEstoqueEntradaPorCompra(compra);
	}
	
	public List<Fornecedor> buscaFornecedor(String query){
        List<Fornecedor> results = fornecedorDAO.buscarPorNome(query);
        return results;
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
