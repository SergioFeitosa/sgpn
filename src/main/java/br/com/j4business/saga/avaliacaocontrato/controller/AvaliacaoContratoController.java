package br.com.j4business.saga.avaliacaocontrato.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.avaliacao.model.AvaliacaoForm;
import br.com.j4business.saga.avaliacao.service.AvaliacaoService;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContrato;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContratoByAvaliacaoForm;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContratoDashboard;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContratoForm;
import br.com.j4business.saga.avaliacaocontrato.service.AvaliacaoContratoService;
import br.com.j4business.saga.avaliacaoresultadocontrato.model.AvaliacaoResultadoContrato;
import br.com.j4business.saga.avaliacaoresultadocontrato.service.AvaliacaoResultadoContratoService;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.service.EstruturafisicaUnidadeorganizacionalService;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.model.QuestionarioForm;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContrato;
import br.com.j4business.saga.unidadeorganizacionalcontrato.service.UnidadeorganizacionalContratoService;

@Controller

public class AvaliacaoContratoController {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoContratoService avaliacaoContratoService;

	@Autowired
	private AvaliacaoResultadoContratoService avaliacaoResultadoContratoService;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private QuestionarioQuestaoService questionarioQuestaoService;

	@Autowired
	private EstruturafisicaUnidadeorganizacionalService estruturafisicaUnidadeorganizacionalService;

