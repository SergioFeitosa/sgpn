package br.com.j4business.saga.colaboradorprocesso.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.processo.model.Processo;

@Entity
@Table(name = "colaborador_processo", uniqueConstraints=@UniqueConstraint(columnNames={"id_colaborador","id_processo"}, name="colaboradorProcessoUnique"))
public class ColaboradorProcesso implements Serializable{

	private static final long serialVersionUID = -1887519042020026757L;

	@Id
	@Column(name = "id_colaboradorprocesso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long colaboradorProcessoPK;
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;    
	
	@ManyToOne
	@JoinColumn(name="id_processo")
	private Processo processo;
	    
	@Column(name = "cp_status",nullable = false, length = 200)
	private String colaboradorProcessoStatus;

	@Column(name = "cp_motivooperacao",nullable = false, length = 200)
	private String colaboradorProcessoMotivoOperacao;

	@ManyToOne
	private Colaborador colaboradorResponsavel;
	
	@Column(name = "dt_colaboradorprocessodatainicio")
    private String  colaboradorProcessoDataInicio;

	@Column(name = "dt_colaboradorprocessodatatermino")
    private String  colaboradorProcessoDataTermino;

	
	public ColaboradorProcesso() {
		super();
	}

	public ColaboradorProcesso(Colaborador colaborador, Processo processo, int colaboradorProcessoSequencia) {
		super();
		this.colaborador = colaborador;
		this.processo = processo;
	}

	public long getColaboradorProcessoPK() {
		return colaboradorProcessoPK;
	}

	public void setColaboradorProcessoPK(long colaboradorProcessoPK) {
		this.colaboradorProcessoPK = colaboradorProcessoPK;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colaborador.getPessoaNome() == null) ? 0 : colaborador.getPessoaNome().hashCode());
		result = prime * result + ((processo.getProcessoNome() == null) ? 0 : processo.getProcessoNome().hashCode());
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
		ColaboradorProcesso other = (ColaboradorProcesso) obj;
		if (colaborador.getPessoaNome() == null) {
			if (other.colaborador.getPessoaNome() != null)
				return false;
		} else if (!colaborador.getPessoaNome().equals(other.colaborador.getPessoaNome()))
			return false;
		if (processo.getProcessoNome() == null) {
			if (other.processo.getProcessoNome() != null)
				return false;
		} else if (!processo.getProcessoNome().equals(other.processo.getProcessoNome()))
			return false;
		return true;
	}

	public Colaborador getColaboradorResponsavel() {
		return colaboradorResponsavel;
	}

	public void setColaboradorResponsavel(Colaborador colaboradorResponsavel) {
		this.colaboradorResponsavel = colaboradorResponsavel;
	}

	public String getColaboradorProcessoDataInicio() {
		return colaboradorProcessoDataInicio;
	}

	public void setColaboradorProcessoDataInicio(String colaboradorProcessoDataInicio) {
		this.colaboradorProcessoDataInicio = colaboradorProcessoDataInicio;
	}

	public String getColaboradorProcessoDataTermino() {
		return colaboradorProcessoDataTermino;
	}

	public void setColaboradorProcessoDataTermino(String colaboradorProcessoDataTermino) {
		this.colaboradorProcessoDataTermino = colaboradorProcessoDataTermino;
	}

	public String getColaboradorProcessoStatus() {
		return colaboradorProcessoStatus;
	}

	public void setColaboradorProcessoStatus(String colaboradorProcessoStatus) {
		this.colaboradorProcessoStatus = colaboradorProcessoStatus;
	}

	public String getColaboradorProcessoMotivoOperacao() {
		return colaboradorProcessoMotivoOperacao;
	}

	public void setColaboradorProcessoMotivoOperacao(String colaboradorProcessoMotivoOperacao) {
		this.colaboradorProcessoMotivoOperacao = colaboradorProcessoMotivoOperacao;
	}

}
