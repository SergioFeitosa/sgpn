package br.com.j4business.saga.processotexto.service;

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
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processotexto.model.ProcessoTexto;
import br.com.j4business.saga.processotexto.model.ProcessoTextoForm;
import br.com.j4business.saga.processotexto.repository.ProcessoTextoRepository;
import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.texto.service.TextoService;

@Service("processoTextoService")
public class ProcessoTextoServiceImpl implements ProcessoTextoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoTextoRepository processoTextoRepository;

	@Autowired
	private TextoService textoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoTextoService.class.getName());


	@Override
	public List<ProcessoTexto> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoTextoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoTexto> getByTextoNome(String textoNome,Pageable pageable) {
		return processoTextoRepository.findByTextoNome(textoNome,pageable);
	}

	@Override
	public List<ProcessoTexto> getByProcessoNome(String processoNome) {
		return processoTextoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoTexto> getByTextoNome(String textoNome) {
		return processoTextoRepository.findByTextoNome(textoNome);
	}

	@Override
	public List<ProcessoTexto> getByTextoPK(long textoPK,Pageable pageable) {
		return processoTextoRepository.findByTextoPK(textoPK,pageable);
	}

	@Override
	public List<ProcessoTexto> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoTextoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoTexto> getByProcessoPK(long processoPK) {
		return processoTextoRepository.findByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoTexto> getProcessoTextoAtivoByProcessoPK(long processoPK) {
		return processoTextoRepository.findProcessoTextoAtivoByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoTexto> getProcessoTextoAll(Pageable pageable) {
		return processoTextoRepository.findProcessoTextoAll(pageable);
	}

	@Override
	public ProcessoTexto getProcessoTextoByProcessoTextoPK(long processoTextoPK) {
		Optional<ProcessoTexto> processoTexto = processoTextoRepository.findById(processoTextoPK);
		return processoTexto.get();
	}

	@Override
	public ProcessoTexto getByProcessoAndTexto (Processo processo,Texto texto) {
		
		return processoTextoRepository.findByProcessoAndTexto(processo,texto);
	}

	@Transactional
	public ProcessoTexto create(ProcessoTextoForm processoTextoForm) {
		
		ProcessoTexto processoTexto = new ProcessoTexto();
		
		processoTexto = this.converteProcessoTextoForm(processoTextoForm);
		
		processoTexto = entityManager.merge(processoTexto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Texto Create " + "\n Usuário => " + username + 
				" // Id => "+processoTexto.getProcessoTextoPK() + 
				" // Processo Id => "+processoTexto.getProcesso().getProcessoPK() + 
				" // Texto Id => "+processoTexto.getTexto().getTextoPK()); 
		
		return processoTexto;
	}

	@Transactional
	public ProcessoTexto save(ProcessoTextoForm processoTextoForm) {

		ProcessoTexto processoTexto = new ProcessoTexto();
		
		processoTexto = this.converteProcessoTextoForm(processoTextoForm);
		
		processoTexto = entityManager.merge(processoTexto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoTexto Save " + "\n Usuário => " + username + 
										" // Id => "+processoTexto.getProcessoTextoPK() + 
										" // Processo Id => "+processoTexto.getProcesso().getProcessoPK() + 
										" // Texto Id => "+processoTexto.getTexto().getTextoPK());
		return processoTexto;
	}

	@Transactional
	public void delete(Long processoTextoPK) {

		ProcessoTexto processoTextoTemp = this.getProcessoTextoByProcessoTextoPK(processoTextoPK);

		processoTextoRepository.delete(processoTextoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoTexto Save " + "\n Usuário => " + username + 
										" // Id => "+processoTextoTemp.getProcessoTextoPK() + 
										" // Processo Id => "+processoTextoTemp.getProcesso().getProcessoPK() + 
										" // Texto Id => "+processoTextoTemp.getTexto().getTextoPK()); 
    }

	@Transactional
	public void deleteByTexto(Texto texto) {
		
		List<ProcessoTexto> processoTextoList = processoTextoRepository.findByTexto(texto);
		for (ProcessoTexto processoTexto2 : processoTextoList) {
			processoTextoRepository.delete(processoTexto2);
			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		processoTextoList.forEach((ProcessoTexto processoTexto) -> {			
			logger.info("ProcessoTexto Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoTexto.getProcessoTextoPK() + 
											" // Processo Id => "+processoTexto.getProcesso().getProcessoPK() + 
											" // Texto Id => "+processoTexto.getTexto().getTextoPK()); 
		});
    }

	@Transactional
	public ProcessoTexto converteProcessoTextoForm(ProcessoTextoForm processoTextoForm) {
		
		ProcessoTexto processoTexto = new ProcessoTexto();
		
		if (processoTextoForm.getProcessoTextoPK() > 0) {
			processoTexto = this.getProcessoTextoByProcessoTextoPK(processoTextoForm.getProcessoTextoPK());
		}
		
		
		Texto texto = textoService.getTextoByTextoPK(Long.parseLong(processoTextoForm.getTextoNome()));
		processoTexto.setTexto(texto);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoTextoForm.getProcessoNome()));
		processoTexto.setProcesso(processo);

		processoTexto.setProcessoTextoMotivoOperacao(processoTextoForm.getProcessoTextoMotivoOperacao().replaceAll("\\s+"," "));
		processoTexto.setProcessoTextoStatus(processoTextoForm.getProcessoTextoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoTextoForm.getProcessoTextoResponsavel()));
		processoTexto.setColaboradorResponsavel(colaborador);
		
		
		return processoTexto;
	}

	@Transactional
	public ProcessoTextoForm converteProcessoTexto(ProcessoTexto processoTexto) {
	
		ProcessoTextoForm processoTextoForm = new ProcessoTextoForm();
		
		processoTextoForm.setProcessoTextoPK(processoTexto.getProcessoTextoPK());
		processoTextoForm.setProcessoPK(processoTexto.getProcesso().getProcessoPK());
		processoTextoForm.setTextoPK(processoTexto.getTexto().getTextoPK());
		processoTextoForm.setProcessoNome(processoTexto.getProcesso().getProcessoPK()+"");
		processoTextoForm.setTextoNome(processoTexto.getTexto().getTextoPK()+"");

		processoTextoForm.setProcessoTextoMotivoOperacao(processoTexto.getProcessoTextoMotivoOperacao());
		processoTextoForm.setProcessoTextoStatus(AtributoStatus.valueOf(processoTexto.getProcessoTextoStatus()));
		processoTextoForm.setProcessoTextoResponsavel(processoTexto.getProcesso().getProcessoPK()+"");
		
	return processoTextoForm;
	}
	
	@Transactional
	public ProcessoTextoForm converteProcessoTextoView(ProcessoTexto processoTexto) {
	
		ProcessoTextoForm processoTextoForm = new ProcessoTextoForm();
		
		processoTextoForm.setProcessoTextoPK(processoTexto.getProcessoTextoPK());
		processoTextoForm.setProcessoPK(processoTexto.getProcesso().getProcessoPK());
		processoTextoForm.setTextoPK(processoTexto.getTexto().getTextoPK());
		processoTextoForm.setProcessoNome(processoTexto.getProcesso().getProcessoNome());
		processoTextoForm.setTextoNome(processoTexto.getTexto().getTextoNome());

		processoTextoForm.setProcessoTextoMotivoOperacao(processoTexto.getProcessoTextoMotivoOperacao());
		processoTextoForm.setProcessoTextoStatus(AtributoStatus.valueOf(processoTexto.getProcessoTextoStatus()));
		processoTextoForm.setProcessoTextoResponsavel(processoTexto.getProcesso().getProcessoNome());
		
	return processoTextoForm;
	}
	

	@Transactional
	public ProcessoTextoForm processoTextoParametros(ProcessoTextoForm processoTextoForm) {
		processoTextoForm.setProcessoTextoStatus(AtributoStatus.valueOf("ATIVO"));
	return processoTextoForm;
	}
	

}