	@Autowired
	private UnidadeorganizacionalContratoService unidadeorganizacionalContratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path = "/avaliacaoContratoAdd", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoAdd(AvaliacaoContratoForm avaliacaoContratoForm) {

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoAdd");
		avaliacaoContratoForm = avaliacaoContratoService.avaliacaoContratoParametros(avaliacaoContratoForm);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = new PageRequest(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable questionarioPageable = new PageRequest(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable avaliacaoPageable = new PageRequest(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalContratoPage", unidadeorganizacionalContratoService.getUnidadeorganizacionalContratoAll(unidadeorganizacionalPageable));
		Pageable estruturafisicaUnidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "estruturafisica.estruturafisicaNome");
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaUnidadeorganizacionalAll(estruturafisicaUnidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoCreate", method = RequestMethod.POST)
	public ModelAndView avaliacaoContratoCreate(@Valid AvaliacaoContratoForm avaliacaoContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		AvaliacaoContrato avaliacaoContrato = null;
		
		if (result.hasErrors()) {
			return avaliacaoContratoAdd(avaliacaoContratoForm);
		}
		
		if (avaliacaoContratoForm.getAvaliacaoContratoPK() > 0) {
			avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoForm.getAvaliacaoContratoPK());
			avaliacaoContratoService.save(avaliacaoContrato);
		} else {
			avaliacaoContrato = avaliacaoContratoService.create(avaliacaoContratoForm);
		}
		
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoContratoHome");
		attributes.addFlashAttribute("mensagem", "Contrato/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/avaliacaoContratoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDelete(@PathVariable("id") long avaliacaoContratoId, @Valid ContratoForm contratoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoContratoHome");
		
		
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoId);
		try {
			avaliacaoContratoService.delete(avaliacaoContratoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Avaliação/Contrato excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Avaliação/Contrato não excluído. Existe(m) relacionamento(s) de Avaliação/Contrato ** "+ 
										  avaliacaoContrato.getAvaliacao().getAvaliacaoNome() +
										  " / " +
										  avaliacaoContrato.getContrato().getContratoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoEdit/{avaliacaoContratoPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoEdit(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoEdit");
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = avaliacaoContratoService.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
		mv.addObject("avaliacaoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoContratoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable contratoPageable = new PageRequest(0, 200, Direction.ASC, "contratoNome");
		mv.addObject("contratoPage", contratoService.getContratoAll(contratoPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable questionarioPageable = new PageRequest(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable avaliacaoPageable = new PageRequest(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));

		Pageable unidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalContratoPage", unidadeorganizacionalContratoService.getUnidadeorganizacionalContratoAll(unidadeorganizacionalPageable));
		Pageable estruturafisicaUnidadeorganizacionalPageable = new PageRequest(0, 200, Direction.ASC, "estruturafisica.estruturafisicaNome");
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaUnidadeorganizacionalAll(estruturafisicaUnidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoContratoDashboard/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboard(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboard");
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = avaliacaoContratoService.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);

		AvaliacaoContratoDashboard avaliacaoContratoDashboard = new AvaliacaoContratoDashboard();
		avaliacaoContratoDashboard.setQuantidadeContratos(contratoService.getContratoAll().size());
		avaliacaoContratoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoContratoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
		
		Contrato contrato = contratoService.getContratoByContratoPK(contratoPK);
		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(questionarioPK);
		
		List<AvaliacaoContrato> avaliacaoContratoTemp = avaliacaoContratoService.getByContratoAndQuestionario(contrato, questionario);
		
		avaliacaoContratoTemp.forEach((AvaliacaoContrato avaliacaoContrato2) -> { 
		
			List<AvaliacaoResultadoContrato> avaliacaoResultadoContratoList = avaliacaoResultadoContratoService.getByAvaliacaoContratoPK(avaliacaoContrato2.getAvaliacaoContratoPK());
			
			
			Calendar avaliacaoContratoPeriodoCalendar = Calendar.getInstance();
			Calendar dataAtual = Calendar.getInstance();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				avaliacaoContratoPeriodoCalendar.setTime(sdf.parse(avaliacaoContrato2.getAvaliacaoContratoPeriodo()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			
			avaliacaoResultadoContratoList.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> { 

				
				if (avaliacaoContratoPeriodoCalendar.get(Calendar.YEAR) == dataAtual.get(Calendar.YEAR)) {
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 0 ) {
						avaliacaoContratoDashboard.setElementoMediaJanAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 1 ) {
						avaliacaoContratoDashboard.setElementoMediaFevAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 2 ) {
						avaliacaoContratoDashboard.setElementoMediaMarAtual(((int)avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/(int)avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 3 ) {
						avaliacaoContratoDashboard.setElementoMediaAbrAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 4 ) {
						avaliacaoContratoDashboard.setElementoMediaMaiAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 5 ) {
						avaliacaoContratoDashboard.setElementoMediaJunAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 6 ) {
						avaliacaoContratoDashboard.setElementoMediaJulAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 7 ) {
						avaliacaoContratoDashboard.setElementoMediaAgoAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 8 ) {
						avaliacaoContratoDashboard.setElementoMediaSetAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 9 ) {
						avaliacaoContratoDashboard.setElementoMediaOutAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 10 ) {
						avaliacaoContratoDashboard.setElementoMediaNovAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoContratoPeriodoCalendar.get(Calendar.MONTH) == 11 ) {
						avaliacaoContratoDashboard.setElementoMediaDezAtual(Integer.parseInt(avaliacaoResultadoContrato.getAvaliacaoElementoResposta()/avaliacaoResultadoContrato.getAvaliacaoElementoQuantidade()+""));
					}
				}
			});
			
			avaliacaoContratoDashboard.setElementoMediaJanAnterior(1);
			avaliacaoContratoDashboard.setElementoMediaFevAnterior(2);
			avaliacaoContratoDashboard.setElementoMediaMarAnterior(3);
			avaliacaoContratoDashboard.setElementoMediaAbrAnterior(4);
			avaliacaoContratoDashboard.setElementoMediaMaiAnterior(5);
			avaliacaoContratoDashboard.setElementoMediaJunAnterior(1);
			avaliacaoContratoDashboard.setElementoMediaJulAnterior(2);
			avaliacaoContratoDashboard.setElementoMediaAgoAnterior(3);
			avaliacaoContratoDashboard.setElementoMediaSetAnterior(4);
			avaliacaoContratoDashboard.setElementoMediaOutAnterior(5);
			avaliacaoContratoDashboard.setElementoMediaNovAnterior(1);
			avaliacaoContratoDashboard.setElementoMediaDezAnterior(2);
			
		});

		
		mv.addObject("avaliacaoContratoDashboard", avaliacaoContratoDashboard);
		
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
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoContratoDashboardCenario/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardCenario(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);

		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 11) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenario(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 12) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenario(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoContratoDashboardMenu/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardMenu(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoDashboardMenu");
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = avaliacaoContratoService.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoContratoQuestionario/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoQuestionario(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoQuestionario");
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		AvaliacaoContratoForm avaliacaoContratoForm = avaliacaoContratoService.converteAvaliacaoContrato(avaliacaoContrato);
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
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
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoContratoHome", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoHome(@Valid AvaliacaoContratoByAvaliacaoForm avaliacaoContratoByAvaliacaoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoHome");
		
		List<AvaliacaoContrato> avaliacaoContratos = new ArrayList<AvaliacaoContrato>();
		
		int avaliacaoContratosTotal = 0;
		
		if (avaliacaoContratoByAvaliacaoForm.getSearchContratoNome() == null) {
			avaliacaoContratoByAvaliacaoForm.setSearchContratoNome("");
		}
		if (avaliacaoContratoByAvaliacaoForm.getSearchAvaliacaoNome() == null) {
			avaliacaoContratoByAvaliacaoForm.setSearchAvaliacaoNome("");
		}
		if (avaliacaoContratoByAvaliacaoForm.getSearchQuestionarioNome() == null) {
			avaliacaoContratoByAvaliacaoForm.setSearchQuestionarioNome("");
		}
		if (avaliacaoContratoByAvaliacaoForm.getSearchPeriodoNome() == null) {
			avaliacaoContratoByAvaliacaoForm.setSearchPeriodoNome("");
		}
		if (avaliacaoContratoByAvaliacaoForm.getAvaliacaoContratoSortTipo() == null) {
			avaliacaoContratoByAvaliacaoForm.setAvaliacaoContratoSortTipo("AvaliacaoNome");	
		}

		if (avaliacaoContratoByAvaliacaoForm.getAvaliacaoContratoSortTipo().equalsIgnoreCase("AvaliacaoNome")
				|| avaliacaoContratoByAvaliacaoForm.getAvaliacaoContratoSortTipo().equalsIgnoreCase("")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"avaliacao.avaliacaoNome","contrato.contratoNome","questionario.questionarioNome","avaliacaoContratoPeriodo"); 
		
		} else if (avaliacaoContratoByAvaliacaoForm.getAvaliacaoContratoSortTipo().equalsIgnoreCase("ContratoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"contrato.contratoNome","avaliacao.avaliacaoNome","questionario.questionarioNome","avaliacaoContratoPeriodo"); 

		} else if (avaliacaoContratoByAvaliacaoForm.getAvaliacaoContratoSortTipo().equalsIgnoreCase("QuestionarioNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"questionario.questionarioNome","contrato.contratoNome","avaliacao.avaliacaoNome","avaliacaoContratoPeriodo"); 

		} else if (avaliacaoContratoByAvaliacaoForm.getAvaliacaoContratoSortTipo().equalsIgnoreCase("PeriodoNome")) {
			pageable = new PageRequest(pageable.getPageNumber(), 15, Direction.ASC,"avaliacaoContratoPeriodo","questionario.questionarioNome","contrato.contratoNome","avaliacao.avaliacaoNome"); 

		}

		if ( ! avaliacaoContratoByAvaliacaoForm.getSearchContratoNome().equalsIgnoreCase("")){
			avaliacaoContratos = avaliacaoContratoService.getByContratoNome(avaliacaoContratoByAvaliacaoForm.getSearchContratoNome(),pageable);
			avaliacaoContratosTotal = avaliacaoContratoService.getByContratoNome(avaliacaoContratoByAvaliacaoForm.getSearchContratoNome()).size();
			
		} else if ( ! avaliacaoContratoByAvaliacaoForm.getSearchPeriodoNome().equalsIgnoreCase("")) {
			avaliacaoContratos = avaliacaoContratoService.getByPeriodoNome(avaliacaoContratoByAvaliacaoForm.getSearchPeriodoNome(),pageable);
			avaliacaoContratosTotal = avaliacaoContratoService.getByPeriodoNome(avaliacaoContratoByAvaliacaoForm.getSearchPeriodoNome()).size();

		} else if ( ! avaliacaoContratoByAvaliacaoForm.getSearchQuestionarioNome().equalsIgnoreCase("")) {
			avaliacaoContratos = avaliacaoContratoService.getByQuestionarioNome(avaliacaoContratoByAvaliacaoForm.getSearchQuestionarioNome(),pageable);
			avaliacaoContratosTotal = avaliacaoContratoService.getByQuestionarioNome(avaliacaoContratoByAvaliacaoForm.getSearchQuestionarioNome()).size();

		} else {
			avaliacaoContratos = avaliacaoContratoService.getByAvaliacaoNome(avaliacaoContratoByAvaliacaoForm.getSearchAvaliacaoNome(),pageable);
			avaliacaoContratosTotal = avaliacaoContratoService.getByAvaliacaoNome(avaliacaoContratoByAvaliacaoForm.getSearchAvaliacaoNome()).size();


		} 
		
		Page<AvaliacaoContrato> pageAvaliacaoContratos = new PageImpl<AvaliacaoContrato>(avaliacaoContratos,pageable,avaliacaoContratosTotal+1);
		
		mv.addObject("avaliacaoContratoPage", pageAvaliacaoContratos);
		mv.addObject("page",pageAvaliacaoContratos);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoSave", method = RequestMethod.POST)
	public ModelAndView avaliacaoContratoSave(@Valid AvaliacaoContratoForm avaliacaoContratoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return avaliacaoContratoAdd(avaliacaoContratoForm);
		}

		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.converteAvaliacaoContratoForm(avaliacaoContratoForm);
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoContratoHome");
		avaliacaoContratoService.save(avaliacaoContrato);
		attributes.addFlashAttribute("mensagem", "Contrato/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoQuestionario", method = RequestMethod.POST)
	public ModelAndView avaliacaoContratoQuestionario(AvaliacaoContratoForm avaliacaoContratoForm, RedirectAttributes attributes,Pageable pageable) {


		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(avaliacaoContratoForm.getQuestionarioPK());
		
		if (questionario.getCenario().getCenarioPK() == 11) {
			avaliacaoContratoService.saveAvaliacaoContratoQuestionario(avaliacaoContratoForm);
		} else
		if (questionario.getCenario().getCenarioPK() == 12) {
			avaliacaoContratoService.saveAvaliacaoContratoQuestionario(avaliacaoContratoForm);
		}
		
		
		
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoContratoQuestionario/" + 
														avaliacaoContratoForm.getAvaliacaoContratoPK()+"/" + 
														avaliacaoContratoForm.getQuestionarioPK() + " /"+ 
														avaliacaoContratoForm.getContratoPK() +"/" + 
														avaliacaoContratoForm.getEstruturafisicaPK()+ "/ "+ 
														avaliacaoContratoForm.getUnidadeorganizacionalPK());
		attributes.addFlashAttribute("mensagem", "Contrato/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoRelMenu", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoRelMenu() {

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/avaliacaoContratoRel001")
	public ModelAndView avaliacaoContratoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoRel001");
		Pageable avaliacaoContratoPageable = new PageRequest(0, 200, Direction.ASC, "avaliacao.avaliacaoNome","contrato.contratoNome");
		mv.addObject("avaliacaoContratoPage", avaliacaoContratoService.getAvaliacaoContratoAll(avaliacaoContratoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoView/{id}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoView(@PathVariable("id") Long avaliacaoContratoId) {

		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoId);
		ModelAndView mv = new ModelAndView("avaliacaoContrato/avaliacaoContratoView"); 
		AvaliacaoContratoForm avaliacaoContratoForm = avaliacaoContratoService.converteAvaliacaoContratoView(avaliacaoContrato);
		AvaliacaoForm avaliacaoForm = avaliacaoService.converteAvaliacaoView(avaliacaoContrato.getAvaliacao());
		ContratoForm contratoForm = contratoService.converteContratoView(avaliacaoContrato.getContrato());
		QuestionarioForm questionarioForm = questionarioService.converteQuestionarioView(avaliacaoContrato.getQuestionario());
		mv.addObject("avaliacaoContratoForm", avaliacaoContratoForm);
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("contratoForm", contratoForm);
		mv.addObject("questionarioForm", questionarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoDashboardCenarioAnual/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardCenarioAnual(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 11) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioAnual(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 12) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioAnual(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoDashboardCenarioBimestral/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardCenarioBimestral(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 11) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioBimestral(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 12) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioBimestral(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoDashboardCenarioMensal/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardCenarioMensal(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 11) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioMensal(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 12) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioMensal(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoDashboardCenarioSemestral/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardCenarioSemestral(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 11) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioSemestral(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 12) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioSemestral(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@RequestMapping(path = "/avaliacaoContratoDashboardCenarioTrimestral/{avaliacaoContratoPK}/{questionarioPK}/{contratoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoContratoDashboardCenarioTrimestral(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("contratoPK") Long contratoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoContrato avaliacaoContrato = avaliacaoContratoService.getAvaliacaoContratoByAvaliacaoContratoPK(avaliacaoContratoPK);
		
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 11) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioTrimestral(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoContrato.getQuestionario().getCenario().getCenarioPK() == 12) {
			mv = avaliacaoContratoService.avaliacaoContratoDashboardCenarioTrimestral(avaliacaoContratoPK, questionarioPK, contratoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}


}