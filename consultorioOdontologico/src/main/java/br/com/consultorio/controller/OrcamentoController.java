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

import br.com.consultorio.dao.AgendaDAO;
import br.com.consultorio.dao.CondicaoPagamentoDAO;
import br.com.consultorio.dao.FormaPagamentoDAO;
import br.com.consultorio.dao.OrcamentoDAO;
import br.com.consultorio.dao.OrcamentoItemDAO;
import br.com.consultorio.dao.PacienteDAO;
import br.com.consultorio.dao.ProntuarioTratamentoDAO;
import br.com.consultorio.dao.TituloDAO;
import br.com.consultorio.dao.TratamentoDAO;
import br.com.consultorio.dao.UsuarioDAO;
import br.com.consultorio.modelo.Agenda;
import br.com.consultorio.modelo.CondicaoPagamento;
import br.com.consultorio.modelo.FormaPagamento;
import br.com.consultorio.modelo.Orcamento;
import br.com.consultorio.modelo.OrcamentoItem;
import br.com.consultorio.modelo.Paciente;
import br.com.consultorio.modelo.ProntuarioTratamento;
import br.com.consultorio.modelo.Titulo;
import br.com.consultorio.modelo.Tratamento;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class OrcamentoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Orcamento orcamento;

	private Agenda agenda;

	private Titulo titulo;

	@Inject
	private OrcamentoItem item;
	
	@Inject
	private Paciente paciente;
	
	@Inject
	private CondicaoPagamento condicaoPagamento;
	
	@Inject
	private FormaPagamento formaPagamento;

	@Inject
	private Usuario profissional;
	
	@Inject
	private OrcamentoDAO dao;

	@Inject
	private OrcamentoItemDAO itemDAO;

	@Inject
	private TratamentoDAO tratamentoDAO;

	@Inject
	private PacienteDAO pacienteDAO;
	
	@Inject
	private TituloDAO tituloDAO;

	@Inject
	private FormaPagamentoDAO formaPagamentoDAO;

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private ProntuarioTratamentoDAO prontuarioTratamentoDAO;


	@Inject
	private AgendaDAO agendaDAO;

	@Inject
	private CondicaoPagamentoDAO condicaoDAO;

	private List<Orcamento> listaOrcamento;
	private List<OrcamentoItem> listaItens;

	private List<Paciente> resultsPaciente = new ArrayList<Paciente>();
	private List<Tratamento> resultsTratamento = new ArrayList<Tratamento>();
	private List<CondicaoPagamento> resultsPagamento = new ArrayList<CondicaoPagamento>();
	private List<FormaPagamento> resultsFormaPagamento = new ArrayList<FormaPagamento>();
	private List<Usuario> resultUsuario = new ArrayList<Usuario>();
	private List<Titulo> parcelas = new ArrayList<Titulo>();;

	private BigDecimal subTotal = BigDecimal.ZERO;
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");

	@PostConstruct
	void init() {
		this.orcamento = new Orcamento();
		this.listaOrcamento = dao.listaTodos();
		this.listaItens = new ArrayList<OrcamentoItem>();
		this.listaItens.clear();
		this.agenda = new Agenda();
		this.titulo = new Titulo();
	}

	public void limpar() {
		this.orcamento = new Orcamento();
		this.listaItens = new ArrayList<OrcamentoItem>();
		this.listaItens.clear();
		this.agenda = new Agenda();
		this.titulo = new Titulo();
		this.subTotal = BigDecimal.ZERO;
	}

	public void carregaPeloId(Orcamento orcamento) {
		this.orcamento = this.dao.buscaPorId(orcamento.getOrc_codigo());
		this.listaItens = this.itemDAO.buscaPorOrcamento(orcamento);
		this.subTotal = this.orcamento.getOrc_total().add(this.orcamento.getOrc_desconto());
		this.condicaoPagamento = this.orcamento.getCondicaoPagamento();
		this.formaPagamento = this.orcamento.getFormaPagamento();
		this.profissional = this.orcamento.getUsuario();
		this.paciente = this.orcamento.getPaciente();
		this.parcelas.clear();
	}

	public Boolean validaGeraAtendimento(String status){
		if(this.getParcelas().size() == 0 && status.equals("Aprovado")){
			FacesUtil.addErrorMessage("Deve ser Gerado as parcelas primeiro! Para poder continuar.");
			return false;
		}
		
		if(this.agenda.getAge_dataConsulta() == null && status.equals("Aprovado")){
			FacesUtil.addErrorMessage("Deve ser informado a Data da Consulta");
			return false;
		}
		
		if(this.orcamento.getCondicaoPagamento() == null){
			this.orcamento.setCondicaoPagamento(condicaoPagamento);
		}
		
		if(this.orcamento.getFormaPagamento() == null){
			this.orcamento.setFormaPagamento(formaPagamento);
		}
		
		if(this.orcamento.getUsuario() == null){
			this.orcamento.setUsuario(profissional);
		}
		
		if(this.orcamento.getPaciente() == null){
			this.orcamento.setPaciente(paciente);
		}
		
		return true;
	}
	
	@Transacional
	public void alteraStatus(String status) {
		if(validaGeraAtendimento(status)){
			for (Titulo t : parcelas) {
				t.setPaciente(this.orcamento.getPaciente());
				t.setTit_favorecido(this.orcamento.getPaciente().getPac_nome());
				t.setUsuario(usuario);
				tituloDAO.adiciona(t);
			}
			this.orcamento.setOrc_status(status);
			this.dao.atualiza(this.orcamento);
			
			this.agenda.setPaciente(this.paciente);
			System.out.println(this.getOrcamento().getUsuario());
			this.agenda.setUsuario(this.getOrcamento().getUsuario());
			
			agendaDAO.adiciona(this.agenda);
			
			for (OrcamentoItem orcamentoItem : listaItens) {
				
				System.out.println(orcamentoItem);
				ProntuarioTratamento t = new ProntuarioTratamento();
				t.setPlanoPai(this.orcamento.getPlano());
				t.setPrt_desconto(this.orcamento.getPlano().getPla_desconto());
				
				t.setPrt_quantidade(orcamentoItem.getOri_quantidade());
				t.setPrt_valor(orcamentoItem.getTratamento().getTra_valor());
				t.setTratamento(orcamentoItem.getTratamento());
				t.setPaciente(orcamentoItem.getOrcamento().getPaciente());
				prontuarioTratamentoDAO.adiciona(t);
			}
			
			FacesUtil.addSuccessMessage("Orçamento Alterado com Sucesso!!");
			init();
		}
	}

	public Boolean validaGrava(){
		
		if(this.orcamento.getCondicaoPagamento() == null){
			this.orcamento.setCondicaoPagamento(condicaoPagamento);
		}
		
		if(this.orcamento.getFormaPagamento() == null){
			this.orcamento.setFormaPagamento(formaPagamento);
		}
		
		if(this.orcamento.getUsuario() == null){
			this.orcamento.setUsuario(profissional);
		}
		
		if(this.orcamento.getPaciente() == null){
			this.orcamento.setPaciente(paciente);
		}
		
		return true;
	}
	
	@Transacional
	public String gravar() {
		if(validaGrava()){
			this.orcamento.setPlano(this.orcamento.getPaciente().getPlano().getPlanoPai());
			if (this.orcamento.getOrc_codigo() != null) {
				this.dao.atualiza(this.orcamento);
				for (OrcamentoItem orcamentoItem : listaItens) {
					if (orcamentoItem.getOri_codigo() == null || orcamentoItem.getOri_codigo() > 0) {
						orcamentoItem.setOrcamento(this.orcamento);
						itemDAO.atualiza(orcamentoItem);
					} else {
						orcamentoItem.setOrcamento(this.orcamento);
						itemDAO.adiciona(orcamentoItem);
					}
				}
				FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
			} else {
				this.dao.adiciona(this.orcamento);
				for (OrcamentoItem orcamentoItem : listaItens) {
					orcamentoItem.setOrcamento(this.orcamento);
					itemDAO.adiciona(orcamentoItem);
				}
				FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
			}
			this.orcamento = new Orcamento();
			init();
		}
		return null;
	}

	public void teste() {
		System.out.println(this.orcamento);
	}

	public Boolean validaAdicionaItem() {
		if (this.item.getTratamento() == null) {
			FacesUtil.addErrorMessage("Voce deve selecionar um Tratamento para adicionar");
			return false;
		}
		if (this.item.getOri_quantidade() == null || item.getOri_quantidade() == 0) {
			FacesUtil.addErrorMessage("A Quantidade deve ser maior que 0");
			return false;
		}
		if (this.orcamento.getPaciente() == null) {
			FacesUtil.addErrorMessage("Deve ser Selecionado um paciente primeiro");
			return false;
		}
		return true;
	}

	public void adicionaItem() {
		if (validaAdicionaItem()) {
			BigDecimal temp = item.getTratamento().getTra_valor().multiply(new BigDecimal(item.getOri_quantidade()));
			this.listaItens.add(item);
			this.item = new OrcamentoItem();
			this.subTotal = this.subTotal.add(temp);
			this.orcamento.setOrc_total(this.subTotal);
			if (this.getOrcamento().getPaciente().getPlano() != null) {
				if (this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("P")) {
					BigDecimal valorDesconto = this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPla_desconto().divide(new BigDecimal(100),3,RoundingMode.UP);
					this.orcamento.setOrc_total(this.orcamento.getOrc_total().multiply(valorDesconto).subtract(this.orcamento.getOrc_total()).abs());
					this.orcamento.setOrc_desconto(getSubTotal().subtract(this.orcamento.getOrc_total()));
				} else if (this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("M")) {
					this.orcamento.setOrc_desconto(this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPla_desconto());
					this.orcamento.setOrc_total(this.orcamento.getOrc_total().subtract(this.getOrcamento().getOrc_desconto()));
				}
			}
		}
	}

	@Transacional
	public void removerItem(OrcamentoItem i) {
		BigDecimal temp = i.getTratamento().getTra_valor().multiply(new BigDecimal(i.getOri_quantidade()));
		this.subTotal = this.subTotal.subtract(temp);
		this.orcamento.setOrc_total(this.subTotal);
		System.out.println(i);
		if (i.getOri_codigo() != null && i.getOri_codigo() > 0) {
			this.itemDAO.remove(i);
		}
		this.listaItens.remove(i);
		System.out.println(this.orcamento);
		if (this.getOrcamento().getPaciente().getPlano() != null) {
			if (this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("P")) {
				BigDecimal valorDesconto = this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPla_desconto().divide(new BigDecimal(100),3,RoundingMode.UP);
				this.orcamento.setOrc_total(this.orcamento.getOrc_total().multiply(valorDesconto).add(this.orcamento.getOrc_total()).abs());
				this.orcamento.setOrc_desconto(getSubTotal().add(this.orcamento.getOrc_total()));
			} else if (this.getOrcamento().getPaciente().getPlano().getPlanoPai().getPlp_tipoDesconto().equals("M")) {
				if (this.listaItens.size() > 0) {
					this.orcamento.setOrc_total(this.orcamento.getOrc_total().subtract(this.getOrcamento().getOrc_desconto()));
				}
			}
		}
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
	
	public void geraTitulo() {
		System.out.println(this.condicaoPagamento);
		System.out.println(this.formaPagamento);
		titulo.setCondicaoPagamento(this.condicaoPagamento);
		titulo.setFormaPagamento(this.formaPagamento);
		titulo.setTit_valor(this.orcamento.getOrc_total().abs());
		titulo.setTit_desconto(this.orcamento.getOrc_desconto());
		titulo.setPaciente(this.paciente);
		titulo.setTit_favorecido(this.paciente.getPac_nome());
		titulo.setTit_vencimento(new Date());
		if(valida()){
			Integer numeroParcela = titulo.getCondicaoPagamento().getCon_numeroParcela();
			BigDecimal valorTemp = titulo.getTit_valor().divide(new BigDecimal(numeroParcela).setScale(4, BigDecimal.ROUND_HALF_EVEN));
			System.out.println(valorTemp);
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
			for (int i = 1; i <= numeroParcela; i++) {
				Titulo temp = new Titulo();
				temp.setCondicaoPagamento(titulo.getCondicaoPagamento());
				temp.setFormaPagamento(titulo.getFormaPagamento());
				temp.setTit_parcela(i);
				temp.setTit_favorecido(titulo.getTit_favorecido());
				temp.setTit_tipo("C");
				temp.setTit_status("Aguardando");
				temp.setTit_valor(valorTemp.abs());
				temp.setUsuario(usuario);
				c.setTime(titulo.getTit_vencimento());
				c.add(Calendar.DAY_OF_MONTH, (i * 30));
				temp.setTit_vencimento(c.getTime());
				this.parcelas.add(temp);
			}
		}
	}

	public List<CondicaoPagamento> buscaCondicaoPagamento(String query) {
		resultsPagamento = condicaoDAO.buscarCondicaoPorNomeAtivo(query);
		return resultsPagamento;
	}

	public List<FormaPagamento> buscaFormaPagamento(String query) {
		resultsFormaPagamento = formaPagamentoDAO.buscarFormaPorNomeAtivo(query);
		return resultsFormaPagamento;
	}

	public List<Paciente> buscaPaciente(String query) {
		resultsPaciente = pacienteDAO.buscarPorNome(query);
		return resultsPaciente;
	}

	public List<Tratamento> buscaTratamento(String query) {
		resultsTratamento = tratamentoDAO.buscarPorNome(query);
		return resultsTratamento;
	}

	public List<Usuario> buscaProfissional(String query) {
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

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public List<Titulo> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Titulo> parcelas) {
		this.parcelas = parcelas;
	}
}
