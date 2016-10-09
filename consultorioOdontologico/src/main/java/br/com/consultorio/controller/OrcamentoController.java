package br.com.consultorio.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.CondicaoPagamentoDAO;
import br.com.consultorio.dao.FormaPagamentoDAO;
import br.com.consultorio.dao.OrcamentoDAO;
import br.com.consultorio.dao.OrcamentoItemDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.TratamentoDAO;
import br.com.consultorio.dao.UsuarioDAO;
import br.com.consultorio.modelo.CondicaoPagamento;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.modelo.Orcamento;
import br.com.consultorio.modelo.OrcamentoItem;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.Tratamento;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class OrcamentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Orcamento orcamento;
	
	@Inject
	private OrcamentoItem item;
	
	@Inject
	private OrcamentoDAO dao;
	
	@Inject
	private OrcamentoItemDAO itemDAO;
	
	@Inject
	private TratamentoDAO tratamentoDAO;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Inject
	private FormaPagamentoDAO formaPagamentoDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private CondicaoPagamentoDAO condicaoDAO;
	
	private List<Orcamento> listaOrcamento;
	private List<OrcamentoItem> listaItens;
	
	private List<Paciente> resultsPaciente = new ArrayList<Paciente>();
	private List<Tratamento> resultsTratamento = new ArrayList<Tratamento>();
	private List<CondicaoPagamento> resultsPagamento = new ArrayList<CondicaoPagamento>();
	private List<FormaPagamento> resultsFormaPagamento = new ArrayList<FormaPagamento>();
	private List<Usuario> resultUsuario = new ArrayList<Usuario>();
	
	private BigDecimal subTotal = BigDecimal.ZERO;
	
	@PostConstruct
	 void init() {
		this.orcamento = new Orcamento();
		this.listaOrcamento = dao.listaTodos();
		this.listaItens = new ArrayList<OrcamentoItem>();
		this.listaItens.clear();
	}
	
	public void limpar(){
		this.orcamento = new Orcamento();
	}
	
	public void carregaPeloId(Orcamento orcamento){
		this.orcamento = this.dao.buscaPorId(orcamento.getOrc_codigo());
		this.listaItens = this.itemDAO.buscaPorOrcamento(orcamento);
		this.subTotal = this.orcamento.getOrc_total().add(this.orcamento.getOrc_desconto());
	}

	@Transacional
	public void alteraStatus(String status){
		this.orcamento.setOrc_status(status);
		this.dao.atualiza(this.orcamento);
		init();
	}
	
	@Transacional
	public String gravar() {
		this.orcamento.setPlano(this.orcamento.getPaciente().getPlano().getPlanoPai());
		if(this.orcamento.getOrc_codigo() != null){
			this.dao.atualiza(this.orcamento);
			for (OrcamentoItem orcamentoItem : listaItens) {
				if(orcamentoItem.getOri_codigo() == null || orcamentoItem.getOri_codigo() > 0){
					orcamentoItem.setOrcamento(this.orcamento);
					itemDAO.atualiza(orcamentoItem);
				}else{
					orcamentoItem.setOrcamento(this.orcamento);
					itemDAO.adiciona(orcamentoItem);
				}
			}
			FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		}else{
			this.dao.adiciona(this.orcamento);
			for (OrcamentoItem orcamentoItem : listaItens) {
				orcamentoItem.setOrcamento(this.orcamento);
				itemDAO.adiciona(orcamentoItem);
			}
			FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
		}
		this.orcamento = new Orcamento();
		init();
		return null;
	}
	
	public void teste(){
		System.out.println(this.orcamento);
	}
	
	public Boolean validaAdicionaItem(){
		if(this.item.getTratamento() == null){
			FacesUtil.addErrorMessage("Voce deve selecionar um Tratamento para adicionar");
			return false;
		}
		if(this.item.getOri_quantidade() == null || item.getOri_quantidade() == 0){
			FacesUtil.addErrorMessage("A Quantidade deve ser maior que 0");
			return false;
		}
		if(this.orcamento.getPaciente() == null){
			FacesUtil.addErrorMessage("Deve ser Selecionado um paciente primeiro");
			return false;
		}
		return true;
	}
	
	public void adicionaItem(){
		if(validaAdicionaItem()){
			BigDecimal temp = item.getTratamento().getTra_valor().multiply(new BigDecimal(item.getOri_quantidade()));
			this.listaItens.add(item);
			this.item = new OrcamentoItem();
			this.subTotal = this.subTotal.add(temp);
			this.orcamento.setOrc_total(this.subTotal);
			if(this.getOrcamento().getPaciente().getPlano() != null){
				if(this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("P")){
					BigDecimal valorDesconto = this.getOrcamento().getOrc_desconto().divide(new BigDecimal(100));
					this.orcamento.setOrc_total(this.orcamento.getOrc_total().multiply(valorDesconto).add(this.orcamento.getOrc_total()));
				}else if (this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("M")){
					this.orcamento.setOrc_desconto(this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPla_desconto());
					this.orcamento.setOrc_total(this.orcamento.getOrc_total().subtract(this.getOrcamento().getOrc_desconto()));
				}
			}
		}
	}
	
	@Transacional
	public void removerItem(OrcamentoItem i){
		BigDecimal temp = i.getTratamento().getTra_valor().multiply(new BigDecimal(i.getOri_quantidade()));
		this.subTotal = this.subTotal.subtract(temp);
		this.orcamento.setOrc_total(this.subTotal);
		System.out.println(i);
		if(i.getOri_codigo() != null || i.getOri_codigo() > 0){
			this.itemDAO.remove(i);
		}
		this.listaItens.remove(i);
		if(this.getOrcamento().getPaciente().getPlano() != null){
			if(this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("P")){
				System.out.println("Porcentagem");
			}else if (this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("M")){
				if(this.listaItens.size() > 0){
					this.orcamento.setOrc_total(this.orcamento.getOrc_total().subtract(this.getOrcamento().getOrc_desconto()));
				}
			}
		}
	}

	public List<CondicaoPagamento> buscaCondicaoPagamento(String query){
		resultsPagamento = condicaoDAO.buscarCondicaoPorNomeAtivo(query);
        return resultsPagamento;
	}
	
	public List<FormaPagamento> buscaFormaPagamento(String query){
		resultsFormaPagamento = formaPagamentoDAO.buscarFormaPorNomeAtivo(query);
        return resultsFormaPagamento;
	}
	
	public List<Paciente> buscaPaciente(String query){
		resultsPaciente = pacienteDAO.buscarPorNome(query);
        return resultsPaciente;
	}
	
	public List<Tratamento> buscaTratamento(String query){
		resultsTratamento = tratamentoDAO.buscarPorNome(query);
        return resultsTratamento;
	}
	
	public List<Usuario> buscaProfissional(String query){
		resultUsuario = usuarioDAO.buscarProfissional(query);
        return resultUsuario;
	}
	
	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public List<Orcamento> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<Orcamento> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}

	public OrcamentoItem getItem() {
		return item;
	}

	public void setItem(OrcamentoItem item) {
		this.item = item;
	}

	public List<OrcamentoItem> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<OrcamentoItem> listaItens) {
		this.listaItens = listaItens;
	}

	public List<Paciente> getResultsPaciente() {
		return resultsPaciente;
	}

	public void setResultsPaciente(List<Paciente> resultsPaciente) {
		this.resultsPaciente = resultsPaciente;
	}

	public List<Tratamento> getResultsTratamento() {
		return resultsTratamento;
	}

	public void setResultsTratamento(List<Tratamento> resultsTratamento) {
		this.resultsTratamento = resultsTratamento;
	}
	
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
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

	public List<Usuario> getResultUsuario() {
		return resultUsuario;
	}

	public void setResultUsuario(List<Usuario> resultUsuario) {
		this.resultUsuario = resultUsuario;
	}
	
}
