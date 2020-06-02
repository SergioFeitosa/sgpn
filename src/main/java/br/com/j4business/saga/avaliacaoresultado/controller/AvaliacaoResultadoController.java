package br.com.j4business.saga.avaliacaoresultado.controller;

import java.util.ArrayList;
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
import br.com.j4business.saga.avaliacaoresultado.model.AvaliacaoResultado;
import br.com.j4business.saga.avaliacaoresultado.service.AvaliacaoResultadoService;
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
public class AvaliacaoResultadoController {

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private AvaliacaoResultadoService avaliacaoResultadoService;

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

	@RequestMapping(path = "/avaliacaoResultadoAdd", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoAdd(AvaliacaoResultado avaliacaoResultado) {

		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoAdd");
		mv.addObject("avaliacaoResultado", avaliacaoResultado);
		mv.addObject("avaliacaoResultadoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoResultadoStatusValues", AtributoStatus.values());
		Pageable avaliacaoPageable = new PageRequest(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questionarioPageable = new PageRequest(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable resultadoPageable = new PageRequest(0, 200, Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(resultadoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/avaliacaoResultadoCreate", method = RequestMethod.POST)
	public ModelAndView avaliacaoResultadoCreate(AvaliacaoResultado avaliacaoResultado, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		
		if (avaliacaoResultado.getAvaliacaoResultadoPK() > 0) {
			avaliacaoResultado = avaliacaoResultadoService.getAvaliacaoResultadoByAvaliacaoResultadoPK(avaliacaoResultado.getAvaliacaoResultadoPK());
			avaliacaoResultadoService.save(avaliacaoResultado);
		} else {
			avaliacaoResultado = avaliacaoResultadoService.create(avaliacaoResultado);
		}
		
		ModelAndView mv = new ModelAndView("redirect:/avaliacaoResultadoHome");
		attributes.addFlashAttribute("mensagem", "Resultado/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}


	@RequestMapping(path = "/avaliacaoResultadoDelete/{id}", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoDelete(@PathVariable("id") long avaliacaoResultadoId, @Valid ResultadoForm resultadoForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoResultadoHome");
		
		
		AvaliacaoResultado avaliacaoResultado = avaliacaoResultadoService.getAvaliacaoResultadoByAvaliacaoResultadoPK(avaliacaoResultadoId);
		try {
			avaliacaoResultadoService.delete(avaliacaoResultadoId);

			attributes.addFlashAttribute("mensagem", "Relacionamento Avaliação/Resultado excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Relacionamento Avaliação/Resultado não excluído. Existe(m) relacionamento(s) de Avaliação/Resultado ** "+ 
										  avaliacaoResultado.getAvaliacao().getAvaliacaoNome() +
										  " / " +
										  avaliacaoResultado.getResultado().getResultadoNome() +
										  " ** no cadastro.");
	    }
		

		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/avaliacaoResultadoEdit/{avaliacaoResultadoPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoEdit(@PathVariable("avaliacaoResultadoPK") Long avaliacaoResultadoPK, Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoEdit");
		AvaliacaoResultado avaliacaoResultado = avaliacaoResultadoService.getAvaliacaoResultadoByAvaliacaoResultadoPK(avaliacaoResultadoPK);
		mv.addObject("avaliacaoResultado", avaliacaoResultado);
		mv.addObject("avaliacaoResultadoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoResultadoStatusValues", AtributoStatus.values());
		Pageable avaliacaoPageable = new PageRequest(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questionarioPageable = new PageRequest(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable resultadoPageable = new PageRequest(0, 200, Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(resultadoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoResultadoQuestionario/{avaliacaoResultadoPK}/{questionarioPK}", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoQuestionario(@PathVariable("avaliacaoResultadoPK") Long avaliacaoResultadoPK,
													  @PathVariable("questionarioPK") Long questionarioPK)
						{

		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoQuestionario");
		AvaliacaoResultado avaliacaoResultado = avaliacaoResultadoService.getAvaliacaoResultadoByAvaliacaoResultadoPK(avaliacaoResultadoPK);
		mv.addObject("avaliacaoResultado", avaliacaoResultado);
		mv.addObject("avaliacaoResultadoPrioridadeValues", AtributoPrioridade.values());
		mv.addObject("avaliacaoResultadoStatusValues", AtributoStatus.values());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		Pageable avaliacaoPageable = new PageRequest(0, 200, Direction.ASC, "avaliacaoNome");
		mv.addObject("avaliacaoPage", avaliacaoService.getAvaliacaoAll(avaliacaoPageable));
		Pageable cenarioPageable = new PageRequest(0, 200, Direction.ASC, "cenarioNome");
		mv.addObject("cenarioPage", cenarioService.getCenarioAll(cenarioPageable));
		Pageable colaboradorPageable = new PageRequest(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable questionarioPageable = new PageRequest(0, 200, Direction.ASC, "questionarioNome");
		mv.addObject("questionarioPage", questionarioService.getQuestionarioAll(questionarioPageable));
		Pageable resultadoPageable = new PageRequest(0, 200, Direction.ASC, "resultadoNome");
		mv.addObject("resultadoPage", resultadoService.getResultadoAll(resultadoPageable));
		Pageable questaoPageable = new PageRequest(0, 200, Direction.ASC, "questaoSequencia");
		mv.addObject("questaoPage", questionarioQuestaoService.getByQuestionarioPK(questionarioPK,questaoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@RequestMapping(path = "/avaliacaoResultadoHome", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoHome(@Valid AvaliacaoResultado avaliacaoResultado, BindingResult result,RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoHome");
		
		List<AvaliacaoResultado> avaliacaoResultados = new ArrayList<AvaliacaoResultado>();
		
		int avaliacaoResultadosTotal = 0;
		
		
		Page<AvaliacaoResultado> pageAvaliacaoResultados = new PageImpl<AvaliacaoResultado>(avaliacaoResultados,pageable,avaliacaoResultadosTotal+1);
		
		mv.addObject("avaliacaoResultadoPage", pageAvaliacaoResultados);
		mv.addObject("page",pageAvaliacaoResultados);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/avaliacaoResultadoSave", method = RequestMethod.POST)
	public ModelAndView avaliacaoResultadoSave(@Valid AvaliacaoResultado avaliacaoResultado, BindingResult result, RedirectAttributes attributes,Pageable pageable) {

		ModelAndView mv = new ModelAndView("redirect:/avaliacaoResultadoHome");
		avaliacaoResultadoService.save(avaliacaoResultado);
		attributes.addFlashAttribute("mensagem", "Resultado/Avaliacao Salvo com sucesso");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@RequestMapping(path = "/avaliacaoResultadoRelMenu", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoRelMenu() {

		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping("/avaliacaoResultadoRel001")
	public ModelAndView avaliacaoResultadoRel001(Pageable pageable) {

		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoRel001");
		mv.addObject("avaliacaoResultadoPage", avaliacaoResultadoService.getAvaliacaoResultadoAll(pageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@RequestMapping(path = "/avaliacaoResultadoView/{id}", method = RequestMethod.GET)
	public ModelAndView avaliacaoResultadoView(@PathVariable("id") Long avaliacaoResultadoId) {

		AvaliacaoResultado avaliacaoResultado = avaliacaoResultadoService.getAvaliacaoResultadoByAvaliacaoResultadoPK(avaliacaoResultadoId);
		ModelAndView mv = new ModelAndView("avaliacaoResultado/avaliacaoResultadoView");
		AvaliacaoForm avaliacaoForm = avaliacaoService.converteAvaliacaoView(avaliacaoResultado.getAvaliacao());
		ResultadoForm resultadoForm = resultadoService.converteResultadoView(avaliacaoResultado.getResultado());
		mv.addObject("avaliacaoForm", avaliacaoForm);
		mv.addObject("resultadoForm", resultadoForm);
		mv.addObject("cenarioPage", cenarioService.getCenarioAll());
		mv.addObject("questionarios", questionarioService.getQuestionarioAll());
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}


}