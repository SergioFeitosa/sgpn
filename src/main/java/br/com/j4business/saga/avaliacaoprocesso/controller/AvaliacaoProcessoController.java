package br.com.j4business.saga.avaliacaoprocesso.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.avaliacao.model.AvaliacaoForm;
import br.com.j4business.saga.avaliacao.service.AvaliacaoService;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcesso;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcessoByAvaliacaoForm;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcessoDashboard;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcessoForm;
import br.com.j4business.saga.avaliacaoprocesso.service.AvaliacaoProcessoService;
import br.com.j4business.saga.avaliacaoresultado.model.AvaliacaoResultado;
import br.com.j4business.saga.avaliacaoresultado.service.AvaliacaoResultadoService;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.service.EstruturafisicaUnidadeorganizacionalService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.model.QuestionarioForm;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcesso;
import br.com.j4business.saga.unidadeorganizacionalprocesso.service.UnidadeorganizacionalProcessoService;

@Controller

public class AvaliacaoProcessoController {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoProcessoService avaliacaoProcessoService;

	@Autowired
	private AvaliacaoResultadoService avaliacaoResultadoService;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private QuestionarioQuestaoService questionarioQuestaoService;

	@Autowired
	private EstruturafisicaUnidadeorganizacionalService estruturafisicaUnidadeorganizacionalService;

