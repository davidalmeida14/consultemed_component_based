package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.repository.repositories.ConsultaRepository;

public class ConsultaService {

	@Inject
	private ConsultaRepository dao;
	
	
	public List<Consulta> listarConsultas(){
		return dao.listarConsultas();
	}
	
	public void salvarConsulta(Consulta consulta) {
		dao.salvarConsulta(consulta);
	}
	
	public void reagendarConsulta(Consulta consulta) {
		dao.reagendarConsulta(consulta);
	}
	
	public void deletarConsulta(Long id) {
		dao.deletarConsulta(id);
	}
}
