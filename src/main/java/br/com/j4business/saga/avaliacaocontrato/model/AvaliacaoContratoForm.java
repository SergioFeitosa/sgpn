package br.com.j4business.saga.avaliacaocontrato.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AvaliacaoContratoForm {

	private long avaliacaoContratoPK;
	private long avaliacaoPK;
	private long estruturafisicaPK;
	private long contratoPK;
	private long questionarioPK;
	private long unidadeorganizacionalPK;
	
	// Ambiente
	private int elementoContratoDocumentacao = 0;
	private int elementoContratoDocumentacaoAcessibilidade = 0;
	private int elementoContratoDocumentacaoAtualizacao = 0;
	private int elementoContratoDocumentacaoVerificacao = 0;
	private int elementoContratoFramework = 0;
	private int elementoContratoHardware = 0;
	private int elementoContratoHorario = 0;
	private int elementoContratoMelhoriaContinua = 0;
	private int elementoContratoMelhoriaContinuaQuebraAcordo = 0;
	private int elementoContratoMudancaConstante = 0;
	private int elementoContratoNivelColaborador = 0;
	private int elementoContratoPagamento = 0;
	private int elementoContratoPagamentoExtra = 0;
	private int elementoContratoPagamentoQuebraAcordo = 0;
	private int elementoContratoPropostaApresentacao = 0;
	private int elementoContratoQuantidadeColaborador = 0;
	private int elementoContratoServicoNivel = 0;
	private int elementoContratoServicoPrestado = 0;
	private int elementoContratoServicoQuebraAcordo = 0;
	private int elementoContratoSoftware = 0;
	
    @NotEmpty(message = "Nome da Avaliacao é uma informação obrigatória.")
	@NotNull(message = "Nome da Avaliacao é uma informação obrigatória.")
	private String avaliacaoNome;

    @NotEmpty(message = "Nome da Estrutura Física é uma informação obrigatória.")
	@NotNull(message = "Nome da Estrutura Física é uma informação obrigatória.")
	private String estruturafisicaNome;
	
    @NotEmpty(message = "Nome do contrato é uma informação obrigatória.")
	@NotNull(message = "Nome do contrato é uma informação obrigatória.")
	private String contratoNome;
	
    @NotEmpty(message = "Nome do questionário é uma informação obrigatória.")
	@NotNull(message = "Nome do questionário é uma informação obrigatória.")
	private String questionarioNome;
	
    @NotEmpty(message = "Nome da Unidade Organizacional é uma informação obrigatória.")
	@NotNull(message = "Nome da Unidade Organizacional é uma informação obrigatória.")
	private String unidadeorganizacionalNome;
	
    @DateTimeFormat(pattern = "MM/yyyy")
	private String avaliacaoContratoPeriodo;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  avaliacaoContratoDataCriacao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus avaliacaoContratoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String avaliacaoContratoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String avaliacaoContratoMotivoOperacao;

	public AvaliacaoContratoForm() {
		super();
	}

	public long getContratoPK() {
		return contratoPK;
	}

	public void setContratoPK(long contratoPK) {
		this.contratoPK = contratoPK;
	}

	public String getContratoNome() {
		return contratoNome;
	}

	public void setContratoNome(String contratoNome) {
		this.contratoNome = contratoNome;
	}

	public long getAvaliacaoContratoPK() {
		return avaliacaoContratoPK;
	}

	public void setAvaliacaoContratoPK(long avaliacaoContratoPK) {
		this.avaliacaoContratoPK = avaliacaoContratoPK;
	}

	public long getAvaliacaoPK() {
		return avaliacaoPK;
	}

	public void setAvaliacaoPK(long avaliacaoPK) {
		this.avaliacaoPK = avaliacaoPK;
	}

	public String getAvaliacaoNome() {
		return avaliacaoNome;
	}

	public void setAvaliacaoNome(String avaliacaoNome) {
		this.avaliacaoNome = avaliacaoNome;
	}

	public String getAvaliacaoContratoDataCriacao() {
		return avaliacaoContratoDataCriacao;
	}

	public void setAvaliacaoContratoDataCriacao(String avaliacaoContratoDataCriacao) {
		this.avaliacaoContratoDataCriacao = avaliacaoContratoDataCriacao;
	}

	public AtributoStatus getAvaliacaoContratoStatus() {
		return avaliacaoContratoStatus;
	}

	public void setAvaliacaoContratoStatus(AtributoStatus avaliacaoContratoStatus) {
		this.avaliacaoContratoStatus = avaliacaoContratoStatus;
	}

	public String getAvaliacaoContratoResponsavel() {
		return avaliacaoContratoResponsavel;
	}

	public void setAvaliacaoContratoResponsavel(String avaliacaoContratoResponsavel) {
		this.avaliacaoContratoResponsavel = avaliacaoContratoResponsavel;
	}

	public String getAvaliacaoContratoMotivoOperacao() {
		return avaliacaoContratoMotivoOperacao;
	}

	public void setAvaliacaoContratoMotivoOperacao(String avaliacaoContratoMotivoOperacao) {
		this.avaliacaoContratoMotivoOperacao = avaliacaoContratoMotivoOperacao;
	}

	public String getQuestionarioNome() {
		return questionarioNome;
	}

	public void setQuestionarioNome(String questionarioNome) {
		this.questionarioNome = questionarioNome;
	}

	public long getQuestionarioPK() {
		return questionarioPK;
	}

	public void setQuestionarioPK(long questionarioPK) {
		this.questionarioPK = questionarioPK;
	}

	public String getEstruturafisicaNome() {
		return estruturafisicaNome;
	}

	public void setEstruturafisicaNome(String estruturafisicaNome) {
		this.estruturafisicaNome = estruturafisicaNome;
	}

	public String getUnidadeorganizacionalNome() {
		return unidadeorganizacionalNome;
	}

	public void setUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		this.unidadeorganizacionalNome = unidadeorganizacionalNome;
	}

	public long getEstruturafisicaPK() {
		return estruturafisicaPK;
	}

	public void setEstruturafisicaPK(long estruturafisicaPK) {
		this.estruturafisicaPK = estruturafisicaPK;
	}

	public long getUnidadeorganizacionalPK() {
		return unidadeorganizacionalPK;
	}

	public void setUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		this.unidadeorganizacionalPK = unidadeorganizacionalPK;
	}

	public String getAvaliacaoContratoPeriodo() {
		return avaliacaoContratoPeriodo;
	}

	public void setAvaliacaoContratoPeriodo(String avaliacaoContratoPeriodo) {
		this.avaliacaoContratoPeriodo = avaliacaoContratoPeriodo;
	}

	public int getElementoContratoDocumentacao() {
		return elementoContratoDocumentacao;
	}

	public void setElementoContratoDocumentacao(int elementoContratoDocumentacao) {
		this.elementoContratoDocumentacao = elementoContratoDocumentacao;
	}

	public int getElementoContratoDocumentacaoAcessibilidade() {
		return elementoContratoDocumentacaoAcessibilidade;
	}

	public void setElementoContratoDocumentacaoAcessibilidade(int elementoContratoDocumentacaoAcessibilidade) {
		this.elementoContratoDocumentacaoAcessibilidade = elementoContratoDocumentacaoAcessibilidade;
	}

	public int getElementoContratoDocumentacaoAtualizacao() {
		return elementoContratoDocumentacaoAtualizacao;
	}

	public void setElementoContratoDocumentacaoAtualizacao(int elementoContratoDocumentacaoAtualizacao) {
		this.elementoContratoDocumentacaoAtualizacao = elementoContratoDocumentacaoAtualizacao;
	}

	public int getElementoContratoDocumentacaoVerificacao() {
		return elementoContratoDocumentacaoVerificacao;
	}

	public void setElementoContratoDocumentacaoVerificacao(int elementoContratoDocumentacaoVerificacao) {
		this.elementoContratoDocumentacaoVerificacao = elementoContratoDocumentacaoVerificacao;
	}

	public int getElementoContratoFramework() {
		return elementoContratoFramework;
	}

	public void setElementoContratoFramework(int elementoContratoFramework) {
		this.elementoContratoFramework = elementoContratoFramework;
	}

	public int getElementoContratoHardware() {
		return elementoContratoHardware;
	}

	public void setElementoContratoHardware(int elementoContratoHardware) {
		this.elementoContratoHardware = elementoContratoHardware;
	}

	public int getElementoContratoHorario() {
		return elementoContratoHorario;
	}

	public void setElementoContratoHorario(int elementoContratoHorario) {
		this.elementoContratoHorario = elementoContratoHorario;
	}

	public int getElementoContratoMelhoriaContinua() {
		return elementoContratoMelhoriaContinua;
	}

	public void setElementoContratoMelhoriaContinua(int elementoContratoMelhoriaContinua) {
		this.elementoContratoMelhoriaContinua = elementoContratoMelhoriaContinua;
	}

	public int getElementoContratoMelhoriaContinuaQuebraAcordo() {
		return elementoContratoMelhoriaContinuaQuebraAcordo;
	}

	public void setElementoContratoMelhoriaContinuaQuebraAcordo(int elementoContratoMelhoriaContinuaQuebraAcordo) {
		this.elementoContratoMelhoriaContinuaQuebraAcordo = elementoContratoMelhoriaContinuaQuebraAcordo;
	}

	public int getElementoContratoMudancaConstante() {
		return elementoContratoMudancaConstante;
	}

	public void setElementoContratoMudancaConstante(int elementoContratoMudancaConstante) {
		this.elementoContratoMudancaConstante = elementoContratoMudancaConstante;
	}

	public int getElementoContratoNivelColaborador() {
		return elementoContratoNivelColaborador;
	}

	public void setElementoContratoNivelColaborador(int elementoContratoNivelColaborador) {
		this.elementoContratoNivelColaborador = elementoContratoNivelColaborador;
	}

	public int getElementoContratoPagamento() {
		return elementoContratoPagamento;
	}

	public void setElementoContratoPagamento(int elementoContratoPagamento) {
		this.elementoContratoPagamento = elementoContratoPagamento;
	}

	public int getElementoContratoPagamentoExtra() {
		return elementoContratoPagamentoExtra;
	}

	public void setElementoContratoPagamentoExtra(int elementoContratoPagamentoExtra) {
		this.elementoContratoPagamentoExtra = elementoContratoPagamentoExtra;
	}

	public int getElementoContratoPagamentoQuebraAcordo() {
		return elementoContratoPagamentoQuebraAcordo;
	}

	public void setElementoContratoPagamentoQuebraAcordo(int elementoContratoPagamentoQuebraAcordo) {
		this.elementoContratoPagamentoQuebraAcordo = elementoContratoPagamentoQuebraAcordo;
	}

	public int getElementoContratoPropostaApresentacao() {
		return elementoContratoPropostaApresentacao;
	}

	public void setElementoContratoPropostaApresentacao(int elementoContratoPropostaApresentacao) {
		this.elementoContratoPropostaApresentacao = elementoContratoPropostaApresentacao;
	}

	public int getElementoContratoQuantidadeColaborador() {
		return elementoContratoQuantidadeColaborador;
	}

	public void setElementoContratoQuantidadeColaborador(int elementoContratoQuantidadeColaborador) {
		this.elementoContratoQuantidadeColaborador = elementoContratoQuantidadeColaborador;
	}

	public int getElementoContratoServicoNivel() {
		return elementoContratoServicoNivel;
	}

	public void setElementoContratoServicoNivel(int elementoContratoServicoNivel) {
		this.elementoContratoServicoNivel = elementoContratoServicoNivel;
	}

	public int getElementoContratoServicoPrestado() {
		return elementoContratoServicoPrestado;
	}

	public void setElementoContratoServicoPrestado(int elementoContratoServicoPrestado) {
		this.elementoContratoServicoPrestado = elementoContratoServicoPrestado;
	}

	public int getElementoContratoServicoQuebraAcordo() {
		return elementoContratoServicoQuebraAcordo;
	}

	public void setElementoContratoServicoQuebraAcordo(int elementoContratoServicoQuebraAcordo) {
		this.elementoContratoServicoQuebraAcordo = elementoContratoServicoQuebraAcordo;
	}

	public int getElementoContratoSoftware() {
		return elementoContratoSoftware;
	}

	public void setElementoContratoSoftware(int elementoContratoSoftware) {
		this.elementoContratoSoftware = elementoContratoSoftware;
	}

}
