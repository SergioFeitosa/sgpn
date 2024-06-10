package br.com.j4business.saga.treinamentotexto.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.texto.service.TextoService;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTexto;
import br.com.j4business.saga.treinamentotexto.model.TreinamentoTextoForm;
import br.com.j4business.saga.treinamentotexto.repository.TreinamentoTextoRepository;

@Service("treinamentoTextoService")
public class TreinamentoTextoServiceImpl implements TreinamentoTextoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private TreinamentoTextoRepository treinamentoTextoRepository;

	@Autowired
	private TextoService textoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(TreinamentoTextoService.class.getName());


	@Override
	public List<TreinamentoTexto> getByTreinamentoNome(String treinamentoNome,Pageable pageable) {
		return treinamentoTextoRepository.findByTreinamentoNome(treinamentoNome,pageable);
	}

	@Override
	public List<TreinamentoTexto> getByTextoNome(String textoNome,Pageable pageable) {
		return treinamentoTextoRepository.findByTextoNome(textoNome,pageable);
	}

	@Override
	public List<TreinamentoTexto> getByTreinamentoNome(String treinamentoNome) {
		return treinamentoTextoRepository.findByTreinamentoNome(treinamentoNome);
	}

	@Override
	public List<TreinamentoTexto> getByTextoNome(String textoNome) {
		return treinamentoTextoRepository.findByTextoNome(textoNome);
	}

	@Override
	public List<TreinamentoTexto> getByTextoPK(long textoPK,Pageable pageable) {
		return treinamentoTextoRepository.findByTextoPK(textoPK,pageable);
	}

	@Override
	public List<TreinamentoTexto> getByTreinamentoPK(long treinamentoPK,Pageable pageable) {
		return treinamentoTextoRepository.findByTreinamentoPK(treinamentoPK,pageable);
	}

	@Override
	public List<TreinamentoTexto> getByTreinamentoPK(long treinamentoPK) {
		return treinamentoTextoRepository.findByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<TreinamentoTexto> getTreinamentoTextoAtivoByTreinamentoPK(long treinamentoPK) {
		return treinamentoTextoRepository.findTreinamentoTextoAtivoByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<TreinamentoTexto> getTreinamentoTextoAll(Pageable pageable) {
		return treinamentoTextoRepository.findTreinamentoTextoAll(pageable);
	}

	@Override
	public TreinamentoTexto getTreinamentoTextoByTreinamentoTextoPK(long treinamentoTextoPK) {
		Optional<TreinamentoTexto> treinamentoTexto = treinamentoTextoRepository.findById(treinamentoTextoPK);
		return treinamentoTexto.get();
	}

	@Override
	public TreinamentoTexto getByTreinamentoAndTexto (Treinamento treinamento,Texto texto) {
		
		return treinamentoTextoRepository.findByTreinamentoAndTexto(treinamento,texto);
	}

	@Transactional
	public TreinamentoTexto create(TreinamentoTextoForm treinamentoTextoForm) {
		
		TreinamentoTexto treinamentoTexto = new TreinamentoTexto();
		
		treinamentoTexto = this.converteTreinamentoTextoForm(treinamentoTextoForm);
		
		treinamentoTexto = entityManager.merge(treinamentoTexto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Texto Create " + "\n Usuário => " + username + 
				" // Id => "+treinamentoTexto.getTreinamentoTextoPK() + 
				" // Treinamento Id => "+treinamentoTexto.getTreinamento().getTreinamentoPK() + 
				" // Texto Id => "+treinamentoTexto.getTexto().getTextoPK()); 
		
		return treinamentoTexto;
	}

	@Transactional
	public TreinamentoTexto save(TreinamentoTextoForm treinamentoTextoForm) {

		TreinamentoTexto treinamentoTexto = new TreinamentoTexto();
		
		treinamentoTexto = this.converteTreinamentoTextoForm(treinamentoTextoForm);
		
		treinamentoTexto = entityManager.merge(treinamentoTexto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("TreinamentoTexto Save " + "\n Usuário => " + username + 
										" // Id => "+treinamentoTexto.getTreinamentoTextoPK() + 
										" // Treinamento Id => "+treinamentoTexto.getTreinamento().getTreinamentoPK() + 
										" // Texto Id => "+treinamentoTexto.getTexto().getTextoPK());
		return treinamentoTexto;
	}

	@Transactional
	public void delete(Long treinamentoTextoPK) {

		TreinamentoTexto treinamentoTextoTemp = this.getTreinamentoTextoByTreinamentoTextoPK(treinamentoTextoPK);

		treinamentoTextoRepository.delete(treinamentoTextoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("TreinamentoTexto Save " + "\n Usuário => " + username + 
										" // Id => "+treinamentoTextoTemp.getTreinamentoTextoPK() + 
										" // Treinamento Id => "+treinamentoTextoTemp.getTreinamento().getTreinamentoPK() + 
										" // Texto Id => "+treinamentoTextoTemp.getTexto().getTextoPK()); 
    }

	@Transactional
	public void deleteByTexto(Texto texto) {
		
		List<TreinamentoTexto> treinamentoTextoList = treinamentoTextoRepository.findByTexto(texto);
		for (TreinamentoTexto treinamentoTexto2 : treinamentoTextoList) {
			treinamentoTextoRepository.delete(treinamentoTexto2);	
		}
		

		String username = usuarioSeguranca.getUsuarioLogado();

		treinamentoTextoList.forEach((TreinamentoTexto treinamentoTexto) -> {

			logger.info("TreinamentoTexto Delete2 " + "\n Usuário => " + username + 
											" // Id => "+treinamentoTexto.getTreinamentoTextoPK() + 
											" // Treinamento Id => "+treinamentoTexto.getTreinamento().getTreinamentoPK() + 
											" // Texto Id => "+treinamentoTexto.getTexto().getTextoPK()); 

		});
		
    }

	@Transactional
	public TreinamentoTexto converteTreinamentoTextoForm(TreinamentoTextoForm treinamentoTextoForm) {
		
		TreinamentoTexto treinamentoTexto = new TreinamentoTexto();
		
		if (treinamentoTextoForm.getTreinamentoTextoPK() > 0) {
			treinamentoTexto = this.getTreinamentoTextoByTreinamentoTextoPK(treinamentoTextoForm.getTreinamentoTextoPK());
		}
		
		
		Texto texto = textoService.getTextoByTextoPK(Long.parseLong(treinamentoTextoForm.getTextoNome()));
		treinamentoTexto.setTexto(texto);

		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(Long.parseLong(treinamentoTextoForm.getTreinamentoNome()));
		treinamentoTexto.setTreinamento(treinamento);

		treinamentoTexto.setTreinamentoTextoMotivoOperacao(treinamentoTextoForm.getTreinamentoTextoMotivoOperacao().replaceAll("\\s+"," "));
		treinamentoTexto.setTreinamentoTextoStatus(treinamentoTextoForm.getTreinamentoTextoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(treinamentoTextoForm.getTreinamentoTextoResponsavel()));
		treinamentoTexto.setColaboradorResponsavel(colaborador);
		
		
		return treinamentoTexto;
	}

	@Transactional
	public TreinamentoTextoForm converteTreinamentoTexto(TreinamentoTexto treinamentoTexto) {
	
		TreinamentoTextoForm treinamentoTextoForm = new TreinamentoTextoForm();
		
		treinamentoTextoForm.setTreinamentoTextoPK(treinamentoTexto.getTreinamentoTextoPK());
		treinamentoTextoForm.setTreinamentoPK(treinamentoTexto.getTreinamento().getTreinamentoPK());
		treinamentoTextoForm.setTextoPK(treinamentoTexto.getTexto().getTextoPK());
		treinamentoTextoForm.setTreinamentoNome(treinamentoTexto.getTreinamento().getTreinamentoPK()+"");
		treinamentoTextoForm.setTextoNome(treinamentoTexto.getTexto().getTextoPK()+"");

		treinamentoTextoForm.setTreinamentoTextoMotivoOperacao(treinamentoTexto.getTreinamentoTextoMotivoOperacao());
		treinamentoTextoForm.setTreinamentoTextoStatus(AtributoStatus.valueOf(treinamentoTexto.getTreinamentoTextoStatus()));
		treinamentoTextoForm.setTreinamentoTextoResponsavel(treinamentoTexto.getTreinamento().getTreinamentoPK()+"");
		
	return treinamentoTextoForm;
	}
	
	@Transactional
	public TreinamentoTextoForm converteTreinamentoTextoView(TreinamentoTexto treinamentoTexto) {
	
		TreinamentoTextoForm treinamentoTextoForm = new TreinamentoTextoForm();
		
		treinamentoTextoForm.setTreinamentoTextoPK(treinamentoTexto.getTreinamentoTextoPK());
		treinamentoTextoForm.setTreinamentoPK(treinamentoTexto.getTreinamento().getTreinamentoPK());
		treinamentoTextoForm.setTextoPK(treinamentoTexto.getTexto().getTextoPK());
		treinamentoTextoForm.setTreinamentoNome(treinamentoTexto.getTreinamento().getTreinamentoNome());
		treinamentoTextoForm.setTextoNome(treinamentoTexto.getTexto().getTextoNome());

		treinamentoTextoForm.setTreinamentoTextoMotivoOperacao(treinamentoTexto.getTreinamentoTextoMotivoOperacao());
		treinamentoTextoForm.setTreinamentoTextoStatus(AtributoStatus.valueOf(treinamentoTexto.getTreinamentoTextoStatus()));
		treinamentoTextoForm.setTreinamentoTextoResponsavel(treinamentoTexto.getTreinamento().getTreinamentoNome());
		
	return treinamentoTextoForm;
	}
	

	@Transactional
	public TreinamentoTextoForm treinamentoTextoParametros(TreinamentoTextoForm treinamentoTextoForm) {
		treinamentoTextoForm.setTreinamentoTextoStatus(AtributoStatus.valueOf("ATIVO"));
	return treinamentoTextoForm;
	}
	

}
