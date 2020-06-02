package br.com.j4business.saga.avaliacaocontrato.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContrato;
import br.com.j4business.saga.avaliacaocontrato.model.AvaliacaoContratoForm;

@Service
public interface AvaliacaoContratoService {
	
	public List<AvaliacaoContrato> getAvaliacaoContratoAll(Pageable pageable);
	public AvaliacaoContrato getAvaliacaoContratoByAvaliacaoContratoPK(long avaliacaoContratoPK);
	public AvaliacaoContrato save(AvaliacaoContrato avaliacaoContrato);
	public void delete(Long avaliacaoContratoPK);
	public void deleteByContrato(Contrato contrato);
	public AvaliacaoContrato create(AvaliacaoContratoForm avaliacaoContratoForm);
	public AvaliacaoContrato getByAvaliacaoAndContrato(Avaliacao avaliacao, Contrato contrato);

	public AvaliacaoContrato converteAvaliacaoContratoForm(AvaliacaoContratoForm avaliacaoContratoForm);
	public void saveAvaliacaoContratoQuestionario(AvaliacaoContratoForm avaliacaoContratoForm);
	public AvaliacaoContratoForm converteAvaliacaoContrato(AvaliacaoContrato avaliacaoContrato);
	public AvaliacaoContratoForm converteAvaliacaoContratoView(AvaliacaoContrato avaliacaoContrato);

	public AvaliacaoContratoForm avaliacaoContratoParametros(AvaliacaoContratoForm avaliacaoContratoForm);

	public List<AvaliacaoContrato> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable);
	public List<AvaliacaoContrato> getByContratoPK(long contratoPK,Pageable pageable);

	public List<AvaliacaoContrato> getByContratoNome(String contratoNome,Pageable pageable);
	public List<AvaliacaoContrato> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable);
	public List<AvaliacaoContrato> getByContratoNome(String contratoNome);
	public List<AvaliacaoContrato> getByAvaliacaoNome(String avaliacaoNome);
	
	public AvaliacaoContrato addAvaliacaoQuestionario(AvaliacaoContrato avaliacaoContrato);

	public List<AvaliacaoContrato> getByContratoAndQuestionario(Contrato contrato, Questionario questionario);

	public ModelAndView avaliacaoContratoDashboardCenario(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
			  @PathVariable("questionarioPK") Long questionarioPK,
			  @PathVariable("contratoPK") Long contratoPK,
			  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
			  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	
	public AvaliacaoContrato getByPeriodo(Avaliacao avaliacao, Contrato contrato,Questionario questionario, String periodo);
	public ModelAndView avaliacaoContratoDashboardCenarioAnual(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
	  @PathVariable("questionarioPK") Long questionarioPK,
	  @PathVariable("contratoPK") Long contratoPK,
	  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
	  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoContratoDashboardCenarioBimestral(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
	  @PathVariable("questionarioPK") Long questionarioPK,
	  @PathVariable("contratoPK") Long contratoPK,
	  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
	  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoContratoDashboardCenarioMensal(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
	  @PathVariable("questionarioPK") Long questionarioPK,
	  @PathVariable("contratoPK") Long contratoPK,
	  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
	  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoContratoDashboardCenarioSemestral(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
	  @PathVariable("questionarioPK") Long questionarioPK,
	  @PathVariable("contratoPK") Long contratoPK,
	  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
	  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public ModelAndView avaliacaoContratoDashboardCenarioTrimestral(@PathVariable("avaliacaoContratoPK") Long avaliacaoContratoPK,
	  @PathVariable("questionarioPK") Long questionarioPK,
	  @PathVariable("contratoPK") Long contratoPK,
	  @PathVariable("estruturafisicaPK") Long estruturafisicaPK,
	  @PathVariable("unidadeorganizacionalPK") Long unidadeorganizacionalPK);
	public List<AvaliacaoContrato> getByPeriodoNome(String periodoNome);
	public List<AvaliacaoContrato> getByPeriodoNome(String periodoNome,Pageable pageable);
	public List<AvaliacaoContrato> getByQuestionarioNome(String questionarioNome);
	public List<AvaliacaoContrato> getByQuestionarioNome(String questionarioNome,Pageable pageable);
}