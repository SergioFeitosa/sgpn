package br.com.j4business.saga.avaliacaocontrato.service;

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
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.resultado.model.ResultadoForm;
import br.com.j4business.saga.resultado.service.ResultadoService;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContrato;
import br.com.j4business.saga.unidadeorganizacionalcontrato.service.UnidadeorganizacionalContratoService;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacao.service.AvaliacaoService;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContrato;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContratoDashboard;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContratoForm;
import br.com.j4business.saga.avaliacaocontrato.repository.AvaliacaoContratoRepository;
import br.com.j4business.saga.avaliacaoresultadocontrato.model.AvaliacaoResultadoContrato;
import br.com.j4business.saga.avaliacaoresultadocontrato.service.AvaliacaoResultadoContratoService;
import br.com.j4business.saga.cenario.service.CenarioService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Service("avaliacaoContratoService")
public class AvaliacaoContratoServiceImpl implements AvaliacaoContratoService {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoResultadoContratoService avaliacaoResultadoContratoService;

	@Autowired
	private AvaliacaoContratoRepository avaliacaoContratoRepository;

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
	private ContratoService contratoService;

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
	private UnidadeorganizacionalContratoService unidadeorganizacionalContratoService;

	@Autowired
	private EstruturafisicaUnidadeorganizacionalService estruturafisicaUnidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AvaliacaoContratoService.class.getName());


	@Override
	public List<AvaliacaoContrato> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable) {
		return avaliacaoContratoRepository.findByAvaliacaoNome(avaliacaoNome,pageable);
	}

	@Override
	public List<AvaliacaoContrato> getByContratoNome(String contratoNome,Pageable pageable) {
		return avaliacaoContratoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<AvaliacaoContrato> getByAvaliacaoNome(String avaliacaoNome) {
		return avaliacaoContratoRepository.findByAvaliacaoNome(avaliacaoNome);
	}

	@Override
	public List<AvaliacaoContrato> getByContratoNome(String contratoNome) {
		return avaliacaoContratoRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<AvaliacaoContrato> getByContratoPK(long contratoPK,Pageable pageable) {
		return avaliacaoContratoRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<AvaliacaoContrato> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable) {
		return avaliacaoContratoRepository.findByAvaliacaoPK(avaliacaoPK,pageable);
	}

	@Override
	public List<AvaliacaoContrato> getAvaliacaoContratoAll(Pageable pageable) {
		return avaliacaoContratoRepository.findAvaliacaoContratoAll(pageable);
	}

	@Override
	public AvaliacaoContrato getAvaliacaoContratoByAvaliacaoContratoPK(long avaliacaoContratoPK) {
		return avaliacaoContratoRepository.findOne(avaliacaoContratoPK);
	}

	@Override
	public AvaliacaoContrato getByAvaliacaoAndContrato (Avaliacao avaliacao,Contrato contrato) {
		
		return avaliacaoContratoRepository.findByAvaliacaoAndContrato(avaliacao,contrato);
	}

	@Override
	public List<AvaliacaoContrato> getByContratoAndQuestionario (Contrato contrato,Questionario questionario) {
		
		return avaliacaoContratoRepository.findByContratoAndQuestionario(contrato, questionario);
	}

	@Transactional
	public AvaliacaoContrato save(AvaliacaoContrato avaliacaoContrato) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoContrato Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoContrato.getAvaliacaoContratoPK() + 
										" // Avaliacao Id => "+avaliacaoContrato.getAvaliacao().getAvaliacaoPK() + 
										" // Contrato Id => "+avaliacaoContrato.getContrato().getContratoPK());
		return avaliacaoContrato;
	}

	@Transactional
	public AvaliacaoContrato addAvaliacaoQuestionario(AvaliacaoContrato avaliacaoContrato) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoContrato Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoContrato.getAvaliacaoContratoPK() + 
										" // Avaliacao Id => "+avaliacaoContrato.getAvaliacao().getAvaliacaoPK() + 
										" // Contrato Id => "+avaliacaoContrato.getContrato().getContratoPK());
		return avaliacaoContrato;
	}

	@Transactional
	public void delete(Long avaliacaoContratoPK) {

		AvaliacaoContrato avaliacaoContratoTemp = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);

		avaliacaoContratoRepository.delete(avaliacaoContratoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoContrato Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoContratoTemp.getAvaliacaoContratoPK() + 
										" // Avaliacao Id => "+avaliacaoContratoTemp.getAvaliacao().getAvaliacaoPK() + 
										" // Contrato Id => "+avaliacaoContratoTemp.getContrato().getContratoPK()); 
    }

	@Transactional
	public void deleteByContrato(Contrato contrato) {
		
		List<AvaliacaoContrato> avaliacaoContratoList = avaliacaoContratoRepository.findByContrato(contrato);

		avaliacaoContratoRepository.delete(avaliacaoContratoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		avaliacaoContratoList.forEach((AvaliacaoContrato avaliacaoContrato) -> {
			
			logger.info("AvaliacaoContrato Delete2 " + "\n Usuário => " + username + 
											" // Id => "+avaliacaoContrato.getAvaliacaoContratoPK() + 
											" // Avaliacao Id => "+avaliacaoContrato.getAvaliacao().getAvaliacaoPK() + 
											" // Contrato Id => "+avaliacaoContrato.getContrato().getContratoPK()); 

		});
		
    }

	@Transactional
	public AvaliacaoContrato converteAvaliacaoContratoForm(AvaliacaoContratoForm avaliacaoContratoForm) {
		
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoForm.getAvaliacaoContratoPK());
		
		Avaliacao avaliacao = avaliacaoService.getAvaliacaoByAvaliacaoPK(Long.parseLong(avaliacaoContratoForm.getAvaliacaoNome()));
		avaliacaoContrato.setAvaliacao(avaliacao);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(avaliacaoContratoForm.getAvaliacaoContratoResponsavel()));
		avaliacaoContrato.setColaboradorResponsavel(colaborador);
		
		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(avaliacaoContratoForm.getContratoNome()));
		avaliacaoContrato.setContrato(contrato);

		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(Long.parseLong(avaliacaoContratoForm.getQuestionarioNome()));
		avaliacaoContrato.setQuestionario(questionario);

		Estruturafisica estruturafisica = estruturafisicaService.getEstruturafisicaByEstruturafisicaPK(avaliacaoContratoForm.getEstruturafisicaPK());
		avaliacaoContrato.setEstruturafisica(estruturafisica);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(avaliacaoContratoForm.getUnidadeorganizacionalPK());
		avaliacaoContrato.setUnidadeorganizacional(unidadeorganizacional);

		avaliacaoContrato.setAvaliacaoContratoMotivoOperacao(avaliacaoContratoForm.getAvaliacaoContratoMotivoOperacao().replaceAll("\\s+"," "));
		avaliacaoContrato.setAvaliacaoContratoStatus(avaliacaoContratoForm.getAvaliacaoContratoStatus()+"".replaceAll("\\s+"," "));

		avaliacaoContrato.setAvaliacaoContratoPeriodo(avaliacaoContratoForm.getAvaliacaoContratoPeriodo()+"");
		
		return avaliacaoContrato;
	}

	@Transactional
	public AvaliacaoContratoForm converteAvaliacaoContrato(AvaliacaoContrato avaliacaoContrato) {
	
		AvaliacaoContratoForm avaliacaoContratoForm = new AvaliacaoContratoForm();
		
		avaliacaoContratoForm.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoForm.setAvaliacaoPK(avaliacaoContrato.getAvaliacao().getAvaliacaoPK());
		avaliacaoContratoForm.setAvaliacaoNome(avaliacaoContrato.getAvaliacao().getAvaliacaoNome()+"");
		avaliacaoContratoForm.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
		avaliacaoContratoForm.setEstruturafisicaNome(avaliacaoContrato.getEstruturafisica().getEstruturafisicaNome()+"");
		avaliacaoContratoForm.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoForm.setContratoNome(avaliacaoContrato.getContrato().getContratoNome()+"");
		avaliacaoContratoForm.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoForm.setQuestionarioNome(avaliacaoContrato.getQuestionario().getQuestionarioNome()+"");
		avaliacaoContratoForm.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoForm.setUnidadeorganizacionalNome(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalNome()+"");

		avaliacaoContratoForm.setAvaliacaoContratoPeriodo(avaliacaoContrato.getAvaliacaoContratoPeriodo());

		avaliacaoContratoForm.setAvaliacaoContratoMotivoOperacao(avaliacaoContrato.getAvaliacaoContratoMotivoOperacao());
		avaliacaoContratoForm.setAvaliacaoContratoStatus(AtributoStatus.valueOf(avaliacaoContrato.getAvaliacaoContratoStatus()));

		avaliacaoContratoForm.setAvaliacaoContratoResponsavel(avaliacaoContrato.getColaboradorResponsavel().getPessoaPK()+"");
		
	return avaliacaoContratoForm;
	}
	
	@Transactional
	public AvaliacaoContratoForm converteAvaliacaoContratoView(AvaliacaoContrato avaliacaoContrato) {
	
		AvaliacaoContratoForm avaliacaoContratoForm = new AvaliacaoContratoForm();
		
		avaliacaoContratoForm.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoForm.setAvaliacaoPK(avaliacaoContrato.getAvaliacao().getAvaliacaoPK());
		avaliacaoContratoForm.setAvaliacaoNome(avaliacaoContrato.getAvaliacao().getAvaliacaoNome());
		avaliacaoContratoForm.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
		avaliacaoContratoForm.setEstruturafisicaNome(avaliacaoContrato.getEstruturafisica().getEstruturafisicaNome()+"");
		avaliacaoContratoForm.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoForm.setContratoNome(avaliacaoContrato.getContrato().getContratoNome()+"");
		avaliacaoContratoForm.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoForm.setQuestionarioNome(avaliacaoContrato.getQuestionario().getQuestionarioNome()+"");
		avaliacaoContratoForm.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoForm.setUnidadeorganizacionalNome(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalNome()+"");

		avaliacaoContratoForm.setAvaliacaoContratoPeriodo(avaliacaoContrato.getAvaliacaoContratoPeriodo());

		avaliacaoContratoForm.setAvaliacaoContratoMotivoOperacao(avaliacaoContrato.getAvaliacaoContratoMotivoOperacao());
		avaliacaoContratoForm.setAvaliacaoContratoStatus(AtributoStatus.valueOf(avaliacaoContrato.getAvaliacaoContratoStatus()));

		avaliacaoContratoForm.setAvaliacaoContratoResponsavel(avaliacaoContrato.getColaboradorResponsavel().getPessoaPK()+"");

	return avaliacaoContratoForm;
	}
	

	@Transactional
	public AvaliacaoContratoForm avaliacaoContratoParametros(AvaliacaoContratoForm avaliacaoContratoForm) {
	
		
		avaliacaoContratoForm.setAvaliacaoContratoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return avaliacaoContratoForm;
	}
	
	@Transactional
	public AvaliacaoContrato create(AvaliacaoContratoForm avaliacaoContratoForm) {
		
		AvaliacaoContrato avaliacaoContrato = new AvaliacaoContrato();
		
		Avaliacao avaliacao = avaliacaoService.getAvaliacaoByAvaliacaoPK(Long.parseLong(avaliacaoContratoForm.getAvaliacaoNome()));
		avaliacaoContrato.setAvaliacao(avaliacao);

		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(avaliacaoContratoForm.getContratoNome()));
		avaliacaoContrato.setContrato(contrato);

		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(Long.parseLong(avaliacaoContratoForm.getQuestionarioNome()));
		avaliacaoContrato.setQuestionario(questionario);

		Estruturafisica estruturafisica = estruturafisicaService.getEstruturafisicaByEstruturafisicaPK(Long.parseLong(avaliacaoContratoForm.getEstruturafisicaNome()));
		avaliacaoContrato.setEstruturafisica(estruturafisica);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(Long.parseLong(avaliacaoContratoForm.getUnidadeorganizacionalNome()));
		avaliacaoContrato.setUnidadeorganizacional(unidadeorganizacional);

		avaliacaoContrato.setAvaliacaoContratoPeriodo(avaliacaoContratoForm.getAvaliacaoContratoPeriodo());
		
		avaliacaoContrato.setAvaliacaoContratoMotivoOperacao(avaliacaoContratoForm.getAvaliacaoContratoMotivoOperacao().replaceAll("\\s+"," "));
		avaliacaoContrato.setAvaliacaoContratoStatus(avaliacaoContratoForm.getAvaliacaoContratoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(avaliacaoContratoForm.getAvaliacaoContratoResponsavel()));
		avaliacaoContrato.setColaboradorResponsavel(colaborador);

		avaliacaoContrato = entityManager.merge(avaliacaoContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Contrato Create " + "\n Usuário => " + username + 
				" // Id => "+avaliacaoContrato.getAvaliacaoContratoPK() + 
				" // Avaliacao Id => "+avaliacaoContrato.getAvaliacao().getAvaliacaoPK() + 
				" // Contrato Id => "+avaliacaoContrato.getContrato().getContratoPK()); 
		
		return entityManager.merge(avaliacaoContrato);
	}

	@Transactional
	public void saveAvaliacaoContratoQuestionario(AvaliacaoContratoForm avaliacaoContratoForm) {
		
	
		AvaliacaoResultadoContrato avaliacaoResultadoContratoTemp = new AvaliacaoResultadoContrato();
		AvaliacaoResultadoContrato avaliacaoResultadoContratoPreenchida = new AvaliacaoResultadoContrato();
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratos = avaliacaoResultadoContratoService.getByAvaliacaoContratoPK(avaliacaoContratoForm.getAvaliacaoContratoPK());
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoForm.getAvaliacaoContratoPK());
		
		if (avaliacaoResultadoContratos.isEmpty()) {
	
			ResultadoForm resultadoForm = new ResultadoForm();
			resultadoForm.setResultadoNome(avaliacaoContrato.getAvaliacao().getAvaliacaoNome());
			resultadoForm.setResultadoDescricao(avaliacaoContrato.getAvaliacao().getAvaliacaoDescricao());
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			Date date = cal.getTime();             
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");          
			String inActiveDate = null;
			try {
			    inActiveDate = format1.format(date);
			    System.out.println(inActiveDate );
			} catch (Exception e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
			
			resultadoForm.setResultadoStatus(AtributoStatus.ATIVO);
			resultadoForm.setResultadoMotivoOperacao("CADASTRO");
			resultadoForm.setResultadoResponsavel("1");
			Resultado resultado = resultadoService.create(resultadoForm);
				
			resultado.setColaboradorResponsavel(avaliacaoContrato.getColaboradorResponsavel());
			avaliacaoResultadoContratoPreenchida.setAvaliacao(avaliacaoContrato.getAvaliacao());
			avaliacaoResultadoContratoPreenchida.setAvaliacaoContrato(avaliacaoContrato);
			avaliacaoResultadoContratoPreenchida.setColaborador(avaliacaoContrato.getColaboradorResponsavel());
			avaliacaoResultadoContratoPreenchida.setResultado(resultado);
			
		} else {
			
/*			avaliacaoResultadoContratoPreenchida  = avaliacaoResultadoContratos.iterator().next();
*/
			avaliacaoResultadoContratoPreenchida  = avaliacaoResultadoContratos.stream().findFirst().get();
			
		}
		
		if (avaliacaoContratoForm.getElementoContratoDocumentacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoDocumentacao");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacao());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacao()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoDocumentacaoAcessibilidade() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoDocumentacaoAcessibilidade");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacaoAcessibilidade());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacaoAcessibilidade()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoDocumentacaoAtualizacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoDocumentacaoAtualizacao");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacaoAtualizacao());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacaoAtualizacao()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoDocumentacaoVerificacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoDocumentacaoVerificacao");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacaoVerificacao());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoDocumentacaoVerificacao()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoFramework() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoFramework");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoFramework());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoFramework()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoHardware() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoHardware");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoHardware());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoHardware()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoHorario() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoHorario");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoHorario());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoHorario()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoMelhoriaContinua() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoMelhoriaContinua");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoMelhoriaContinua());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoMelhoriaContinua()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoMelhoriaContinuaQuebraAcordo() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoMelhoriaContinuaQuebraAcordo");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoMelhoriaContinuaQuebraAcordo());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoMelhoriaContinuaQuebraAcordo()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoMudancaConstante() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoMudancaConstante");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoMudancaConstante());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoMudancaConstante()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoNivelColaborador() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoNivelColaborador");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoNivelColaborador());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoNivelColaborador()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoPagamento() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoPagamento");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPagamento());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPagamento()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoPagamentoExtra() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoPagamentoExtra");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPagamentoExtra());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPagamentoExtra()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoPagamentoQuebraAcordo() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoPagamentoQuebraAcordo");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPagamentoQuebraAcordo());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPagamentoQuebraAcordo()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoPropostaApresentacao() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoPropostaApresentacao");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPropostaApresentacao());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoPropostaApresentacao()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoQuantidadeColaborador() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoQuantidadeColaborador");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoQuantidadeColaborador());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoQuantidadeColaborador()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoServicoNivel() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoServicoNivel");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoServicoNivel());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoServicoNivel()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoServicoPrestado() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoServicoPrestado");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoServicoPrestado());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoServicoPrestado()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoServicoQuebraAcordo() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoServicoQuebraAcordo");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoServicoQuebraAcordo());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoServicoQuebraAcordo()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
		if (avaliacaoContratoForm.getElementoContratoSoftware() > 0) {
			Elemento elemento = elementoService.getByElementoDescricaoCompleto("elementoContratoSoftware");
			avaliacaoResultadoContratoTemp = avaliacaoResultadoContratoService.getByAvaliacaoContratoPKAndElementoPK(avaliacaoContrato.getAvaliacaoContratoPK(), elemento.getElementoPK());
			
			if (avaliacaoResultadoContratoTemp == null) {
				AvaliacaoResultadoContrato avaliacaoResultadoContrato = new AvaliacaoResultadoContrato();
				avaliacaoResultadoContrato.setElemento(elemento);
				avaliacaoResultadoContrato.setAvaliacao(avaliacaoResultadoContratoPreenchida.getAvaliacao());
				avaliacaoResultadoContrato.setAvaliacaoContrato(avaliacaoResultadoContratoPreenchida.getAvaliacaoContrato());
				avaliacaoResultadoContrato.setColaborador(avaliacaoResultadoContratoPreenchida.getColaborador());
				avaliacaoResultadoContrato.setResultado(avaliacaoResultadoContratoPreenchida.getResultado());
				avaliacaoResultadoContrato.setAvaliacaoElementoQuantidade(avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContrato.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoSoftware());
				avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
			} else {
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoQuantidade(avaliacaoResultadoContratoTemp.getAvaliacaoElementoQuantidade() + 1);
				avaliacaoResultadoContratoTemp.setAvaliacaoElementoResposta(avaliacaoContratoForm.getElementoContratoSoftware()+avaliacaoResultadoContratoTemp.getAvaliacaoElementoResposta());
				avaliacaoResultadoContratoService.save(avaliacaoResultadoContratoTemp);
			}
		}
	}

	public ModelAndView avaliacaoContratoDashboardCenario(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardCenario");
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = this.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);

		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
		
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService.getByAvaliacaoContratoPK(avaliacaoContratoPK);
		
		avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
			
			
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
				avaliacaoContratoDashboard.setElemento1((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
				avaliacaoContratoDashboard.setElemento2((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
				avaliacaoContratoDashboard.setElemento3((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
				avaliacaoContratoDashboard.setElemento4((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
				avaliacaoContratoDashboard.setElemento5((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
				avaliacaoContratoDashboard.setElemento6((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
				avaliacaoContratoDashboard.setElemento7((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
				avaliacaoContratoDashboard.setElemento8((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
				avaliacaoContratoDashboard.setElemento9((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
				avaliacaoContratoDashboard.setElemento10((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
				avaliacaoContratoDashboard.setElemento11((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
				avaliacaoContratoDashboard.setElemento12((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
				avaliacaoContratoDashboard.setElemento13((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
				avaliacaoContratoDashboard.setElemento14((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
				avaliacaoContratoDashboard.setElemento15((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
				avaliacaoContratoDashboard.setElemento16((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
				avaliacaoContratoDashboard.setElemento17((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
				avaliacaoContratoDashboard.setElemento18((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
				avaliacaoContratoDashboard.setElemento19((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
				avaliacaoContratoDashboard.setElemento20((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			}
		});
		
		avaliacaoContratoDashboard.setCenarioNome(avaliacaoContrato.getQuestionario().getCenario().getCenarioNome());
		avaliacaoContratoDashboard.setContratoNome(avaliacaoContrato.getContrato().getContratoNome());
		
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoPage", contratoService.getContratoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalContrato>  unidadeorganizacionalContratoList = unidadeorganizacionalContratoService.getByContratoPK(avaliacaoContratoForm.getContratoPK()); 
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalContratoList);
		List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>(); 
		
		unidadeorganizacionalContratoList.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
		
		avaliacaoContratoDashboard.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoDashboard.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoDashboard.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoDashboard.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoDashboard.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoContratoDashboardCenarioAnual(
			@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("contratoPK") Long contratoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {
	
		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardCenarioPeriodo");
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = this.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
	
		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
	
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService
				.getByAvaliacaoContratoPK(avaliacaoContratoPK);
	
		avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
			
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
				avaliacaoContratoDashboard.setElemento1((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
				avaliacaoContratoDashboard.setElemento2((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
				avaliacaoContratoDashboard.setElemento3((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
				avaliacaoContratoDashboard.setElemento4((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
				avaliacaoContratoDashboard.setElemento5((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
				avaliacaoContratoDashboard.setElemento6((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
				avaliacaoContratoDashboard.setElemento7((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
				avaliacaoContratoDashboard.setElemento8((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
				avaliacaoContratoDashboard.setElemento9((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
				avaliacaoContratoDashboard.setElemento10((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
				avaliacaoContratoDashboard.setElemento11((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
				avaliacaoContratoDashboard.setElemento12((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
				avaliacaoContratoDashboard.setElemento13((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
				avaliacaoContratoDashboard.setElemento14((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
				avaliacaoContratoDashboard.setElemento15((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
				avaliacaoContratoDashboard.setElemento16((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
				avaliacaoContratoDashboard.setElemento17((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
				avaliacaoContratoDashboard.setElemento18((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
				avaliacaoContratoDashboard.setElemento19((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
				avaliacaoContratoDashboard.setElemento20((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			}
		});
	
		avaliacaoContratoDashboard.setCenarioNome(avaliacaoContrato.getQuestionario().getCenario().getCenarioNome());
		avaliacaoContratoDashboard.setContratoNome(avaliacaoContrato.getContrato().getContratoNome());
	
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoPage", contratoService.getContratoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));
	
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratos = unidadeorganizacionalContratoService
				.getByContratoPK(avaliacaoContratoForm.getContratoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalContratos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();

		unidadeorganizacionalContratos.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
	
		Calendar avaliacaoContratoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoContratoPeriodoCalendar.setTime(sdf.parse(avaliacaoContrato.getAvaliacaoContratoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");
	
		Date dateConvertidaAtual = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoContratoPeriodoCalendar.add(Calendar.MONTH, -12);
	
		Date dateConvertidaAnterior = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoContrato avaliacaoContratoAnterior = this.getByPeriodo(avaliacaoContrato.getAvaliacao(), 
																		avaliacaoContrato.getContrato(), 
																		avaliacaoContrato.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoContratoAnterior != null) {
		
			List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList2 = avaliacaoResultadoContratoService
					.getByAvaliacaoContratoPK(avaliacaoContratoAnterior.getAvaliacaoContratoPK());
	
			avaliacaoResultadoContratoList2.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
	
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
					avaliacaoContratoDashboard.setElemento21((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
					avaliacaoContratoDashboard.setElemento22((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
					avaliacaoContratoDashboard.setElemento23((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
					avaliacaoContratoDashboard.setElemento24((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
					avaliacaoContratoDashboard.setElemento25((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
					avaliacaoContratoDashboard.setElemento26((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
					avaliacaoContratoDashboard.setElemento27((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
					avaliacaoContratoDashboard.setElemento28((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
					avaliacaoContratoDashboard.setElemento29((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
					avaliacaoContratoDashboard.setElemento30((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
					avaliacaoContratoDashboard.setElemento31((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
					avaliacaoContratoDashboard.setElemento32((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
					avaliacaoContratoDashboard.setElemento33((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
					avaliacaoContratoDashboard.setElemento34((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
					avaliacaoContratoDashboard.setElemento35((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
					avaliacaoContratoDashboard.setElemento36((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
					avaliacaoContratoDashboard.setElemento37((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
					avaliacaoContratoDashboard.setElemento38((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
					avaliacaoContratoDashboard.setElemento39((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
					avaliacaoContratoDashboard.setElemento40((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoContratoDashboard.setElemento21(0);
			avaliacaoContratoDashboard.setElemento22(0);
			avaliacaoContratoDashboard.setElemento23(0);
			avaliacaoContratoDashboard.setElemento24(0);
			avaliacaoContratoDashboard.setElemento25(0);
			avaliacaoContratoDashboard.setElemento26(0);
			avaliacaoContratoDashboard.setElemento27(0);
			avaliacaoContratoDashboard.setElemento28(0);
			avaliacaoContratoDashboard.setElemento29(0);
			avaliacaoContratoDashboard.setElemento30(0);
			avaliacaoContratoDashboard.setElemento31(0);
			avaliacaoContratoDashboard.setElemento32(0);
			avaliacaoContratoDashboard.setElemento33(0);
			avaliacaoContratoDashboard.setElemento34(0);
			avaliacaoContratoDashboard.setElemento35(0);
			avaliacaoContratoDashboard.setElemento36(0);
			avaliacaoContratoDashboard.setElemento37(0);
			avaliacaoContratoDashboard.setElemento38(0);
			avaliacaoContratoDashboard.setElemento39(0);
			avaliacaoContratoDashboard.setElemento40(0);
		}
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoDashboard.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoDashboard.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoDashboard.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoDashboard.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
		
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
	
		return mv;
	}

	public ModelAndView avaliacaoContratoDashboardCenarioBimestral(
			@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("contratoPK") Long contratoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {
	
		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardCenarioPeriodo");
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = this.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
	
		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
	
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService
				.getByAvaliacaoContratoPK(avaliacaoContratoPK);
	
		avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
	
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
				avaliacaoContratoDashboard.setElemento1((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
				avaliacaoContratoDashboard.setElemento2((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
				avaliacaoContratoDashboard.setElemento3((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
				avaliacaoContratoDashboard.setElemento4((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
				avaliacaoContratoDashboard.setElemento5((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
				avaliacaoContratoDashboard.setElemento6((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
				avaliacaoContratoDashboard.setElemento7((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
				avaliacaoContratoDashboard.setElemento8((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
				avaliacaoContratoDashboard.setElemento9((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
				avaliacaoContratoDashboard.setElemento10((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
				avaliacaoContratoDashboard.setElemento11((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
				avaliacaoContratoDashboard.setElemento12((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
				avaliacaoContratoDashboard.setElemento13((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
				avaliacaoContratoDashboard.setElemento14((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
				avaliacaoContratoDashboard.setElemento15((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
				avaliacaoContratoDashboard.setElemento16((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
				avaliacaoContratoDashboard.setElemento17((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
				avaliacaoContratoDashboard.setElemento18((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
				avaliacaoContratoDashboard.setElemento19((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
				avaliacaoContratoDashboard.setElemento20((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			}
		});
	
		avaliacaoContratoDashboard.setCenarioNome(avaliacaoContrato.getQuestionario().getCenario().getCenarioNome());
		avaliacaoContratoDashboard.setContratoNome(avaliacaoContrato.getContrato().getContratoNome());
	
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoPage", contratoService.getContratoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));
	
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratos = unidadeorganizacionalContratoService
				.getByContratoPK(avaliacaoContratoForm.getContratoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalContratos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();

		unidadeorganizacionalContratos.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
	
		Calendar avaliacaoContratoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoContratoPeriodoCalendar.setTime(sdf.parse(avaliacaoContrato.getAvaliacaoContratoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");
	
		Date dateConvertidaAtual = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoContratoPeriodoCalendar.add(Calendar.MONTH, -2);
	
		Date dateConvertidaAnterior = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoContrato avaliacaoContratoAnterior = this.getByPeriodo(avaliacaoContrato.getAvaliacao(), 
																		avaliacaoContrato.getContrato(), 
																		avaliacaoContrato.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoContratoAnterior != null) {
		
			List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList2 = avaliacaoResultadoContratoService
					.getByAvaliacaoContratoPK(avaliacaoContratoAnterior.getAvaliacaoContratoPK());
	
			avaliacaoResultadoContratoList2.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
	
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
					avaliacaoContratoDashboard.setElemento21((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
					avaliacaoContratoDashboard.setElemento22((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
					avaliacaoContratoDashboard.setElemento23((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
					avaliacaoContratoDashboard.setElemento24((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
					avaliacaoContratoDashboard.setElemento25((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
					avaliacaoContratoDashboard.setElemento26((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
					avaliacaoContratoDashboard.setElemento27((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
					avaliacaoContratoDashboard.setElemento28((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
					avaliacaoContratoDashboard.setElemento29((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
					avaliacaoContratoDashboard.setElemento30((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
					avaliacaoContratoDashboard.setElemento31((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
					avaliacaoContratoDashboard.setElemento32((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
					avaliacaoContratoDashboard.setElemento33((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
					avaliacaoContratoDashboard.setElemento34((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
					avaliacaoContratoDashboard.setElemento35((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
					avaliacaoContratoDashboard.setElemento36((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
					avaliacaoContratoDashboard.setElemento37((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
					avaliacaoContratoDashboard.setElemento38((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
					avaliacaoContratoDashboard.setElemento39((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
					avaliacaoContratoDashboard.setElemento40((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoContratoDashboard.setElemento21(0);
			avaliacaoContratoDashboard.setElemento22(0);
			avaliacaoContratoDashboard.setElemento23(0);
			avaliacaoContratoDashboard.setElemento24(0);
			avaliacaoContratoDashboard.setElemento25(0);
			avaliacaoContratoDashboard.setElemento26(0);
			avaliacaoContratoDashboard.setElemento27(0);
			avaliacaoContratoDashboard.setElemento28(0);
			avaliacaoContratoDashboard.setElemento29(0);
			avaliacaoContratoDashboard.setElemento30(0);
			avaliacaoContratoDashboard.setElemento31(0);
			avaliacaoContratoDashboard.setElemento32(0);
			avaliacaoContratoDashboard.setElemento33(0);
			avaliacaoContratoDashboard.setElemento34(0);
			avaliacaoContratoDashboard.setElemento35(0);
			avaliacaoContratoDashboard.setElemento36(0);
			avaliacaoContratoDashboard.setElemento37(0);
			avaliacaoContratoDashboard.setElemento38(0);
			avaliacaoContratoDashboard.setElemento39(0);
			avaliacaoContratoDashboard.setElemento40(0);
		}
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoDashboard.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoDashboard.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoDashboard.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoDashboard.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
	
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
	
		return mv;
	}

	public ModelAndView avaliacaoContratoDashboardCenarioMensal(
			@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("contratoPK") Long contratoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {
	
		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardCenarioPeriodo");
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = this.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
	
		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
	
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService
				.getByAvaliacaoContratoPK(avaliacaoContratoPK);
	
		avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
			
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
				avaliacaoContratoDashboard.setElemento1((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
				avaliacaoContratoDashboard.setElemento2((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
				avaliacaoContratoDashboard.setElemento3((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
				avaliacaoContratoDashboard.setElemento4((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
				avaliacaoContratoDashboard.setElemento5((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
				avaliacaoContratoDashboard.setElemento6((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
				avaliacaoContratoDashboard.setElemento7((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
				avaliacaoContratoDashboard.setElemento8((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
				avaliacaoContratoDashboard.setElemento9((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
				avaliacaoContratoDashboard.setElemento10((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
				avaliacaoContratoDashboard.setElemento11((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
				avaliacaoContratoDashboard.setElemento12((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
				avaliacaoContratoDashboard.setElemento13((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
				avaliacaoContratoDashboard.setElemento14((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
				avaliacaoContratoDashboard.setElemento15((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
				avaliacaoContratoDashboard.setElemento16((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
				avaliacaoContratoDashboard.setElemento17((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
				avaliacaoContratoDashboard.setElemento18((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
				avaliacaoContratoDashboard.setElemento19((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
				avaliacaoContratoDashboard.setElemento20((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			}
		});
	
		avaliacaoContratoDashboard.setCenarioNome(avaliacaoContrato.getQuestionario().getCenario().getCenarioNome());
		avaliacaoContratoDashboard.setContratoNome(avaliacaoContrato.getContrato().getContratoNome());
	
	
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoPage", contratoService.getContratoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));
	
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratos = unidadeorganizacionalContratoService
				.getByContratoPK(avaliacaoContratoForm.getContratoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalContratos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();

		unidadeorganizacionalContratos.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});

		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
	
		Calendar avaliacaoContratoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoContratoPeriodoCalendar.setTime(sdf.parse(avaliacaoContrato.getAvaliacaoContratoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");
	
		Date dateConvertidaAtual = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoContratoPeriodoCalendar.add(Calendar.MONTH, -1);
	
		Date dateConvertidaAnterior = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoContrato avaliacaoContratoAnterior = this.getByPeriodo(avaliacaoContrato.getAvaliacao(), 
																		avaliacaoContrato.getContrato(), 
																		avaliacaoContrato.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoContratoAnterior != null) {
		
			List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList2 = avaliacaoResultadoContratoService
					.getByAvaliacaoContratoPK(avaliacaoContratoAnterior.getAvaliacaoContratoPK());
	
			avaliacaoResultadoContratoList2.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
				
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
					avaliacaoContratoDashboard.setElemento21((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
					avaliacaoContratoDashboard.setElemento22((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
					avaliacaoContratoDashboard.setElemento23((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
					avaliacaoContratoDashboard.setElemento24((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
					avaliacaoContratoDashboard.setElemento25((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
					avaliacaoContratoDashboard.setElemento26((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
					avaliacaoContratoDashboard.setElemento27((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
					avaliacaoContratoDashboard.setElemento28((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
					avaliacaoContratoDashboard.setElemento29((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
					avaliacaoContratoDashboard.setElemento30((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
					avaliacaoContratoDashboard.setElemento31((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
					avaliacaoContratoDashboard.setElemento32((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
					avaliacaoContratoDashboard.setElemento33((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
					avaliacaoContratoDashboard.setElemento34((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
					avaliacaoContratoDashboard.setElemento35((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
					avaliacaoContratoDashboard.setElemento36((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
					avaliacaoContratoDashboard.setElemento37((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
					avaliacaoContratoDashboard.setElemento38((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
					avaliacaoContratoDashboard.setElemento39((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
					avaliacaoContratoDashboard.setElemento40((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoContratoDashboard.setElemento21(0);
			avaliacaoContratoDashboard.setElemento22(0);
			avaliacaoContratoDashboard.setElemento23(0);
			avaliacaoContratoDashboard.setElemento24(0);
			avaliacaoContratoDashboard.setElemento25(0);
			avaliacaoContratoDashboard.setElemento26(0);
			avaliacaoContratoDashboard.setElemento27(0);
			avaliacaoContratoDashboard.setElemento28(0);
			avaliacaoContratoDashboard.setElemento29(0);
			avaliacaoContratoDashboard.setElemento30(0);
			avaliacaoContratoDashboard.setElemento31(0);
			avaliacaoContratoDashboard.setElemento32(0);
			avaliacaoContratoDashboard.setElemento33(0);
			avaliacaoContratoDashboard.setElemento34(0);
			avaliacaoContratoDashboard.setElemento35(0);
			avaliacaoContratoDashboard.setElemento36(0);
			avaliacaoContratoDashboard.setElemento37(0);
			avaliacaoContratoDashboard.setElemento38(0);
			avaliacaoContratoDashboard.setElemento39(0);
			avaliacaoContratoDashboard.setElemento40(0);
		}
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoDashboard.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoDashboard.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoDashboard.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoDashboard.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
	
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
	
		return mv;
	}

	public ModelAndView avaliacaoContratoDashboardCenarioSemestral(
			@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("contratoPK") Long contratoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {
	
		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardCenarioPeriodo");
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = this.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
	
		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
	
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService
				.getByAvaliacaoContratoPK(avaliacaoContratoPK);
	
		avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
			
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
				avaliacaoContratoDashboard.setElemento1((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
				avaliacaoContratoDashboard.setElemento2((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
				avaliacaoContratoDashboard.setElemento3((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
				avaliacaoContratoDashboard.setElemento4((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
				avaliacaoContratoDashboard.setElemento5((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
				avaliacaoContratoDashboard.setElemento6((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
				avaliacaoContratoDashboard.setElemento7((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
				avaliacaoContratoDashboard.setElemento8((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
				avaliacaoContratoDashboard.setElemento9((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
				avaliacaoContratoDashboard.setElemento10((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
				avaliacaoContratoDashboard.setElemento11((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
				avaliacaoContratoDashboard.setElemento12((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
				avaliacaoContratoDashboard.setElemento13((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
				avaliacaoContratoDashboard.setElemento14((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
				avaliacaoContratoDashboard.setElemento15((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
				avaliacaoContratoDashboard.setElemento16((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
				avaliacaoContratoDashboard.setElemento17((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
				avaliacaoContratoDashboard.setElemento18((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
				avaliacaoContratoDashboard.setElemento19((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
				avaliacaoContratoDashboard.setElemento20((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			}
		});
	
		avaliacaoContratoDashboard.setCenarioNome(avaliacaoContrato.getQuestionario().getCenario().getCenarioNome());
		avaliacaoContratoDashboard.setContratoNome(avaliacaoContrato.getContrato().getContratoNome());
	
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoPage", contratoService.getContratoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));
	
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratos = unidadeorganizacionalContratoService
				.getByContratoPK(avaliacaoContratoForm.getContratoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalContratos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();
		
		unidadeorganizacionalContratos.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
	
		Calendar avaliacaoContratoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoContratoPeriodoCalendar.setTime(sdf.parse(avaliacaoContrato.getAvaliacaoContratoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");
	
		Date dateConvertidaAtual = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoContratoPeriodoCalendar.add(Calendar.MONTH, -6);
	
		Date dateConvertidaAnterior = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoContrato avaliacaoContratoAnterior = this.getByPeriodo(avaliacaoContrato.getAvaliacao(), 
																		avaliacaoContrato.getContrato(), 
																		avaliacaoContrato.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoContratoAnterior != null) {
		
			List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList2 = avaliacaoResultadoContratoService
					.getByAvaliacaoContratoPK(avaliacaoContratoAnterior.getAvaliacaoContratoPK());
	
			avaliacaoResultadoContratoList2.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {

				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
					avaliacaoContratoDashboard.setElemento21((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
					avaliacaoContratoDashboard.setElemento22((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
					avaliacaoContratoDashboard.setElemento23((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
					avaliacaoContratoDashboard.setElemento24((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
					avaliacaoContratoDashboard.setElemento25((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
					avaliacaoContratoDashboard.setElemento26((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
					avaliacaoContratoDashboard.setElemento27((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
					avaliacaoContratoDashboard.setElemento28((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
					avaliacaoContratoDashboard.setElemento29((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
					avaliacaoContratoDashboard.setElemento30((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
					avaliacaoContratoDashboard.setElemento31((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
					avaliacaoContratoDashboard.setElemento32((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
					avaliacaoContratoDashboard.setElemento33((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
					avaliacaoContratoDashboard.setElemento34((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
					avaliacaoContratoDashboard.setElemento35((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
					avaliacaoContratoDashboard.setElemento36((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
					avaliacaoContratoDashboard.setElemento37((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
					avaliacaoContratoDashboard.setElemento38((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
					avaliacaoContratoDashboard.setElemento39((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
					avaliacaoContratoDashboard.setElemento40((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoContratoDashboard.setElemento21(0);
			avaliacaoContratoDashboard.setElemento22(0);
			avaliacaoContratoDashboard.setElemento23(0);
			avaliacaoContratoDashboard.setElemento24(0);
			avaliacaoContratoDashboard.setElemento25(0);
			avaliacaoContratoDashboard.setElemento26(0);
			avaliacaoContratoDashboard.setElemento27(0);
			avaliacaoContratoDashboard.setElemento28(0);
			avaliacaoContratoDashboard.setElemento29(0);
			avaliacaoContratoDashboard.setElemento30(0);
			avaliacaoContratoDashboard.setElemento31(0);
			avaliacaoContratoDashboard.setElemento32(0);
			avaliacaoContratoDashboard.setElemento33(0);
			avaliacaoContratoDashboard.setElemento34(0);
			avaliacaoContratoDashboard.setElemento35(0);
			avaliacaoContratoDashboard.setElemento36(0);
			avaliacaoContratoDashboard.setElemento37(0);
			avaliacaoContratoDashboard.setElemento38(0);
			avaliacaoContratoDashboard.setElemento39(0);
			avaliacaoContratoDashboard.setElemento40(0);
		}
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoDashboard.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoDashboard.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoDashboard.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoDashboard.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
	
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
		
		return mv;
	}

	public ModelAndView avaliacaoContratoDashboardCenarioTrimestral(
			@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
			@PathVariable("questionarioPK") Long questionarioPK, @PathVariable("contratoPK") Long contratoPK,
			@PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			@PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK) {
	
		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardCenarioPeriodo");
		AvaliacaoContrato avaliacaoContrato = this.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = this.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
	
		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
	
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService
				.getByAvaliacaoContratoPK(avaliacaoContratoPK);
	
		avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
	
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
				avaliacaoContratoDashboard.setElemento1((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
				avaliacaoContratoDashboard.setElemento2((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
				avaliacaoContratoDashboard.setElemento3((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
				avaliacaoContratoDashboard.setElemento4((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
				avaliacaoContratoDashboard.setElemento5((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
				avaliacaoContratoDashboard.setElemento6((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
				avaliacaoContratoDashboard.setElemento7((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
				avaliacaoContratoDashboard.setElemento8((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
				avaliacaoContratoDashboard.setElemento9((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
				avaliacaoContratoDashboard.setElemento10((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
				avaliacaoContratoDashboard.setElemento11((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
				avaliacaoContratoDashboard.setElemento12((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
				avaliacaoContratoDashboard.setElemento13((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
				avaliacaoContratoDashboard.setElemento14((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
				avaliacaoContratoDashboard.setElemento15((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
				avaliacaoContratoDashboard.setElemento16((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
				avaliacaoContratoDashboard.setElemento17((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
				avaliacaoContratoDashboard.setElemento18((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
				avaliacaoContratoDashboard.setElemento19((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			} else
			if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
				avaliacaoContratoDashboard.setElemento20((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
			}
		});
	
		avaliacaoContratoDashboard.setCenarioNome(avaliacaoContrato.getQuestionario().getCenario().getCenarioNome());
		avaliacaoContratoDashboard.setContratoNome(avaliacaoContrato.getContrato().getContratoNome());
	
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoPage", contratoService.getContratoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));
	
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratos = unidadeorganizacionalContratoService
				.getByContratoPK(avaliacaoContratoForm.getContratoPK());
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalContratos);
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>();

		unidadeorganizacionalContratos.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});

		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
	
		Calendar avaliacaoContratoPeriodoCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			avaliacaoContratoPeriodoCalendar.setTime(sdf.parse(avaliacaoContrato.getAvaliacaoContratoPeriodo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format2 = new SimpleDateFormat("MM/yy");
	
		Date dateConvertidaAtual = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaMesAtual = format2.format(dateConvertidaAtual); 
		
		avaliacaoContratoPeriodoCalendar.add(Calendar.MONTH, -3);
	
		Date dateConvertidaAnterior = avaliacaoContratoPeriodoCalendar.getTime(); 
		String dataFormatadaAnterior = format1.format(dateConvertidaAnterior); 
		String dataFormatadaMesAnterior = format2.format(dateConvertidaAnterior); 
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAnterior(dataFormatadaMesAnterior);
		avaliacaoContratoDashboard.setAvaliacaoContratoPeriodoAtual(dataFormatadaMesAtual);
		
		
		AvaliacaoContrato avaliacaoContratoAnterior = this.getByPeriodo(avaliacaoContrato.getAvaliacao(), 
																		avaliacaoContrato.getContrato(), 
																		avaliacaoContrato.getQuestionario(),
																		dataFormatadaAnterior);
		
		if (avaliacaoContratoAnterior != null) {
		
			List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList2 = avaliacaoResultadoContratoService
					.getByAvaliacaoContratoPK(avaliacaoContratoAnterior.getAvaliacaoContratoPK());
	
			avaliacaoResultadoContratoList2.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
				
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 61) { 
					avaliacaoContratoDashboard.setElemento21((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 62) {
					avaliacaoContratoDashboard.setElemento22((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 63) {
					avaliacaoContratoDashboard.setElemento23((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 64) {
					avaliacaoContratoDashboard.setElemento24((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 65) {
					avaliacaoContratoDashboard.setElemento25((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 66) {
					avaliacaoContratoDashboard.setElemento26((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 67) {
					avaliacaoContratoDashboard.setElemento27((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 68) {
					avaliacaoContratoDashboard.setElemento28((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 69) {
					avaliacaoContratoDashboard.setElemento29((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 70) {
					avaliacaoContratoDashboard.setElemento30((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 71) {
					avaliacaoContratoDashboard.setElemento31((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 72) {
					avaliacaoContratoDashboard.setElemento32((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 73) {
					avaliacaoContratoDashboard.setElemento33((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 74) {
					avaliacaoContratoDashboard.setElemento34((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 75) {
					avaliacaoContratoDashboard.setElemento35((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 76) {
					avaliacaoContratoDashboard.setElemento36((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 77) {
					avaliacaoContratoDashboard.setElemento37((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 78) {
					avaliacaoContratoDashboard.setElemento38((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 79) {
					avaliacaoContratoDashboard.setElemento39((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				} else
				if (avaliacaoResultadoContrato.getElemento().getElementoPK() == 80) {
					avaliacaoContratoDashboard.setElemento40((int)Math.round(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
				}
			});
		} else {
			
			avaliacaoContratoDashboard.setElemento21(0);
			avaliacaoContratoDashboard.setElemento22(0);
			avaliacaoContratoDashboard.setElemento23(0);
			avaliacaoContratoDashboard.setElemento24(0);
			avaliacaoContratoDashboard.setElemento25(0);
			avaliacaoContratoDashboard.setElemento26(0);
			avaliacaoContratoDashboard.setElemento27(0);
			avaliacaoContratoDashboard.setElemento28(0);
			avaliacaoContratoDashboard.setElemento29(0);
			avaliacaoContratoDashboard.setElemento30(0);
			avaliacaoContratoDashboard.setElemento31(0);
			avaliacaoContratoDashboard.setElemento32(0);
			avaliacaoContratoDashboard.setElemento33(0);
			avaliacaoContratoDashboard.setElemento34(0);
			avaliacaoContratoDashboard.setElemento35(0);
			avaliacaoContratoDashboard.setElemento36(0);
			avaliacaoContratoDashboard.setElemento37(0);
			avaliacaoContratoDashboard.setElemento38(0);
			avaliacaoContratoDashboard.setElemento39(0);
			avaliacaoContratoDashboard.setElemento40(0);
		}
	
		avaliacaoContratoDashboard.setAvaliacaoContratoPK(avaliacaoContrato.getAvaliacaoContratoPK());
		avaliacaoContratoDashboard.setQuestionarioPK(avaliacaoContrato.getQuestionario().getQuestionarioPK());
		avaliacaoContratoDashboard.setContratoPK(avaliacaoContrato.getContrato().getContratoPK());
		avaliacaoContratoDashboard.setUnidadeorganizacionalPK(avaliacaoContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		avaliacaoContratoDashboard.setEstruturafisicaPK(avaliacaoContrato.getEstruturafisica().getEstruturafisicaPK());
	
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
	
		return mv;
	}

	@Override
	public AvaliacaoContrato getByPeriodo(Avaliacao avaliacao, Contrato contrato,Questionario questionario, String periodo) {
	
		return avaliacaoContratoRepository.findByPeriodo(avaliacao, contrato, questionario, periodo);
	}

	@Override
	public List<AvaliacaoContrato> getByPeriodoNome(String periodoNome) {
		return avaliacaoContratoRepository.findByPeriodoNome(periodoNome);
	}

	@Override
	public List<AvaliacaoContrato> getByPeriodoNome(String periodoNome, Pageable pageable) {
		return avaliacaoContratoRepository.findByPeriodoNome(periodoNome, pageable);
	}

	@Override
	public List<AvaliacaoContrato> getByQuestionarioNome(String questionarioNome) {
		return avaliacaoContratoRepository.findByQuestionarioNome(questionarioNome);
	}

	@Override
	public List<AvaliacaoContrato> getByQuestionarioNome(String questionarioNome, Pageable pageable) {
		return avaliacaoContratoRepository.findByQuestionarioNome(questionarioNome, pageable);
	}
	

}
