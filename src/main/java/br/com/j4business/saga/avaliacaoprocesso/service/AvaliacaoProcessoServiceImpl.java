package br.com.j4business.saga.avaliacaoprocesso.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisica.service.EstruturafisicaService;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.service.EstruturafisicaUnidadeorganizacionalService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.resultado.model.ResultadoForm;
import br.com.j4business.saga.resultado.service.ResultadoService;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcesso;
import br.com.j4business.saga.unidadeorganizacionalprocesso.service.UnidadeorganizacionalProcessoService;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacao.service.AvaliacaoService;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcesso;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcessoDashboard;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcessoForm;
import br.com.j4business.saga.avaliacaoprocesso.repository.AvaliacaoProcessoRepository;
import br.com.j4business.saga.avaliacaoresultado.model.AvaliacaoResultado;
import br.com.j4business.saga.avaliacaoresultado.service.AvaliacaoResultadoService;
import br.com.j4business.saga.cenario.service.CenarioService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Service("avaliacaoProcessoService")
public class AvaliacaoProcessoServiceImpl implements AvaliacaoProcessoService {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoResultadoService avaliacaoResultadoService;

	@Autowired
	private AvaliacaoProcessoRepository avaliacaoProcessoRepository;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private EstruturafisicaService estruturafisicaService;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private ResultadoService resultadoService;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private QuestionarioQuestaoService questionarioQuestaoService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UnidadeorganizacionalProcessoService unidadeorganizacionalProcessoService;

