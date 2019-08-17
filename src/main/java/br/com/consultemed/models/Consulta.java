package br.com.consultemed.models;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "TB_CONSULTA")
public class Consulta {
	
	@Getter
	@Setter
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Getter
	@Setter
	private Date dataAgendamento;
	
	@Column
	@Getter
	@Setter
	private Date dataCancelamento;
	
	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	@Column
	private StatusConsulta statusConsulta;
	
	@Getter
	@Setter
	@ManyToOne
	@Inject
	private Medico medico;
	
	@Getter
	@Setter
	@OneToOne
	@Inject
	private Paciente paciente;

}
