package br.com.j4business.saga.contratotexto.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.contratotexto.model.ContratoTexto;
import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.texto.service.TextoService;
import br.com.j4business.saga.contratotexto.model.ContratoTextoForm;
import br.com.j4business.saga.contratotexto.repository.ContratoTextoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("contratoTextoService")
public class ContratoTextoServiceImpl implements ContratoTextoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ContratoTextoRepository contratoTextoRepository;

	@Autowired
	private TextoService textoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ContratoTextoService.class.getName());


	@Override
	public List<ContratoTexto> getByContratoNome(String contratoNome,Pageable pageable) {
		return contratoTextoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<ContratoTexto> getByTextoNome(String textoNome,Pageable pageable) {
		return contratoTextoRepository.findByTextoNome(textoNome,pageable);
	}

	@Override
	public List<ContratoTexto> getByContratoNome(String contratoNome) {
		return contratoTextoRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<ContratoTexto> getByTextoNome(String textoNome) {
		return contratoTextoRepository.findByTextoNome(textoNome);
	}

	@Override
	public List<ContratoTexto> getByTextoPK(long textoPK,Pageable pageable) {
		return contratoTextoRepository.findByTextoPK(textoPK,pageable);
	}

	@Override
	public List<ContratoTexto> getByContratoPK(long contratoPK,Pageable pageable) {
		return contratoTextoRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<ContratoTexto> getByContratoPK(long contratoPK) {
		return contratoTextoRepository.findByContratoPK(contratoPK);
	}

	@Override
	public List<ContratoTexto> getContratoTextoAtivoByContratoPK(long contratoPK) {
		return contratoTextoRepository.findContratoTextoAtivoByContratoPK(contratoPK);
	}

	@Override
	public List<ContratoTexto> getContratoTextoAll(Pageable pageable) {
		return contratoTextoRepository.findContratoTextoAll(pageable);
	}

	@Override
	public ContratoTexto getContratoTextoByContratoTextoPK(long contratoTextoPK) {
		Optional<ContratoTexto> contratoTexto = contratoTextoRepository.findById(contratoTextoPK);
		return contratoTexto.get();
	}

	@Override
	public ContratoTexto getByContratoAndTexto (Contrato contrato,Texto texto) {
		
		return contratoTextoRepository.findByContratoAndTexto(contrato,texto);
	}

	@Transactional
	public ContratoTexto create(ContratoTextoForm contratoTextoForm) {
		
		ContratoTexto contratoTexto = new ContratoTexto();
		
		contratoTexto = this.converteContratoTextoForm(contratoTextoForm);
		
		contratoTexto = entityManager.merge(contratoTexto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Texto Create " + "\n Usuário => " + username + 
				" // Id => "+contratoTexto.getContratoTextoPK() + 
				" // Contrato Id => "+contratoTexto.getContrato().getContratoPK() + 
				" // Texto Id => "+contratoTexto.getTexto().getTextoPK()); 
		
		return contratoTexto;
	}

	@Transactional
	public ContratoTexto save(ContratoTextoForm contratoTextoForm) {

		ContratoTexto contratoTexto = new ContratoTexto();
		
		contratoTexto = this.converteContratoTextoForm(contratoTextoForm);
		
		contratoTexto = entityManager.merge(contratoTexto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ContratoTexto Save " + "\n Usuário => " + username + 
										" // Id => "+contratoTexto.getContratoTextoPK() + 
										" // Contrato Id => "+contratoTexto.getContrato().getContratoPK() + 
										" // Texto Id => "+contratoTexto.getTexto().getTextoPK());
		return contratoTexto;
	}

	@Transactional
	public void delete(Long contratoTextoPK) {

		ContratoTexto contratoTextoTemp = this.getContratoTextoByContratoTextoPK(contratoTextoPK);

		contratoTextoRepository.delete(contratoTextoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ContratoTexto Save " + "\n Usuário => " + username + 
										" // Id => "+contratoTextoTemp.getContratoTextoPK() + 
										" // Contrato Id => "+contratoTextoTemp.getContrato().getContratoPK() + 
										" // Texto Id => "+contratoTextoTemp.getTexto().getTextoPK()); 
    }

	@Transactional
	public void deleteByTexto(Texto texto) {
		
		List<ContratoTexto> contratoTextoList = contratoTextoRepository.findByTexto(texto);

		for (ContratoTexto contratoTexto2 : contratoTextoList) {
			contratoTextoRepository.delete(contratoTexto2);			
		}


		String username = usuarioSeguranca.getUsuarioLogado();

		contratoTextoList.forEach((ContratoTexto contratoTexto) -> {
			
			logger.info("ContratoTexto Delete2 " + "\n Usuário => " + username + 
											" // Id => "+contratoTexto.getContratoTextoPK() + 
											" // Contrato Id => "+contratoTexto.getContrato().getContratoPK() + 
											" // Texto Id => "+contratoTexto.getTexto().getTextoPK()); 

		});
		
    }

	@Transactional
	public ContratoTexto converteContratoTextoForm(ContratoTextoForm contratoTextoForm) {
		
		ContratoTexto contratoTexto = new ContratoTexto();
		
		if (contratoTextoForm.getContratoTextoPK() > 0) {
			contratoTexto = this.getContratoTextoByContratoTextoPK(contratoTextoForm.getContratoTextoPK());
		}
		
		
		Texto texto = textoService.getTextoByTextoPK(Long.parseLong(contratoTextoForm.getTextoNome()));
		contratoTexto.setTexto(texto);

		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(contratoTextoForm.getContratoNome()));
		contratoTexto.setContrato(contrato);

		contratoTexto.setContratoTextoMotivoOperacao(contratoTextoForm.getContratoTextoMotivoOperacao().replaceAll("\\s+"," "));
		contratoTexto.setContratoTextoStatus(contratoTextoForm.getContratoTextoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(contratoTextoForm.getContratoTextoResponsavel()));
		contratoTexto.setColaboradorResponsavel(colaborador);
		
		
		return contratoTexto;
	}

	@Transactional
	public ContratoTextoForm converteContratoTexto(ContratoTexto contratoTexto) {
	
		ContratoTextoForm contratoTextoForm = new ContratoTextoForm();
		
		contratoTextoForm.setContratoTextoPK(contratoTexto.getContratoTextoPK());
		contratoTextoForm.setContratoPK(contratoTexto.getContrato().getContratoPK());
		contratoTextoForm.setTextoPK(contratoTexto.getTexto().getTextoPK());
		contratoTextoForm.setContratoNome(contratoTexto.getContrato().getContratoPK()+"");
		contratoTextoForm.setTextoNome(contratoTexto.getTexto().getTextoPK()+"");

		contratoTextoForm.setContratoTextoMotivoOperacao(contratoTexto.getContratoTextoMotivoOperacao());
		contratoTextoForm.setContratoTextoStatus(AtributoStatus.valueOf(contratoTexto.getContratoTextoStatus()));
		contratoTextoForm.setContratoTextoResponsavel(contratoTexto.getContrato().getContratoPK()+"");
		
	return contratoTextoForm;
	}
	
	@Transactional
	public ContratoTextoForm converteContratoTextoView(ContratoTexto contratoTexto) {
	
		ContratoTextoForm contratoTextoForm = new ContratoTextoForm();
		
		contratoTextoForm.setContratoTextoPK(contratoTexto.getContratoTextoPK());
		contratoTextoForm.setContratoPK(contratoTexto.getContrato().getContratoPK());
		contratoTextoForm.setTextoPK(contratoTexto.getTexto().getTextoPK());
		contratoTextoForm.setContratoNome(contratoTexto.getContrato().getContratoNome());
		contratoTextoForm.setTextoNome(contratoTexto.getTexto().getTextoNome());

		contratoTextoForm.setContratoTextoMotivoOperacao(contratoTexto.getContratoTextoMotivoOperacao());
		contratoTextoForm.setContratoTextoStatus(AtributoStatus.valueOf(contratoTexto.getContratoTextoStatus()));
		contratoTextoForm.setContratoTextoResponsavel(contratoTexto.getContrato().getContratoNome());
		
	return contratoTextoForm;
	}
	

	@Transactional
	public ContratoTextoForm contratoTextoParametros(ContratoTextoForm contratoTextoForm) {
		contratoTextoForm.setContratoTextoStatus(AtributoStatus.valueOf("ATIVO"));
	return contratoTextoForm;
	}
	

}
