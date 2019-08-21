package br.com.consultemed.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.swing.text.DateFormatter;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.models.StatusConsulta;
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

		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = consulta.getDataAgendamento().toInstant();
		LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
		
		validarDataRetroativa(localDate, LocalDate.now());
		
		List<Consulta> ret = dao.validarConsultaDataHoraPorMedico(consulta);
		
		if(ret.size() > 0 ) {
			throw new RuntimeException("Já existe uma consulta nesse mesmo horário para o mesmo médico.");
		}
		
		Paciente p = pacienteService.buscarPacientePorCPF(consulta.getPaciente().getCpf());
		Medico m = medicoService.buscarMedicoPorCRM(consulta.getMedico().getCrm());

		boolean pacienteValidado = validarPaciente(p);
		boolean medicoValidado = validarMedico(m);
		
		if(!pacienteValidado) {
			throw new RuntimeException("Paciente não encontrado.");
		}
		
		if(!medicoValidado) {
			throw new RuntimeException("Médico não encontrado.");
		}
		
		consulta.setMedico(m);
		consulta.setPaciente(p);
		consulta.setStatusConsulta(StatusConsulta.AGENDADO);
		
		dao.salvarConsulta(consulta);

	}

	public List<Consulta> consultarAgendamentosPorData(LocalDate data){
		
		List<Consulta> consultas = new ArrayList<Consulta>();
		if(Objects.isNull(data)) {
			throw new RuntimeException("Data não informada!");
		}
		
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		consultas = dao.consultarAgendamentosPorData(dataFormatada);
		
		return consultas;
	}
	private boolean validarMedico(Medico m) {
		if (Objects.isNull(m)) {
			
			return false;
		} else {
			return true;
		}
	}

	private boolean validarPaciente(Paciente p) {
		if (Objects.isNull(p)) {
			return false;
		} else {
			return true;
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
