package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.DestinoProdutoSaidaDAO;
import br.com.consultorio.dao.EstoqueDAO;
import br.com.consultorio.dao.EstoqueSaidaDAO;
import br.com.consultorio.modelo.Compra;
import br.com.consultorio.modelo.DestinoProdutoSaida;
import br.com.consultorio.modelo.Estoque;
import br.com.consultorio.modelo.EstoqueSaida;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EstoqueSaidaController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Estoque estoque; // Produto
	
	private EstoqueSaida estoqueSaida; // Saida do Produto
	
	@Inject
	private EstoqueDAO dao;
	
	@Inject
	private EstoqueSaidaDAO saidaDAO;
	
	@Inject
	private DestinoProdutoSaidaDAO destinoDAO;
	
	private List<EstoqueSaida> listaProduto;
	private List<EstoqueSaida> listaSaidas;
	private List<Compra> listaCompra;
	private List<Estoque> results = new ArrayList<Estoque>();
	private List<DestinoProdutoSaida> resultsDestinoSaida = new ArrayList<DestinoProdutoSaida>();
	private String destinoSaida;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

	
	@PostConstruct
	 void init() {
		System.out.println("Init Paciente ----------------------------------------------------------------");
		this.estoque = new Estoque();
		this.estoqueSaida = new EstoqueSaida();
		this.listaProduto =  new ArrayList<>();
		this.listaSaidas = saidaDAO.listaTodos();
		this.listaProduto.clear();
	}
	
	public void limpar(){
		init();
	}
	
	public void inativarSaida(){
		this.estoqueSaida.setEts_status("I");
		this.estoqueSaida.setEts_dataInativacao(new Date());
		this.saidaDAO.atualiza(this.estoqueSaida);
		FacesUtil.addSuccessMessage("Produto Inativado com Sucesso");
	}
	
	public void buscaPorId(Long codigo){
		this.estoqueSaida = saidaDAO.buscaPorId(codigo);
	}
	
	public Boolean validaFecharNota(){
		if(this.listaProduto.size() == 0 || this.listaProduto.isEmpty()){
			FacesUtil.addErrorMessage("Deve ser adicionado um produto no mínimo para efetivar a saida de Produto");
			FacesContext.getCurrentInstance().validationFailed();
			return false;
		}
			return true;
	}
	
	@Transacional
	public void alterarSaida(){
		this.saidaDAO.atualiza(estoqueSaida);
		estoqueSaida = new EstoqueSaida();
		FacesUtil.addSuccessMessage("Saída Alterada com Sucesso!!");
		init();
	}
	
	@Transacional
	public void fecharSaida(){
		if(validaFecharNota()){
			for (EstoqueSaida p : listaProduto) {
				Estoque produto = dao.buscaPorId(p.getEstoque().getEst_codigo());
				produto.setEst_quantidade(produto.getEst_quantidade() - p.getEts_quantidade());
				dao.atualiza(produto);
				this.saidaDAO.adiciona(p);
			}
			FacesUtil.addSuccessMessage("Saída Efetuada com Sucesso!!");
			init();
		}
	}
	
	public Boolean validaAdicionaProduto(){
		if(this.estoqueSaida.getEts_quantidade() == null || this.estoqueSaida.getEts_quantidade() == 0){
			FacesUtil.addErrorMessage("Não Pode inserir um Produto com Quantidade 0");
			return false;
		}
		if(this.estoqueSaida.getEts_quantidade() > this.estoqueSaida.getEstoque().getEst_quantidade()){
			FacesUtil.addErrorMessage("Não Pode Retirar essa quantidade: Produto Não tem Essa quantidade em Estoque");
			return false;
		}
		return true;
	}
	
	public void adicionaProduto(){
		if(validaAdicionaProduto()){
			this.estoqueSaida.setEts_status("A");
			this.estoqueSaida.setUsuario(usuario);
			this.listaProduto.add(this.estoqueSaida);
			this.estoqueSaida = new EstoqueSaida();
		}
	}
	
	public void removeProduto(EstoqueSaida e){
		System.out.println(e);
		this.listaProduto.remove(e);
		this.estoqueSaida = new EstoqueSaida();
	}
	
	public EstoqueSaida getEstoqueSaida() {
		return estoqueSaida;
	}
	
	public void setEstoqueSaida(EstoqueSaida estoqueSaida) {
		this.estoqueSaida = estoqueSaida;
	}
	
	public List<Estoque> buscaProduto(String query){
        results = dao.buscarPorNome(query);
        return results;
	}
	
	public List<DestinoProdutoSaida> buscaDestinoSaida(String query){
        resultsDestinoSaida = destinoDAO.buscarPorNome(query);
        return resultsDestinoSaida;
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
	
	public List<EstoqueSaida> getListaSaidas() {
		return listaSaidas;
	}
	
	public void setListaSaidas(List<EstoqueSaida> listaSaidas) {
		this.listaSaidas = listaSaidas;
	}
	
	public List<EstoqueSaida> getListaProduto() {
		return listaProduto;
	}
	
	public void setListaProduto(List<EstoqueSaida> listaProduto) {
		this.listaProduto = listaProduto;
	}
	
	public List<DestinoProdutoSaida> getResultsDestinoSaida() {
		return resultsDestinoSaida;
	}
	
	public void setResultsDestinoSaida(List<DestinoProdutoSaida> resultsDestinoSaida) {
		this.resultsDestinoSaida = resultsDestinoSaida;
	}
}
