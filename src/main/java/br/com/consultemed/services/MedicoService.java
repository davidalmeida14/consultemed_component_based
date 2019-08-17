/**
 * 
 */
package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.models.Medico;
import br.com.consultemed.repository.repositories.MedicoRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class MedicoService {

	@Inject
	private MedicoRepository dao;
	
	public List<Medico> listaMedico() throws Exception{
		return this.dao.listarMedicos();
	}
	
	public void salvarMedico(Medico medico) {
		this.dao.salvarMedico(medico);
	}
	
	public void deletarMedico(Long id) throws Exception {
		this.dao.deleteById(id);
	}
	
	public List<Medico> listaMedicoPorNome(String nome) throws Exception{
		return this.dao.listarMedicosPorNome(nome);
	}
	
}
