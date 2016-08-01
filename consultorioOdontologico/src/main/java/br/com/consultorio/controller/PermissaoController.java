package br.com.consultorio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultorio.dao.PerfilDAO;
import br.com.consultorio.dao.PerfilPermissaoDAO;
import br.com.consultorio.dao.PermissaoDAO;
import br.com.consultorio.dao.ProgramaDAO;
import br.com.consultorio.modelo.Perfil;
import br.com.consultorio.modelo.PerfilPermissao;
import br.com.consultorio.modelo.Permissao;
import br.com.consultorio.modelo.Programa;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PermissaoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Permissao permissao;
	
	@Inject
	private Perfil perfil;
	
	@Inject
	private PerfilPermissao perfilPermissao;
	
	@Inject
	private Programa programa;
	
	@Inject
	private PermissaoDAO dao;
	
	@Inject
	private ProgramaDAO programaDAO;
	
	@Inject
	private PerfilPermissaoDAO perfilPermissaoDAO;
	
	@Inject
	private PerfilDAO perfilDAO;
	
	private List<Permissao> permissoes;
	
	private List<Programa> programas;
	
	private List<Permissao> filterPermissoes;
	
	private List<PerfilPermissao> permissoesSelecionados = new ArrayList<>();
	
	private List<Permissao> permissoesAddPerfil = new ArrayList<>();
	
	private List<Perfil> listaPerfis;
	
	private List<PerfilPermissao> listaPerfisComPermissao;
	
	private Long perfilId;
	
	@PostConstruct
	 public void init() {
		this.permissoes = dao.listaTodos();
		this.programas = programaDAO.listaTodos();
		this.listaPerfisComPermissao = dao.carregaPerfilComPermissoes();
		this.permissao.setPer_status(Boolean.TRUE);
		buscaPerfilSemPermissao();
		this.permissoesAddPerfil  = new ArrayList<>(); 
		this.perfilId = 0L;
		this.perfilPermissao = new PerfilPermissao();
	}
	
	public void novo(){
		init();
	}
	
	private void buscaPerfilSemPermissao() {
		this.listaPerfis = perfilDAO.buscaPerfilSemPermissao();
	}


	public void carregaPermissoesPeloPerfilPermissaoId(PerfilPermissao perfilPermissao){
		this.permissoesAddPerfil = dao.carregaPermissoesPeloPerfilPermissaoId(perfilPermissao);
		this.perfilPermissao = perfilPermissaoDAO.buscaPorId(perfilPermissao.getPerfilPermissaoCodigo());
		this.listaPerfis =  new ArrayList<>(); 
		this.listaPerfis.add(this.permissoesAddPerfil.get(0).getPerfil());
		this.perfil =  this.permissoesAddPerfil.get(0).getPerfil();
		this.perfilId = this.perfil.getPer_codigo();
		System.out.println(permissao);
	}
	
	@Transacional
	public void inativarSelecionados(Permissao permissao){
			if(permissao.getPer_status().equals('A')){
				permissao.setPer_status(Boolean.FALSE);
			}else{
				permissao.setPer_status(Boolean.TRUE);
			}
			this.dao.atualiza(permissao);
			init();
			FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
		}
	
	@Transacional
	public String gravar() {
		perfilPermissao.setPerfil(getPerfil());
		System.out.println("PerfilPermissaooo = "+perfilPermissao);
		if(perfilPermissao.getPerfilPermissaoCodigo() != null){
			perfilPermissaoDAO.atualiza(perfilPermissao);
		}else{
			perfilPermissaoDAO.adiciona(perfilPermissao);
		}
		System.out.println("Chegou3");
		List<Permissao> permissaoTemp = dao.carregaPermissoesPeloPerfilPermissaoId(perfilPermissao);
		System.out.println("Chegou");
		for (Permissao pp : permissaoTemp) {
			System.out.println("PermissaoTemp = "+pp.toString());
			dao.atualiza(pp);
			dao.remove(pp);
		}
		
		for (Permissao p : permissoesAddPerfil) {
			System.out.println("P = "+p);
			System.out.println("Perfldldldl = "+perfilPermissao);
			p.setPerfilPermissao(perfilPermissao);
			dao.atualiza(p);
		}
		
		FacesUtil.addSuccessMessage("Salvo Com Sucesso!!");
		
		init();
		return null;
	}
	
	@Transacional
	public void excluirSelecionados(){
		
		for (PerfilPermissao permissao : permissoesSelecionados) {
			
			List<Permissao> permissaoTemp = dao.carregaPermissoesPeloPerfilPermissaoId(permissao);
			for (Permissao pp : permissaoTemp) {
				System.out.println("Removeu = "+ pp);
				dao.remove(pp);
			}
		}
		
		for (PerfilPermissao permissao : permissoesSelecionados) {
			this.perfilPermissaoDAO.remove(permissao);
			this.permissao = new Permissao();
		}
		
		init();
		FacesUtil.addSuccessMessage("Registros Excluidos Com Sucesso!!");
	}
	
	@Transacional
	public void remover(Long codigo){
		this.perfilPermissao = this.perfilPermissaoDAO.buscaPorId(codigo);
		
		List<Permissao> permissaoTemp = dao.carregaPermissoesPeloPerfilPermissaoId(perfilPermissao);
		for (Permissao permissao : permissaoTemp) {
			dao.remove(permissao);
		}
		this.perfilPermissaoDAO.remove(this.perfilPermissao);
		init();
		FacesUtil.addSuccessMessage("Registro Excluido Com Sucesso!!");
	}
	
	public void adicionarPermissao(){
		
		permissao.setPerfil(getPerfil());
		
		if(getPerfilId() == null || getPerfilId() == 0){
			setPerfilId(getPerfil().getPer_codigo());
			System.out.println("PerfilId = "+getPerfilId());
		}
		
		if(permissao.getPerfil() == null){
			this.perfil = perfilDAO.buscaPorId(getPerfilId());
			this.permissao.setPerfil(perfil);
		}
		
		this.permissao.setPrograma(getPrograma());
		
		for (Permissao pp : permissoesAddPerfil) {
			if(pp.getPrograma().getPro_codigo() == this.permissao.getPrograma().getPro_codigo() ){
				FacesUtil.addErrorMessage("Programa Já adicionado neste Perfil!");
				return;
			}
		}
		
		permissoesAddPerfil.add(this.permissao);
		
		for (Permissao permissao : permissoesAddPerfil) {
			System.out.println("Permissão = "+permissao.toString());
		}
		permissao = new Permissao();
		permissao.setPerfil(getPerfil());
	}
	
	public void removePermissao(Permissao p){
		permissoesAddPerfil.remove(p);
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Permissao> getFilterPermissoes() {
		return filterPermissoes;
	}

	public void setFilterPermissoes(List<Permissao> filterPermissoes) {
		this.filterPermissoes = filterPermissoes;
	}

	public List<PerfilPermissao> getPermissoesSelecionados() {
		return permissoesSelecionados;
	}

	public void setPermissoesSelecionados(List<PerfilPermissao> permissoesSelecionados) {
		this.permissoesSelecionados = permissoesSelecionados;
	}
	
	public List<Perfil> getListaPerfis() {
		return listaPerfis;
	}
	
	public void setListaPerfis(List<Perfil> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}
	
	public List<Permissao> getPermissoesAddPerfil() {
		return permissoesAddPerfil;
	}
	
	public void setPermissoesAddPerfil(List<Permissao> permissoesAddPerfil) {
		this.permissoesAddPerfil = permissoesAddPerfil;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public List<PerfilPermissao> getListaPerfisComPermissao() {
		return listaPerfisComPermissao;
	}
	
	public void setListaPerfisComPermissao(List<PerfilPermissao> listaPerfisComPermissao) {
		this.listaPerfisComPermissao = listaPerfisComPermissao;
	}
	
	public Long getPerfilId() {
		return perfilId;
	}
	
	public void setPerfilId(Long perfilId) {
		this.perfilId = perfilId;
	}

	public PerfilPermissao getPerfilPermissao() {
		return perfilPermissao;
	}
	
	public void setPerfilPermissao(PerfilPermissao perfilPermissao) {
		this.perfilPermissao = perfilPermissao;
	}
	
	public Programa getPrograma() {
		return programa;
	}
	
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	
}
