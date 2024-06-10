package br.com.j4business.saga.colaborador.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.certificacao.service.CertificacaoService;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.model.ColaboradorByColaboradorForm;
import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorcertificacao.service.ColaboradorCertificacaoService;
import br.com.j4business.saga.colaboradorcurso.service.ColaboradorCursoService;
import br.com.j4business.saga.colaboradorformacao.service.ColaboradorFormacaoService;
import br.com.j4business.saga.colaboradorhabilidade.service.ColaboradorHabilidadeService;
import br.com.j4business.saga.colaboradorprocesso.service.ColaboradorProcessoService;
import br.com.j4business.saga.colaboradortreinamento.service.ColaboradorTreinamentoService;
import br.com.j4business.saga.curso.service.CursoService;
import br.com.j4business.saga.formacao.service.FormacaoService;
import br.com.j4business.saga.habilidade.service.HabilidadeService;


@Controller
public class ColaboradorController {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ColaboradorCertificacaoService colaboradorCertificacaoService;

	@Autowired
	private ColaboradorCursoService colaboradorCursoService;

	@Autowired
	private ColaboradorFormacaoService colaboradorFormacaoService;

	@Autowired
	private ColaboradorHabilidadeService colaboradorHabilidadeService;

	@Autowired
	private ColaboradorProcessoService colaboradorProcessoService;

	@Autowired
	private ColaboradorTreinamentoService colaboradorTreinamentoService;

	@Autowired
	private CertificacaoService certificacaoService;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private FormacaoService formacaoService;

