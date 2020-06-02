package br.com.j4business.saga.avaliacaoprocesso.model;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AvaliacaoProcessoDashboard {

	private long avaliacaoProcessoPK;
	private long avaliacaoPK;
	private long estruturafisicaPK;
	private long processoPK;
	private long questionarioPK;
	private long unidadeorganizacionalPK;
	private String cenarioNome;
	private String avaliacaoNome;
	private String estruturafisicaNome;
	private String processoNome;
	private String questionarioNome;
	private String unidadeorganizacionalNome;
	private String avaliacaoProcessoPeriodo;
    private String avaliacaoProcessoDataCriacao;
	private AtributoStatus avaliacaoProcessoStatus;
	private String avaliacaoProcessoResponsavel;
	private String avaliacaoProcessoMotivoOperacao;

	private String avaliacaoProcessoPeriodoAtual;
	private String avaliacaoProcessoPeriodoAnterior;
	
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
	
	// Mobilia 
	private int elementoMesa = 0;
	private int elementoCadeira = 0;
	private int elementoBalcao = 0;
	private int elementoEstante = 0;
	private int elementoArmario = 0;
	
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
	
	// Variaveis
	private int quantidadeProcessos = 0;
	private int quantidadeColaboradores = 0;
	private int quantidadeQuestionarios = 0;
	private int quantidadeQuestoes = 0;
	
	// Dashboard
	private int elementoMediaJanAtual = 0;
	private int elementoMediaFevAtual = 0;
	private int elementoMediaMarAtual = 0;
	private int elementoMediaAbrAtual = 0;
	private int elementoMediaMaiAtual = 0;
	private int elementoMediaJunAtual = 0;
	private int elementoMediaJulAtual = 0;
	private int elementoMediaAgoAtual = 0;
	private int elementoMediaSetAtual = 0;
	private int elementoMediaOutAtual = 0;
	private int elementoMediaNovAtual = 0;
	private int elementoMediaDezAtual = 0;
	
	private int elementoMediaJanAnterior = 0;
	private int elementoMediaFevAnterior = 0;
	private int elementoMediaMarAnterior = 0;
	private int elementoMediaAbrAnterior = 0;
	private int elementoMediaMaiAnterior = 0;
	private int elementoMediaJunAnterior = 0;
	private int elementoMediaJulAnterior = 0;
	private int elementoMediaAgoAnterior = 0;
	private int elementoMediaSetAnterior = 0;
	private int elementoMediaOutAnterior = 0;
	private int elementoMediaNovAnterior = 0;
	private int elementoMediaDezAnterior = 0;
	
	// Dashboard por cenário / elemento
	private String corElemento1 = "rgba(255, 217, 102, 0.8)";
	private String corBordaElemento1 = "rgba(255, 217, 102, 1)";

	// Dashboard por cenário / elemento
	private int elemento1 = 0;
	private int elemento2 = 0;
	private int elemento3 = 0;
	private int elemento4 = 0;
	private int elemento5 = 0;
	private int elemento6 = 0;
	private int elemento7 = 0;
	private int elemento8 = 0;
	private int elemento9 = 0;
	private int elemento10= 0;
	private int elemento11= 0;
	private int elemento12= 0;
	private int elemento13= 0;
	private int elemento14= 0;
	private int elemento15= 0;
	private int elemento16= 0;
	private int elemento17= 0;
	private int elemento18= 0;
	private int elemento19= 0;
	private int elemento20= 0;
	private int elemento21= 0;
	private int elemento22= 0;
	private int elemento23= 0;
	private int elemento24= 0;
	private int elemento25= 0;
	private int elemento26= 0;
	private int elemento27= 0;
	private int elemento28= 0;
	private int elemento29= 0;
	private int elemento30= 0;
	
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

	
	public AvaliacaoProcessoDashboard() {
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

	public int getQuantidadeProcessos() {
		return quantidadeProcessos;
	}

	public void setQuantidadeProcessos(int quantidadeProcessos) {
		this.quantidadeProcessos = quantidadeProcessos;
	}

	public int getQuantidadeColaboradores() {
		return quantidadeColaboradores;
	}

	public void setQuantidadeColaboradores(int quantidadeColaboradores) {
		this.quantidadeColaboradores = quantidadeColaboradores;
	}

	public int getQuantidadeQuestionarios() {
		return quantidadeQuestionarios;
	}

	public void setQuantidadeQuestionarios(int quantidadeQuestionarios) {
		this.quantidadeQuestionarios = quantidadeQuestionarios;
	}

	public int getQuantidadeQuestoes() {
		return quantidadeQuestoes;
	}

	public void setQuantidadeQuestoes(int quantidadeQuestoes) {
		this.quantidadeQuestoes = quantidadeQuestoes;
	}

	public int getElementoMediaJanAtual() {
		return elementoMediaJanAtual;
	}

	public void setElementoMediaJanAtual(int elementoMediaJanAtual) {
		this.elementoMediaJanAtual = elementoMediaJanAtual;
	}

	public int getElementoMediaFevAtual() {
		return elementoMediaFevAtual;
	}

	public void setElementoMediaFevAtual(int elementoMediaFevAtual) {
		this.elementoMediaFevAtual = elementoMediaFevAtual;
	}

	public int getElementoMediaMarAtual() {
		return elementoMediaMarAtual;
	}

	public void setElementoMediaMarAtual(int elementoMediaMarAtual) {
		this.elementoMediaMarAtual = elementoMediaMarAtual;
	}

	public int getElementoMediaAbrAtual() {
		return elementoMediaAbrAtual;
	}

	public void setElementoMediaAbrAtual(int elementoMediaAbrAtual) {
		this.elementoMediaAbrAtual = elementoMediaAbrAtual;
	}

	public int getElementoMediaMaiAtual() {
		return elementoMediaMaiAtual;
	}

	public void setElementoMediaMaiAtual(int elementoMediaMaiAtual) {
		this.elementoMediaMaiAtual = elementoMediaMaiAtual;
	}

	public int getElementoMediaJunAtual() {
		return elementoMediaJunAtual;
	}

	public void setElementoMediaJunAtual(int elementoMediaJunAtual) {
		this.elementoMediaJunAtual = elementoMediaJunAtual;
	}

	public int getElementoMediaJulAtual() {
		return elementoMediaJulAtual;
	}

	public void setElementoMediaJulAtual(int elementoMediaJulAtual) {
		this.elementoMediaJulAtual = elementoMediaJulAtual;
	}

	public int getElementoMediaAgoAtual() {
		return elementoMediaAgoAtual;
	}

	public void setElementoMediaAgoAtual(int elementoMediaAgoAtual) {
		this.elementoMediaAgoAtual = elementoMediaAgoAtual;
	}

	public int getElementoMediaSetAtual() {
		return elementoMediaSetAtual;
	}

	public void setElementoMediaSetAtual(int elementoMediaSetAtual) {
		this.elementoMediaSetAtual = elementoMediaSetAtual;
	}

	public int getElementoMediaOutAtual() {
		return elementoMediaOutAtual;
	}

	public void setElementoMediaOutAtual(int elementoMediaOutAtual) {
		this.elementoMediaOutAtual = elementoMediaOutAtual;
	}

	public int getElementoMediaNovAtual() {
		return elementoMediaNovAtual;
	}

	public void setElementoMediaNovAtual(int elementoMediaNovAtual) {
		this.elementoMediaNovAtual = elementoMediaNovAtual;
	}

	public int getElementoMediaDezAtual() {
		return elementoMediaDezAtual;
	}

	public void setElementoMediaDezAtual(int elementoMediaDezAtual) {
		this.elementoMediaDezAtual = elementoMediaDezAtual;
	}

	public int getElementoMediaJanAnterior() {
		return elementoMediaJanAnterior;
	}

	public void setElementoMediaJanAnterior(int elementoMediaJanAnterior) {
		this.elementoMediaJanAnterior = elementoMediaJanAnterior;
	}

	public int getElementoMediaFevAnterior() {
		return elementoMediaFevAnterior;
	}

	public void setElementoMediaFevAnterior(int elementoMediaFevAnterior) {
		this.elementoMediaFevAnterior = elementoMediaFevAnterior;
	}

	public int getElementoMediaMarAnterior() {
		return elementoMediaMarAnterior;
	}

	public void setElementoMediaMarAnterior(int elementoMediaMarAnterior) {
		this.elementoMediaMarAnterior = elementoMediaMarAnterior;
	}

	public int getElementoMediaAbrAnterior() {
		return elementoMediaAbrAnterior;
	}

	public void setElementoMediaAbrAnterior(int elementoMediaAbrAnterior) {
		this.elementoMediaAbrAnterior = elementoMediaAbrAnterior;
	}

	public int getElementoMediaMaiAnterior() {
		return elementoMediaMaiAnterior;
	}

	public void setElementoMediaMaiAnterior(int elementoMediaMaiAnterior) {
		this.elementoMediaMaiAnterior = elementoMediaMaiAnterior;
	}

	public int getElementoMediaJunAnterior() {
		return elementoMediaJunAnterior;
	}

	public void setElementoMediaJunAnterior(int elementoMediaJunAnterior) {
		this.elementoMediaJunAnterior = elementoMediaJunAnterior;
	}

	public int getElementoMediaJulAnterior() {
		return elementoMediaJulAnterior;
	}

	public void setElementoMediaJulAnterior(int elementoMediaJulAnterior) {
		this.elementoMediaJulAnterior = elementoMediaJulAnterior;
	}

	public int getElementoMediaAgoAnterior() {
		return elementoMediaAgoAnterior;
	}

	public void setElementoMediaAgoAnterior(int elementoMediaAgoAnterior) {
		this.elementoMediaAgoAnterior = elementoMediaAgoAnterior;
	}

	public int getElementoMediaSetAnterior() {
		return elementoMediaSetAnterior;
	}

	public void setElementoMediaSetAnterior(int elementoMediaSetAnterior) {
		this.elementoMediaSetAnterior = elementoMediaSetAnterior;
	}

	public int getElementoMediaOutAnterior() {
		return elementoMediaOutAnterior;
	}

	public void setElementoMediaOutAnterior(int elementoMediaOutAnterior) {
		this.elementoMediaOutAnterior = elementoMediaOutAnterior;
	}

	public int getElementoMediaNovAnterior() {
		return elementoMediaNovAnterior;
	}

	public void setElementoMediaNovAnterior(int elementoMediaNovAnterior) {
		this.elementoMediaNovAnterior = elementoMediaNovAnterior;
	}

	public int getElementoMediaDezAnterior() {
		return elementoMediaDezAnterior;
	}

	public void setElementoMediaDezAnterior(int elementoMediaDezAnterior) {
		this.elementoMediaDezAnterior = elementoMediaDezAnterior;
	}

	public int getElemento1() {
		return elemento1;
	}

	public void setElemento1(int elemento1) {
		this.elemento1 = elemento1;
	}

	public int getElemento2() {
		return elemento2;
	}

	public void setElemento2(int elemento2) {
		this.elemento2 = elemento2;
	}

	public int getElemento3() {
		return elemento3;
	}

	public void setElemento3(int elemento3) {
		this.elemento3 = elemento3;
	}

	public int getElemento4() {
		return elemento4;
	}

	public void setElemento4(int elemento4) {
		this.elemento4 = elemento4;
	}

	public int getElemento5() {
		return elemento5;
	}

	public void setElemento5(int elemento5) {
		this.elemento5 = elemento5;
	}

	public int getElemento6() {
		return elemento6;
	}

	public void setElemento6(int elemento6) {
		this.elemento6 = elemento6;
	}

	public int getElemento7() {
		return elemento7;
	}

	public void setElemento7(int elemento7) {
		this.elemento7 = elemento7;
	}

	public int getElemento8() {
		return elemento8;
	}

	public void setElemento8(int elemento8) {
		this.elemento8 = elemento8;
	}

	public int getElemento9() {
		return elemento9;
	}

	public void setElemento9(int elemento9) {
		this.elemento9 = elemento9;
	}

	public int getElemento10() {
		return elemento10;
	}

	public void setElemento10(int elemento10) {
		this.elemento10 = elemento10;
	}

	public int getElemento11() {
		return elemento11;
	}

	public void setElemento11(int elemento11) {
		this.elemento11 = elemento11;
	}

	public int getElemento12() {
		return elemento12;
	}

	public void setElemento12(int elemento12) {
		this.elemento12 = elemento12;
	}

	public int getElemento13() {
		return elemento13;
	}

	public void setElemento13(int elemento13) {
		this.elemento13 = elemento13;
	}

	public int getElemento14() {
		return elemento14;
	}

	public void setElemento14(int elemento14) {
		this.elemento14 = elemento14;
	}

	public int getElemento15() {
		return elemento15;
	}

	public void setElemento15(int elemento15) {
		this.elemento15 = elemento15;
	}

	public int getElemento16() {
		return elemento16;
	}

	public void setElemento16(int elemento16) {
		this.elemento16 = elemento16;
	}

	public int getElemento17() {
		return elemento17;
	}

	public void setElemento17(int elemento17) {
		this.elemento17 = elemento17;
	}

	public int getElemento18() {
		return elemento18;
	}

	public void setElemento18(int elemento18) {
		this.elemento18 = elemento18;
	}

	public int getElemento19() {
		return elemento19;
	}

	public void setElemento19(int elemento19) {
		this.elemento19 = elemento19;
	}

	public int getElemento20() {
		return elemento20;
	}

	public void setElemento20(int elemento20) {
		this.elemento20 = elemento20;
	}

	public int getElemento21() {
		return elemento21;
	}

	public void setElemento21(int elemento21) {
		this.elemento21 = elemento21;
	}

	public int getElemento22() {
		return elemento22;
	}

	public void setElemento22(int elemento22) {
		this.elemento22 = elemento22;
	}

	public int getElemento23() {
		return elemento23;
	}

	public void setElemento23(int elemento23) {
		this.elemento23 = elemento23;
	}

	public int getElemento24() {
		return elemento24;
	}

	public void setElemento24(int elemento24) {
		this.elemento24 = elemento24;
	}

	public int getElemento25() {
		return elemento25;
	}

	public void setElemento25(int elemento25) {
		this.elemento25 = elemento25;
	}

	public int getElemento26() {
		return elemento26;
	}

	public void setElemento26(int elemento26) {
		this.elemento26 = elemento26;
	}

	public int getElemento27() {
		return elemento27;
	}

	public void setElemento27(int elemento27) {
		this.elemento27 = elemento27;
	}

	public int getElemento28() {
		return elemento28;
	}

	public void setElemento28(int elemento28) {
		this.elemento28 = elemento28;
	}

	public int getElemento29() {
		return elemento29;
	}

	public void setElemento29(int elemento29) {
		this.elemento29 = elemento29;
	}

	public int getElemento30() {
		return elemento30;
	}

	public void setElemento30(int elemento30) {
		this.elemento30 = elemento30;
	}

	public String getCenarioNome() {
		return cenarioNome;
	}

	public void setCenarioNome(String cenarioNome) {
		this.cenarioNome = cenarioNome;
	}

	public String getCorElemento1() {
		return corElemento1;
	}

	public void setCorElemento1(String corElemento1) {
		this.corElemento1 = corElemento1;
	}

	public String getCorBordaElemento1() {
		return corBordaElemento1;
	}

	public void setCorBordaElemento1(String corBordaElemento1) {
		this.corBordaElemento1 = corBordaElemento1;
	}

	public String getAvaliacaoProcessoPeriodoAtual() {
		return avaliacaoProcessoPeriodoAtual;
	}

	public void setAvaliacaoProcessoPeriodoAtual(String avaliacaoProcessoPeriodoAtual) {
		this.avaliacaoProcessoPeriodoAtual = avaliacaoProcessoPeriodoAtual;
	}

	public String getAvaliacaoProcessoPeriodoAnterior() {
		return avaliacaoProcessoPeriodoAnterior;
	}

	public void setAvaliacaoProcessoPeriodoAnterior(String avaliacaoProcessoPeriodoAnterior) {
		this.avaliacaoProcessoPeriodoAnterior = avaliacaoProcessoPeriodoAnterior;
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

	public int getElementoCopoDescartavel() {
		return elementoCopoDescartavel;
	}

	public void setElementoCopoDescartavel(int elementoCopoDescartavel) {
		this.elementoCopoDescartavel = elementoCopoDescartavel;
	}

	public int getElementoAguaPotavel() {
		return elementoAguaPotavel;
	}

	public void setElementoAguaPotavel(int elementoAguaPotavel) {
		this.elementoAguaPotavel = elementoAguaPotavel;
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

}
