package br.com.j4business.saga.servicoprocesso.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.servico.model.Servico;

@Entity
@Table(name = "servico_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_servico","id_processo"}, name="servicoProcessoUnique"))

public class ServicoProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_servicoprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long servicoProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_servico")
	private Servico servico;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "sq_servicoprocesso",nullable = false, length = 200)
	private int servicoProcessoSequencia;

	@Column(name = "sp_status")
	private String servicoProcessoStatus;

	@Column(name = "sp_motivooperacao")
	private String servicoProcessoMotivoOperacao;

	
	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	public ServicoProcesso() {
		super();
	}

	public ServicoProcesso(Servico servico, Processo processo, int servicoProcessoSequencia) {
		super();
		this.servico = servico;
		this.processo = processo;
		this.servicoProcessoSequencia = servicoProcessoSequencia;
	}

	public long getServicoProcessoPK() {
		return servicoProcessoPK;
	}

	public void setServicoProcessoPK(long servicoProcessoPK) {
		this.servicoProcessoPK = servicoProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((servico.getServicoNome() == null) ? 0 : servico.getServicoNome().hashCode());
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
		result = prime * result + ((servicoProcessoSequencia+"" == null) ? 0 : servicoProcessoSequencia+"".hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoProcesso other = (ServicoProcesso) obj;
		if (servico.getServicoNome() == null) {
			if (other.servico.getServicoNome() != null)
				return false;
		} else if (!servico.getServicoNome().equals(other.servico.getServicoNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public int getServicoProcessoSequencia() {
		return servicoProcessoSequencia;
	}

	public void setServicoProcessoSequencia(int servicoProcessoSequencia) {
		this.servicoProcessoSequencia = servicoProcessoSequencia;
	}

	public String getServicoProcessoStatus() {
		return servicoProcessoStatus;
	}

	public void setServicoProcessoStatus(String servicoProcessoStatus) {
		this.servicoProcessoStatus = servicoProcessoStatus;
	}

	public String getServicoProcessoMotivoOperacao() {
		return servicoProcessoMotivoOperacao;
	}

	public void setServicoProcessoMotivoOperacao(String servicoProcessoMotivoOperacao) {
		this.servicoProcessoMotivoOperacao = servicoProcessoMotivoOperacao;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}


}
