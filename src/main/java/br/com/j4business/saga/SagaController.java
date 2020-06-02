package br.com.j4business.saga;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.j4business.saga.atividade.service.AtividadeService;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.estruturafisica.service.EstruturafisicaService;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.ocorrencia.service.OcorrenciaService;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.servico.service.ServicoService;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;

@Controller
public class SagaController {

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private EstruturafisicaService estruturafisicaService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private ServicoService servicoService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@RequestMapping(path="/", method=RequestMethod.GET)
	public ModelAndView goPrincipal(){
		
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("atividadeList", atividadeService.getAtividadeAll());
		mv.addObject("cenarioList", cenarioService.getCenarioAll());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoList", contratoService.getContratoAll());
		mv.addObject("elementoList", elementoService.getElementoAll());
		mv.addObject("estruturaFisicaList", estruturafisicaService.getEstruturafisicaAll());
		mv.addObject("eventoList", eventoService.getEventoAll());
		mv.addObject("ocorrenciaList", ocorrenciaService.getOcorrenciaAll());
		mv.addObject("planejamentoList", planejamentoService.getPlanejamentoAll());
		mv.addObject("processoList", processoService.getProcessoAll());
		mv.addObject("servicoList", servicoService.getServicoAll());
		mv.addObject("unidadeOrganizacionalList", unidadeorganizacionalService.getUnidadeorganizacionalAll());
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

/*	public ModelAndView acaoAdd(AcaoForm acaoForm) {

		ModelAndView mv = new ModelAndView("acao/acaoAdd");
		acaoForm = acaoService.acaoParametros(acaoForm);
		mv.addObject("acaoForm", acaoForm);
		mv.addObject("acaoStatusValues", AcaoStatus.values());
		mv.addObject("acaoAprovacaoValues", AcaoAprovacao.values());
		mv.addObject("colaboradores", colaboradorService.getColaboradorAll());
		return mv;
	}
*/	
	
	@RequestMapping(path="/login", method=RequestMethod.GET)
	public String goLogin(){
		
		
/*		Evento evento = new Evento();
		evento.setEventoNome( "Aniversario da cidade22222" );
		evento.setEventoDescricao( "Aniversario da cidade22222" );
		
		Atributo atributo = new Atributo();
		atributo.setAtributoNome("Data de criação22222");
		atributo.setAtributoDescricao("Data da criação do atributo22222");
		
		EventoAtributo eventoAtributo = new EventoAtributo();
		eventoAtributo.setEventoAtributoValor("19/10/2017");
		
		eventoAtributo.setEvento(evento);
		eventoAtributo.setAtributo(atributo);
		
		evento.getEventoAtributos().add(eventoAtributo);
		
		eventoService.save(evento);
*/		
		
		return "login";
	}

	@RequestMapping(path="/indexBootstrap", method=RequestMethod.GET)
	public String goIndexBootstrap(){
		return "indexBootstrap";
	}

	@RequestMapping(path="/template", method=RequestMethod.GET)
	public String goTemplate(){
		return "template";
	}

	@RequestMapping(path="/add", method=RequestMethod.GET)
	public String goAdd(){
		return "add";
	}

	@RequestMapping(path="/edit", method=RequestMethod.GET)
	public String goEdit(){
		return "edit";
	}

	@RequestMapping(path="/view", method=RequestMethod.GET)
	public String goView(){
		return "view";
	}

	@RequestMapping(path="/home", method=RequestMethod.GET)
	public String goHome(){
		return "home";
	}

	@RequestMapping(path="/sidebar", method=RequestMethod.GET)
	public String goSidebar(){
		return "sidebar";
	}

	@RequestMapping(path="/vertical", method=RequestMethod.GET)
	public String goVertical(){
		return "vertical";
	}

	@RequestMapping(path="/colapse", method=RequestMethod.GET)
	public String goColpase(){
		return "colapse";
	}

	@RequestMapping(path="/index", method=RequestMethod.GET)
	public ModelAndView goIndex(){
		
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("atividadeList", atividadeService.getAtividadeAll());
		mv.addObject("cenarioList", cenarioService.getCenarioAll());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoList", contratoService.getContratoAll());
		mv.addObject("elementoList", elementoService.getElementoAll());
		mv.addObject("estruturaFisicaList", estruturafisicaService.getEstruturafisicaAll());
		mv.addObject("eventoList", eventoService.getEventoAll());
		mv.addObject("ocorrenciaList", ocorrenciaService.getOcorrenciaAll());
		mv.addObject("planejamentoList", planejamentoService.getPlanejamentoAll());
		mv.addObject("processoList", processoService.getProcessoAll());
		mv.addObject("servicoList", servicoService.getServicoAll());
		mv.addObject("unidadeOrganizacionalList", unidadeorganizacionalService.getUnidadeorganizacionalAll());
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		
		return mv;
	}

	@RequestMapping(path="/403", method=RequestMethod.GET)
	public String go403(){
		return "403";
	}
	
	@RequestMapping(path="/evento", method=RequestMethod.GET)
	public String goEvento(){
		return "evento/eventoHome";
	}

	@RequestMapping(path="/atributo", method=RequestMethod.GET)
	public String goAtributo(){
		return "atributo/atributoHome";
	}

	@RequestMapping(path="/colaborador", method=RequestMethod.GET)
	public String goColaborador(){
		return "colaborador/colaboradorHome";
	}



}