package br.com.j4business.saga.planejamento.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoCusto;
import br.com.j4business.saga.atributo.enumeration.AtributoPrazo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;


public class PlanejamentoForm {

	private long planejamentoPK;
	
    @NotBlank(message = "Nome é uma informação obrigatória.")
	@Size(min = 3,max = 200, message = "O nome não pode ter menos que 3 e mais que 100 caracteres")
	private String planejamentoNome;
	
    @NotBlank(message = "Descrição é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "A descrição não pode ter menos que 5 e mais que 200 caracteres")
	private String planejamentoDescricao;

    @NotBlank(message = "O Gestor da Ação é uma informação obrigatória.")
	@NotNull(message = "O Gestor da Ação é uma informação obrigatória.")
	private String planejamentoGestor;

    @NotBlank(message = "O Dono da Ação é uma informação obrigatória.")
	@NotNull(message = "O Dono da Ação é uma informação obrigatória.")
	private String planejamentoDono;

	@NotNull(message = "Data prevista de início é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoDataPrevistaInicio;

	@NotNull(message = "Data prevista de término é uma informação obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoDataPrevistaTermino;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoDataRealInicio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  planejamentoDataRealTermino;

	@NotNull(message = "Status do prazo é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoPrazo planejamentoPrazoStatus;

	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
    private Double   planejamentoCustoPrevisto;

	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private Double  planejamentoCustoFinal;

	@NotNull(message = "Status do Custo é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoCusto planejamentoCustoStatus;

	@NotNull(message = "Status da Aprovação é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoAprovacao planejamentoAprovacao;

	@NotNull(message = "Status do Planejamento é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus planejamentoStatus;

    @NotBlank(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String planejamentoResponsavel;

    @NotBlank(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String planejamentoMotivoOperacao;

	private List<Acao> acaoList = new ArrayList<Acao>();

	private List<PlanejamentoAcaoBean> planejamentoAcaoBeanList = new ArrayList<PlanejamentoAcaoBean>();
	
	public PlanejamentoForm() {
		super();
	}

	public long getPlanejamentoPK() {
		return planejamentoPK;
	}

	public void setPlanejamentoPK(long planejamentoPK) {
		this.planejamentoPK = planejamentoPK;
	}

	public String getPlanejamentoNome() {
		return planejamentoNome;
	}

	public void setPlanejamentoNome(String planejamentoNome) {
		this.planejamentoNome = planejamentoNome;
	}

	public String getPlanejamentoDescricao() {
		return planejamentoDescricao;
	}

	public void setPlanejamentoDescricao(String planejamentoDescricao) {
		this.planejamentoDescricao = planejamentoDescricao;
	}

	public String getPlanejamentoResponsavel() {
		return planejamentoResponsavel;
	}

	public void setPlanejamentoResponsavel(String planejamentoResponsavel) {
		this.planejamentoResponsavel = planejamentoResponsavel;
	}

	public String getPlanejamentoMotivoOperplanejamento() {
		return planejamentoMotivoOperacao;
	}

	public void setPlanejamentoMotivoOperplanejamento(String planejamentoMotivoOperacao) {
		this.planejamentoMotivoOperacao = planejamentoMotivoOperacao;
	}

	public String getPlanejamentoGestor() {
		return planejamentoGestor;
	}

	public void setPlanejamentoGestor(String planejamentoGestor) {
		this.planejamentoGestor = planejamentoGestor;
	}

	public String getPlanejamentoDono() {
		return planejamentoDono;
	}

	public void setPlanejamentoDono(String planejamentoDono) {
		this.planejamentoDono = planejamentoDono;
	}

	public AtributoAprovacao getPlanejamentoAprovacao() {
		return planejamentoAprovacao;
	}

	public void setPlanejamentoAprovacao(AtributoAprovacao planejamentoAprovacao) {
		this.planejamentoAprovacao = planejamentoAprovacao;
	}

	public String getPlanejamentoDataPrevistaInicio() {
		return planejamentoDataPrevistaInicio;
	}

	public void setPlanejamentoDataPrevistaInicio(String planejamentoDataPrevistaInicio) {
		this.planejamentoDataPrevistaInicio = planejamentoDataPrevistaInicio;
	}

	public String getPlanejamentoDataPrevistaTermino() {
		return planejamentoDataPrevistaTermino;
	}

	public void setPlanejamentoDataPrevistaTermino(String planejamentoDataPrevistaTermino) {
		this.planejamentoDataPrevistaTermino = planejamentoDataPrevistaTermino;
	}

	public String getPlanejamentoDataRealInicio() {
		return planejamentoDataRealInicio;
	}

	public void setPlanejamentoDataRealInicio(String planejamentoDataRealInicio) {
		this.planejamentoDataRealInicio = planejamentoDataRealInicio;
	}

	public String getPlanejamentoDataRealTermino() {
		return planejamentoDataRealTermino;
	}

	public void setPlanejamentoDataRealTermino(String planejamentoDataRealTermino) {
		this.planejamentoDataRealTermino = planejamentoDataRealTermino;
	}

	public void setPlanejamentoPrazoStatus(AtributoPrazo planejamentoPrazoStatus) {
		this.planejamentoPrazoStatus = planejamentoPrazoStatus;
	}

	public void setPlanejamentoCustoStatus(AtributoCusto planejamentoCustoStatus) {
		this.planejamentoCustoStatus = planejamentoCustoStatus;
	}

	public AtributoPrazo getPlanejamentoPrazoStatus() {
		return planejamentoPrazoStatus;
	}

	public AtributoCusto getPlanejamentoCustoStatus() {
		return planejamentoCustoStatus;
	}

	public Double getPlanejamentoCustoPrevisto() {
		return planejamentoCustoPrevisto;
	}

	public void setPlanejamentoCustoPrevisto(Double planejamentoCustoPrevisto) {
		this.planejamentoCustoPrevisto = planejamentoCustoPrevisto;
	}

	public Double getPlanejamentoCustoFinal() {
		return planejamentoCustoFinal;
	}

	public void setPlanejamentoCustoFinal(Double planejamentoCustoFinal) {
		this.planejamentoCustoFinal = planejamentoCustoFinal;
	}

	public String getPlanejamentoMotivoOperacao() {
		return planejamentoMotivoOperacao;
	}

	public void setPlanejamentoMotivoOperacao(String planejamentoMotivoOperacao) {
		this.planejamentoMotivoOperacao = planejamentoMotivoOperacao;
	}

	public AtributoStatus getPlanejamentoStatus() {
		return planejamentoStatus;
	}

	public void setPlanejamentoStatus(AtributoStatus planejamentoStatus) {
		this.planejamentoStatus = planejamentoStatus;
	}

	public List<Acao> getAcaoList() {
		return acaoList;
	}

	public void setAcaoList(List<Acao> acaoList) {
		this.acaoList = acaoList;
	}

	public List<PlanejamentoAcaoBean> getPlanejamentoAcaoBeanList() {
		return planejamentoAcaoBeanList;
	}

	public void setPlanejamentoAcaoBeanList(List<PlanejamentoAcaoBean> planejamentoAcaoBeanList) {
		this.planejamentoAcaoBeanList = planejamentoAcaoBeanList;
	}

	
}
