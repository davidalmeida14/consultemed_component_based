/**
 * 
 */
package br.com.consultemed.repository.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.consultemed.models.Medico;
import br.com.consultemed.utils.JPAUtils;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class MedicoRepository {

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = emf.createEntityManager();


	public List<Medico> listarMedicos() throws Exception {
		this.factory = emf.createEntityManager();
		List<Medico> medicos = new ArrayList<Medico>();
		try {
			factory.getTransaction().begin();
			Query query = this.factory.createQuery("SELECT object(m) FROM Medico as m");
			factory.getTransaction().commit();
			return query.getResultList();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

		return medicos;
	}
	
	public List<Medico> listarMedicosPorNome(String nome) throws Exception {
		this.factory = emf.createEntityManager();
		List<Medico> medicos = new ArrayList<Medico>();
		try {
			factory.getTransaction().begin();
			Query query = this.factory.createQuery("SELECT m FROM Medico m Where m.nome like :nome");
			query.setParameter("nome",nome + "%");
			factory.getTransaction().commit();
			return query.getResultList();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

		return medicos;
	}

	public void salvarMedico(Medico medico) {
		this.factory = emf.createEntityManager();
		try {
			factory.getTransaction().begin();
			if (medico.getId() == null) {
				factory.persist(medico);
			} else {
				factory.merge(medico);
			}
			factory.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();

		} finally {
			factory.close();
		}
	}

	public void deleteById(Long id) throws Exception {
		this.factory = emf.createEntityManager();
		Medico medico = new Medico();

		try {

			medico = factory.find(Medico.class, id);
			factory.getTransaction().begin();
			factory.remove(medico);
			factory.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

	}

	public Medico buscarMedicoPorCRM(String crm) {
		
		this.factory = emf.createEntityManager();
		Medico medicoBuscado = null;
		try {
			Query query = factory.createQuery("SELECT M FROM Medico M Where M.crm = :crm");
			query.setParameter("crm", crm);
			medicoBuscado = (Medico) query.getSingleResult();
		}catch(Exception e) {
			System.out.println("Erro ao buscar médico: " + e.getStackTrace());
		}
		
		return medicoBuscado;
		
	}

}
