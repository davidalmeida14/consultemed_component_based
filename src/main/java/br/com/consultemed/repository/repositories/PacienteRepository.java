package br.com.consultemed.repository.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.utils.JPAUtils;

public class PacienteRepository {

	EntityManagerFactory em= JPAUtils.getEntityManagerFactory();
	EntityManager factory = em.createEntityManager();
	
	@SuppressWarnings("unchecked")
	public List<Paciente> listarPacientes(){
		this.factory = em.createEntityManager();
		List<Paciente> pacientes = new ArrayList<>();
		try {
			factory.getTransaction().begin();
			Query query = factory.createQuery("SELECT P From Paciente P");
			factory.getTransaction().commit();
			pacientes = query.getResultList();
			
		} catch(Exception e) {
			System.out.println("Erro ao realizar consulta de paciente: " + e.getStackTrace());
			factory.getTransaction().rollback();
		} finally {
			factory.close();
		}
		
		return pacientes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Paciente> listarPacientesPorNome(String nome){
		this.factory = em.createEntityManager();
		List<Paciente> pacientes = new ArrayList<>();
		try {
			factory.getTransaction().begin();
			Query query = factory.createQuery("SELECT P From Paciente P Where P.nome Like :nome");
			query.setParameter("nome", nome + "%");
			factory.getTransaction().commit();
			pacientes = query.getResultList();
			
		} catch(Exception e) {
			System.out.println("Erro ao realizar consulta de paciente: " + e.getMessage());
			factory.getTransaction().rollback();
		} finally {
			factory.close();
		}
		
		return pacientes;
	}
	
	public void salvar(Paciente paciente) {
		
		this.factory = em.createEntityManager();
		try {
			factory.getTransaction().begin();
			if (paciente.getId() == null) {
				factory.persist(paciente);
			} else {
				factory.merge(paciente);
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
		this.factory = em.createEntityManager();
		Paciente paciente = new Paciente();

		try {

			paciente = factory.find(Paciente.class, id);
			factory.getTransaction().begin();
			factory.remove(paciente);
			factory.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}
	}

	public Paciente buscarPacientePorCpf(String cpf) {
		this.factory = em.createEntityManager();
		Paciente pacienteBuscado = null;
		try {
			Query query = factory.createQuery("SELECT P FROM Paciente P Where P.cpf = :cpf");
			query.setParameter("cpf", cpf);
			pacienteBuscado = (Paciente) query.getSingleResult();
		}catch(Exception e) {
			System.out.println(e.getStackTrace() + e.getMessage());
		} 
		return pacienteBuscado;
		
	}
	
}
