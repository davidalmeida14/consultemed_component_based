/**
 * 
 */
package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.cripto.Criptografia;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.repository.repositories.UsuarioRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioService {

	@Inject
	private UsuarioRepository dao;
	
	public List<Usuario> listaUsuarios(){
		return this.dao.listaUsuarios();
	}

	public void deletar(Long id) {
		this.dao.excluir(id);
	}

	public void salvarUsuario(Usuario usuario) {
		
		String senhaCripto = Criptografia.criptografar(usuario.getSenha());
		usuario.setSenha(senhaCripto);
		
		this.dao.salvar(usuario);	
	}
}
