package br.com.consultemed.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.PacienteService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class PacienteController {

	@Getter
	@Setter
	private List<Paciente> pacientes;

	@Inject
	@Getter
	@Setter
	private Paciente paciente;
	
	@Getter
	@Setter
	private Paciente pacienteEditar;
	
	@Inject
	PacienteService pacienteService;
	
	public List<Paciente> listarPacientes(){
		this.pacientes =  this.pacienteService.listarPacientes();
		return this.pacientes;
	}
	
	public String novoPaciente() {
		this.paciente = new Paciente();
		return "/pages/pacientes/addPacientes.xhtml?faces-redirect=true";
	}
	
	public String salvarPaciente() throws Exception {
		this.pacienteService.salvar(this.paciente);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente " +paciente.getNome()+ ", cadastrado com sucesso", null));
		listarPacientes();
		return "/pages/pacientes/pacientes.xhtml";
	}
	
	public String excluir() {
		this.paciente = this.pacienteEditar;
		this.pacienteService.deletar(this.paciente.getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Médico " + paciente.getNome()+ ", excluído com sucesso", null));
		listarPacientes();
		return "/pages/pacientes/pacientes.xhtml";
	}
}
