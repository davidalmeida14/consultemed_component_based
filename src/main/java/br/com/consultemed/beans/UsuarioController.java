/**
 * 
 */
package br.com.consultemed.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.services.UsuarioService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */
@Named
@RequestScoped
public class UsuarioController{

	@Getter
	@Setter
	@Inject
	private Usuario usuario;
	
	@Getter
	@Setter
	private List<Usuario> usuarios;
	
	@Getter
	@Setter
	@Inject
	private Usuario usuarioEditar;
	
	@Inject
	private UsuarioService service;
	
	
	public List<Usuario> listarUsuarios(){
		return this.service.listaUsuarios();
	}
	
	public String editar(){
		this.usuario = this.usuarioEditar;
		return "/pages/usuarios/addUsuarios.xhtml";
	}
	
	public String excluir() {
		this.service.deletar(usuario.getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário " + usuario.getLogin() + ", excluído com sucesso", null));
		listarUsuarios();
		return "/pages/usuarios/usuarios.xhtml";
	}
	
	public String novoUsuario() {
		this.usuario = new Usuario();
		return "/pages/usuarios/addUsuarios.xhtml?faces-redirect=true";
	}
	
	public String addUsuario() throws Exception {
		this.service.salvarUsuario(this.usuario);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário " +usuario.getLogin()+ ", cadastrado/editado com sucesso", null));
		listarUsuarios();
		return "/pages/usuarios/usuarios.xhtml";
	}
	
}
