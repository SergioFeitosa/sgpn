package br.com.j4business.saga;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping(path="/")
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
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());
		
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
	
	@GetMapping(path="/login")
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

	@GetMapping(path="/indexBootstrap")
	public String goIndexBootstrap(){
		return "indexBootstrap";
	}

	@GetMapping(path="/template")
	public String goTemplate(){
		return "template";
	}

	@GetMapping(path="/add")
	public String goAdd(){
		return "add";
	}

	@GetMapping(path="/edit")
	public String goEdit(){
		return "edit";
	}

	@GetMapping(path="/view")
	public String goView(){
		return "view";
	}

	@GetMapping(path="/home")
	public String goHome(){
		return "home";
	}

	@GetMapping(path="/sidebar")
	public String goSidebar(){
		return "sidebar";
	}

	@GetMapping(path="/vertical")
	public String goVertical(){
		return "vertical";
	}

	@GetMapping(path="/colapse")
	public String goColpase(){
		return "colapse";
	}

	@GetMapping(path="/index")
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

	@GetMapping(path="/403")
	public String go403(){
		return "403";
	}
	
	@GetMapping(path="/evento")
	public String goEvento(){
		return "evento/eventoHome";
	}

	@GetMapping(path="/atributo")
	public String goAtributo(){
		return "atributo/atributoHome";
	}

	@GetMapping(path="/colaborador")
	public String goColaborador(){
		return "colaborador/colaboradorHome";
	}



}