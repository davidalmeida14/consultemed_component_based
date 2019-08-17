package br.com.consultemed.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.ConsultaRepository;

public class ConsultaService {

	@Inject
	private ConsultaRepository dao;

	@Inject
	private PacienteService pacienteService;

	@Inject
	private MedicoService medicoService;

	public List<Consulta> listarConsultas() {
		return dao.listarConsultas();
	}

	public void salvarConsulta(Consulta consulta) {
		
		Paciente p = pacienteService.buscarPacientePorCPF(consulta.getPaciente().getCpf());
		Medico m = medicoService.buscarMedicoPorCRM(consulta.getMedico().getCrm());
		
		if(!Objects.isNull(p) || !Objects.isNull(m)) {
			ZoneId defaultZoneId = ZoneId.systemDefault();
			Instant instant = consulta.getDataAgendamento().toInstant();
			LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
			consulta.setMedico(m);
			consulta.setPaciente(p);
			
			validarDataRetroativa(localDate, LocalDate.now());
			dao.salvarConsulta(consulta);
		}
		
	}

	private void validarDataRetroativa(LocalDate dataAgendamento, LocalDate now) {

		ChronoLocalDate d = now;

		boolean condicao = dataAgendamento.isBefore(d);
		if (condicao) {
			throw new RuntimeException("Nao é permitido agendamento para data retroativa!");
		}

	}

	public void reagendarConsulta(Consulta consulta) {
		dao.reagendarConsulta(consulta);
	}

	public void deletarConsulta(Long id) {
		dao.deletarConsulta(id);
	}
}
