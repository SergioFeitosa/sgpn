package br.com.j4business.saga.avaliacaoresultadocontrato.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoresultadocontrato.model.AvaliacaoResultadoContrato;
import br.com.j4business.saga.avaliacaoresultadocontrato.repository.AvaliacaoResultadoContratoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("avaliacaoResultadoContratoService")
public class AvaliacaoResultadoContratoServiceImpl implements AvaliacaoResultadoContratoService {

	@Autowired
	private AvaliacaoResultadoContratoRepository avaliacaoResultadoContratoRepository;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AvaliacaoResultadoContratoService.class.getName());


	@Override
	public List<AvaliacaoResultadoContrato> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable) {
		return avaliacaoResultadoContratoRepository.findByAvaliacaoNome(avaliacaoNome,pageable);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getByResultadoNome(String resultadoNome,Pageable pageable) {
		return avaliacaoResultadoContratoRepository.findByResultadoNome(resultadoNome,pageable);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getByAvaliacaoNome(String avaliacaoNome) {
		return avaliacaoResultadoContratoRepository.findByAvaliacaoNome(avaliacaoNome);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getByResultadoNome(String resultadoNome) {
		return avaliacaoResultadoContratoRepository.findByResultadoNome(resultadoNome);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getByAvaliacaoContratoPK(long avaliacaoContratoPK) {
		return avaliacaoResultadoContratoRepository.findByAvaliacaoContratoPK(avaliacaoContratoPK);
	}

	@Override
	public AvaliacaoResultadoContrato getByAvaliacaoContratoPKAndElementoPK(long avaliacaoContratoPK, long elementoPK) {
		return avaliacaoResultadoContratoRepository.findByAvaliacaoContratoPKAndElementoPK(avaliacaoContratoPK,elementoPK);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getByResultadoPK(long resultadoPK,Pageable pageable) {
		return avaliacaoResultadoContratoRepository.findByResultadoPK(resultadoPK,pageable);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable) {
		return avaliacaoResultadoContratoRepository.findByAvaliacaoPK(avaliacaoPK,pageable);
	}

	@Override
	public List<AvaliacaoResultadoContrato> getAvaliacaoResultadoContratoAll(Pageable pageable) {
		return avaliacaoResultadoContratoRepository.findAvaliacaoResultadoContratoAll(pageable);
	}

	@Override
	public AvaliacaoResultadoContrato getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(long avaliacaoResultadoContratoPK) {
		Optional<AvaliacaoResultadoContrato> avaliacaoResultadoContrato = avaliacaoResultadoContratoRepository.findById(avaliacaoResultadoContratoPK);

		return avaliacaoResultadoContrato.get();
	}

	@Override
	public AvaliacaoResultadoContrato getByAvaliacaoAndResultado (Avaliacao avaliacao,Resultado resultado) {
		
		return avaliacaoResultadoContratoRepository.findByAvaliacaoAndResultado(avaliacao,resultado);
	}

	@Transactional
	public AvaliacaoResultadoContrato save(AvaliacaoResultadoContrato avaliacaoResultadoContrato) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoResultadoContrato Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoResultadoContrato.getAvaliacaoResultadoContratoPK() + 
										" // Avaliacao Id => "+avaliacaoResultadoContrato.getAvaliacao().getAvaliacaoPK() + 
										" // Resultado Id => "+avaliacaoResultadoContrato.getResultado().getResultadoPK());
		return avaliacaoResultadoContrato;
	}

	@Transactional
	public AvaliacaoResultadoContrato addAvaliacaoQuestionario(AvaliacaoResultadoContrato avaliacaoResultadoContrato) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoResultadoContrato Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoResultadoContrato.getAvaliacaoResultadoContratoPK() + 
										" // Avaliacao Id => "+avaliacaoResultadoContrato.getAvaliacao().getAvaliacaoPK() + 
										" // Resultado Id => "+avaliacaoResultadoContrato.getResultado().getResultadoPK());
		return avaliacaoResultadoContrato;
	}

	@Transactional
	public void delete(Long avaliacaoResultadoContratoPK) {

		AvaliacaoResultadoContrato avaliacaoResultadoContratoTemp = this.getAvaliacaoResultadoContratoByAvaliacaoResultadoContratoPK(avaliacaoResultadoContratoPK);

		avaliacaoResultadoContratoRepository.delete(avaliacaoResultadoContratoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoResultadoContrato Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoResultadoContratoTemp.getAvaliacaoResultadoContratoPK() + 
										" // Avaliacao Id => "+avaliacaoResultadoContratoTemp.getAvaliacao().getAvaliacaoPK() + 
										" // Resultado Id => "+avaliacaoResultadoContratoTemp.getResultado().getResultadoPK()); 
    }

	@Transactional
	public void deleteByResultado(Resultado resultado) {
		
		List<AvaliacaoResultadoContrato> avaliacaoResultadoContratos = avaliacaoResultadoContratoRepository.findByResultado(resultado);

		for (AvaliacaoResultadoContrato avaliacaoResultadoContrato2 : avaliacaoResultadoContratos) {

			avaliacaoResultadoContratoRepository.delete(avaliacaoResultadoContrato2);

		}

		String username = usuarioSeguranca.getUsuarioLogado();

		avaliacaoResultadoContratos.forEach((AvaliacaoResultadoContrato avaliacaoResultadoContrato) -> {
		
			logger.info("AvaliacaoResultadoContrato Delete2 " + "\n Usuário => " + username + 
											" // Id => "+avaliacaoResultadoContrato.getAvaliacaoResultadoContratoPK() + 
											" // Avaliacao Id => "+avaliacaoResultadoContrato.getAvaliacao().getAvaliacaoPK() + 
											" // Resultado Id => "+avaliacaoResultadoContrato.getResultado().getResultadoPK()); 
		});
    }

	
	public AvaliacaoResultadoContrato create(AvaliacaoResultadoContrato avaliacaoResultadoContrato) {
		
		//avaliacaoResultadoContrato = entityManager.merge(avaliacaoResultadoContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Resultado Create " + "\n Usuário => " + username + 
				" // Id => "+avaliacaoResultadoContrato.getAvaliacaoResultadoContratoPK() + 
				" // Avaliacao Id => "+avaliacaoResultadoContrato.getAvaliacao().getAvaliacaoPK() + 
				" // Resultado Id => "+avaliacaoResultadoContrato.getResultado().getResultadoPK()); 
		
		return entityManager.merge(avaliacaoResultadoContrato);
	}


}
