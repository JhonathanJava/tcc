package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.CompraDAO;
import br.com.consultorio.dao.EstoqueDAO;
import br.com.consultorio.dao.EstoqueEntradaDAO;
import br.com.consultorio.dao.EstoqueSaidaDAO;
import br.com.consultorio.dao.FornecedorDAO;
import br.com.consultorio.modelo.Compra;
import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.EstoqueEntrada;
import br.com.consultorio.modelo.EstoqueSaida;
import br.com.consultorio.modelo.Fornecedor;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstoqueController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer countNegativo;
	
	private Estoque estoque;
	
	private EstoqueEntrada estoqueEntrada;
	
	private EstoqueSaida estoqueSaida;
	
	private Compra compra;
	
	private Fornecedor fornecedor;
	
	private Estoque estoqueEditar;
	
	@Inject
	private Estoque estoqueFiltro;
	
	@Inject
	private EstoqueDAO dao;
	
	@Inject
	private EstoqueSaidaDAO saidaDAO;
	
	@Inject
	private FornecedorDAO fornecedorDAO;
	
	@Inject
	private EstoqueEntradaDAO entradaDAO;
	
	@Inject
	private CompraDAO compraDAO;
	
	private List<Estoque> listaEstoque;
	private List<Estoque> listaEstoqueNegativo;
	private List<Estoque> listaAdicionaProduto;
	private List<EstoqueSaida> listaEstoqueSaida;
	private List<EstoqueEntrada> listaEstoqueEntrada;
	private List<EstoqueEntrada> listaEstoqueEntradaNovo;
	private List<EstoqueSaida> listaEstoqueSaidaNovo;
	private List<Fornecedor> listaFornecedores;
	
	private String destinoSaida;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

	
	@PostConstruct
	 void init() {
		System.out.println("Init Paciente ----------------------------------------------------------------");
		this.estoque = new Estoque();
		this.fornecedor = new Fornecedor();
		this.estoqueEditar = new Estoque();
		this.compra = new Compra();
		this.estoqueEntrada = new EstoqueEntrada();
		this.estoqueSaida = new EstoqueSaida();
		this.listaEstoque =  dao.listaTodos();
		this.listaFornecedores = fornecedorDAO.listaTodos();
		this.listaAdicionaProduto =  new ArrayList<>();
		this.listaEstoqueEntradaNovo = new ArrayList<>();
		this.listaEstoqueSaidaNovo = new ArrayList<>();
		this.listaEstoqueSaida =  new ArrayList<>();
		this.listaEstoqueNegativo =  new ArrayList<>();
		this.countNegativo = 0;
		verificaEstoque();
	}
	
	public void verificaEstoque(){
		for (Estoque estoque : listaEstoque) {
			if(estoque.getEst_quantidade() < estoque.getEst_quantidadeMinima()){
				countNegativo++;
				listaEstoqueNegativo.add(estoque);
			}
		}
		
	}
	
	public void limpar(){
		init();
	}
	
	@Transacional
	public String salvarProdutoNovo(){
		for (Estoque estoque : listaAdicionaProduto) {
			this.dao.adiciona(estoque);
		}
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.estoque = new Estoque();
		init();
		return null;
	}
	
	@Transacional
	public String salvaEntrada(){
		compraDAO.adiciona(compra);
		for (EstoqueEntrada entrada : listaEstoqueEntradaNovo) {
			Estoque temp = dao.buscaPorId(entrada.getEstoque().getEst_codigo());
			temp.setEst_quantidade(temp.getEst_quantidade() + entrada.getEte_quantidade());
			dao.atualiza(temp);
			entrada.setCompra(compra);
			this.entradaDAO.adiciona(entrada);
		}
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.estoqueEntrada = new EstoqueEntrada();
		init();
		return null;
	}
	
	@Transacional
	public String editar(){
		compra.setFornecedor(fornecedor);
		for (Estoque estoque : listaAdicionaProduto) {
			estoqueEntrada = new EstoqueEntrada();
			estoqueEntrada.setCompra(compra);
			estoqueEntrada.setEstoque(estoque);
			this.entradaDAO.adiciona(estoqueEntrada);
		}
		FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		this.estoque = new Estoque();
		init();
		return null;
	}
	
	@Transacional
	public String removerEstoque(){
		for (EstoqueSaida estoque : listaEstoqueSaidaNovo) {
			Estoque temp = dao.buscaPorId(estoque.getEstoque().getEst_codigo());
			temp.setEst_quantidade(temp.getEst_quantidade() - estoque.getEts_quantidade());
			this.dao.atualiza(temp);
			this.saidaDAO.adiciona(estoque);
		}
		FacesUtil.addSuccessMessage("Produtos Removido Com Sucesso!!");
		this.estoque = new Estoque();
		init();
		return null;
	}
	
	@Transacional
	public void excluirRegistro(){
		System.out.println("Estoque: "+estoque.toString());
		this.dao.remove(estoque);
		listaTodos();
		this.estoque = new Estoque();
		FacesUtil.addSuccessMessage("Excluído Com Sucesso!!");
	}
	
	public void adicionaProdutoNovo(){
		listaAdicionaProduto.add(estoque);
		estoque = new Estoque();
	}
	
	@Transacional
	public void adicionaProduto(){
		compra.setUsuario(usuario);
		compra.setFornecedor(fornecedor);
		estoqueEntrada.setEstoque(estoque);
		listaEstoqueEntradaNovo.add(estoqueEntrada);
		estoqueEntrada = new EstoqueEntrada();
	}
	
	public void removeProduto(){
		estoqueSaida.setUsuario(usuario);
		listaEstoqueSaidaNovo.add(estoqueSaida);
		estoqueSaida = new EstoqueSaida();
	}
	
	public void removeListaProduto(EstoqueEntrada estoque){
		listaEstoqueEntradaNovo.remove(estoque);
		estoqueEntrada = new EstoqueEntrada();
	}
	
	public void removeListaProdutoSaida(EstoqueSaida estoque){
		listaEstoqueSaidaNovo.remove(estoque);
	}
	
	public void removeListaProdutoNovo(Estoque estoque){
		listaAdicionaProduto.remove(estoque);
		estoque = new Estoque();
	}
	
	
	public void visualizarCompra(EstoqueEntrada entrada){
		estoqueEntrada = entradaDAO.buscaPorId(entrada.getEte_codigo());
		setCompra(estoqueEntrada.getCompra());
		setFornecedor(getCompra().getFornecedor());
		setEstoque(getEstoqueEntrada().getEstoque());
		
		//listaEstoqueEntradaNovo = entradaDAO.pesquisaEstoqueEntradaPorCompra(entrada);
	}
	
	public void buscaPorId(Long id){
		estoque = dao.buscaPorId(id);
	}
	
	public void buscaEditarPorId(Long id){
		estoqueEditar = dao.buscaPorId(id);
	}
	
	public void listaTodos(){
		this.listaEstoque = dao.listaTodos();
		for (Estoque estoque : listaEstoque) {
			System.out.println(estoque);
		}
	}

	public List<EstoqueSaida> getListaEstoqueSaida() {
		return listaEstoqueSaida = saidaDAO.listaTodos();
	}
	
	public List<EstoqueEntrada> getListaEstoqueEntrada() {
		return listaEstoqueEntrada = entradaDAO.listaTodos();
	}
	
	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Estoque getEstoqueEditar() {
		return estoqueEditar;
	}

	public void setEstoqueEditar(Estoque estoqueEditar) {
		this.estoqueEditar = estoqueEditar;
	}

	public Estoque getEstoqueFiltro() {
		return estoqueFiltro;
	}

	public void setEstoqueFiltro(Estoque estoqueFiltro) {
		this.estoqueFiltro = estoqueFiltro;
	}

	public List<Estoque> getListaEstoque() {
		return listaEstoque;
	}

	public void setListaEstoque(List<Estoque> listaEstoque) {
		this.listaEstoque = listaEstoque;
	}
	
	public List<Estoque> getListaAdicionaProduto() {
		return listaAdicionaProduto;
	}
	
	public void setListaAdicionaProduto(List<Estoque> listaAdicionaProduto) {
		this.listaAdicionaProduto = listaAdicionaProduto;
	}
	
	public Integer getCountNegativo() {
		return countNegativo;
	}
	
	public void setCountNegativo(Integer countNegativo) {
		this.countNegativo = countNegativo;
	}

	public List<Estoque> getListaEstoqueNegativo() {
		return listaEstoqueNegativo;
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
	
	public List<EstoqueEntrada> getListaEstoqueEntradaNovo() {
		return listaEstoqueEntradaNovo;
	}
	
	public void setListaEstoqueEntradaNovo(List<EstoqueEntrada> listaEstoqueEntradaNovo) {
		this.listaEstoqueEntradaNovo = listaEstoqueEntradaNovo;
	}
	
	public String getDestinoSaida() {
		return destinoSaida;
	}
	
	public void setDestinoSaida(String destinoSaida) {
		this.destinoSaida = destinoSaida;
	}
	
	public List<EstoqueSaida> getListaEstoqueSaidaNovo() {
		return listaEstoqueSaidaNovo;
	}
	
	public void setListaEstoqueSaidaNovo(List<EstoqueSaida> listaEstoqueSaidaNovo) {
		this.listaEstoqueSaidaNovo = listaEstoqueSaidaNovo;
	}
	
	public EstoqueSaida getEstoqueSaida() {
		return estoqueSaida;
	}
	
	public void setEstoqueSaida(EstoqueSaida estoqueSaida) {
		this.estoqueSaida = estoqueSaida;
	}
}
