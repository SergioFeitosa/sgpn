package br.com.j4business.saga.avaliacaocontrato.model;

import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

public class AvaliacaoContratoDashboard {

	private long avaliacaoContratoPK;
	private long avaliacaoPK;
	private long estruturafisicaPK;
	private long contratoPK;
	private long questionarioPK;
	private long unidadeorganizacionalPK;
	private String cenarioNome;
	private String avaliacaoNome;
	private String estruturafisicaNome;
	private String contratoNome;
	private String questionarioNome;
	private String unidadeorganizacionalNome;
	private String avaliacaoContratoPeriodo;
    private String avaliacaoContratoDataCriacao;
	private AtributoStatus avaliacaoContratoStatus;
	private String avaliacaoContratoResponsavel;
	private String avaliacaoContratoMotivoOperacao;

	private String avaliacaoContratoPeriodoAtual;
	private String avaliacaoContratoPeriodoAnterior;

	
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
	private int elementoAparelhoTecnico = 0;
	
	// Desempenho
	private int elementoCliente = 0;
	private int elementoReclamacao = 0;
	
	// Financeiro
	private int elementoSalario = 0;
	private int elementoManutencao = 0;
	private int elementoTreinamento = 0;
	private int elementoAparelhoTecnicoFinanceiro = 0;
	private int elementoTecnologiaInformacaoFinanceiro = 0;
	
	// Logistica
	private int elementoIntegracao = 0;
	
	// Mobilia 
	private int elementoMesa = 0;
	private int elementoCadeira = 0;
	private int elementoBalcao = 0;
	
	// Pessoal
	private int elementoApresentacaoPessoal = 0;
	private int elementoAtendimento = 0;
	private int elementoConhecimentoTecnico = 0;
	private int elementoQuadroFuncionario = 0;
	
	// Seguranca
	private int elementoSinalizacaoAviso = 0;
	private int elementoVigilancia = 0;
	private int elementoSinalizacaoEmergencia = 0;
	private int elementoExtintorIncendio = 0;
	
	// Sustentabilidade
	private int elementoRecursoNaturalDireto = 0;
	private int elementoRecursoNaturaIndireto = 0;
	
	// Tecnologia
	private int elementoTecnologiaInformacao = 0;
	
	// Variaveis
	private int quantidadeContratos = 0;
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
	private int elemento31 = 0;
	private int elemento32 = 0;
	private int elemento33 = 0;
	private int elemento34 = 0;
	private int elemento35 = 0;
	private int elemento36 = 0;
	private int elemento37 = 0;
	private int elemento38 = 0;
	private int elemento39 = 0;
	private int elemento40= 0;
	
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

	
	public AvaliacaoContratoDashboard() {
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

	public int getElementoAparelhoTecnico() {
		return elementoAparelhoTecnico;
	}

	public void setElementoAparelhoTecnico(int elementoAparelhoTecnico) {
		this.elementoAparelhoTecnico = elementoAparelhoTecnico;
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

	public int getElementoVigilancia() {
		return elementoVigilancia;
	}

	public void setElementoVigilancia(int elementoVigilancia) {
		this.elementoVigilancia = elementoVigilancia;
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

	public int getElementoRecursoNaturalDireto() {
		return elementoRecursoNaturalDireto;
	}

	public void setElementoRecursoNaturalDireto(int elementoRecursoNaturalDireto) {
		this.elementoRecursoNaturalDireto = elementoRecursoNaturalDireto;
	}

	public int getElementoRecursoNaturaIndireto() {
		return elementoRecursoNaturaIndireto;
	}

	public void setElementoRecursoNaturaIndireto(int elementoRecursoNaturaIndireto) {
		this.elementoRecursoNaturaIndireto = elementoRecursoNaturaIndireto;
	}

	public int getElementoTecnologiaInformacao() {
		return elementoTecnologiaInformacao;
	}

	public void setElementoTecnologiaInformacao(int elementoTecnologiaInformacao) {
		this.elementoTecnologiaInformacao = elementoTecnologiaInformacao;
	}

	public int getQuantidadeContratos() {
		return quantidadeContratos;
	}

	public void setQuantidadeContratos(int quantidadeContratos) {
		this.quantidadeContratos = quantidadeContratos;
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

	public String getAvaliacaoContratoPeriodoAtual() {
		return avaliacaoContratoPeriodoAtual;
	}

	public void setAvaliacaoContratoPeriodoAtual(String avaliacaoContratoPeriodoAtual) {
		this.avaliacaoContratoPeriodoAtual = avaliacaoContratoPeriodoAtual;
	}

	public String getAvaliacaoContratoPeriodoAnterior() {
		return avaliacaoContratoPeriodoAnterior;
	}

	public void setAvaliacaoContratoPeriodoAnterior(String avaliacaoContratoPeriodoAnterior) {
		this.avaliacaoContratoPeriodoAnterior = avaliacaoContratoPeriodoAnterior;
	}

	public int getElemento31() {
		return elemento31;
	}

	public void setElemento31(int elemento31) {
		this.elemento31 = elemento31;
	}

	public int getElemento32() {
		return elemento32;
	}

	public void setElemento32(int elemento32) {
		this.elemento32 = elemento32;
	}

	public int getElemento33() {
		return elemento33;
	}

	public void setElemento33(int elemento33) {
		this.elemento33 = elemento33;
	}

	public int getElemento34() {
		return elemento34;
	}

	public void setElemento34(int elemento34) {
		this.elemento34 = elemento34;
	}

	public int getElemento35() {
		return elemento35;
	}

	public void setElemento35(int elemento35) {
		this.elemento35 = elemento35;
	}

	public int getElemento36() {
		return elemento36;
	}

	public void setElemento36(int elemento36) {
		this.elemento36 = elemento36;
	}

	public int getElemento37() {
		return elemento37;
	}

	public void setElemento37(int elemento37) {
		this.elemento37 = elemento37;
	}

	public int getElemento38() {
		return elemento38;
	}

	public void setElemento38(int elemento38) {
		this.elemento38 = elemento38;
	}

	public int getElemento39() {
		return elemento39;
	}

	public void setElemento39(int elemento39) {
		this.elemento39 = elemento39;
	}

	public int getElemento40() {
		return elemento40;
	}

	public void setElemento40(int elemento40) {
		this.elemento40 = elemento40;
	}

}
