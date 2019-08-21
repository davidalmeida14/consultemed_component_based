package br.com.consultemed.repository.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.StatusConsulta;
import br.com.consultemed.utils.JPAUtils;

public class ConsultaRepository {

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = emf.createEntityManager();

	@SuppressWarnings("unchecked")
	public List<Consulta> listarConsultas() {
		List<Consulta> consultas = new ArrayList<Consulta>();
		try {
			this.factory = emf.createEntityManager();
			this.factory.getTransaction().begin();
			Query query = factory.createQuery("SELECT C From Consulta C");
			consultas = (List<Consulta>) query.getResultList();
			this.factory.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao realizar listagem de consultas. Erro: " + e.getStackTrace());
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}
		return consultas;
	}

	public void salvarConsulta(Consulta consulta) {
		this.factory = emf.createEntityManager();
		this.factory.getTransaction().begin();
		try {
			if (consulta.getId() == null) {
				this.factory.persist(consulta);
			} else {
				this.factory.merge(consulta);
			}
			this.factory.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao salvar consulta. Erro: " + e.getStackTrace());
			this.factory.getTransaction().rollback();
		} finally {
			this.factory.close();
		}

	}

	public void deletarConsulta(Long id) {

		this.factory = emf.createEntityManager();
		Consulta c = new Consulta();

		try {

			this.factory.getTransaction().begin();
			c = this.factory.find(Consulta.class, id);
			if (!Objects.isNull(c.getId())) {
				this.factory.remove(c);
				this.factory.getTransaction().commit();
			}

		} catch (Exception e) {
			System.out.println("Erro ao deletar consulta. Erro: " + e.getStackTrace());
			this.factory.getTransaction().rollback();
		} finally {
			this.factory.close();
		}

	}

	public void reagendarConsulta(Consulta consulta) {
		this.factory = emf.createEntityManager();
		Consulta c = new Consulta();
		consulta.setStatusConsulta(StatusConsulta.REAGENDADO);
		
		try {
			
			this.factory.getTransaction().begin();
			c = this.factory.find(Consulta.class, consulta.getId());
			if(!Objects.isNull(c.getId()) && c.getDataAgendamento() != consulta.getDataAgendamento()) {
				this.factory.merge(consulta);
				this.factory.getTransaction().commit();
			} else {
				this.factory.getTransaction().rollback();
				throw new RuntimeException("Consulta indisponível para reagendamento.");
			}

		} catch (Exception e) {
			System.out.println("Erro ao deletar consulta. Erro: " + e.getStackTrace());
			this.factory.getTransaction().rollback();
		} finally {
			this.factory.close();
		}
	}
	
	public List<Consulta> validarConsultaDataHoraPorMedico(Consulta consulta){
		
		this.factory = emf.createEntityManager();
		List<Consulta> consultas = new ArrayList<Consulta>();
		
		try {
			Query query = factory.createQuery(
					 "SELECT C FROM Consulta C Where C.dataAgendamento = :dataAgendamento"
					+ "And C.medico_id = :idMedico And C.statusConsulta = :status ");
			query.setParameter("idMedico", consulta.getMedico().getId());
			query.setParameter("status", consulta.getStatusConsulta());
			query.setParameter("dataAgendamento", consulta.getDataAgendamento());
			
			consultas = (List<Consulta>) query.getResultList();
			
		} catch(Exception e) {
			System.out.println("Nenhuma consulta encontrada para o mesmo horário.");
		}
		
		return consultas;
		
	}
	
	public List<Consulta> consultarAgendamentosPorData(String data){

		this.factory = emf.createEntityManager();
		List<Consulta> consultas = new ArrayList<Consulta>();
		try {
			Query query = factory.createNativeQuery(
					"SELECT C FROM TB_CONSULTA C Where CAST(C.dataagendamento AS DATE) = :data "
					);
			query.setParameter("data", data);
			consultas = query.getResultList();
		}catch(Exception e) {
			e.getStackTrace();
		}
		return consultas;
	}
	
	
}
