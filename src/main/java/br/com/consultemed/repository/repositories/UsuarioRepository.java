/**
 * 
 */
package br.com.consultemed.repository.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.utils.JPAUtils;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioRepository {

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = emf.createEntityManager();

	public List<Usuario> listaUsuarios() {
		this.factory = emf.createEntityManager();
		Query query = this.factory.createQuery("SELECT object(u) FROM Usuario as u");
		return query.getResultList();
	}

	public void salvar(Usuario usuario) {

		this.factory = emf.createEntityManager();
		try {
			factory.getTransaction().begin();
			if (usuario.getId() == null) {
				factory.persist(usuario);
			} else {
				factory.merge(usuario);
			}
			factory.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();

		} finally {
			factory.close();
		}

	}

	public void excluir(Long id) {
		this.factory = emf.createEntityManager();
		Usuario usuario = new Usuario();

		try {

			usuario = factory.find(Usuario.class, id);
			factory.getTransaction().begin();
			factory.remove(usuario);
			factory.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}
	}

}
