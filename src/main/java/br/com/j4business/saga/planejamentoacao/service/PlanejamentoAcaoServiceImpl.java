package br.com.j4business.saga.planejamentoacao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.acao.service.AcaoService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcao;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.planejamentoacao.model.PlanejamentoAcaoForm;
import br.com.j4business.saga.planejamentoacao.repository.PlanejamentoAcaoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("planejamentoAcaoService")
public class PlanejamentoAcaoServiceImpl implements PlanejamentoAcaoService {

	@Autowired
	private AcaoService acaoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private PlanejamentoAcaoRepository planejamentoAcaoRepository;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(PlanejamentoAcaoService.class.getName());


	@Override
	public List<PlanejamentoAcao> getPlanejamentoAcaoAll(Pageable pageable) {
		return planejamentoAcaoRepository.findPlanejamentoAcaoAll(pageable);
	}

	@Override
	public List<PlanejamentoAcao> getByPlanejamentoPK(long planejamentoPK,Pageable pageable) {
		return planejamentoAcaoRepository.findByPlanejamentoPK(planejamentoPK,pageable);
	}

	@Override
	public List<PlanejamentoAcao> getByAcaoNome(String acaoNome,Pageable pageable) {
		return planejamentoAcaoRepository.findByAcaoNome(acaoNome,pageable);
	}

	@Override
	public List<PlanejamentoAcao> getByPlanejamentoNome(String planejamentoNome,Pageable pageable) {
		return planejamentoAcaoRepository.findByPlanejamentoNome(planejamentoNome,pageable);
	}

	@Override
	public PlanejamentoAcao getPlanejamentoAcaoByPlanejamentoAcaoPK(long planejamentoAcaoPK) {
		return planejamentoAcaoRepository.findOne(planejamentoAcaoPK);
	}

	@Override
	public PlanejamentoAcao getByPlanejamentoAndAcao(Planejamento planejamento,Acao acao) {
		
		return planejamentoAcaoRepository.findByPlanejamentoAndAcao(planejamento,acao);
	}

	@Transactional
	public PlanejamentoAcao create(PlanejamentoAcaoForm planejamentoAcaoForm) {
		
		PlanejamentoAcao planejamentoAcao = new PlanejamentoAcao();
		
		planejamentoAcao = this.convertePlanejamentoAcaoForm(planejamentoAcaoForm);
		
		planejamentoAcao = entityManager.merge(planejamentoAcao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Planejamento Create " + "\n Usuário => " + username + 
				" // Id => "+planejamentoAcao.getPlanejamentoAcaoPK() + 
				" // Planejamento Id => "+planejamentoAcao.getPlanejamento().getPlanejamentoPK() + 
				" // Acao Id => "+planejamentoAcao.getAcao().getAcaoPK() + 
				" // Valor => "+planejamentoAcao.getPlanejamentoAcaoSequencia()); 
		
		return planejamentoAcao;
	}

