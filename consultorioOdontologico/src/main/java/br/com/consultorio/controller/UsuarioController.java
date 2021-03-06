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

import br.com.consultorio.dao.CargoDAO;
import br.com.consultorio.dao.PerfilDAO;
import br.com.consultorio.dao.PermissaoDAO;
import br.com.consultorio.dao.UsuarioDAO;
import br.com.consultorio.modelo.Cargo;
import br.com.consultorio.modelo.Perfil;
import br.com.consultorio.modelo.Permissao;
import br.com.consultorio.modelo.Usuario;
import br.com.consultorio.tx.Transacional;
import br.com.consultorio.util.jsf.FacesUtil;


@Named
@ViewScoped
public class UsuarioController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	private List<Usuario> usuarios;
	
	private Usuario filterUsuarios;
	
	private Usuario usuarioEditar;
	
	private List<Usuario> usuariosSelecionados = new ArrayList<>();
	
	private List<Cargo> listCargo;
	
	private List<Perfil> listPerfis;
	
	private Long usuarioId;
	
	private String filtroCpf;
	
	@Inject
	private UsuarioDAO dao;
	
	@Inject
	private PerfilDAO perfilDAO;
	
	@Inject
	private PermissaoDAO permissaoDAO;
	
	@Inject
	private CargoDAO cargoDao;
	
	@PostConstruct
	 void init() {
		this.usuarios = dao.listaTodos();
		this.listCargo = cargoDao.listaTodos();
		this.listPerfis = perfilDAO.listaTodos();
		this.usuario = new Usuario();
		this.filterUsuarios = new Usuario();
		this.usuarioEditar = new Usuario();
	}
	
	public void carregaPeloId(Usuario usuario){
		System.out.println(usuario.getUsu_codigo());
		this.usuario = this.dao.buscaPorId(usuario.getUsu_codigo());
	}
	
	public List<Cargo> buscaCargo(String query){
		listCargo = cargoDao.buscarCargo(query);
        return listCargo;
	}
	
	public List<Perfil> buscaPerfil(String query){
		listPerfis = perfilDAO.buscarPorNome(query);
        return listPerfis;
	}
	
	@Transacional
	public String editar(){
		System.out.println("ToString = "+ this.usuarioEditar.toString());
		if(this.usuarioEditar.getUsu_ativo().equals("false")){
			this.usuarioEditar.setUsu_ativo("Inativo");
		}else{
			this.usuarioEditar.setUsu_ativo("Ativo");
		}
		this.dao.atualiza(this.usuarioEditar);
		FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
		this.usuarioEditar = new Usuario();
		init();
		this.usuarios = dao.listaTodos();
		return null;
	}
	
	@Transacional
	public void inativarSelecionados(Usuario usuario){
		if(usuario.getUsu_status().equals("A")){
			usuario.setUsu_status("I");
			usuario.setUsu_dataInativacao(new Date());
		}else{
			usuario.setUsu_status("A");
		}
		this.dao.atualiza(usuario);
		init();
		FacesUtil.addSuccessMessage("Status Alterado Com Sucesso!!");
	}
	
	public String efetuaLogin(){
		Usuario existe = dao.existe(this.usuario);
		if(existe != null){
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("usuarioLogado", existe);
			
			List<Permissao> lstPermissao = this.permissaoDAO.carregaPermissaoPorPerfil(existe.getPerfil().getPer_codigo());
			context.getExternalContext().getSessionMap().put("permissao", lstPermissao);
			
			return "home?faces-redirect=true";
		}
		return null;
	}
	
	public Boolean validaGravar(){
		if(this.usuario.getCargo() == null){
			FacesUtil.addErrorMessage("Você deve Selecionar um cargo para o usuário");
			return false;
		}
		if(this.usuario.getPerfil() == null){
			FacesUtil.addErrorMessage("Você deve Selecionar um perfil para o usuário");
			return false;
		}
		return true;
	}
	
	@Transacional
	public String gravar() {
		if(validaGravar()){
			if(this.usuario.getUsu_codigo() != null){
				this.dao.atualiza(this.usuario);
				FacesUtil.addSuccessMessage("Alterado Com Sucesso!!");
			}else{
				this.dao.adiciona(this.usuario);
				FacesUtil.addSuccessMessage("Adicionado Com Sucesso!!");
			}
			init();
		}
		return null;
	}
	
	@Transacional
	public void remover(){
		this.usuario.setUsu_dataInativacao(new Date());
		if(this.usuario.getUsu_status().equals("A")){
			this.usuario.setUsu_status("I");
		}else{
			this.usuario.setUsu_status("A");	
		}
		this.dao.atualiza(usuario);
		init();
		FacesUtil.addSuccessMessage("Registro Inativado Com Sucesso!!");
	}
	
	public void pesquisaPorFiltro(){
		this.filterUsuarios.setUsu_cpf(getFiltroCpf());
		if(this.filterUsuarios.getUsu_ativo().equals("false")){
			this.filterUsuarios.setUsu_ativo("Inativo");
		}else{
			this.filterUsuarios.setUsu_ativo("Ativo");
		}
		System.out.println(this.filterUsuarios.toString());
		this.usuarios = dao.pesquisaPorFiltro(this.filterUsuarios);
	}
	public void limparUsuario(){
		this.usuario = new Usuario();
	}
	
	public String deslogar(){
		System.out.println("Deslogou");
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

	public String getFiltroCpf() {
		return filtroCpf;
	}
	
	public void setFiltroCpf(String filtroCpf) {
		this.filtroCpf = filtroCpf;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getFilterUsuarios() {
		return filterUsuarios;
	}

	public void setFilterUsuarios(Usuario filterUsuarios) {
		this.filterUsuarios = filterUsuarios;
	}

	public List<Usuario> getUsuariosSelecionados() {
		return usuariosSelecionados;
	}

	public void setUsuariosSelecionados(List<Usuario> usuariosSelecionados) {
		this.usuariosSelecionados = usuariosSelecionados;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Cargo> getListCargo() {
		return listCargo;
	}
	
	public void setListCargo(List<Cargo> listCargo) {
		this.listCargo = listCargo;
	}
	
	public List<Perfil> getListPerfis() {
		return listPerfis;
	}
	
	public void setListPerfis(List<Perfil> listPerfis) {
		this.listPerfis = listPerfis;
	}
	
	public Usuario getUsuarioEditar() {
		return usuarioEditar;
	}
	
	public void setUsuarioEditar(Usuario usuarioEditar) {
		this.usuarioEditar = usuarioEditar;
	}
}
