package br.com.j4business.saga.treinamento.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoCusto;
import br.com.j4business.saga.atributo.enumeration.AtributoPrazo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class TreinamentoForm {

	private long treinamentoPK;
	
    @NotEmpty(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String treinamentoNome;
	
    @NotEmpty(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String treinamentoDescricao;

    @NotEmpty(message = "O Gestor da Ação é uma informação obrigatória.")
	@NotNull(message = "O Gestor da Ação é uma informação obrigatória.")
	private String treinamentoGestor;

    @NotEmpty(message = "O Dono da Ação é uma informação obrigatória.")
	@NotNull(message = "O Dono da Ação é uma informação obrigatória.")
	private String treinamentoDono;

	@NotNull(message = "Data prevista de início é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoDataPrevistaInicio;

	@NotNull(message = "Data prevista de término é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoDataPrevistaTermino;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoDataRealInicio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  treinamentoDataRealTermino;

	@NotNull(message = "Status do prazo é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoPrazo treinamentoPrazoStatus;

	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
    private Double   treinamentoCustoPrevisto;

	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private Double  treinamentoCustoFinal;

	@NotNull(message = "Status do Custo é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoCusto treinamentoCustoStatus;

	@NotNull(message = "Status da Aprovação é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoAprovacao treinamentoAprovacao;

	@NotNull(message = "Status do Treinamento é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus treinamentoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String treinamentoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String treinamentoMotivoOperacao;

    
	public TreinamentoForm() {
		super();
	}

	public long getTreinamentoPK() {
		return treinamentoPK;
	}

	public void setTreinamentoPK(long treinamentoPK) {
		this.treinamentoPK = treinamentoPK;
	}

	public String getTreinamentoNome() {
		return treinamentoNome;
	}

	public void setTreinamentoNome(String treinamentoNome) {
		this.treinamentoNome = treinamentoNome;
	}

	public String getTreinamentoDescricao() {
		return treinamentoDescricao;
	}

	public void setTreinamentoDescricao(String treinamentoDescricao) {
		this.treinamentoDescricao = treinamentoDescricao;
	}

	public AtributoStatus getTreinamentoStatus() {
		return treinamentoStatus;
	}

	public void setTreinamentoStatus(AtributoStatus treinamentoStatus) {
		this.treinamentoStatus = treinamentoStatus;
	}

	public String getTreinamentoResponsavel() {
		return treinamentoResponsavel;
	}

	public void setTreinamentoResponsavel(String treinamentoResponsavel) {
		this.treinamentoResponsavel = treinamentoResponsavel;
	}

	public String getTreinamentoMotivoOperacao() {
		return treinamentoMotivoOperacao;
	}

	public void setTreinamentoMotivoOperacao(String treinamentoMotivoOperacao) {
		this.treinamentoMotivoOperacao = treinamentoMotivoOperacao;
	}

	public String getTreinamentoGestor() {
		return treinamentoGestor;
	}

	public void setTreinamentoGestor(String treinamentoGestor) {
		this.treinamentoGestor = treinamentoGestor;
	}

	public String getTreinamentoDono() {
		return treinamentoDono;
	}

	public void setTreinamentoDono(String treinamentoDono) {
		this.treinamentoDono = treinamentoDono;
	}

	public String getTreinamentoDataPrevistaInicio() {
		return treinamentoDataPrevistaInicio;
	}

	public void setTreinamentoDataPrevistaInicio(String treinamentoDataPrevistaInicio) {
		this.treinamentoDataPrevistaInicio = treinamentoDataPrevistaInicio;
	}

	public String getTreinamentoDataPrevistaTermino() {
		return treinamentoDataPrevistaTermino;
	}

	public void setTreinamentoDataPrevistaTermino(String treinamentoDataPrevistaTermino) {
		this.treinamentoDataPrevistaTermino = treinamentoDataPrevistaTermino;
	}

	public String getTreinamentoDataRealInicio() {
		return treinamentoDataRealInicio;
	}

	public void setTreinamentoDataRealInicio(String treinamentoDataRealInicio) {
		this.treinamentoDataRealInicio = treinamentoDataRealInicio;
	}

	public String getTreinamentoDataRealTermino() {
		return treinamentoDataRealTermino;
	}

	public void setTreinamentoDataRealTermino(String treinamentoDataRealTermino) {
		this.treinamentoDataRealTermino = treinamentoDataRealTermino;
	}

	public AtributoPrazo getTreinamentoPrazoStatus() {
		return treinamentoPrazoStatus;
	}

	public void setTreinamentoPrazoStatus(AtributoPrazo treinamentoPrazoStatus) {
		this.treinamentoPrazoStatus = treinamentoPrazoStatus;
	}

	public Double getTreinamentoCustoPrevisto() {
		return treinamentoCustoPrevisto;
	}

	public void setTreinamentoCustoPrevisto(Double treinamentoCustoPrevisto) {
		this.treinamentoCustoPrevisto = treinamentoCustoPrevisto;
	}

	public Double getTreinamentoCustoFinal() {
		return treinamentoCustoFinal;
	}

	public void setTreinamentoCustoFinal(Double treinamentoCustoFinal) {
		this.treinamentoCustoFinal = treinamentoCustoFinal;
	}

	public AtributoCusto getTreinamentoCustoStatus() {
		return treinamentoCustoStatus;
	}

	public void setTreinamentoCustoStatus(AtributoCusto treinamentoCustoStatus) {
		this.treinamentoCustoStatus = treinamentoCustoStatus;
	}

	public AtributoAprovacao getTreinamentoAprovacao() {
		return treinamentoAprovacao;
	}

	public void setTreinamentoAprovacao(AtributoAprovacao treinamentoAprovacao) {
		this.treinamentoAprovacao = treinamentoAprovacao;
	}

}
