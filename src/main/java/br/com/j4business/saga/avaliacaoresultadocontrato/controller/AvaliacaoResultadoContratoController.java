package br.com.j4business.saga.avaliacaoresultadocontrato.controller;

import java.util.ArrayList;
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
import br.com.j4business.saga.avaliacaoresultadocontrato.model.AvaliacaoResultadoContrato;
import br.com.j4business.saga.avaliacaoresultadocontrato.service.AvaliacaoResultadoContratoService;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.resultado.model.ResultadoForm;
import br.com.j4business.saga.resultado.service.ResultadoService;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.service.QuestionarioQuestaoService;

@Controller
public class AvaliacaoResultadoContratoController {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoResultadoContratoService avaliacaoResultadoContratoService;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ResultadoService resultadoService;

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private QuestionarioQuestaoService questionarioQuestaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/avaliacaoResultadoContratoAdd")
	public ModelAndView avaliacaoResultadoContratoAdd(AvaliacaoResultadoContrato avaliacaoResultadoContrato) {

		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoAdd");
		mv.addObject("avaliacaoResultadoContrato", avaliacaoResultadoContrato);
		mv.addObject("avaliacaoResultadoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoResultadoContratoStatusValues", AtributoStatus.values());
		Pageable avaliacaoPageable = PageRequest.of(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable resultadoPageable = PageRequest.of(0, 200, Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(resultadoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@PostMapping(path = "/avaliacaoResultadoContratoCreate")
	public ModelAndView avaliacaoResultadoContratoCreate(AvaliacaoResultadoContrato avaliacaoResultadoContrato, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		
		if (avaliacaoResultadoContrato.getAvaliacaoResultadoContratoPK() > 0) {
			avaliacaoResultadoContrato = avaliacaoResultadoContratoService.getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(avaliacaoResultadoContrato.getAvaliacaoResultadoContratoPK());
			avaliacaoResultadoContratoService.save(avaliacaoResultadoContrato);
		} else {
			avaliacaoResultadoContrato = avaliacaoResultadoContratoService.create(avaliacaoResultadoContrato);
		}
		
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoResultadoContratoHome");
		attributes.addFlashAttribute("mensagem", "Resultado/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@GetMapping(path = "/avaliacaoResultadoContratoDelete/{id}")
	public ModelAndView avaliacaoResultadoContratoDelete(@PathVariable("id") long resultadoId, @Valid ResultadoForm resultadoForm, BindingResult result, RedirectAttributes attributes) {

		avaliacaoResultadoContratoService.delete(resultadoId);
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoResultadoContratoHome");
		attributes.addFlashAttribute("mensagem", "Relacionamento Avaliacao/Resultado excluído com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoResultadoContratoEdit/{avaliacaoResultadoContratoPK}")
	public ModelAndView avaliacaoResultadoContratoEdit(@PathVariable("avaliacaoResultadoContratoPK") Long avaliacaoResultadoContratoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoEdit");
		AvaliacaoResultadoContrato avaliacaoResultadoContrato = avaliacaoResultadoContratoService.getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(avaliacaoResultadoContratoPK);
		mv.addObject("avaliacaoResultadoContrato", avaliacaoResultadoContrato);
		mv.addObject("avaliacaoResultadoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoResultadoContratoStatusValues", AtributoStatus.values());
		Pageable avaliacaoPageable = PageRequest.of(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable resultadoPageable = PageRequest.of(0, 200, Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(resultadoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoResultadoContratoQuestionario/{avaliacaoResultadoContratoPK}/{questionarioPK}")
	public ModelAndView avaliacaoResultadoContratoQuestionario(@PathVariable("avaliacaoResultadoContratoPK") Long avaliacaoResultadoContratoPK,
													  @PathVariable("questionarioPK") Long questionarioPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoQuestionario");
		AvaliacaoResultadoContrato avaliacaoResultadoContrato = avaliacaoResultadoContratoService.getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(avaliacaoResultadoContratoPK);
		mv.addObject("avaliacaoResultadoContrato", avaliacaoResultadoContrato);
		mv.addObject("avaliacaoResultadoContratoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoResultadoContratoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		Pageable avaliacaoPageable = PageRequest.of(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable cenarioPageable = PageRequest.of(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questionarioPageable = PageRequest.of(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable resultadoPageable = PageRequest.of(0, 200, Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(resultadoPageable));
		Pageable questaoPageable = PageRequest.of(0, 200, Direction.ASC, "questaoSequencia");
		mv.addObject("questaoPage", questionarioQuestaoService.getByQuestionarioPK(questionarioPK,questaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping(path = "/avaliacaoResultadoContratoHome")
	public ModelAndView avaliacaoResultadoContratoHome(@Valid AvaliacaoResultadoContrato avaliacaoResultadoContrato, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoHome");
		
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratos = new ArrayList<AvaliacaoResultadoContrato>();
		
		int avaliacaoResultadoContratosTotal = 0;
		
		
		Page<AvaliacaoResultadoContrato> pageAvaliacaoResultadoContratos = new PageImpl<AvaliacaoResultadoContrato>(avaliacaoResultadoContratos,pageable,avaliacaoResultadoContratosTotal+1);
		
		mv.addObject("avaliacaoResultadoContratoPage", pageAvaliacaoResultadoContratos);
		mv.addObject("page",pageAvaliacaoResultadoContratos);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/avaliacaoResultadoContratoSave")
	public ModelAndView avaliacaoResultadoContratoSave(@Valid AvaliacaoResultadoContrato avaliacaoResultadoContrato, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoResultadoContratoHome");
		avaliacaoResultadoContratoService.save(avaliacaoResultadoContrato);
		attributes.addFlashAttribute("mensagem", "Resultado/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/avaliacaoResultadoContratoRelMenu")
	public ModelAndView avaliacaoResultadoContratoRelMenu() {

		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/avaliacaoResultadoContratoRel001")
	public ModelAndView avaliacaoResultadoContratoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoRel001");
		mv.addObject("avaliacaoResultadoContratoPage", avaliacaoResultadoContratoService.getAvaliacaoResultadoContratoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/avaliacaoResultadoContratoView/{id}")
	public ModelAndView avaliacaoResultadoContratoView(@PathVariable("id") Long avaliacaoResultadoContratoId) {

		AvaliacaoResultadoContrato avaliacaoResultadoContrato = avaliacaoResultadoContratoService.getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(avaliacaoResultadoContratoId);
		ModelAndView mv = new ModelAndView("avaliacaoResultadoContrato/avaliacaoResultadoContratoView");
		AvaliacaoForm avaliacaoForm = avaliacaoService.converteAvaliacaoView(avaliacaoResultadoContrato.getAvaliacao());
		ResultadoForm resultadoForm = resultadoService.converteResultadoView(avaliacaoResultadoContrato.getResultado());
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("resultadoForm", resultadoForm);
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}