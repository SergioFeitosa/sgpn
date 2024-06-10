package br.com.j4business.saga.acao.model;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.planejamento.model.Planejamento;

@Entity
@Table(name = "acao", uniqueConstraints=@UniqueConstraint(columnNames={"nm_acao"}, name="acaoNome"))
@RequestMapping("/saga")
@Data
public class Acao {

	@Id
	@Column(name = "id_acao") // ac
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long acaoPK;
	
	@Column(name = "nm_acao",nullable = false, length = 100)
	private String acaoNome;
	
	@Column(name = "ds_acao")
	private String acaoDescricao;

	@ManyToOne
	private Colaborador colaboradorGestor;

	@ManyToOne
	private Colaborador colaboradorDono;

	@Column(name = "id_acaoaprovacao")
	private String acaoAprovacao;

	@Column(name = "ac_status")
	private String acaoStatus;

	@Column(name = "ac_motivooperacao")
	private String acaoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;

	@ManyToMany
	@JoinTable(name="join_planejamento_acao",joinColumns= { @JoinColumn(name="acaoPK")},
										inverseJoinColumns= {@JoinColumn(name="planejamentoPK")})
	private List<Planejamento> planejamentoList = new ArrayList<Planejamento>();

}
