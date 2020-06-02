package br.com.j4business.saga.avaliacaoprocesso.model;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AvaliacaoProcessoForm {

	private long avaliacaoProcessoPK;
	private long avaliacaoPK;
	private long estruturafisicaPK;
	private long processoPK;
	private long questionarioPK;
	private long unidadeorganizacionalPK;
	
	// Ambiente
	private int elementoEspacoFisico = 0;
	private int elementoIluminacao = 0;
	private int elementoSomAmbiente = 0;
	private int elementoUmidade = 0;
	private int elementoVentilacao = 0;
	private int elementoConforto = 0;
	private int elementoPrivacidade = 0;
	private int elementoColorimetria = 0;
	private int elementoPraticidade = 0;
	private int elementoEntretenimento = 0;
	private int elementoHigiene = 0;

	// Área Tecnica
	private int elementoAparelhoTecnico01 = 0;
	private int elementoAparelhoTecnico02 = 0;
	private int elementoAparelhoTecnico03 = 0;
	private int elementoAparelhoTecnico04 = 0;
	private int elementoAparelhoTecnico05 = 0;
	
	// Desempenho
	private int elementoCliente = 0;
	private int elementoReclamacao = 0;
	private int elementoDesempenho01 = 0;
	private int elementoDesempenho02 = 0;
	private int elementoDesempenho03 = 0;
	private int elementoLucro = 0;
	private int elementoCompras = 0;
	private int elementoVendas = 0;
	
	// Financeiro
	private int elementoSalario = 0;
	private int elementoManutencao = 0;
	private int elementoTreinamento = 0;
	private int elementoAparelhoTecnicoFinanceiro = 0;
	private int elementoTecnologiaInformacaoFinanceiro = 0;
	
	// Logistica
	private int elementoIntegracao = 0;
	private int elementoAdministracaoMaterial = 0;
	private int elementoArmazenamento = 0;
	private int elementoDistribuicao = 0;
	private int elementoAdministracaoCompras = 0;
 	private int elementoTransporte = 0;
 	private int elementoLogistica01 = 0;
 	private int elementoLogistica02 = 0;
 	private int elementoLogistica03 = 0;
 		
	// Mobilia 
	private int elementoMesa = 0;
	private int elementoCadeira = 0;
	private int elementoBalcao = 0;
	private int elementoEstante = 0;
	private int elementoArmario = 0;
	private int elementoSofa = 0;
	private int elementoCortina = 0;
	
	// Pessoal
	private int elementoAgilidade = 0;
	private int elementoApresentacaoPessoal = 0;
	private int elementoAtendimento = 0;
	private int elementoConhecimentoTecnico = 0;
	private int elementoCortesia = 0;
	private int elementoDisponibilidade = 0;
	private int elementoEducacao = 0;
	private int elementoEquilibrioEmocional = 0;
	private int elementoInformacaoInstituicao = 0;
	private int elementoProAtividade = 0;
	private int elementoQuadroFuncionario = 0;
	private int elementoSimpatia = 0;
	private int elementoTempoUtilizado = 0;
	private int elementoVocabulario = 0;
	
	// Seguranca
	private int elementoSinalizacaoAviso = 0;
	private int elementoVigilante = 0;
	private int elementoCamera = 0;
	private int elementoSinalizacaoEmergencia = 0;
	private int elementoExtintorIncendio = 0;
	
	// Sustentabilidade
	private int elementoAguaPotavel = 0;
	private int elementoArCondicionado = 0;
	private int elementoBlocoAnotacao = 0;
	private int elementoCaronaSolidaria = 0;
	private int elementoCopoDescartavel = 0;
	private int elementoLampadaEconomica = 0;
	private int elementoLapisMadeiraReplantio = 0;
	private int elementoLixeiraReciclavel = 0;
	private int elementoPalestraSustentabilidade = 0;
	private int elementoPapelImpressora = 0;
	private int elementoTransporteColetivo = 0;
	private int elementoUsoEnergiaEletrica = 0;
	private int elementoPapelReciclado = 0;
	
	// Tecnologia
	private int elementoTecnologiaComputadorPCVelocidade = 0;
	private int elementoTecnologiaComputadorPCCapacidade = 0;
	private int elementoTecnologiaComputadorPCSom = 0;
	private int elementoTecnologiaImpressoraTipo = 0;
	private int elementoTecnologiaImpressoraVelocidade = 0;
	private int elementoTecnologiaMonitorTamanho = 0;
	private int elementoTecnologiaMonitorTipo = 0;
	private int elementoTecnologiaMouse = 0;
	private int elementoTecnologiaTabletVelocidade = 0;
	private int elementoTecnologiaTabletTamanho = 0;
	private int elementoTecnologiaWifiCapacidade = 0;
	private int elementoTecnologiaWifiVelocidade = 0;
	
	
	
	public int getElementoEspacoFisico() {
		return elementoEspacoFisico;
	}

	public void setElementoEspacoFisico(int elementoEspacoFisico) {
		this.elementoEspacoFisico = elementoEspacoFisico;
	}

	public int getElementoIluminacao() {
		return elementoIluminacao;
	}

	public void setElementoIluminacao(int elementoIluminacao) {
		this.elementoIluminacao = elementoIluminacao;
	}

	public int getElementoSomAmbiente() {
		return elementoSomAmbiente;
	}

	public void setElementoSomAmbiente(int elementoSomAmbiente) {
		this.elementoSomAmbiente = elementoSomAmbiente;
	}

	public int getElementoUmidade() {
		return elementoUmidade;
	}

	public void setElementoUmidade(int elementoUmidade) {
		this.elementoUmidade = elementoUmidade;
	}

	public int getElementoVentilacao() {
		return elementoVentilacao;
	}

	public void setElementoVentilacao(int elementoVentilacao) {
		this.elementoVentilacao = elementoVentilacao;
	}

	public int getElementoConforto() {
		return elementoConforto;
	}

	public void setElementoConforto(int elementoConforto) {
		this.elementoConforto = elementoConforto;
	}

	public int getElementoPrivacidade() {
		return elementoPrivacidade;
	}

	public void setElementoPrivacidade(int elementoPrivacidade) {
		this.elementoPrivacidade = elementoPrivacidade;
	}

	public int getElementoColorimetria() {
		return elementoColorimetria;
	}

	public void setElementoColorimetria(int elementoColorimetria) {
		this.elementoColorimetria = elementoColorimetria;
	}

	public int getElementoPraticidade() {
		return elementoPraticidade;
	}

	public void setElementoPraticidade(int elementoPraticidade) {
		this.elementoPraticidade = elementoPraticidade;
	}

	public int getElementoEntretenimento() {
		return elementoEntretenimento;
	}

	public void setElementoEntretenimento(int elementoEntretenimento) {
		this.elementoEntretenimento = elementoEntretenimento;
	}

	public int getElementoHigiene() {
		return elementoHigiene;
	}

	public void setElementoHigiene(int elementoHigiene) {
		this.elementoHigiene = elementoHigiene;
	}

	
    @NotEmpty(message = "Nome da Avaliacao é uma informação obrigatória.")
	@NotNull(message = "Nome da Avaliacao é uma informação obrigatória.")
	private String avaliacaoNome;

    @NotEmpty(message = "Nome da Estrutura Física é uma informação obrigatória.")
	@NotNull(message = "Nome da Estrutura Física é uma informação obrigatória.")
	private String estruturafisicaNome;
	
    @NotEmpty(message = "Nome do processo é uma informação obrigatória.")
	@NotNull(message = "Nome do processo é uma informação obrigatória.")
	private String processoNome;
	
    @NotEmpty(message = "Nome do questionário é uma informação obrigatória.")
	@NotNull(message = "Nome do questionário é uma informação obrigatória.")
	private String questionarioNome;
	
    @NotEmpty(message = "Nome da Unidade Organizacional é uma informação obrigatória.")
	@NotNull(message = "Nome da Unidade Organizacional é uma informação obrigatória.")
	private String unidadeorganizacionalNome;
	
    @DateTimeFormat(pattern = "MM/yyyy")
	private String avaliacaoProcessoPeriodo;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String  avaliacaoProcessoDataCriacao;

	@NotNull(message = "Status é uma informação obrigatória.")
	@Enumerated(EnumType.STRING)
	private AtributoStatus avaliacaoProcessoStatus;

    @NotEmpty(message = "Responsável é uma informação obrigatória.")
	@NotNull(message = "Responsável é uma informação obrigatória.")
	private String avaliacaoProcessoResponsavel;

    @NotEmpty(message = "Motivo da Operação é uma informação obrigatória.")
	@Size(min = 5,max = 200, message = "Motivo da Operação não pode ter menos que 5 e mais que 200 caracteres")
	private String avaliacaoProcessoMotivoOperacao;

	public AvaliacaoProcessoForm() {
		super();
	}

	public long getProcessoPK() {
		return processoPK;
	}

	public void setProcessoPK(long processoPK) {
		this.processoPK = processoPK;
	}

	public String getProcessoNome() {
		return processoNome;
	}

	public void setProcessoNome(String processoNome) {
		this.processoNome = processoNome;
	}

	public long getAvaliacaoProcessoPK() {
		return avaliacaoProcessoPK;
	}

	public void setAvaliacaoProcessoPK(long avaliacaoProcessoPK) {
		this.avaliacaoProcessoPK = avaliacaoProcessoPK;
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

	public String getAvaliacaoProcessoDataCriacao() {
		return avaliacaoProcessoDataCriacao;
	}

	public void setAvaliacaoProcessoDataCriacao(String avaliacaoProcessoDataCriacao) {
		this.avaliacaoProcessoDataCriacao = avaliacaoProcessoDataCriacao;
	}

	public AtributoStatus getAvaliacaoProcessoStatus() {
		return avaliacaoProcessoStatus;
	}

	public void setAvaliacaoProcessoStatus(AtributoStatus avaliacaoProcessoStatus) {
		this.avaliacaoProcessoStatus = avaliacaoProcessoStatus;
	}

	public String getAvaliacaoProcessoResponsavel() {
		return avaliacaoProcessoResponsavel;
	}

	public void setAvaliacaoProcessoResponsavel(String avaliacaoProcessoResponsavel) {
		this.avaliacaoProcessoResponsavel = avaliacaoProcessoResponsavel;
	}

	public String getAvaliacaoProcessoMotivoOperacao() {
		return avaliacaoProcessoMotivoOperacao;
	}

	public void setAvaliacaoProcessoMotivoOperacao(String avaliacaoProcessoMotivoOperacao) {
		this.avaliacaoProcessoMotivoOperacao = avaliacaoProcessoMotivoOperacao;
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

	public String getAvaliacaoProcessoPeriodo() {
		return avaliacaoProcessoPeriodo;
	}

	public void setAvaliacaoProcessoPeriodo(String avaliacaoProcessoPeriodo) {
		this.avaliacaoProcessoPeriodo = avaliacaoProcessoPeriodo;
	}

	public int getElementoCliente() {
		return elementoCliente;
	}

	public void setElementoCliente(int elementoCliente) {
		this.elementoCliente = elementoCliente;
	}

	public int getElementoReclamacao() {
		return elementoReclamacao;
	}

	public void setElementoReclamacao(int elementoReclamacao) {
		this.elementoReclamacao = elementoReclamacao;
	}

	public int getElementoSalario() {
		return elementoSalario;
	}

	public void setElementoSalario(int elementoSalario) {
		this.elementoSalario = elementoSalario;
	}

	public int getElementoManutencao() {
		return elementoManutencao;
	}

	public void setElementoManutencao(int elementoManutencao) {
		this.elementoManutencao = elementoManutencao;
	}

	public int getElementoTreinamento() {
		return elementoTreinamento;
	}

	public void setElementoTreinamento(int elementoTreinamento) {
		this.elementoTreinamento = elementoTreinamento;
	}

	public int getElementoAparelhoTecnicoFinanceiro() {
		return elementoAparelhoTecnicoFinanceiro;
	}

	public void setElementoAparelhoTecnicoFinanceiro(int elementoAparelhoTecnicoFinanceiro) {
		this.elementoAparelhoTecnicoFinanceiro = elementoAparelhoTecnicoFinanceiro;
	}

	public int getElementoTecnologiaInformacaoFinanceiro() {
		return elementoTecnologiaInformacaoFinanceiro;
	}

	public void setElementoTecnologiaInformacaoFinanceiro(int elementoTecnologiaInformacaoFinanceiro) {
		this.elementoTecnologiaInformacaoFinanceiro = elementoTecnologiaInformacaoFinanceiro;
	}

	public int getElementoIntegracao() {
		return elementoIntegracao;
	}

	public void setElementoIntegracao(int elementoIntegracao) {
		this.elementoIntegracao = elementoIntegracao;
	}

	public int getElementoMesa() {
		return elementoMesa;
	}

	public void setElementoMesa(int elementoMesa) {
		this.elementoMesa = elementoMesa;
	}

	public int getElementoCadeira() {
		return elementoCadeira;
	}

	public void setElementoCadeira(int elementoCadeira) {
		this.elementoCadeira = elementoCadeira;
	}

	public int getElementoBalcao() {
		return elementoBalcao;
	}

	public void setElementoBalcao(int elementoBalcao) {
		this.elementoBalcao = elementoBalcao;
	}

	public int getElementoApresentacaoPessoal() {
		return elementoApresentacaoPessoal;
	}

	public void setElementoApresentacaoPessoal(int elementoApresentacaoPessoal) {
		this.elementoApresentacaoPessoal = elementoApresentacaoPessoal;
	}

	public int getElementoAtendimento() {
		return elementoAtendimento;
	}

	public void setElementoAtendimento(int elementoAtendimento) {
		this.elementoAtendimento = elementoAtendimento;
	}

	public int getElementoConhecimentoTecnico() {
		return elementoConhecimentoTecnico;
	}

	public void setElementoConhecimentoTecnico(int elementoConhecimentoTecnico) {
		this.elementoConhecimentoTecnico = elementoConhecimentoTecnico;
	}

	public int getElementoQuadroFuncionario() {
		return elementoQuadroFuncionario;
	}

	public void setElementoQuadroFuncionario(int elementoQuadroFuncionario) {
		this.elementoQuadroFuncionario = elementoQuadroFuncionario;
	}

	public int getElementoSinalizacaoAviso() {
		return elementoSinalizacaoAviso;
	}

	public void setElementoSinalizacaoAviso(int elementoSinalizacaoAviso) {
		this.elementoSinalizacaoAviso = elementoSinalizacaoAviso;
	}

	public int getElementoSinalizacaoEmergencia() {
		return elementoSinalizacaoEmergencia;
	}

	public void setElementoSinalizacaoEmergencia(int elementoSinalizacaoEmergencia) {
		this.elementoSinalizacaoEmergencia = elementoSinalizacaoEmergencia;
	}

	public int getElementoExtintorIncendio() {
		return elementoExtintorIncendio;
	}

	public void setElementoExtintorIncendio(int elementoExtintorIncendio) {
		this.elementoExtintorIncendio = elementoExtintorIncendio;
	}

	public int getElementoVigilante() {
		return elementoVigilante;
	}

	public void setElementoVigilante(int elementoVigilante) {
		this.elementoVigilante = elementoVigilante;
	}

	public int getElementoCamera() {
		return elementoCamera;
	}

	public void setElementoCamera(int elementoCamera) {
		this.elementoCamera = elementoCamera;
	}

	public int getElementoAguaPotavel() {
		return elementoAguaPotavel;
	}

	public void setElementoAguaPotavel(int elementoAguaPotavel) {
		this.elementoAguaPotavel = elementoAguaPotavel;
	}

	public int getElementoArCondicionado() {
		return elementoArCondicionado;
	}

	public void setElementoArCondicionado(int elementoArCondicionado) {
		this.elementoArCondicionado = elementoArCondicionado;
	}

	public int getElementoBlocoAnotacao() {
		return elementoBlocoAnotacao;
	}

	public void setElementoBlocoAnotacao(int elementoBlocoAnotacao) {
		this.elementoBlocoAnotacao = elementoBlocoAnotacao;
	}

	public int getElementoCaronaSolidaria() {
		return elementoCaronaSolidaria;
	}

	public void setElementoCaronaSolidaria(int elementoCaronaSolidaria) {
		this.elementoCaronaSolidaria = elementoCaronaSolidaria;
	}

	public int getElementoCopoDescartavel() {
		return elementoCopoDescartavel;
	}

	public void setElementoCopoDescartavel(int elementoCopoDescartavel) {
		this.elementoCopoDescartavel = elementoCopoDescartavel;
	}

	public int getElementoLampadaEconomica() {
		return elementoLampadaEconomica;
	}

	public void setElementoLampadaEconomica(int elementoLampadaEconomica) {
		this.elementoLampadaEconomica = elementoLampadaEconomica;
	}

	public int getElementoLapisMadeiraReplantio() {
		return elementoLapisMadeiraReplantio;
	}

	public void setElementoLapisMadeiraReplantio(int elementoLapisMadeiraReplantio) {
		this.elementoLapisMadeiraReplantio = elementoLapisMadeiraReplantio;
	}

	public int getElementoLixeiraReciclavel() {
		return elementoLixeiraReciclavel;
	}

	public void setElementoLixeiraReciclavel(int elementoLixeiraReciclavel) {
		this.elementoLixeiraReciclavel = elementoLixeiraReciclavel;
	}

	public int getElementoPalestraSustentabilidade() {
		return elementoPalestraSustentabilidade;
	}

	public void setElementoPalestraSustentabilidade(int elementoPalestraSustentabilidade) {
		this.elementoPalestraSustentabilidade = elementoPalestraSustentabilidade;
	}

	public int getElementoPapelImpressora() {
		return elementoPapelImpressora;
	}

	public void setElementoPapelImpressora(int elementoPapelImpressora) {
		this.elementoPapelImpressora = elementoPapelImpressora;
	}

	public int getElementoTransporteColetivo() {
		return elementoTransporteColetivo;
	}

	public void setElementoTransporteColetivo(int elementoTransporteColetivo) {
		this.elementoTransporteColetivo = elementoTransporteColetivo;
	}

	public int getElementoUsoEnergiaEletrica() {
		return elementoUsoEnergiaEletrica;
	}

	public void setElementoUsoEnergiaEletrica(int elementoUsoEnergiaEletrica) {
		this.elementoUsoEnergiaEletrica = elementoUsoEnergiaEletrica;
	}

	public int getElementoPapelReciclado() {
		return elementoPapelReciclado;
	}

	public void setElementoPapelReciclado(int elementoPapelReciclado) {
		this.elementoPapelReciclado = elementoPapelReciclado;
	}

	public int getElementoTecnologiaComputadorPCVelocidade() {
		return elementoTecnologiaComputadorPCVelocidade;
	}

	public void setElementoTecnologiaComputadorPCVelocidade(int elementoTecnologiaComputadorPCVelocidade) {
		this.elementoTecnologiaComputadorPCVelocidade = elementoTecnologiaComputadorPCVelocidade;
	}

	public int getElementoTecnologiaComputadorPCCapacidade() {
		return elementoTecnologiaComputadorPCCapacidade;
	}

	public void setElementoTecnologiaComputadorPCCapacidade(int elementoTecnologiaComputadorPCCapacidade) {
		this.elementoTecnologiaComputadorPCCapacidade = elementoTecnologiaComputadorPCCapacidade;
	}

	public int getElementoTecnologiaComputadorPCSom() {
		return elementoTecnologiaComputadorPCSom;
	}

	public void setElementoTecnologiaComputadorPCSom(int elementoTecnologiaComputadorPCSom) {
		this.elementoTecnologiaComputadorPCSom = elementoTecnologiaComputadorPCSom;
	}

	public int getElementoTecnologiaImpressoraTipo() {
		return elementoTecnologiaImpressoraTipo;
	}

	public void setElementoTecnologiaImpressoraTipo(int elementoTecnologiaImpressoraTipo) {
		this.elementoTecnologiaImpressoraTipo = elementoTecnologiaImpressoraTipo;
	}

	public int getElementoTecnologiaImpressoraVelocidade() {
		return elementoTecnologiaImpressoraVelocidade;
	}

	public void setElementoTecnologiaImpressoraVelocidade(int elementoTecnologiaImpressoraVelocidade) {
		this.elementoTecnologiaImpressoraVelocidade = elementoTecnologiaImpressoraVelocidade;
	}

	public int getElementoTecnologiaMonitorTamanho() {
		return elementoTecnologiaMonitorTamanho;
	}

	public void setElementoTecnologiaMonitorTamanho(int elementoTecnologiaMonitorTamanho) {
		this.elementoTecnologiaMonitorTamanho = elementoTecnologiaMonitorTamanho;
	}

	public int getElementoTecnologiaMonitorTipo() {
		return elementoTecnologiaMonitorTipo;
	}

	public void setElementoTecnologiaMonitorTipo(int elementoTecnologiaMonitorTipo) {
		this.elementoTecnologiaMonitorTipo = elementoTecnologiaMonitorTipo;
	}

	public int getElementoTecnologiaMouse() {
		return elementoTecnologiaMouse;
	}

	public void setElementoTecnologiaMouse(int elementoTecnologiaMouse) {
		this.elementoTecnologiaMouse = elementoTecnologiaMouse;
	}

	public int getElementoTecnologiaTabletVelocidade() {
		return elementoTecnologiaTabletVelocidade;
	}

	public void setElementoTecnologiaTabletVelocidade(int elementoTecnologiaTabletVelocidade) {
		this.elementoTecnologiaTabletVelocidade = elementoTecnologiaTabletVelocidade;
	}

	public int getElementoTecnologiaTabletTamanho() {
		return elementoTecnologiaTabletTamanho;
	}

	public void setElementoTecnologiaTabletTamanho(int elementoTecnologiaTabletTamanho) {
		this.elementoTecnologiaTabletTamanho = elementoTecnologiaTabletTamanho;
	}

	public int getElementoTecnologiaWifiCapacidade() {
		return elementoTecnologiaWifiCapacidade;
	}

	public void setElementoTecnologiaWifiCapacidade(int elementoTecnologiaWifiCapacidade) {
		this.elementoTecnologiaWifiCapacidade = elementoTecnologiaWifiCapacidade;
	}

	public int getElementoTecnologiaWifiVelocidade() {
		return elementoTecnologiaWifiVelocidade;
	}

	public void setElementoTecnologiaWifiVelocidade(int elementoTecnologiaWifiVelocidade) {
		this.elementoTecnologiaWifiVelocidade = elementoTecnologiaWifiVelocidade;
	}

	public int getElementoAgilidade() {
		return elementoAgilidade;
	}

	public void setElementoAgilidade(int elementoAgilidade) {
		this.elementoAgilidade = elementoAgilidade;
	}

	public int getElementoCortesia() {
		return elementoCortesia;
	}

	public void setElementoCortesia(int elementoCortesia) {
		this.elementoCortesia = elementoCortesia;
	}

	public int getElementoDisponibilidade() {
		return elementoDisponibilidade;
	}

	public void setElementoDisponibilidade(int elementoDisponibilidade) {
		this.elementoDisponibilidade = elementoDisponibilidade;
	}

	public int getElementoEducacao() {
		return elementoEducacao;
	}

	public void setElementoEducacao(int elementoEducacao) {
		this.elementoEducacao = elementoEducacao;
	}

	public int getElementoEquilibrioEmocional() {
		return elementoEquilibrioEmocional;
	}

	public void setElementoEquilibrioEmocional(int elementoEquilibrioEmocional) {
		this.elementoEquilibrioEmocional = elementoEquilibrioEmocional;
	}

	public int getElementoInformacaoInstituicao() {
		return elementoInformacaoInstituicao;
	}

	public void setElementoInformacaoInstituicao(int elementoInformacaoInstituicao) {
		this.elementoInformacaoInstituicao = elementoInformacaoInstituicao;
	}

	public int getElementoProAtividade() {
		return elementoProAtividade;
	}

	public void setElementoProAtividade(int elementoProAtividade) {
		this.elementoProAtividade = elementoProAtividade;
	}

	public int getElementoSimpatia() {
		return elementoSimpatia;
	}

	public void setElementoSimpatia(int elementoSimpatia) {
		this.elementoSimpatia = elementoSimpatia;
	}

	public int getElementoTempoUtilizado() {
		return elementoTempoUtilizado;
	}

	public void setElementoTempoUtilizado(int elementoTempoUtilizado) {
		this.elementoTempoUtilizado = elementoTempoUtilizado;
	}

	public int getElementoVocabulario() {
		return elementoVocabulario;
	}

	public void setElementoVocabulario(int elementoVocabulario) {
		this.elementoVocabulario = elementoVocabulario;
	}

	public int getElementoAparelhoTecnico01() {
		return elementoAparelhoTecnico01;
	}

	public void setElementoAparelhoTecnico01(int elementoAparelhoTecnico01) {
		this.elementoAparelhoTecnico01 = elementoAparelhoTecnico01;
	}

	public int getElementoAparelhoTecnico02() {
		return elementoAparelhoTecnico02;
	}

	public void setElementoAparelhoTecnico02(int elementoAparelhoTecnico02) {
		this.elementoAparelhoTecnico02 = elementoAparelhoTecnico02;
	}

	public int getElementoAparelhoTecnico03() {
		return elementoAparelhoTecnico03;
	}

	public void setElementoAparelhoTecnico03(int elementoAparelhoTecnico03) {
		this.elementoAparelhoTecnico03 = elementoAparelhoTecnico03;
	}

	public int getElementoAparelhoTecnico04() {
		return elementoAparelhoTecnico04;
	}

	public void setElementoAparelhoTecnico04(int elementoAparelhoTecnico04) {
		this.elementoAparelhoTecnico04 = elementoAparelhoTecnico04;
	}

	public int getElementoAparelhoTecnico05() {
		return elementoAparelhoTecnico05;
	}

	public void setElementoAparelhoTecnico05(int elementoAparelhoTecnico05) {
		this.elementoAparelhoTecnico05 = elementoAparelhoTecnico05;
	}

	public int getElementoLucro() {
		return elementoLucro;
	}

	public void setElementoLucro(int elementoLucro) {
		this.elementoLucro = elementoLucro;
	}

	public int getElementoCompras() {
		return elementoCompras;
	}

	public void setElementoCompras(int elementoCompras) {
		this.elementoCompras = elementoCompras;
	}

	public int getElementoVendas() {
		return elementoVendas;
	}

	public void setElementoVendas(int elementoVendas) {
		this.elementoVendas = elementoVendas;
	}

	public int getElementoAdministracaoMaterial() {
		return elementoAdministracaoMaterial;
	}

	public void setElementoAdministracaoMaterial(int elementoAdministracaoMaterial) {
		this.elementoAdministracaoMaterial = elementoAdministracaoMaterial;
	}

	public int getElementoArmazenamento() {
		return elementoArmazenamento;
	}

	public void setElementoArmazenamento(int elementoArmazenamento) {
		this.elementoArmazenamento = elementoArmazenamento;
	}

	public int getElementoDistribuicao() {
		return elementoDistribuicao;
	}

	public void setElementoDistribuicao(int elementoDistribuicao) {
		this.elementoDistribuicao = elementoDistribuicao;
	}

	public int getElementoAdministracaoCompras() {
		return elementoAdministracaoCompras;
	}

	public void setElementoAdministracaoCompras(int elementoAdministracaoCompras) {
		this.elementoAdministracaoCompras = elementoAdministracaoCompras;
	}

	public int getElementoTransporte() {
		return elementoTransporte;
	}

	public void setElementoTransporte(int elementoTransporte) {
		this.elementoTransporte = elementoTransporte;
	}

	public int getElementoEstante() {
		return elementoEstante;
	}

	public void setElementoEstante(int elementoEstante) {
		this.elementoEstante = elementoEstante;
	}

	public int getElementoArmario() {
		return elementoArmario;
	}

	public void setElementoArmario(int elementoArmario) {
		this.elementoArmario = elementoArmario;
	}

	public int getElementoDesempenho01() {
		return elementoDesempenho01;
	}

	public void setElementoDesempenho01(int elementoDesempenho01) {
		this.elementoDesempenho01 = elementoDesempenho01;
	}

	public int getElementoDesempenho02() {
		return elementoDesempenho02;
	}

	public void setElementoDesempenho02(int elementoDesempenho02) {
		this.elementoDesempenho02 = elementoDesempenho02;
	}

	public int getElementoDesempenho03() {
		return elementoDesempenho03;
	}

	public void setElementoDesempenho03(int elementoDesempenho03) {
		this.elementoDesempenho03 = elementoDesempenho03;
	}

	public int getElementoLogistica01() {
		return elementoLogistica01;
	}

	public void setElementoLogistica01(int elementoLogistica01) {
		this.elementoLogistica01 = elementoLogistica01;
	}

	public int getElementoLogistica02() {
		return elementoLogistica02;
	}

	public void setElementoLogistica02(int elementoLogistica02) {
		this.elementoLogistica02 = elementoLogistica02;
	}

	public int getElementoLogistica03() {
		return elementoLogistica03;
	}

	public void setElementoLogistica03(int elementoLogistica03) {
		this.elementoLogistica03 = elementoLogistica03;
	}

	public int getElementoSofa() {
		return elementoSofa;
	}

	public void setElementoSofa(int elementoSofa) {
		this.elementoSofa = elementoSofa;
	}

	public int getElementoCortina() {
		return elementoCortina;
	}

	public void setElementoCortina(int elementoCortina) {
		this.elementoCortina = elementoCortina;
	}

}
