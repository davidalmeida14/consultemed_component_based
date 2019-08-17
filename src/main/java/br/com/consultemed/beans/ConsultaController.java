package br.com.consultemed.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.ConsultaService;
import br.com.consultemed.services.MedicoService;
import br.com.consultemed.services.PacienteService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class ConsultaController {

	@Getter
	@Setter
	private List<Consulta> consultas;
	
	@Getter
	@Setter
	private String pacientes;

	@Inject
	@Getter
	@Setter
	private Consulta consulta;

	@Getter
	@Setter
	private Consulta consultaEditar;

	@Getter
	@Setter
	private String txt1;

	@Getter
	@Setter
	private String txt2;

	@Inject
	ConsultaService consultaService;

	@Inject
	private MedicoService medicoService;

	@Inject
	private PacienteService pacienteService;

	public List<Consulta> listarConsultas() {
		this.consultas = consultaService.listarConsultas();
		return this.consultas;
	}

	public String novaConsulta() {
		this.consulta = new Consulta();
		return "/pages/consultas/addConsultas.xhtml?faces-redirect=true";
	}

	public String salvarConsulta() throws Exception {
		Medico medico = new Medico();
		medico = medicoService.listaMedicoPorNome(this.txt1.trim()).stream().findFirst().get();
		Paciente paciente = new Paciente();
		paciente = pacienteService.listarPacientesPorNome(this.txt2.trim()).stream().findFirst().get();

		this.consulta.setPaciente(paciente);
		this.consulta.setMedico(Arrays.asList(medico));

		this.consultaService.salvarConsulta(this.consulta);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Consulta para o paciente" + consulta.getPaciente().getNome() + ", agendada com sucesso", null));
		listarConsultas();
		return "/pages/consultas/consultas.xhtml";
	}

	public String excluir() {
		this.consulta = this.consultaEditar;
		this.consultaService.deletarConsulta(this.consulta.getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				"Consulta com o paciente " + consulta.getPaciente().getNome() + ", excluída com sucesso", null));
		listarConsultas();
		return "/pages/consultas/consultas.xhtml";
	}

	public List<String> completeText(String query) throws Exception {
		List<Medico> medicos = medicoService.listaMedicoPorNome(query);
		List<String> medicosNome = new ArrayList<String>();

		medicos.forEach(m -> {
			if (m.getNome().contains(query)) {
				medicosNome.add(m.getNome());
			}
		});

		return medicosNome;
	}

	public List<String> completePaciente(String query) throws Exception {
		List<Paciente> pacientes = pacienteService.listarPacientes();
		List<String> pacientesNome = new ArrayList<String>();
		pacientes.forEach(p -> {
			if (p.getNome().contains(query)) {
				pacientesNome.add(p.getNome());
			}
		});

		return pacientesNome;
	}

	public void onItemSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Item Selected", event.getObject().toString()));
		this.pacientes = event.getObject().toString();
	}

}
