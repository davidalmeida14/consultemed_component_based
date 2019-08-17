package br.com.consultemed.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "TB_PACIENTE")
public class Paciente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	@Column
	private String nome;

	@Getter
	@Setter
	@Column
	private String telefone;

	@Getter
	@Setter
	@Column
	private Date dataNascimento;

	@Getter
	@Setter
	@Column
	private String cpf;
	
	@Getter
	@Setter
	@OneToOne
	private Consulta consulta;
}
