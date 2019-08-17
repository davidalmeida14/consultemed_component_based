package br.com.consultemed.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "TB_CONSULTA")
public class Consulta {
	
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Date dataAgendamento;
	
	@Column
	private Date dataCancelamento;
	
	@Enumerated(EnumType.STRING)
	@Column
	private StatusConsulta statusConsulta;
	
	@Column
	@ManyToMany
	private List<Medico> medico;
	
	@OneToOne
	private Paciente paciente;

}