	@Autowired
	private EstruturafisicaUnidadeorganizacionalService estruturafisicaUnidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AvaliacaoProcessoService.class.getName());

	@Override
	public List<AvaliacaoProcesso> getByAvaliacaoNome(String avaliacaoNome, Pageable pageable) {
		return avaliacaoProcessoRepository.findByAvaliacaoNome(avaliacaoNome, pageable);
	}

	@Override
	public List<AvaliacaoProcesso> getByProcessoNome(String processoNome, Pageable pageable) {
		return avaliacaoProcessoRepository.findByProcessoNome(processoNome, pageable);
	}

	@Override
	public List<AvaliacaoProcesso> getByQuestionarioNome(String questionarioNome, Pageable pageable) {
		return avaliacaoProcessoRepository.findByQuestionarioNome(questionarioNome, pageable);
	}

	@Override
	public List<AvaliacaoProcesso> getByPeriodoNome(String periodoNome, Pageable pageable) {
		return avaliacaoProcessoRepository.findByPeriodoNome(periodoNome, pageable);
	}

	@Override
	public List<AvaliacaoProcesso> getByAvaliacaoNome(String avaliacaoNome) {
		return avaliacaoProcessoRepository.findByAvaliacaoNome(avaliacaoNome);
	}

	@Override
	public List<AvaliacaoProcesso> getByProcessoNome(String processoNome) {
		return avaliacaoProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<AvaliacaoProcesso> getByQuestionarioNome(String questionarioNome) {
		return avaliacaoProcessoRepository.findByQuestionarioNome(questionarioNome);
	}

	@Override
	public List<AvaliacaoProcesso> getByPeriodoNome(String periodoNome) {
		return avaliacaoProcessoRepository.findByPeriodoNome(periodoNome);
	}

	@Override
	public List<AvaliacaoProcesso> getByProcessoPK(long processoPK, Pageable pageable) {
		return avaliacaoProcessoRepository.findByProcessoPK(processoPK, pageable);
	}

	@Override
	public List<AvaliacaoProcesso> getByAvaliacaoPK(long avaliacaoPK, Pageable pageable) {
		return avaliacaoProcessoRepository.findByAvaliacaoPK(avaliacaoPK, pageable);
	}

	@Override
	public List<AvaliacaoProcesso> getAvaliacaoProcessoAll(Pageable pageable) {
		return avaliacaoProcessoRepository.findAvaliacaoProcessoAll(pageable);
	}

	@Override
	public AvaliacaoProcesso getAvaliacaoProcessoByAvaliacaoProcessoPK(long avaliacaoProcessoPK) {
		return avaliacaoProcessoRepository.findOne(avaliacaoProcessoPK);
	}

	@Override
	public AvaliacaoProcesso getByAvaliacaoAndProcesso(Avaliacao avaliacao, Processo processo) {

		return avaliacaoProcessoRepository.findByAvaliacaoAndProcesso(avaliacao, processo);
	}

	@Override
	public AvaliacaoProcesso getByPeriodoNome(Avaliacao avaliacao, Processo processo,Questionario questionario, String periodoNome) {

		return avaliacaoProcessoRepository.findByPeriodoNome(avaliacao, processo, questionario, periodoNome);
	}

	@Override
	public List<AvaliacaoProcesso> getByProcessoAndQuestionario(Processo processo, Questionario questionario) {

		return avaliacaoProcessoRepository.findByProcessoAndQuestionario(processo, questionario);
	}

	@Override
	public List<AvaliacaoProcesso> getByProcessoAndQuestionario(Processo processo, Questionario questionario,Pageable pageable) {

		return avaliacaoProcessoRepository.findByProcessoAndQuestionario(processo, questionario, pageable);
	}

	@Transactional
	public AvaliacaoProcesso save(AvaliacaoProcesso avaliacaoProcesso) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoProcesso Save " + "\n Usuário => " + username + " // Id => "
				+ avaliacaoProcesso.getAvaliacaoProcessoPK() + " // Avaliacao Id => "
				+ avaliacaoProcesso.getAvaliacao().getAvaliacaoPK() + " // Processo Id => "
				+ avaliacaoProcesso.getProcesso().getProcessoPK());
		return avaliacaoProcesso;
	}

	@Transactional
	public AvaliacaoProcesso addAvaliacaoQuestionario(AvaliacaoProcesso avaliacaoProcesso) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoProcesso Save " + "\n Usuário => " + username + " // Id => "
				+ avaliacaoProcesso.getAvaliacaoProcessoPK() + " // Avaliacao Id => "
				+ avaliacaoProcesso.getAvaliacao().getAvaliacaoPK() + " // Processo Id => "
				+ avaliacaoProcesso.getProcesso().getProcessoPK());
		return avaliacaoProcesso;
	}

	@Transactional
	public void delete(Long avaliacaoProcessoPK) {

		AvaliacaoProcesso avaliacaoProcessoTemp = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoProcessoRepository.delete(avaliacaoProcessoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoProcesso Save " + "\n Usuário => " + username + " // Id => "
				+ avaliacaoProcessoTemp.getAvaliacaoProcessoPK() + " // Avaliacao Id => "
				+ avaliacaoProcessoTemp.getAvaliacao().getAvaliacaoPK() + " // Processo Id => "
				+ avaliacaoProcessoTemp.getProcesso().getProcessoPK());
	}

	@Transactional
	public void deleteByProcesso(Processo processo) {

		List<AvaliacaoProcesso> avaliacaoProcessos = avaliacaoProcessoRepository.findByProcesso(processo);

		avaliacaoProcessoRepository.delete(avaliacaoProcessos);

		String username = usuarioSeguranca.getUsuarioLogado();

		avaliacaoProcessos.forEach((AvaliacaoProcesso avaliacaoProcesso) -> {			
			
			logger.info("AvaliacaoProcesso Delete2 " + "\n Usuário => " + username + " // Id => "
					+ avaliacaoProcesso.getAvaliacaoProcessoPK() + " // Avaliacao Id => "
					+ avaliacaoProcesso.getAvaliacao().getAvaliacaoPK() + " // Processo Id => "
					+ avaliacaoProcesso.getProcesso().getProcessoPK());

		});

	}

	@Transactional
	public AvaliacaoProcesso converteAvaliacaoProcessoForm(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		Avaliacao avaliacao = avaliacaoService
				.getAvaliacaoByAvaliacaoPK(Long.parseLong(avaliacaoProcessoForm.getAvaliacaoNome()));
		avaliacaoProcesso.setAvaliacao(avaliacao);

		Colaborador colaborador = colaboradorService
				.getColaboradorByColaboradorPK(Long.parseLong(avaliacaoProcessoForm.getAvaliacaoProcessoResponsavel()));
		avaliacaoProcesso.setColaboradorResponsavel(colaborador);

		Processo processo = processoService
				.getProcessoByProcessoPK(Long.parseLong(avaliacaoProcessoForm.getProcessoNome()));
		avaliacaoProcesso.setProcesso(processo);

		Questionario questionario = questionarioService
				.getQuestionarioByQuestionarioPK(Long.parseLong(avaliacaoProcessoForm.getQuestionarioNome()));
		avaliacaoProcesso.setQuestionario(questionario);

		Estruturafisica estruturafisica = estruturafisicaService
				.getEstruturafisicaByEstruturafisicaPK(avaliacaoProcessoForm.getEstruturafisicaPK());
		avaliacaoProcesso.setEstruturafisica(estruturafisica);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService
				.getUnidadeorganizacionalByUnidadeorganizacionalPK(avaliacaoProcessoForm.getUnidadeorganizacionalPK());
		avaliacaoProcesso.setUnidadeorganizacional(unidadeorganizacional);

		avaliacaoProcesso.setAvaliacaoProcessoMotivoOperacao(
				avaliacaoProcessoForm.getAvaliacaoProcessoMotivoOperacao().replaceAll("\\s+", " "));
		avaliacaoProcesso.setAvaliacaoProcessoStatus(
				avaliacaoProcessoForm.getAvaliacaoProcessoStatus() + "".replaceAll("\\s+", " "));

		avaliacaoProcesso.setAvaliacaoProcessoPeriodo(avaliacaoProcessoForm.getAvaliacaoProcessoPeriodo() + "");

		return avaliacaoProcesso;
	}

	@Transactional
	public AvaliacaoProcessoForm converteAvaliacaoProcesso(AvaliacaoProcesso avaliacaoProcesso) {

		AvaliacaoProcessoForm avaliacaoProcessoForm = new AvaliacaoProcessoForm();

		avaliacaoProcessoForm.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoForm.setAvaliacaoPK(avaliacaoProcesso.getAvaliacao().getAvaliacaoPK());
		avaliacaoProcessoForm.setAvaliacaoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome() + "");
		avaliacaoProcessoForm.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		avaliacaoProcessoForm
				.setEstruturafisicaNome(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaNome() + "");
		avaliacaoProcessoForm.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoForm.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome() + "");
		avaliacaoProcessoForm.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoForm.setQuestionarioNome(avaliacaoProcesso.getQuestionario().getQuestionarioNome() + "");
		avaliacaoProcessoForm
				.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoForm.setUnidadeorganizacionalNome(
				avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalNome() + "");

		avaliacaoProcessoForm.setAvaliacaoProcessoPeriodo(avaliacaoProcesso.getAvaliacaoProcessoPeriodo());

		avaliacaoProcessoForm
				.setAvaliacaoProcessoMotivoOperacao(avaliacaoProcesso.getAvaliacaoProcessoMotivoOperacao());
		avaliacaoProcessoForm
				.setAvaliacaoProcessoStatus(AtributoStatus.valueOf(avaliacaoProcesso.getAvaliacaoProcessoStatus()));

		avaliacaoProcessoForm
				.setAvaliacaoProcessoResponsavel(avaliacaoProcesso.getColaboradorResponsavel().getPessoaPK() + "");

		return avaliacaoProcessoForm;
	}

	@Transactional
	public AvaliacaoProcessoForm converteAvaliacaoProcessoView(AvaliacaoProcesso avaliacaoProcesso) {

		AvaliacaoProcessoForm avaliacaoProcessoForm = new AvaliacaoProcessoForm();

		avaliacaoProcessoForm.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoForm.setAvaliacaoPK(avaliacaoProcesso.getAvaliacao().getAvaliacaoPK());
		avaliacaoProcessoForm.setAvaliacaoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome());
		avaliacaoProcessoForm.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		avaliacaoProcessoForm
				.setEstruturafisicaNome(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaNome() + "");
		avaliacaoProcessoForm.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoForm.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome() + "");
		avaliacaoProcessoForm.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoForm.setQuestionarioNome(avaliacaoProcesso.getQuestionario().getQuestionarioNome() + "");
		avaliacaoProcessoForm
				.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoForm.setUnidadeorganizacionalNome(
				avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalNome() + "");

		avaliacaoProcessoForm.setAvaliacaoProcessoPeriodo(avaliacaoProcesso.getAvaliacaoProcessoPeriodo());

		avaliacaoProcessoForm
				.setAvaliacaoProcessoMotivoOperacao(avaliacaoProcesso.getAvaliacaoProcessoMotivoOperacao());
		avaliacaoProcessoForm
				.setAvaliacaoProcessoStatus(AtributoStatus.valueOf(avaliacaoProcesso.getAvaliacaoProcessoStatus()));

		avaliacaoProcessoForm
				.setAvaliacaoProcessoResponsavel(avaliacaoProcesso.getColaboradorResponsavel().getPessoaPK() + "");

		return avaliacaoProcessoForm;
	}

	@Transactional
	public AvaliacaoProcessoForm avaliacaoProcessoParametros(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		avaliacaoProcessoForm.setAvaliacaoProcessoStatus(AtributoStatus.valueOf("ATIVO"));

		return avaliacaoProcessoForm;
	}

	@Transactional
	public AvaliacaoProcesso create(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoProcesso avaliacaoProcesso = new AvaliacaoProcesso();

		Avaliacao avaliacao = avaliacaoService
				.getAvaliacaoByAvaliacaoPK(Long.parseLong(avaliacaoProcessoForm.getAvaliacaoNome()));
		avaliacaoProcesso.setAvaliacao(avaliacao);

		Processo processo = processoService
				.getProcessoByProcessoPK(Long.parseLong(avaliacaoProcessoForm.getProcessoNome()));
		avaliacaoProcesso.setProcesso(processo);

		Questionario questionario = questionarioService
				.getQuestionarioByQuestionarioPK(Long.parseLong(avaliacaoProcessoForm.getQuestionarioNome()));
		avaliacaoProcesso.setQuestionario(questionario);

		Estruturafisica estruturafisica = estruturafisicaService
				.getEstruturafisicaByEstruturafisicaPK(Long.parseLong(avaliacaoProcessoForm.getEstruturafisicaNome()));
		avaliacaoProcesso.setEstruturafisica(estruturafisica);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService
				.getUnidadeorganizacionalByUnidadeorganizacionalPK(
						Long.parseLong(avaliacaoProcessoForm.getUnidadeorganizacionalNome()));
		avaliacaoProcesso.setUnidadeorganizacional(unidadeorganizacional);

		avaliacaoProcesso.setAvaliacaoProcessoPeriodo(avaliacaoProcessoForm.getAvaliacaoProcessoPeriodo());

		avaliacaoProcesso.setAvaliacaoProcessoMotivoOperacao(
				avaliacaoProcessoForm.getAvaliacaoProcessoMotivoOperacao().replaceAll("\\s+", " "));
		avaliacaoProcesso.setAvaliacaoProcessoStatus(
				avaliacaoProcessoForm.getAvaliacaoProcessoStatus() + "".replaceAll("\\s+", " "));

		Colaborador colaborador = colaboradorService
				.getColaboradorByColaboradorPK(Long.parseLong(avaliacaoProcessoForm.getAvaliacaoProcessoResponsavel()));
		avaliacaoProcesso.setColaboradorResponsavel(colaborador);

		avaliacaoProcesso = entityManager.merge(avaliacaoProcesso);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + " // Id => "
				+ avaliacaoProcesso.getAvaliacaoProcessoPK() + " // Avaliacao Id => "
				+ avaliacaoProcesso.getAvaliacao().getAvaliacaoPK() + " // Processo Id => "
				+ avaliacaoProcesso.getProcesso().getProcessoPK());

		return entityManager.merge(avaliacaoProcesso);
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioAmbiente(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoColorimetria() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoColorimetria");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoColorimetria());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoColorimetria()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoConforto() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoConforto");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoConforto());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoConforto()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoEntretenimento() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoEntretenimento");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEntretenimento());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEntretenimento()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoEspacoFisico() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoEspacoFisico");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEspacoFisico());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEspacoFisico()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoHigiene() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoHigiene");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoHigiene());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoHigiene()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoIluminacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoIluminacao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoIluminacao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoIluminacao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoPrivacidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoPrivacidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPrivacidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPrivacidade()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoSomAmbiente() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoSomAmbiente");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSomAmbiente());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSomAmbiente()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoUmidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoUmidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoUmidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoUmidade()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoVentilacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoVentilacao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoVentilacao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoVentilacao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}

	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioAreaTecnica(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoAparelhoTecnico01() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAparelhoTecnico01");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico01());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico01()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}

		if (avaliacaoProcessoForm.getElementoAparelhoTecnico02() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAparelhoTecnico02");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico02());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico02()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}

		if (avaliacaoProcessoForm.getElementoAparelhoTecnico03() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAparelhoTecnico03");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico03());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico03()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}

		if (avaliacaoProcessoForm.getElementoAparelhoTecnico04() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAparelhoTecnico04");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico04());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico04()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}

		if (avaliacaoProcessoForm.getElementoAparelhoTecnico05() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAparelhoTecnico05");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico05());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnico05()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}

	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioDesempenho(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();
		}
		

		if (avaliacaoProcessoForm.getElementoCliente() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCliente");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCliente());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCliente()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoReclamacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoReclamacao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoReclamacao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoReclamacao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoDesempenho01() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoDesempenho01");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDesempenho01());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDesempenho01()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoDesempenho02() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoDesempenho02");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDesempenho02());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDesempenho02()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoDesempenho03() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoDesempenho03");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDesempenho03());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDesempenho03()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioFinanceiro(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoSalario() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoSalario");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSalario());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSalario()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoManutencao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoManutencao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoManutencao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoManutencao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTreinamento() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTreinamento");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTreinamento());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTreinamento()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoAparelhoTecnicoFinanceiro() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAparelhoTecnicoFinanceiro");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnicoFinanceiro());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAparelhoTecnicoFinanceiro()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaInformacaoFinanceiro() > 0) {
			Elemento elemento = elementoService
					.getByElementoDescricaoCompleto("elementoTecnologiaInformacaoFinanceiro");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(
						avaliacaoProcessoForm.getElementoTecnologiaInformacaoFinanceiro());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaInformacaoFinanceiro()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioLogistica(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoIntegracao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoIntegracao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoIntegracao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoIntegracao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoPraticidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoPraticidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPraticidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPraticidade()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoLogistica01() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoLogistica01");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLogistica01());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLogistica01()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoLogistica02() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoLogistica02");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLogistica02());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLogistica02()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoLogistica03() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoLogistica03");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLogistica03());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLogistica03()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioMobilia(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());

			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoMesa() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoMesa");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoMesa());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoMesa()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoCadeira() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCadeira");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCadeira());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCadeira()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoBalcao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoBalcao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoBalcao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoBalcao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoSofa() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoSofa");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSofa());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSofa()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoCortina() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCortina");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCortina());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCortina()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioPessoal(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoAgilidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAgilidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAgilidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAgilidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoApresentacaoPessoal() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoApresentacaoPessoal");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoApresentacaoPessoal());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoApresentacaoPessoal()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoAtendimento() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAtendimento");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAtendimento());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAtendimento()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoConhecimentoTecnico() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoConhecimentoTecnico");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoConhecimentoTecnico());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoConhecimentoTecnico()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoCortesia() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCortesia");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCortesia());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCortesia()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoDisponibilidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoDisponibilidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDisponibilidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoDisponibilidade()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoEducacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoEducacao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEducacao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEducacao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoEquilibrioEmocional() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoEquilibrioEmocional");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEquilibrioEmocional());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoEquilibrioEmocional()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoInformacaoInstituicao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoInformacaoInstituicao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoInformacaoInstituicao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoInformacaoInstituicao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoProAtividade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoProAtividade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoProAtividade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoProAtividade()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoQuadroFuncionario() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoQuadroFuncionario");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoQuadroFuncionario());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoQuadroFuncionario()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoSimpatia() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoSimpatia");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSimpatia());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSimpatia()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTempoUtilizado() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTempoUtilizado");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTempoUtilizado());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTempoUtilizado()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoVocabulario() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoVocabulario");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoVocabulario());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoVocabulario()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioSeguranca(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoSinalizacaoAviso() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoSinalizacaoAviso");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSinalizacaoAviso());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSinalizacaoAviso()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoSinalizacaoEmergencia() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoSinalizacaoEmergencia");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSinalizacaoEmergencia());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoSinalizacaoEmergencia()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoVigilante() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoVigilante");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoVigilante());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoVigilante()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoExtintorIncendio() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoExtintorIncendio");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoExtintorIncendio());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoExtintorIncendio()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoCamera() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCamera");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCamera());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCamera()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioSustentabilidade(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoAguaPotavel() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoAguaPotavel");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAguaPotavel());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoAguaPotavel()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoArCondicionado() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoArCondicionado");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoArCondicionado());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoArCondicionado()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoBlocoAnotacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoBlocoAnotacao");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoBlocoAnotacao());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoBlocoAnotacao()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoCaronaSolidaria() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCaronaSolidaria");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCaronaSolidaria());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCaronaSolidaria()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoCopoDescartavel() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoCopoDescartavel");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCopoDescartavel());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoCopoDescartavel()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoLampadaEconomica() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoLampadaEconomica");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLampadaEconomica());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLampadaEconomica()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoLapisMadeiraReplantio() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoLapisMadeiraReplantio");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLapisMadeiraReplantio());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLapisMadeiraReplantio()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoLixeiraReciclavel() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoLixeiraReciclavel");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLixeiraReciclavel());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoLixeiraReciclavel()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoPalestraSustentabilidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoPalestraSustentabilidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPalestraSustentabilidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPalestraSustentabilidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoPapelImpressora() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoPapelImpressora");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPapelImpressora());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPapelImpressora()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoPapelReciclado() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoPapelReciclado");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPapelReciclado());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoPapelReciclado()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTransporteColetivo() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTransporteColetivo");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTransporteColetivo());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTransporteColetivo()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoUsoEnergiaEletrica() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoUsoEnergiaEletrica");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoUsoEnergiaEletrica());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoUsoEnergiaEletrica()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	@Transactional
	public void saveAvaliacaoProcessoQuestionarioTecnologia(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		AvaliacaoResultado avaliacaoResultadoTemp = new AvaliacaoResultado();
		AvaliacaoResultado avaliacaoResultadoPreenchida = new AvaliacaoResultado();
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
		AvaliacaoProcesso avaliacaoProcesso = this
				.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());

		if (avaliacaoResultados.isEmpty()) {

			ResultadoForm resultadoForm = new ResultadoForm();

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmssZ");
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(date);
				System.out.println(inActiveDate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			resultadoForm.setResultadoNome(avaliacaoProcesso.getAvaliacao().getAvaliacaoNome()+inActiveDate);
			resultadoForm.setResultadoDescricao(avaliacaoProcesso.getAvaliacao().getAvaliacaoDescricao());
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);

			resultado.setColaboradorResponsavel(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setAvaliacao(avaliacaoProcesso.getAvaliacao());
			avaliacaoResultadoPreenchida.setAvaliacaoProcesso(avaliacaoProcesso);
			avaliacaoResultadoPreenchida.setColaborador(avaliacaoProcesso.getColaboradorResponsavel());
			avaliacaoResultadoPreenchida.setResultado(resultado);

		} else {

			avaliacaoResultadoPreenchida  = avaliacaoResultados.stream().findFirst().get();

		}

		if (avaliacaoProcessoForm.getElementoTecnologiaComputadorPCCapacidade() > 0) {
			Elemento elemento = elementoService
					.getByElementoDescricaoCompleto("elementoTecnologiaComputadorPCCapacidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(
						avaliacaoProcessoForm.getElementoTecnologiaComputadorPCCapacidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(
						avaliacaoProcessoForm.getElementoTecnologiaComputadorPCCapacidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaComputadorPCSom() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaComputadorPCSom");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaComputadorPCSom());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaComputadorPCSom()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaComputadorPCVelocidade() > 0) {
			Elemento elemento = elementoService
					.getByElementoDescricaoCompleto("elementoTecnologiaComputadorPCVelocidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(
						avaliacaoProcessoForm.getElementoTecnologiaComputadorPCVelocidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(
						avaliacaoProcessoForm.getElementoTecnologiaComputadorPCVelocidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaImpressoraTipo() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaImpressoraTipo");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaImpressoraTipo());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaImpressoraTipo()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaImpressoraVelocidade() > 0) {
			Elemento elemento = elementoService
					.getByElementoDescricaoCompleto("elementoTecnologiaImpressoraVelocidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(
						avaliacaoProcessoForm.getElementoTecnologiaImpressoraVelocidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaImpressoraVelocidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaMonitorTamanho() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaMonitorTamanho");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaMonitorTamanho());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaMonitorTamanho()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaMonitorTipo() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaMonitorTipo");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaMonitorTipo());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaMonitorTipo()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaMouse() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaMouse");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaMouse());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaMouse()
						+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaTabletTamanho() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaTabletTamanho");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaTabletTamanho());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaTabletTamanho()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaTabletVelocidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaTabletVelocidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaTabletVelocidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaTabletVelocidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaWifiCapacidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaWifiCapacidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaWifiCapacidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaWifiCapacidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
		if (avaliacaoProcessoForm.getElementoTecnologiaWifiVelocidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoTecnologiaWifiVelocidade");
			avaliacaoResultadoTemp = avaliacaoResultadoService.getByAvaliacaoProcessoPKAndElementoPK(
					avaliacaoProcesso.getAvaliacaoProcessoPK(), elemento.getElementoPK());

			if (avaliacaoResultadoTemp == null) {
				AvaliacaoResultado avaliacaoResultado = new AvaliacaoResultado();
				avaliacaoResultado.setElemento(elemento);
				avaliacaoResultado.setAvaliacao(avaliacaoResultadoPreenchida.getAvaliacao());
				avaliacaoResultado.setAvaliacaoProcesso(avaliacaoResultadoPreenchida.getAvaliacaoProcesso());
				avaliacaoResultado.setColaborador(avaliacaoResultadoPreenchida.getColaborador());
				avaliacaoResultado.setResultado(avaliacaoResultadoPreenchida.getResultado());
				avaliacaoResultado
						.setAvaliacaoElementoQuantidade(avaliacaoResultado.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultado
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaWifiVelocidade());
				avaliacaoResultadoService.create(avaliacaoResultado);
			} else {
				avaliacaoResultadoTemp
						.setAvaliacaoElementoQuantidade(avaliacaoResultadoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoTemp
						.setAvaliacaoElementoResposta(avaliacaoProcessoForm.getElementoTecnologiaWifiVelocidade()
								+ avaliacaoResultadoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoService.save(avaliacaoResultadoTemp);
			}
		}
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAmbiente(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAmbiente");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();

		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
			
			
			if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());
		

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();

		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {			
			
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAmbientePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
			
			if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());


		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {			
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
				
				if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAmbientePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		for (Iterator<AvaliacaoResultado> iteratorX = avaliacaoResultadoList.iterator(); iteratorX.hasNext();) {
			AvaliacaoResultado avaliacaoResultado = (AvaliacaoResultado) iteratorX.next();

			if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		}

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
				
				if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAmbientePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		for (Iterator<AvaliacaoResultado> iteratorX = avaliacaoResultadoList.iterator(); iteratorX.hasNext();) {
			AvaliacaoResultado avaliacaoResultado = (AvaliacaoResultado) iteratorX.next();

			if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		}

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
				
				if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAmbientePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
				
				if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAmbientePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 1) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 2) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 3) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 4) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 5) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 6) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 7) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 8) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 10) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 11) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenho(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioDesempenho");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioDesempenhoPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioDesempenhoPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioDesempenhoPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioDesempenhoPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioDesempenhoPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 17) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 18) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 96) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 97) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 98) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiro(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioFinanceiro");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioFinanceiroPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioFinanceiroPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioFinanceiroPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioFinanceiroPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioFinanceiroPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 19) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 21) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 32) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 33) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 34) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioLogistica(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioLogistica");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioLogisticaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioLogisticaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioLogisticaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioLogisticaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioLogisticaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 9) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 22) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 99) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 100) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 101) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioPessoal");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 23) //Apresentação Pessoal 
			{  
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 24) //Atendimento
			{
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 25) //Conhecimento Técnico
			{
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 26) //Quadro de Funcionário
			{
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 81) //Simpatia
			{
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 82) //Cortesia
			{
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 83) //InformacaoInstituicao
			{
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 84) //Agilidade
			{
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 85) //Tempo Utilizado
			{
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 86) //Vocabulario
			{
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 87) //Disponibilidade
			{
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 88) //ProAtividade
			{
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 89) //Educacao
			{
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} 
			else if (avaliacaoResultado.getElemento().getElementoPK() == 90) //Equilibrio Emocional
			{
				avaliacaoProcessoDashboard
						.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioPessoalPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
				avaliacaoProcessoDashboard
						.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
					avaliacaoProcessoDashboard
							.setElemento27((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
					avaliacaoProcessoDashboard
							.setElemento28((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
			avaliacaoProcessoDashboard.setElemento27(0);
			avaliacaoProcessoDashboard.setElemento28(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioPessoalPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
				avaliacaoProcessoDashboard
						.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
					avaliacaoProcessoDashboard
							.setElemento27((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
					avaliacaoProcessoDashboard
							.setElemento28((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
			avaliacaoProcessoDashboard.setElemento27(0);
			avaliacaoProcessoDashboard.setElemento28(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioPessoalPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
				avaliacaoProcessoDashboard
						.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
					avaliacaoProcessoDashboard
							.setElemento27((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
					avaliacaoProcessoDashboard
							.setElemento28((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
			avaliacaoProcessoDashboard.setElemento27(0);
			avaliacaoProcessoDashboard.setElemento28(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioPessoalPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
				avaliacaoProcessoDashboard
						.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
					avaliacaoProcessoDashboard
							.setElemento27((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
					avaliacaoProcessoDashboard
							.setElemento28((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
			avaliacaoProcessoDashboard.setElemento27(0);
			avaliacaoProcessoDashboard.setElemento28(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioPessoalPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
				avaliacaoProcessoDashboard
						.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 23) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 24) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 25) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 26) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 81) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 82) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 83) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 84) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 85) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 86) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 87) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 88) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 89) {
					avaliacaoProcessoDashboard
							.setElemento27((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 90) {
					avaliacaoProcessoDashboard
							.setElemento28((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
			avaliacaoProcessoDashboard.setElemento27(0);
			avaliacaoProcessoDashboard.setElemento28(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioSeguranca(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSeguranca");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSegurancaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSegurancaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSegurancaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSegurancaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSegurancaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 12) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 27) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 28) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 29) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 35) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnica(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAreaTecnica");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAreaTecnicaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}

		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAreaTecnicaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAreaTecnicaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAreaTecnicaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioAreaTecnicaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 91) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 92) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 93) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 94) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 95) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}


	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologia(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioTecnologia");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioTecnologiaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioTecnologiaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioTecnologiaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioTecnologiaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioTecnologiaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 49) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 50) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 51) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 52) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 53) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 54) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 55) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 56) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 57) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 58) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 59) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 60) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidade(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSustentabilidade");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSustentabilidadePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSustentabilidadePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSustentabilidadePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSustentabilidadePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioSustentabilidadePeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
				avaliacaoProcessoDashboard
						.setElemento6((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
				avaliacaoProcessoDashboard
						.setElemento7((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
				avaliacaoProcessoDashboard
						.setElemento8((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
				avaliacaoProcessoDashboard
						.setElemento9((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
				avaliacaoProcessoDashboard
						.setElemento10((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
				avaliacaoProcessoDashboard
						.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
				avaliacaoProcessoDashboard
						.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
				avaliacaoProcessoDashboard
						.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 36) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 37) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 38) {
					avaliacaoProcessoDashboard
							.setElemento16((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 39) {
					avaliacaoProcessoDashboard
							.setElemento17((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 40) {
					avaliacaoProcessoDashboard
							.setElemento18((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 41) {
					avaliacaoProcessoDashboard
							.setElemento19((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 42) {
					avaliacaoProcessoDashboard
							.setElemento20((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 43) {
					avaliacaoProcessoDashboard
							.setElemento21((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 44) {
					avaliacaoProcessoDashboard
							.setElemento22((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 45) {
					avaliacaoProcessoDashboard
							.setElemento23((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 46) {
					avaliacaoProcessoDashboard
							.setElemento24((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 47) {
					avaliacaoProcessoDashboard
							.setElemento25((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 48) {
					avaliacaoProcessoDashboard
							.setElemento26((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
			avaliacaoProcessoDashboard.setElemento21(0);
			avaliacaoProcessoDashboard.setElemento22(0);
			avaliacaoProcessoDashboard.setElemento23(0);
			avaliacaoProcessoDashboard.setElemento24(0);
			avaliacaoProcessoDashboard.setElemento25(0);
			avaliacaoProcessoDashboard.setElemento26(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	
	public ModelAndView avaliacaoProcessoDashboardCenarioMobilia(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioMobilia");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaMensal(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioMobiliaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -1);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaBimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioMobiliaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -2);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaTrimestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioMobiliaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -3);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaSemestral(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioMobiliaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -6);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());

		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}

	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaAnual(
			@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("processoPK") Long processoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardCenarioMobiliaPeriodo");
		AvaliacaoProcesso avaliacaoProcesso = this.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = this.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());

		List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService
				.getByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
				avaliacaoProcessoDashboard
						.setElemento1((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
				avaliacaoProcessoDashboard
						.setElemento2((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
				avaliacaoProcessoDashboard
						.setElemento3((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
				avaliacaoProcessoDashboard
						.setElemento4((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
				avaliacaoProcessoDashboard
						.setElemento5((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
								/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
			}
		});

		avaliacaoProcessoDashboard.setCenarioNome(avaliacaoProcesso.getQuestionario().getCenario().getCenarioNome());
		avaliacaoProcessoDashboard.setProcessoNome(avaliacaoProcesso.getProcesso().getProcessoNome());

		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService
				.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService
					.getByUnidadeorganizacionalPK(
							unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);

		Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso.getAvaliacaoProcessoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");

		Date dateConvertidaAtual = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoProcessoPeriodoCalendar.add(Calendar.MONTH, -12);

		Date dateConvertidaAnterior = avaliacaoProcessoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoProcessoDashboard.setAvaliacaoProcessoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoProcesso avaliacaoProcessoAnterior = this.getByPeriodoNome(avaliacaoProcesso.getAvaliacao(), 
																		avaliacaoProcesso.getProcesso(), 
																		avaliacaoProcesso.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoProcessoAnterior != null) {
		
			List<AvaliacaoResultado> avaliacaoResultadoList2 = avaliacaoResultadoService
					.getByAvaliacaoProcessoPK(avaliacaoProcessoAnterior.getAvaliacaoProcessoPK());
	
			avaliacaoResultadoList2.forEach((AvaliacaoResultado avaliacaoResultado) -> {			
	
				if (avaliacaoResultado.getElemento().getElementoPK() == 14) {
					avaliacaoProcessoDashboard
							.setElemento11((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 15) {
					avaliacaoProcessoDashboard
							.setElemento12((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 16) {
					avaliacaoProcessoDashboard
							.setElemento13((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 102) {
					avaliacaoProcessoDashboard
							.setElemento14((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				} else if (avaliacaoResultado.getElemento().getElementoPK() == 103) {
					avaliacaoProcessoDashboard
							.setElemento15((int) Math.round(avaliacaoResultado.getAvaliacaoElementoResposta()
									/ avaliacaoResultado.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoProcessoDashboard.setElemento11(0);
			avaliacaoProcessoDashboard.setElemento12(0);
			avaliacaoProcessoDashboard.setElemento13(0);
			avaliacaoProcessoDashboard.setElemento14(0);
			avaliacaoProcessoDashboard.setElemento15(0);
			avaliacaoProcessoDashboard.setElemento16(0);
			avaliacaoProcessoDashboard.setElemento17(0);
			avaliacaoProcessoDashboard.setElemento18(0);
			avaliacaoProcessoDashboard.setElemento19(0);
			avaliacaoProcessoDashboard.setElemento20(0);
		}

		avaliacaoProcessoDashboard.setAvaliacaoProcessoPK(avaliacaoProcesso.getAvaliacaoProcessoPK());
		avaliacaoProcessoDashboard.setQuestionarioPK(avaliacaoProcesso.getQuestionario().getQuestionarioPK());
		avaliacaoProcessoDashboard.setProcessoPK(avaliacaoProcesso.getProcesso().getProcessoPK());
		avaliacaoProcessoDashboard.setUnidadeorganizacionalPK(avaliacaoProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoProcessoDashboard.setEstruturafisicaPK(avaliacaoProcesso.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);

		return mv;
	}


}