	@Transactional
	public PlanejamentoAcao save(PlanejamentoAcaoForm planejamentoAcaoForm) {

		PlanejamentoAcao planejamentoAcao = new PlanejamentoAcao();
		
		planejamentoAcao = this.convertePlanejamentoAcaoForm(planejamentoAcaoForm);
		
		planejamentoAcao = entityManager.merge(planejamentoAcao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("PlanejamentoAcao Save " + "\n Usuário => " + username + 
										" // Id => "+planejamentoAcao.getPlanejamentoAcaoPK() + 
										" // Planejamento Id => "+planejamentoAcao.getPlanejamento().getPlanejamentoPK() + 
										" // Acao Id => "+planejamentoAcao.getAcao().getAcaoPK() + 
										" // Valor => "+planejamentoAcao.getPlanejamentoAcaoSequencia()); 
		return planejamentoAcao;
	}

	@Transactional
	public void delete(Long planejamentoAcaoPK) {

		PlanejamentoAcao planejamentoAcaoTemp = this.getPlanejamentoAcaoByPlanejamentoAcaoPK(planejamentoAcaoPK);

		planejamentoAcaoRepository.delete(planejamentoAcaoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("PlanejamentoAcao Save " + "\n Usuário => " + username + 
										" // Id => "+planejamentoAcaoTemp.getPlanejamentoAcaoPK() + 
										" // Planejamento Id => "+planejamentoAcaoTemp.getPlanejamento().getPlanejamentoPK() + 
										" // Acao Id => "+planejamentoAcaoTemp.getAcao().getAcaoPK() + 
										" // Valor => "+planejamentoAcaoTemp.getPlanejamentoAcaoSequencia()); 
    }

	@Transactional
	public void deleteByPlanejamento(Planejamento planejamento) {
		
		List<PlanejamentoAcao> planejamentoAcaoList = planejamentoAcaoRepository.findByPlanejamento(planejamento);

		planejamentoAcaoRepository.delete(planejamentoAcaoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		planejamentoAcaoList.forEach((PlanejamentoAcao planejamentoAcao) -> {
			
			logger.info("PlanejamentoAcao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+planejamentoAcao.getPlanejamentoAcaoPK() + 
											" // Planejamento Id => "+planejamentoAcao.getPlanejamento().getPlanejamentoPK() + 
											" // Acao Id => "+planejamentoAcao.getAcao().getAcaoPK() + 
											" // Valor => "+planejamentoAcao.getPlanejamentoAcaoSequencia()); 
		});
    }

	@Transactional
	public PlanejamentoAcao convertePlanejamentoAcaoForm(PlanejamentoAcaoForm planejamentoAcaoForm) {
		
		PlanejamentoAcao planejamentoAcao = new PlanejamentoAcao();
		
		if (planejamentoAcaoForm.getPlanejamentoAcaoPK() > 0) {
			planejamentoAcao = this.getPlanejamentoAcaoByPlanejamentoAcaoPK(planejamentoAcaoForm.getPlanejamentoAcaoPK());
		}
		
		Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(Long.parseLong(planejamentoAcaoForm.getPlanejamentoNome()));
		planejamentoAcao.setPlanejamento(planejamento);

		Acao acao = acaoService.getAcaoByAcaoPK(Long.parseLong(planejamentoAcaoForm.getAcaoNome()));
		planejamentoAcao.setAcao(acao);

		planejamentoAcao.setPlanejamentoAcaoSequencia(planejamentoAcaoForm.getPlanejamentoAcaoSequencia().replaceAll("\\s+"," "));

		planejamentoAcao.setPlanejamentoAcaoMotivoOperacao(planejamentoAcaoForm.getPlanejamentoAcaoMotivoOperacao().replaceAll("\\s+"," "));
		planejamentoAcao.setPlanejamentoAcaoStatus(planejamentoAcaoForm.getPlanejamentoAcaoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(planejamentoAcaoForm.getPlanejamentoAcaoResponsavel()));
		planejamentoAcao.setColaboradorResponsavel(colaborador);
		
		return planejamentoAcao;
	}

	@Transactional
	public PlanejamentoAcaoForm convertePlanejamentoAcao(PlanejamentoAcao planejamentoAcao) {
	
		PlanejamentoAcaoForm planejamentoAcaoForm = new PlanejamentoAcaoForm();
		
		planejamentoAcaoForm.setPlanejamentoAcaoPK(planejamentoAcao.getPlanejamentoAcaoPK());
		planejamentoAcaoForm.setPlanejamentoNome(planejamentoAcao.getPlanejamento().getPlanejamentoPK()+"");
		planejamentoAcaoForm.setAcaoNome(planejamentoAcao.getAcao().getAcaoPK()+"");
		planejamentoAcaoForm.setPlanejamentoAcaoSequencia(planejamentoAcao.getPlanejamentoAcaoSequencia());

		planejamentoAcaoForm.setPlanejamentoAcaoMotivoOperacao(planejamentoAcao.getPlanejamentoAcaoMotivoOperacao());
		planejamentoAcaoForm.setPlanejamentoAcaoStatus(AtributoStatus.valueOf(planejamentoAcao.getPlanejamentoAcaoStatus()));
		
		planejamentoAcaoForm.setPlanejamentoAcaoResponsavel(planejamentoAcao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return planejamentoAcaoForm;
	}
	
	@Transactional
	public PlanejamentoAcaoForm convertePlanejamentoAcaoView(PlanejamentoAcao planejamentoAcao) {
	
		PlanejamentoAcaoForm planejamentoAcaoForm = new PlanejamentoAcaoForm();
		
		planejamentoAcaoForm.setPlanejamentoAcaoPK(planejamentoAcao.getPlanejamentoAcaoPK());
		planejamentoAcaoForm.setPlanejamentoNome(planejamentoAcao.getPlanejamento().getPlanejamentoNome());
		planejamentoAcaoForm.setAcaoNome(planejamentoAcao.getAcao().getAcaoNome());
		planejamentoAcaoForm.setPlanejamentoAcaoSequencia(planejamentoAcao.getPlanejamentoAcaoSequencia());

		planejamentoAcaoForm.setPlanejamentoAcaoMotivoOperacao(planejamentoAcao.getPlanejamentoAcaoMotivoOperacao());
		planejamentoAcaoForm.setPlanejamentoAcaoStatus(AtributoStatus.valueOf(planejamentoAcao.getPlanejamentoAcaoStatus()));
		
		planejamentoAcaoForm.setPlanejamentoAcaoMotivoOperacao(planejamentoAcao.getPlanejamentoAcaoMotivoOperacao());
		planejamentoAcaoForm.setPlanejamentoAcaoStatus(AtributoStatus.valueOf(planejamentoAcao.getPlanejamentoAcaoStatus()));
		planejamentoAcaoForm.setPlanejamentoAcaoResponsavel(planejamentoAcao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return planejamentoAcaoForm;
	}
	

	@Transactional
	public PlanejamentoAcaoForm planejamentoAcaoParametros(PlanejamentoAcaoForm planejamentoAcaoForm) {
	
		
		planejamentoAcaoForm.setPlanejamentoAcaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return planejamentoAcaoForm;
	}
	

}
