package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.PacienteRepository;

public class PacienteService {

	@Inject
	PacienteRepository dao;
	
	public List<Paciente> listarPacientes(){
		return dao.listarPacientes();
	}
	
	public void salvar(Paciente paciente) {
		dao.salvar(paciente);
	}
	
	public void deletar (Long id) {
		dao.excluir(id);
	}
	
	public List<Paciente> listarPacientesPorNome (String nome) {
		return dao.listarPacientesPorNome(nome);
	}
	
	public Paciente buscarPacientePorCPF (String cpf) {
		return dao.buscarPacientePorCpf(cpf);
	}
}
