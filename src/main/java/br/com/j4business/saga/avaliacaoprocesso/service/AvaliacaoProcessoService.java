package br.com.j4business.saga.avaliacaoprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcesso;
import br.com.j4business.saga.avaliacaoprocesso.model.AvaliacaoProcessoForm;

@Service
public interface AvaliacaoProcessoService {
	
	public List<AvaliacaoProcesso> getAvaliacaoProcessoAll(Pageable pageable);
	public AvaliacaoProcesso getAvaliacaoProcessoByAvaliacaoProcessoPK(long avaliacaoProcessoPK);
	public AvaliacaoProcesso save(AvaliacaoProcesso avaliacaoProcesso);
	public void delete(Long avaliacaoProcessoPK);
	public void deleteByProcesso(Processo processo);
	public AvaliacaoProcesso create(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public AvaliacaoProcesso getByAvaliacaoAndProcesso(Avaliacao avaliacao, Processo processo);

	public AvaliacaoProcesso converteAvaliacaoProcessoForm(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioAmbiente(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioAreaTecnica(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioDesempenho(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioFinanceiro(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioLogistica(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioMobilia(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioPessoal(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioSeguranca(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioSustentabilidade(AvaliacaoProcessoForm avaliacaoProcessoForm);
	public void saveAvaliacaoProcessoQuestionarioTecnologia(AvaliacaoProcessoForm avaliacaoProcessoForm);

	public AvaliacaoProcessoForm converteAvaliacaoProcesso(AvaliacaoProcesso avaliacaoProcesso);
	public AvaliacaoProcessoForm converteAvaliacaoProcessoView(AvaliacaoProcesso avaliacaoProcesso);

	public AvaliacaoProcessoForm avaliacaoProcessoParametros(AvaliacaoProcessoForm avaliacaoProcessoForm);

	public List<AvaliacaoProcesso> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable);
	public List<AvaliacaoProcesso> getByProcessoPK(long processoPK,Pageable pageable);

	public List<AvaliacaoProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<AvaliacaoProcesso> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable);
	public List<AvaliacaoProcesso> getByProcessoNome(String processoNome);
	public List<AvaliacaoProcesso> getByAvaliacaoNome(String avaliacaoNome);
	public List<AvaliacaoProcesso> getByQuestionarioNome(String questionarioNome,Pageable pageable);
	public List<AvaliacaoProcesso> getByPeriodoNome(String periodoNome,Pageable pageable);
	public List<AvaliacaoProcesso> getByQuestionarioNome(String questionarioNome);
	public List<AvaliacaoProcesso> getByPeriodoNome(String periodoNome);
	
	public AvaliacaoProcesso addAvaliacaoQuestionario(AvaliacaoProcesso avaliacaoProcesso);

	public List<AvaliacaoProcesso> getByProcessoAndQuestionario(Processo processo, Questionario questionario,Pageable pageable);
	public List<AvaliacaoProcesso> getByProcessoAndQuestionario(Processo processo, Questionario questionario);
	
	public AvaliacaoProcesso getByPeriodoNome(Avaliacao avaliacao, Processo processo,Questionario questionario, String periodoNome);
	
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbiente(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAmbienteAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenho(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioDesempenhoAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiro(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioFinanceiroAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioLogistica(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioLogisticaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioPessoalAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSeguranca(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSegurancaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnica(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioAreaTecnicaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologia(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioTecnologiaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidade(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioSustentabilidadeAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioMobilia(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaMensal(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaBimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaTrimestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaSemestral(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoProcessoDashboardCenarioMobiliaAnual(@PathVariable("avaliacaoProcessoPK") Long avaliacaoProcessoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("processoPK") Long processoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
}