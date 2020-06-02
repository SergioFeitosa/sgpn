package br.com.j4business.saga.avaliacaoresultado.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacaoresultado.model.AvaliacaoResultado;
import br.com.j4business.saga.avaliacaoresultado.repository.AvaliacaoResultadoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("avaliacaoResultadoService")
public class AvaliacaoResultadoServiceImpl implements AvaliacaoResultadoService {

	@Autowired
	private AvaliacaoResultadoRepository avaliacaoResultadoRepository;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AvaliacaoResultadoService.class.getName());


	@Override
	public List<AvaliacaoResultado> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable) {
		return avaliacaoResultadoRepository.findByAvaliacaoNome(avaliacaoNome,pageable);
	}

	@Override
	public List<AvaliacaoResultado> getByResultadoNome(String resultadoNome,Pageable pageable) {
		return avaliacaoResultadoRepository.findByResultadoNome(resultadoNome,pageable);
	}

	@Override
	public List<AvaliacaoResultado> getByAvaliacaoNome(String avaliacaoNome) {
		return avaliacaoResultadoRepository.findByAvaliacaoNome(avaliacaoNome);
	}

	@Override
	public List<AvaliacaoResultado> getByResultadoNome(String resultadoNome) {
		return avaliacaoResultadoRepository.findByResultadoNome(resultadoNome);
	}

	@Override
	public List<AvaliacaoResultado> getByAvaliacaoProcessoPK(long avaliacaoProcessoPK) {
		return avaliacaoResultadoRepository.findByAvaliacaoProcessoPK(avaliacaoProcessoPK);
	}

	@Override
	public AvaliacaoResultado getByAvaliacaoProcessoPKAndElementoPK(long avaliacaoProcessoPK, long elementoPK) {
		return avaliacaoResultadoRepository.findByAvaliacaoProcessoPKAndElementoPK(avaliacaoProcessoPK,elementoPK);
	}

	@Override
	public List<AvaliacaoResultado> getByResultadoPK(long resultadoPK,Pageable pageable) {
		return avaliacaoResultadoRepository.findByResultadoPK(resultadoPK,pageable);
	}

	@Override
	public List<AvaliacaoResultado> getByAvaliacaoPK(long avaliacaoPK,Pageable pageable) {
		return avaliacaoResultadoRepository.findByAvaliacaoPK(avaliacaoPK,pageable);
	}

	@Override
	public List<AvaliacaoResultado> getAvaliacaoResultadoAll(Pageable pageable) {
		return avaliacaoResultadoRepository.findAvaliacaoResultadoAll(pageable);
	}

	@Override
	public AvaliacaoResultado getAvaliacaoResultadoByAvaliacaoResultadoPK(long avaliacaoResultadoPK) {
		return avaliacaoResultadoRepository.findOne(avaliacaoResultadoPK);
	}

	@Override
	public AvaliacaoResultado getByAvaliacaoAndResultado (Avaliacao avaliacao,Resultado resultado) {
		
		return avaliacaoResultadoRepository.findByAvaliacaoAndResultado(avaliacao,resultado);
	}

	@Transactional
	public AvaliacaoResultado save(AvaliacaoResultado avaliacaoResultado) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoResultado Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoResultado.getAvaliacaoResultadoPK() + 
										" // Avaliacao Id => "+avaliacaoResultado.getAvaliacao().getAvaliacaoPK() + 
										" // Resultado Id => "+avaliacaoResultado.getResultado().getResultadoPK());
		return avaliacaoResultado;
	}

	@Transactional
	public AvaliacaoResultado addAvaliacaoQuestionario(AvaliacaoResultado avaliacaoResultado) {

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoResultado Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoResultado.getAvaliacaoResultadoPK() + 
										" // Avaliacao Id => "+avaliacaoResultado.getAvaliacao().getAvaliacaoPK() + 
										" // Resultado Id => "+avaliacaoResultado.getResultado().getResultadoPK());
		return avaliacaoResultado;
	}

	@Transactional
	public void delete(Long avaliacaoResultadoPK) {

		AvaliacaoResultado avaliacaoResultadoTemp = this.getAvaliacaoResultadoByAvaliacaoResultadoPK(avaliacaoResultadoPK);

		avaliacaoResultadoRepository.delete(avaliacaoResultadoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AvaliacaoResultado Save " + "\n Usuário => " + username + 
										" // Id => "+avaliacaoResultadoTemp.getAvaliacaoResultadoPK() + 
										" // Avaliacao Id => "+avaliacaoResultadoTemp.getAvaliacao().getAvaliacaoPK() + 
										" // Resultado Id => "+avaliacaoResultadoTemp.getResultado().getResultadoPK()); 
    }

	@Transactional
	public void deleteByResultado(Resultado resultado) {
		
		List<AvaliacaoResultado> avaliacaoResultados = avaliacaoResultadoRepository.findByResultado(resultado);

		avaliacaoResultadoRepository.delete(avaliacaoResultados);

		String username = usuarioSeguranca.getUsuarioLogado();

		avaliacaoResultados.forEach((AvaliacaoResultado avaliacaoResultado) -> {

			logger.info("AvaliacaoResultado Delete2 " + "\n Usuário => " + username + 
											" // Id => "+avaliacaoResultado.getAvaliacaoResultadoPK() + 
											" // Avaliacao Id => "+avaliacaoResultado.getAvaliacao().getAvaliacaoPK() + 
											" // Resultado Id => "+avaliacaoResultado.getResultado().getResultadoPK()); 

		});
		
    }

	
	public AvaliacaoResultado create(AvaliacaoResultado avaliacaoResultado) {
		
		//avaliacaoResultado = entityManager.merge(avaliacaoResultado);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Resultado Create " + "\n Usuário => " + username + 
				" // Id => "+avaliacaoResultado.getAvaliacaoResultadoPK() + 
				" // Avaliacao Id => "+avaliacaoResultado.getAvaliacao().getAvaliacaoPK() + 
				" // Resultado Id => "+avaliacaoResultado.getResultado().getResultadoPK()); 
		
		return entityManager.merge(avaliacaoResultado);
	}


}