	@Autowired
	private HabilidadeService habilidadeService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	@GetMapping(path = "/colaboradorAdd")
	public ModelAndView colaboradorAdd(ColaboradorForm colaboradorForm) {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorAdd");
		
		colaboradorForm = colaboradorService.colaboradorParametros(colaboradorForm);
		
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("colaboradorStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable certificacaoPageable = PageRequest.of(0, 200, Direction.ASC, "certificacaoNome");
		mv.addObject("certificacaoPage", certificacaoService.getCertificacaoAll(certificacaoPageable));
		Pageable cursoPageable = PageRequest.of(0, 200, Direction.ASC, "cursoNome");
		mv.addObject("cursoPage", cursoService.getCursoAll(cursoPageable));
		Pageable formacaoPageable = PageRequest.of(0, 200, Direction.ASC, "formacaoNivel");
		mv.addObject("formacaoPage", formacaoService.getFormacaoAll(formacaoPageable));
		Pageable habilidadePageable = PageRequest.of(0, 200, Direction.ASC, "habilidadeNome");
		mv.addObject("habilidadePage", habilidadeService.getHabilidadeAll(habilidadePageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@PostMapping(path = "/colaboradorCreate")
	public ModelAndView colaboradorCreate(@Valid ColaboradorForm colaboradorForm, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return colaboradorAdd(colaboradorForm);
		}

		if (colaboradorForm.getColaboradorPK() > 0) {
			
			return this.colaboradorSave(colaboradorForm,result, attributes);
			
		}
		
		try {
			colaboradorService.create(colaboradorForm);

		} catch (PersistenceException  e) {
		
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("pessoaCPF")) {
		        ObjectError error = new ObjectError("pessoaCPF","Número do CPF já existente no cadastro.");
		        result.addError(error);			
			}
            
			return colaboradorAdd(colaboradorForm);
	    }


		ModelAndView mv = new ModelAndView("redirect:/acaoHome");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());

		
		return mv;
	}

	@GetMapping(path = "/colaboradorDelete/{id}")
	public ModelAndView colaboradorDelete(@PathVariable("id") long colaboradorPK, @Valid ColaboradorForm colaboradorForm, BindingResult result, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView("redirect:/colaboradorHome");

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(colaboradorPK);
		try {
			colaboradorService.delete(colaboradorPK);

			attributes.addFlashAttribute("mensagem", "Colaborador excluído com sucesso");

		} catch (Exception  e) {
			attributes.addFlashAttribute("mensagemErro", "Colaborador não excluído. Existe(m) relacionamento(s) de Colaborador ** "+ colaborador.getPessoaNome() + " ** no cadastro.");
	    }
		
		
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

	@GetMapping(path = "/colaboradorEdit/{id}")
	public ModelAndView colaboradorEdit(@PathVariable("id") Long colaboradorPK) {

		
		ModelAndView mv = new ModelAndView("colaborador/colaboradorEdit");
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(colaboradorPK);
		
		ColaboradorForm colaboradorForm = colaboradorService.converteColaborador(colaborador);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("colaboradorStatusValues", AtributoStatus.values());
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		Pageable colaboradorCertificacaoPageable = PageRequest.of(0, 200, Direction.ASC, "certificacao.certificacaoNome");
		mv.addObject("colaboradorCertificacaoPage", colaboradorCertificacaoService.getByColaboradorPK(colaboradorPK, colaboradorCertificacaoPageable));
		Pageable colaboradorCursoPageable = PageRequest.of(0, 200, Direction.ASC, "curso.cursoNome");
		mv.addObject("colaboradorCursoPage", colaboradorCursoService.getByColaboradorPK(colaboradorPK, colaboradorCursoPageable));
		Pageable colaboradorFormacaoPageable = PageRequest.of(0, 200, Direction.ASC, "formacao.formacaoNome");
		mv.addObject("colaboradorFormacaoPage", colaboradorFormacaoService.getByColaboradorPK(colaboradorPK, colaboradorFormacaoPageable));
		Pageable colaboradorHabilidadePageable = PageRequest.of(0, 200, Direction.ASC, "habilidade.habilidadeNome");
		mv.addObject("colaboradorHabilidadePage", colaboradorHabilidadeService.getByColaboradorPK(colaboradorPK, colaboradorHabilidadePageable));
		Pageable colaboradorProcessoPageable = PageRequest.of(0, 200, Direction.ASC, "processo.processoNome");
		mv.addObject("colaboradorProcessoPage", colaboradorProcessoService.getByColaboradorPK(colaboradorPK, colaboradorProcessoPageable));
		Pageable colaboradorTreinamentoPageable = PageRequest.of(0, 200, Direction.ASC, "treinamento.treinamentoNome");
		mv.addObject("colaboradorTreinamentoPage", colaboradorTreinamentoService.getByColaboradorPK(colaboradorPK,colaboradorTreinamentoPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@GetMapping("/colaboradorHome")
	public ModelAndView colaboradorHome(@Valid ColaboradorByColaboradorForm colaboradorByColaboradorForm, BindingResult result, RedirectAttributes attributes, Pageable pageable) {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorHome");
		List<Colaborador> colaboradores = new ArrayList<Colaborador>();

		int colaboradorsTotal = 0;
		
		if (colaboradorByColaboradorForm.getSearchColaboradorNome() == null) {
			colaboradorByColaboradorForm.setSearchColaboradorNome("");
			colaboradorByColaboradorForm.setSearchColaboradorApelido("");
			if (colaboradorByColaboradorForm.getColaboradorSortTipo() == null) {
				colaboradorByColaboradorForm.setColaboradorSortTipo("ColaboradorNome");
			}

		}

		if (colaboradorByColaboradorForm.getColaboradorSortTipo().equalsIgnoreCase("ColaboradorNome") || colaboradorByColaboradorForm.getColaboradorSortTipo().equalsIgnoreCase("")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "pessoaNome");

		} else if (colaboradorByColaboradorForm.getColaboradorSortTipo().equalsIgnoreCase("ColaboradorApelido")) {
			pageable = PageRequest.of(pageable.getPageNumber(), 15, Direction.ASC, "colaboradorApelido");

		}

		if ((!colaboradorByColaboradorForm.getSearchColaboradorNome().equalsIgnoreCase(""))) {
			colaboradores = colaboradorService.getByColaboradorNome(colaboradorByColaboradorForm.getSearchColaboradorNome(), pageable);
			colaboradorsTotal = colaboradorService.getByColaboradorNome(colaboradorByColaboradorForm.getSearchColaboradorNome()).size();

		} else {
			colaboradores = colaboradorService.getByColaboradorApelido(colaboradorByColaboradorForm.getSearchColaboradorApelido(), pageable);
			colaboradorsTotal = colaboradorService.getByColaboradorApelido(colaboradorByColaboradorForm.getSearchColaboradorApelido()).size();
		}

		Page<Colaborador> pageColaboradores = new PageImpl<Colaborador>(colaboradores, pageable, colaboradorsTotal+1);

		mv.addObject("colaboradorPage", pageColaboradores);
		mv.addObject("page", pageColaboradores);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}
	
	@PostMapping(path = "/colaboradorSave")
	public ModelAndView colaboradorSave(@Valid ColaboradorForm colaboradorForm,BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return colaboradorAdd(colaboradorForm);
		}

		ModelAndView mv = new ModelAndView("redirect:/colaboradorHome");

		try {
			colaboradorService.save(colaboradorForm);
		} catch (Exception  e) {
			ConstraintViolationException t = (ConstraintViolationException) e.getCause();	
			if (t.getConstraintName().equalsIgnoreCase("pessoaCPF")) {
			        ObjectError error = new ObjectError("pessoaCPF","Número do CPF já existente no cadastro.");
			        result.addError(error);			
			}
            return colaboradorAdd(colaboradorForm);
	    }
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); 
		mv.addObject("standardDate",new Date());
		return mv;
		
	}


	@GetMapping(path = "/colaboradorRelMenu")
	public ModelAndView colaboradorRelMenu() {
		ModelAndView mv = new ModelAndView("colaborador/colaboradorRelMenu");
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
		
	}

	@GetMapping("/colaboradorRel001")
	public ModelAndView colaboradorRel001() {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorRel001");
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorPage", colaboradorService.getColaboradorAll(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorRel002")
	public ModelAndView colaboradorRel002() {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorRel002");
		
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorRel002List", colaboradorService.preparaRelatorio002(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorRel003")
	public ModelAndView colaboradorRel003() {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorRel003");
		
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorRel003List", colaboradorService.preparaRelatorio003(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorRel004")
	public ModelAndView colaboradorRel004() {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorRel004");
		
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorRel004List", colaboradorService.preparaRelatorio004(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping("/colaboradorRel005")
	public ModelAndView colaboradorRel005() {

		ModelAndView mv = new ModelAndView("colaborador/colaboradorRel005");
		
		Pageable colaboradorPageable = PageRequest.of(0, 200, Direction.ASC, "pessoaNome");
		mv.addObject("colaboradorRel005List", colaboradorService.preparaRelatorio005(colaboradorPageable));
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

		return mv;
	}

	@GetMapping(path = "/colaboradorView/{id}")
	public ModelAndView colaboradorView(@PathVariable("id") Long colaboradorId) {

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(colaboradorId);
		ModelAndView mv = new ModelAndView("colaborador/colaboradorView");
		ColaboradorForm colaboradorForm = colaboradorService.converteColaboradorView(colaborador);
		mv.addObject("colaboradorForm", colaboradorForm);
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());
		return mv;
	}

}