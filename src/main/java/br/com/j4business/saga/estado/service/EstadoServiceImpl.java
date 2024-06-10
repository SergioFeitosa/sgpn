package br.com.j4business.saga.estado.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.estado.model.Estado;
import br.com.j4business.saga.estado.model.EstadoForm;
import br.com.j4business.saga.estado.repository.EstadoRepository;

@Service("estadoService")
public class EstadoServiceImpl implements EstadoService {

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EstadoService.class.getName());

	@Override
	public List<Estado> getEstadoAll() {
		return estadoRepository.findAll();
	}

	@Override
	public Page<Estado> getEstadoAll(Pageable pageable) {
		return estadoRepository.findAll(pageable);
	}

	@Override
	public Estado getEstadoByEstadoPK(long estadoPK) {
		
		Optional<Estado> estado = estadoRepository.findById(estadoPK);
		return estado.get();
	}

	@Transactional
	public Estado create(EstadoForm estadoForm) {
		
		Estado estado = new Estado();
		
		estado = this.converteEstadoForm(estadoForm);
		
		estado = entityManager.merge(estado);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Estado Create " + "\n Usuário => " + username + 
										" // Id => "+estado.getEstadoPK() + 
										" // Estado => "+estado.getEstadoNome() + 
										" // Descrição => "+ estado.getEstadoSigla());
		
		return estado;
	}

	@Transactional
	public Estado save(EstadoForm estadoForm) {
		
		Estado estado = new Estado();
		
		estado = this.converteEstadoForm(estadoForm);
		
		estado = entityManager.merge(estado);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Estado Save " + "\n Usuário => " + username + 
										" // Id => "+estado.getEstadoPK() + 
										" // Estado => "+estado.getEstadoNome() + 
										" // Descrição => "+ estado.getEstadoSigla());
		

		return estado;
		
	}

	@Transactional
	public void delete(Long estadoId) {

		Estado estado = this.getEstadoByEstadoPK(estadoId);
		
		estadoRepository.delete(estado);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Estado Delete " + "\n Usuário => " + username + 
										" // Id => "+estado.getEstadoPK() + 
										" // Estado => "+estado.getEstadoNome() + 
										" // Descrição => "+ estado.getEstadoSigla());
		

    }

	@Transactional
	public Estado converteEstadoForm(EstadoForm estadoForm) {
		
		Estado estado = new Estado();
		
		if (estadoForm.getEstadoPK() > 0) {
			estado = this.getEstadoByEstadoPK(estadoForm.getEstadoPK());
		}
		estado.setEstadoNome(estadoForm.getEstadoNome().replaceAll("\\s+"," "));
		estado.setEstadoSigla(estadoForm.getEstadoSigla().replaceAll("\\s+"," "));

		return estado;
	}

	@Transactional
	public EstadoForm converteEstado(Estado estado) {
	
		EstadoForm estadoForm = new EstadoForm();
		
		estadoForm.setEstadoPK(estado.getEstadoPK());
		estadoForm.setEstadoNome(estado.getEstadoNome());
		estadoForm.setEstadoSigla(estado.getEstadoSigla());

	return estadoForm;
	}
	
	@Transactional
	public EstadoForm converteEstadoView(Estado estado) {
	
		EstadoForm estadoForm = new EstadoForm();
		
		estadoForm.setEstadoPK(estado.getEstadoPK());
		estadoForm.setEstadoNome(estado.getEstadoNome());
		estadoForm.setEstadoSigla(estado.getEstadoSigla());

	return estadoForm;
	}
	

	@Transactional
	public EstadoForm estadoParametros(EstadoForm estadoForm) {
	
		
		estadoForm.setEstadoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return estadoForm;
	}

	@Override
	public List<Estado> getByEstadoNome(String estadoNome,Pageable pageable) {
		return estadoRepository.findByEstadoNome(estadoNome,pageable);
	}

	@Override
	public List<Estado> getByEstadoSigla(String estadoSigla,Pageable pageable) {
		return estadoRepository.findByEstadoSigla(estadoSigla,pageable);
	}

	@Override
	public List<Estado> getByEstadoNome(String estadoNome) {
		return estadoRepository.findByEstadoNome(estadoNome);
	}

	@Override
	public List<Estado> getByEstadoSigla(String estadoSigla) {
		return estadoRepository.findByEstadoSigla(estadoSigla);
	}



}
