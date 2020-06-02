package br.com.j4business.saga.cenarioelemento.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.cenarioelemento.model.CenarioElemento;
import br.com.j4business.saga.cenarioelemento.model.CenarioElementoForm;
import br.com.j4business.saga.cenarioelemento.repository.CenarioElementoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("cenarioElementoService")
public class CenarioElementoServiceImpl implements CenarioElementoService {

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private CenarioElementoRepository cenarioElementoRepository;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(CenarioElementoService.class.getName());


	@Override
	public List<CenarioElemento> getByCenarioNome(String cenarioNome,Pageable pageable) {
		return cenarioElementoRepository.findByCenarioNome(cenarioNome,pageable);
	}

	@Override
	public List<CenarioElemento> getByElementoNome(String elementoNome,Pageable pageable) {
		return cenarioElementoRepository.findByElementoNome(elementoNome,pageable);
	}

	@Override
	public List<CenarioElemento> getByCenarioNome(String cenarioNome) {
		return cenarioElementoRepository.findByCenarioNome(cenarioNome);
	}

	@Override
	public List<CenarioElemento> getByElementoNome(String elementoNome) {
		return cenarioElementoRepository.findByElementoNome(elementoNome);
	}

	@Override
	public List<CenarioElemento> getByElementoPK(long elementoPK,Pageable pageable) {
		return cenarioElementoRepository.findByElementoPK(elementoPK,pageable);
	}

	@Override
	public List<CenarioElemento> getByCenarioPK(long cenarioPK,Pageable pageable) {
		return cenarioElementoRepository.findByCenarioPK(cenarioPK,pageable);
	}

	@Override
	public List<CenarioElemento> getCenarioElementoAll(Pageable pageable) {
		return cenarioElementoRepository.findCenarioElementoAll(pageable);
	}

	@Override
	public CenarioElemento getCenarioElementoByCenarioElementoPK(long cenarioElementoPK) {
		return cenarioElementoRepository.findOne(cenarioElementoPK);
	}

	@Override
	public CenarioElemento getByCenarioAndElemento (Cenario cenario,Elemento elemento) {
		
		return cenarioElementoRepository.findByCenarioAndElemento(cenario,elemento);
	}