	@Autowired
	private UnidadeorganizacionalProcessoService unidadeorganizacionalProcessoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/avaliacaoProcessoAdd")
	public ModelAndView avaliacaoProcessoAdd(AvaliacaoProcessoForm avaliacaoProcessoForm) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoAdd");
		avaliacaoProcessoForm = avaliacaoProcessoService.avaliacaoProcessoParametros(avaliacaoProcessoForm);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);
		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable avaliacaoPageable = PageRequest.of(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable unidadeorganizacionalPageable = PageRequest.of(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalProcessoPage", unidadeorganizacionalProcessoService.getUnidadeorganizacionalProcessoAll(unidadeorganizacionalPageable));
		Pageable estruturafisicaUnidadeorganizacionalPageable = PageRequest.of(0, 200, Direction.ASC, "estruturafisica.estruturafisicaNome");
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaUnidadeorganizacionalAll(estruturafisicaUnidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/avaliacaoProcessoCreate")
	public ModelAndView avaliacaoProcessoCreate(@Valid AvaliacaoProcessoForm avaliacaoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		AvaliacaoProcesso avaliacaoProcesso = null;
		
		if (result.hasErrors()) {
			return avaliacaoProcessoAdd(avaliacaoProcessoForm);
		}
		
		if (avaliacaoProcessoForm.getAvaliacaoProcessoPK() > 0) {
			avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoForm.getAvaliacaoProcessoPK());
			avaliacaoProcessoService.save(avaliacaoProcesso);
		} else {
			avaliacaoProcesso = avaliacaoProcessoService.create(avaliacaoProcessoForm);
		}
		
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoProcessoHome");
		attributes.addFlashAttribute("mensagem", "Processo/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/avaliacaoProcessoDelete/{id}")
	public ModelAndView avaliacaoProcessoDelete(@PathVariable("id") long avaliacaoProcessoId, @Valid ProcessoForm processoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoProcessoHome");
		
		
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoId);
		try {
			avaliacaoProcessoService.delete(avaliacaoProcessoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Avaliação/Processo excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Avaliação/Processo não excluído. Existe(m) relacionamento(s) de Avaliação/Processo ** "+ 
										  avaliacaoProcesso.getAvaliacao().getAvaliacaoNome() +
										  " / " +
										  avaliacaoProcesso.getProcesso().getProcessoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoEdit/{avaliacaoProcessoPK}")
	public ModelAndView avaliacaoProcessoEdit(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoEdit");
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = avaliacaoProcessoService.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);
		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable processoPageable = PageRequest.of(0, 200, Direction.ASC, "processoNome");
		mv.addObject("processoPage", processoService.getProcessoAll(processoPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable avaliacaoPageable = PageRequest.of(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));

		Pageable unidadeorganizacionalPageable = PageRequest.of(0, 200, Direction.ASC, "unidadeorganizacional.unidadeorganizacionalNome");
		mv.addObject("unidadeorganizacionalProcessoPage", unidadeorganizacionalProcessoService.getUnidadeorganizacionalProcessoAll(unidadeorganizacionalPageable));
		Pageable estruturafisicaUnidadeorganizacionalPageable = PageRequest.of(0, 200, Direction.ASC, "estruturafisica.estruturafisicaNome");
		mv.addObject("estruturafisicaUnidadeorganizacionalPage", estruturafisicaUnidadeorganizacionalService.getByEstruturafisicaUnidadeorganizacionalAll(estruturafisicaUnidadeorganizacionalPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboard/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboard(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboard");
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = avaliacaoProcessoService.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);

		AvaliacaoProcessoDashboard avaliacaoProcessoDashboard = new AvaliacaoProcessoDashboard();
		avaliacaoProcessoDashboard.setQuantidadeProcessos(processoService.getProcessoAll().size());
		avaliacaoProcessoDashboard.setQuantidadeColaboradores(colaboradorService.getColaboradorAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestionarios(questionarioService.getQuestionarioAll().size());
		avaliacaoProcessoDashboard.setQuantidadeQuestoes(questaoService.getQuestaoAll().size());
		
		Processo processo = processoService.getProcessoByProcessoPK(processoPK);
		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(questionarioPK);
		
		List<AvaliacaoProcesso> avaliacaoProcessoTemp = avaliacaoProcessoService.getByProcessoAndQuestionario(processo, questionario);
		
		avaliacaoProcessoTemp.forEach((AvaliacaoProcesso avaliacaoProcesso2) -> {
			
			List<AvaliacaoResultado> avaliacaoResultadoList = avaliacaoResultadoService.getByAvaliacaoProcessoPK(avaliacaoProcesso2.getAvaliacaoProcessoPK());
			
			
			Calendar avaliacaoProcessoPeriodoCalendar = Calendar.getInstance();
			Calendar dataAtual = Calendar.getInstance();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				avaliacaoProcessoPeriodoCalendar.setTime(sdf.parse(avaliacaoProcesso2.getAvaliacaoProcessoPeriodo()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			avaliacaoResultadoList.forEach((AvaliacaoResultado avaliacaoResultado) -> {
				
				if (avaliacaoProcessoPeriodoCalendar.get(Calendar.YEAR) == dataAtual.get(Calendar.YEAR)) {
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 0 ) {
						avaliacaoProcessoDashboard.setElementoMediaJanAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 1 ) {
						avaliacaoProcessoDashboard.setElementoMediaFevAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 2 ) {
						avaliacaoProcessoDashboard.setElementoMediaMarAtual(((int)avaliacaoResultado.getAvaliacaoElementoResposta()/(int)avaliacaoResultado.getAvaliacaoElementoQuantidade()));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 3 ) {
						avaliacaoProcessoDashboard.setElementoMediaAbrAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 4 ) {
						avaliacaoProcessoDashboard.setElementoMediaMaiAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 5 ) {
						avaliacaoProcessoDashboard.setElementoMediaJunAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 6 ) {
						avaliacaoProcessoDashboard.setElementoMediaJulAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 7 ) {
						avaliacaoProcessoDashboard.setElementoMediaAgoAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 8 ) {
						avaliacaoProcessoDashboard.setElementoMediaSetAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 9 ) {
						avaliacaoProcessoDashboard.setElementoMediaOutAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 10 ) {
						avaliacaoProcessoDashboard.setElementoMediaNovAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
					if (avaliacaoProcessoPeriodoCalendar.get(Calendar.MONTH) == 11 ) {
						avaliacaoProcessoDashboard.setElementoMediaDezAtual(Integer.parseInt(avaliacaoResultado.getAvaliacaoElementoResposta()/avaliacaoResultado.getAvaliacaoElementoQuantidade()+""));
					}
				}
			});
			
			avaliacaoProcessoDashboard.setElementoMediaJanAnterior(1);
			avaliacaoProcessoDashboard.setElementoMediaFevAnterior(2);
			avaliacaoProcessoDashboard.setElementoMediaMarAnterior(3);
			avaliacaoProcessoDashboard.setElementoMediaAbrAnterior(4);
			avaliacaoProcessoDashboard.setElementoMediaMaiAnterior(5);
			avaliacaoProcessoDashboard.setElementoMediaJunAnterior(1);
			avaliacaoProcessoDashboard.setElementoMediaJulAnterior(2);
			avaliacaoProcessoDashboard.setElementoMediaAgoAnterior(3);
			avaliacaoProcessoDashboard.setElementoMediaSetAnterior(4);
			avaliacaoProcessoDashboard.setElementoMediaOutAnterior(5);
			avaliacaoProcessoDashboard.setElementoMediaNovAnterior(1);
			avaliacaoProcessoDashboard.setElementoMediaDezAnterior(2);
			
		});

		
		mv.addObject("avaliacaoProcessoDashboard", avaliacaoProcessoDashboard);
		
		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso>  unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK()); 
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>(); 
		
		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenario/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenario(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);

		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 1) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAmbiente(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 2) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioDesempenho(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 3) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioFinanceiro(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 4) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioLogistica(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 5) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioPessoal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 6) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSeguranca(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 7) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAreaTecnica(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 8) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioTecnologia(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 9) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSustentabilidade(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		} else
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 10) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioMobilia(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAmbienteMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 1) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAmbienteMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAmbienteBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 1) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAmbienteBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAmbienteTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 1) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAmbienteTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAmbienteSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 1) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAmbienteSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAmbienteAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 1) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAmbienteAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioDesempenhoMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 2) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioDesempenhoMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioDesempenhoBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 2) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioDesempenhoBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioDesempenhoTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 2) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioDesempenhoTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioDesempenhoSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 2) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioDesempenhoSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioDesempenhoAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 2) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioDesempenhoAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioFinanceiroMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() ==3) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioFinanceiroMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioFinanceiroBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 3) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioFinanceiroBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioFinanceiroTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 3) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioFinanceiroTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioFinanceiroSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 3) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioFinanceiroSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioFinanceiroAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 3) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioFinanceiroAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioLogisticaMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 4) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioLogisticaMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioLogisticaBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 4) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioLogisticaBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioLogisticaTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 4) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioLogisticaTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioLogisticaSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 4) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioLogisticaSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioLogisticaAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 4) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioLogisticaAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioMobiliaMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 10) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioMobiliaMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioMobiliaBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 10) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioMobiliaBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioMobiliaTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 10) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioMobiliaTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioMobiliaSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 10) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioMobiliaSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioMobiliaAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 10) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioMobiliaAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioPessoalMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 5) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioPessoalMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioPessoalBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 5) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioPessoalBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioPessoalTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 5) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioPessoalTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioPessoalSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 5) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioPessoalSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioPessoalAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 5) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioPessoalAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSegurancaMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 6) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSegurancaMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSegurancaBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 6) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSegurancaBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSegurancaTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 6) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSegurancaTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSegurancaSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 6) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSegurancaSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSegurancaAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 6) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSegurancaAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSustentabilidadeMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 9) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSustentabilidadeMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSustentabilidadeBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 9) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSustentabilidadeBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSustentabilidadeTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 9) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSustentabilidadeTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSustentabilidadeSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 9) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSustentabilidadeSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioSustentabilidadeAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 9) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioSustentabilidadeAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioTecnologiaMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 8) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioTecnologiaMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioTecnologiaBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 8) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioTecnologiaBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioTecnologiaTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 8) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioTecnologiaTrimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioTecnologiaSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 8) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioTecnologiaSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioTecnologiaAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 8) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioTecnologiaAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	
	
	@GetMapping(path = "/avaliacaoProcessoDashboardMenu/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardMenu(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoDashboardMenu");
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = avaliacaoProcessoService.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoQuestionario/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoQuestionario(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoQuestionario");
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		AvaliacaoProcessoForm avaliacaoProcessoForm = avaliacaoProcessoService.converteAvaliacaoProcesso(avaliacaoProcesso);
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);
		mv.addObject("avaliacaoProcessoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoProcessoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("processoPage", processoService.getProcessoAll());
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll());
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("questaos", questionarioQuestaoService.getByQuestionarioPK(questionarioPK));

		List<UnidadeorganizacionalProcesso>  unidadeorganizacionalProcessos = unidadeorganizacionalProcessoService.getByProcessoPK(avaliacaoProcessoForm.getProcessoPK()); 
		mv.addObject("unidadeorganizacionals", unidadeorganizacionalProcessos);
		List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionals = new ArrayList<EstruturafisicaUnidadeorganizacional>(); 

		unidadeorganizacionalProcessos.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			List<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalService.getByUnidadeorganizacionalPK(unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK());
			estruturafisicaUnidadeorganizacionals.addAll(estruturafisicaUnidadeorganizacionalList);
		});
		
		mv.addObject("estruturafisicaUnidadeorganizacionals", estruturafisicaUnidadeorganizacionals);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoProcessoHome")
	public ModelAndView avaliacaoProcessoHome(@Valid AvaliacaoProcessoByAvaliacaoForm avaliacaoProcessoByAvaliacaoForm, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoHome");
		
		List<AvaliacaoProcesso> avaliacaoProcessos = new ArrayList<AvaliacaoProcesso>();
		
		int avaliacaoProcessosTotal = 0;
		
		if (avaliacaoProcessoByAvaliacaoForm.getSearchProcessoNome() == null) {
			avaliacaoProcessoByAvaliacaoForm.setSearchProcessoNome("");
		}
		if (avaliacaoProcessoByAvaliacaoForm.getSearchAvaliacaoNome() == null) {
			avaliacaoProcessoByAvaliacaoForm.setSearchAvaliacaoNome("");
		}
		if (avaliacaoProcessoByAvaliacaoForm.getSearchQuestionarioNome() == null) {
			avaliacaoProcessoByAvaliacaoForm.setSearchQuestionarioNome("");
		}
		if (avaliacaoProcessoByAvaliacaoForm.getSearchPeriodoNome() == null) {
			avaliacaoProcessoByAvaliacaoForm.setSearchPeriodoNome("");
		}
		if (avaliacaoProcessoByAvaliacaoForm.getAvaliacaoProcessoSortTipo() == null) {
			avaliacaoProcessoByAvaliacaoForm.setAvaliacaoProcessoSortTipo("AvaliacaoNome");	
		}

		if (avaliacaoProcessoByAvaliacaoForm.getAvaliacaoProcessoSortTipo().equalsIgnoreCase("AvaliacaoNome")
				|| avaliacaoProcessoByAvaliacaoForm.getAvaliacaoProcessoSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"avaliacao.avaliacaoNome","processo.processoNome","questionario.questionarioNome","avaliacaoProcessoPeriodo"); 
		
		} else if (avaliacaoProcessoByAvaliacaoForm.getAvaliacaoProcessoSortTipo().equalsIgnoreCase("ProcessoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"processo.processoNome","avaliacao.avaliacaoNome","questionario.questionarioNome","avaliacaoProcessoPeriodo"); 

		} else if (avaliacaoProcessoByAvaliacaoForm.getAvaliacaoProcessoSortTipo().equalsIgnoreCase("QuestionarioNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"questionario.questionarioNome","processo.processoNome","avaliacao.avaliacaoNome","avaliacaoProcessoPeriodo"); 

		} else if (avaliacaoProcessoByAvaliacaoForm.getAvaliacaoProcessoSortTipo().equalsIgnoreCase("PeriodoNome")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC,"avaliacaoProcessoPeriodo","questionario.questionarioNome","processo.processoNome","avaliacao.avaliacaoNome"); 

		}

		if ( ! avaliacaoProcessoByAvaliacaoForm.getSearchProcessoNome().equalsIgnoreCase("")){
			avaliacaoProcessos = avaliacaoProcessoService.getByProcessoNome(avaliacaoProcessoByAvaliacaoForm.getSearchProcessoNome(),pageable);
			avaliacaoProcessosTotal = avaliacaoProcessoService.getByProcessoNome(avaliacaoProcessoByAvaliacaoForm.getSearchProcessoNome()).size();
			
		} else if ( ! avaliacaoProcessoByAvaliacaoForm.getSearchPeriodoNome().equalsIgnoreCase("")) {
			avaliacaoProcessos = avaliacaoProcessoService.getByPeriodoNome(avaliacaoProcessoByAvaliacaoForm.getSearchPeriodoNome(),pageable);
			avaliacaoProcessosTotal = avaliacaoProcessoService.getByPeriodoNome(avaliacaoProcessoByAvaliacaoForm.getSearchPeriodoNome()).size();

		} else if ( ! avaliacaoProcessoByAvaliacaoForm.getSearchQuestionarioNome().equalsIgnoreCase("")) {
			avaliacaoProcessos = avaliacaoProcessoService.getByQuestionarioNome(avaliacaoProcessoByAvaliacaoForm.getSearchQuestionarioNome(),pageable);
			avaliacaoProcessosTotal = avaliacaoProcessoService.getByQuestionarioNome(avaliacaoProcessoByAvaliacaoForm.getSearchQuestionarioNome()).size();

		} else {
			avaliacaoProcessos = avaliacaoProcessoService.getByAvaliacaoNome(avaliacaoProcessoByAvaliacaoForm.getSearchAvaliacaoNome(),pageable);
			avaliacaoProcessosTotal = avaliacaoProcessoService.getByAvaliacaoNome(avaliacaoProcessoByAvaliacaoForm.getSearchAvaliacaoNome()).size();


		} 
		
		Page<AvaliacaoProcesso> pageAvaliacaoProcessos = new PageImpl<AvaliacaoProcesso>(avaliacaoProcessos,pageable,avaliacaoProcessosTotal+1);
		
		mv.addObject("avaliacaoProcessoPage", pageAvaliacaoProcessos);
		mv.addObject("page",pageAvaliacaoProcessos);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/avaliacaoProcessoSave")
	public ModelAndView avaliacaoProcessoSave(@Valid AvaliacaoProcessoForm avaliacaoProcessoForm, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		if (result.hasErrors()) {
			return avaliacaoProcessoAdd(avaliacaoProcessoForm);
		}

		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.converteAvaliacaoProcessoForm(avaliacaoProcessoForm);
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoProcessoHome");
		avaliacaoProcessoService.save(avaliacaoProcesso);
		attributes.addFlashAttribute("mensagem", "Processo/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/avaliacaoProcessoQuestionario")
	public ModelAndView avaliacaoProcessoQuestionario(AvaliacaoProcessoForm avaliacaoProcessoForm, RedirectAttributes attributes,Pageable pageable) {


		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(avaliacaoProcessoForm.getQuestionarioPK());
		
		if (questionario.getCenario().getCenarioPK() == 1) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioAmbiente(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 2) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioDesempenho(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 3) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioFinanceiro(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 4) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioLogistica(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 5) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioPessoal(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 6) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioSeguranca(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 7) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioAreaTecnica(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 8) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioTecnologia(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 9) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioSustentabilidade(avaliacaoProcessoForm);
		} else if (questionario.getCenario().getCenarioPK() == 10) {
			avaliacaoProcessoService.saveAvaliacaoProcessoQuestionarioMobilia(avaliacaoProcessoForm);
		}
		
		
		
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoProcessoQuestionario/" + 
														avaliacaoProcessoForm.getAvaliacaoProcessoPK()+"/" + 
														avaliacaoProcessoForm.getQuestionarioPK() + " /"+ 
														avaliacaoProcessoForm.getProcessoPK() +"/" + 
														avaliacaoProcessoForm.getEstruturafisicaPK()+ "/ "+ 
														avaliacaoProcessoForm.getUnidadeorganizacionalPK());
		attributes.addFlashAttribute("mensagem", "Processo/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoRelMenu")
	public ModelAndView avaliacaoProcessoRelMenu() {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/avaliacaoProcessoRel001")
	public ModelAndView avaliacaoProcessoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoRel001");
		Pageable avaliacaoProcessoPageable = PageRequest.of(0, 200, Direction.ASC, "avaliacao.avaliacaoNome","processo.processoNome");
		mv.addObject("avaliacaoProcessoPage", avaliacaoProcessoService.getAvaliacaoProcessoAll(avaliacaoProcessoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoView/{id}")
	public ModelAndView avaliacaoProcessoView(@PathVariable("id") Long avaliacaoProcessoId) {

		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoId);
		ModelAndView mv = new ModelAndView("avaliacaoProcesso/avaliacaoProcessoView"); 
		AvaliacaoProcessoForm avaliacaoProcessoForm = avaliacaoProcessoService.converteAvaliacaoProcessoView(avaliacaoProcesso);
		AvaliacaoForm avaliacaoForm = avaliacaoService.converteAvaliacaoView(avaliacaoProcesso.getAvaliacao());
		ProcessoForm processoForm = processoService.converteProcessoView(avaliacaoProcesso.getProcesso());
		QuestionarioForm questionarioForm = questionarioService.converteQuestionarioView(avaliacaoProcesso.getQuestionario());
		mv.addObject("avaliacaoProcessoForm", avaliacaoProcessoForm);
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("processoForm", processoForm);
		mv.addObject("questionarioForm", questionarioForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAreaTecnicaMensal/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 7) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAreaTecnicaMensal(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAreaTecnicaBimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 7) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAreaTecnicaBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAreaTecnicaTrimestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 7) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAreaTecnicaBimestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAreaTecnicaSemestral/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 7) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAreaTecnicaSemestral(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}

	@GetMapping(path = "/avaliacaoProcessoDashboardCenarioAreaTecnicaAnual/{avaliacaoProcessoPK}/{questionarioPK}/{processoPK}/{estruturafisicaPK}/{unidadeorganizacionalPK}")
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
													  @PathVariable("questionarioPK") Long questionarioPK,
													  @PathVariable("processoPK") Long processoPK,
													  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
													  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK)
						{
	
		ModelAndView mv = new ModelAndView();
		AvaliacaoProcesso avaliacaoProcesso = avaliacaoProcessoService.getAvaliacaoProcessoByAvaliacaoProcessoPK(avaliacaoProcessoPK);
		
		if (avaliacaoProcesso.getQuestionario().getCenario().getCenarioPK() == 7) {
			mv = avaliacaoProcessoService.avaliacaoProcessoDashboardCenarioAreaTecnicaAnual(avaliacaoProcessoPK, questionarioPK, processoPK, estruturafisicaPK, unidadeorganizacionalPK);
		}
	
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
	
		return mv;
	}


}