	@Transactional
	public CenarioElemento save(CenarioElementoForm cenarioElementoForm) {

		CenarioElemento cenarioElemento = new CenarioElemento();
		
		cenarioElemento = this.converteCenarioElementoForm(cenarioElementoForm);
		
		cenarioElemento = entityManager.merge(cenarioElemento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("CenarioElemento Save " + "\n Usuário => " + username + 
										" // Id => "+cenarioElemento.getCenarioElementoPK() + 
										" // Cenario Id => "+cenarioElemento.getCenario().getCenarioPK() + 
										" // Elemento Id => "+cenarioElemento.getElemento().getElementoPK());
		return cenarioElemento;
	}

	@Transactional
	public void delete(Long cenarioElementoPK) {

		CenarioElemento cenarioElementoTemp = this.getCenarioElementoByCenarioElementoPK(cenarioElementoPK);

		cenarioElementoRepository.delete(cenarioElementoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("CenarioElemento Save " + "\n Usuário => " + username + 
										" // Id => "+cenarioElementoTemp.getCenarioElementoPK() + 
										" // Cenario Id => "+cenarioElementoTemp.getCenario().getCenarioPK() + 
										" // Elemento Id => "+cenarioElementoTemp.getElemento().getElementoPK()); 
    }

	@Transactional
	public void deleteByElemento(Elemento elemento) {
		
		List<CenarioElemento> cenarioElementos = cenarioElementoRepository.findByElemento(elemento);

		cenarioElementoRepository.delete(cenarioElementos);

		String username = usuarioSeguranca.getUsuarioLogado();

		cenarioElementos.forEach((CenarioElemento cenarioElemento) -> {			
			logger.info("CenarioElemento Delete2 " + "\n Usuário => " + username + 
											" // Id => "+cenarioElemento.getCenarioElementoPK() + 
											" // Cenario Id => "+cenarioElemento.getCenario().getCenarioPK() + 
											" // Elemento Id => "+cenarioElemento.getElemento().getElementoPK()); 

		});
		
    }

	@Transactional
	public CenarioElemento converteCenarioElementoForm(CenarioElementoForm cenarioElementoForm) {
		
		CenarioElemento cenarioElemento = new CenarioElemento();
		
		if (cenarioElementoForm.getCenarioElementoPK() > 0) {
			cenarioElemento = this.getCenarioElementoByCenarioElementoPK(cenarioElementoForm.getCenarioElementoPK());
		}
		
		Elemento elemento = elementoService.getElementoByElementoPK(Long.parseLong(cenarioElementoForm.getElementoNome()));
		cenarioElemento.setElemento(elemento);

		Cenario cenario = cenarioService.getCenarioByCenarioPK(Long.parseLong(cenarioElementoForm.getCenarioNome()));
		cenarioElemento.setCenario(cenario);

		cenarioElemento.setCenarioElementoMotivoOperacao(cenarioElementoForm.getCenarioElementoMotivoOperacao().replaceAll("\\s+"," "));
		cenarioElemento.setCenarioElementoStatus(cenarioElementoForm.getCenarioElementoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(cenarioElementoForm.getCenarioElementoResponsavel()));
		cenarioElemento.setColaboradorResponsavel(colaborador);
		
		return cenarioElemento;
	}

	@Transactional
	public CenarioElementoForm converteCenarioElemento(CenarioElemento cenarioElemento) {
	
		CenarioElementoForm cenarioElementoForm = new CenarioElementoForm();
		
		cenarioElementoForm.setCenarioElementoPK(cenarioElemento.getCenarioElementoPK());
		cenarioElementoForm.setCenarioNome(cenarioElemento.getCenario().getCenarioPK()+"");
		cenarioElementoForm.setElementoNome(cenarioElemento.getElemento().getElementoPK()+"");

		cenarioElementoForm.setCenarioElementoMotivoOperacao(cenarioElemento.getCenarioElementoMotivoOperacao());
		cenarioElementoForm.setCenarioElementoStatus(AtributoStatus.valueOf(cenarioElemento.getCenarioElementoStatus()));

		cenarioElementoForm.setCenarioElementoResponsavel(cenarioElemento.getColaboradorResponsavel().getPessoaPK()+"");
		
	return cenarioElementoForm;
	}
	
	@Transactional
	public CenarioElementoForm converteCenarioElementoView(CenarioElemento cenarioElemento) {
	
		CenarioElementoForm cenarioElementoForm = new CenarioElementoForm();
		
		cenarioElementoForm.setCenarioElementoPK(cenarioElemento.getCenarioElementoPK());
		cenarioElementoForm.setCenarioNome(cenarioElemento.getCenario().getCenarioNome());
		cenarioElementoForm.setElementoNome(cenarioElemento.getElemento().getElementoNome());

		cenarioElementoForm.setCenarioElementoMotivoOperacao(cenarioElemento.getCenarioElementoMotivoOperacao());
		cenarioElementoForm.setCenarioElementoStatus(AtributoStatus.valueOf(cenarioElemento.getCenarioElementoStatus()));

		cenarioElementoForm.setCenarioElementoResponsavel(cenarioElemento.getColaboradorResponsavel().getPessoaNome());
		
	return cenarioElementoForm;
	}
	

	@Transactional
	public CenarioElementoForm cenarioElementoParametros(CenarioElementoForm cenarioElementoForm) {
	
		
		cenarioElementoForm.setCenarioElementoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return cenarioElementoForm;
	}
	
	@Transactional
	public CenarioElemento create(CenarioElementoForm cenarioElementoForm) {
		
		CenarioElemento cenarioElemento = new CenarioElemento();
		
		cenarioElemento = this.converteCenarioElementoForm(cenarioElementoForm);
		
		cenarioElemento = entityManager.merge(cenarioElemento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Elemento Create " + "\n Usuário => " + username + 
				" // Id => "+cenarioElemento.getCenarioElementoPK() + 
				" // Cenario Id => "+cenarioElemento.getCenario().getCenarioPK() + 
				" // Elemento Id => "+cenarioElemento.getElemento().getElementoPK()); 
		
		return cenarioElemento;
	}


}
