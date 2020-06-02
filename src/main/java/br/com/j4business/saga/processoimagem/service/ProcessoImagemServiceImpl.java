package br.com.j4business.saga.processoimagem.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidade;
import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.imagem.service.ImagemService;
import br.com.j4business.saga.processoimagem.model.ProcessoImagem;
import br.com.j4business.saga.processoimagem.model.ProcessoImagemForm;
import br.com.j4business.saga.processoimagem.repository.ProcessoImagemRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("processoImagemService")
public class ProcessoImagemServiceImpl implements ProcessoImagemService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoImagemRepository processoImagemRepository;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoImagemService.class.getName());


	@Override
	public List<ProcessoImagem> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoImagemRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoImagem> getByImagemNome(String imagemNome,Pageable pageable) {
		return processoImagemRepository.findByImagemNome(imagemNome,pageable);
	}

	@Override
	public List<ProcessoImagem> getByProcessoNome(String processoNome) {
		return processoImagemRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoImagem> getByImagemNome(String imagemNome) {
		return processoImagemRepository.findByImagemNome(imagemNome);
	}

	@Override
	public List<ProcessoImagem> getByImagemPK(long imagemPK,Pageable pageable) {
		return processoImagemRepository.findByImagemPK(imagemPK,pageable);
	}

	@Override
	public List<ProcessoImagem> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoImagemRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoImagem> getByProcessoPK(long processoPK) {
		return processoImagemRepository.findByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoImagem> getProcessoImagemAtivoByProcessoPK(long processoPK) {
		return processoImagemRepository.findProcessoImagemAtivoByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoImagem> getProcessoImagemAll(Pageable pageable) {
		return processoImagemRepository.findProcessoImagemAll(pageable);
	}

	@Override
	public ProcessoImagem getProcessoImagemByProcessoImagemPK(long processoImagemPK) {
		return processoImagemRepository.findOne(processoImagemPK);
	}

	@Override
	public ProcessoImagem getByProcessoAndImagem (Processo processo,Imagem imagem) {
		
		return processoImagemRepository.findByProcessoAndImagem(processo,imagem);
	}

	@Transactional
	public ProcessoImagem create(ProcessoImagemForm processoImagemForm) {
		
		ProcessoImagem processoImagem = new ProcessoImagem();
		
		processoImagem = this.converteProcessoImagemForm(processoImagemForm);
		
		processoImagem = entityManager.merge(processoImagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Imagem Create " + "\n Usuário => " + username + 
				" // Id => "+processoImagem.getProcessoImagemPK() + 
				" // Processo Id => "+processoImagem.getProcesso().getProcessoPK() + 
				" // Imagem Id => "+processoImagem.getImagem().getImagemPK()); 
		
		return processoImagem;
	}

	@Transactional
	public ProcessoImagem save(ProcessoImagemForm processoImagemForm) {

		ProcessoImagem processoImagem = new ProcessoImagem();
		
		processoImagem = this.converteProcessoImagemForm(processoImagemForm);
		
		processoImagem = entityManager.merge(processoImagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoImagem Save " + "\n Usuário => " + username + 
										" // Id => "+processoImagem.getProcessoImagemPK() + 
										" // Processo Id => "+processoImagem.getProcesso().getProcessoPK() + 
										" // Imagem Id => "+processoImagem.getImagem().getImagemPK());
		return processoImagem;
	}

	@Transactional
	public void delete(Long processoImagemPK) {

		ProcessoImagem processoImagemTemp = this.getProcessoImagemByProcessoImagemPK(processoImagemPK);

		processoImagemRepository.delete(processoImagemPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoImagem Save " + "\n Usuário => " + username + 
										" // Id => "+processoImagemTemp.getProcessoImagemPK() + 
										" // Processo Id => "+processoImagemTemp.getProcesso().getProcessoPK() + 
										" // Imagem Id => "+processoImagemTemp.getImagem().getImagemPK()); 
    }

	@Transactional
	public void deleteByImagem(Imagem imagem) {
		
		List<ProcessoImagem> processoImagemList = processoImagemRepository.findByImagem(imagem);

		processoImagemRepository.delete(processoImagemList);

		String username = usuarioSeguranca.getUsuarioLogado();

		processoImagemList.forEach((ProcessoImagem processoImagem) -> {
			
			logger.info("ProcessoImagem Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoImagem.getProcessoImagemPK() + 
											" // Processo Id => "+processoImagem.getProcesso().getProcessoPK() + 
											" // Imagem Id => "+processoImagem.getImagem().getImagemPK()); 
		});
		
    }

	@Transactional
	public ProcessoImagem converteProcessoImagemForm(ProcessoImagemForm processoImagemForm) {
		
		ProcessoImagem processoImagem = new ProcessoImagem();
		
		if (processoImagemForm.getProcessoImagemPK() > 0) {
			processoImagem = this.getProcessoImagemByProcessoImagemPK(processoImagemForm.getProcessoImagemPK());
		}
		
		
		Imagem imagem = imagemService.getImagemByImagemPK(Long.parseLong(processoImagemForm.getImagemNome()));
		processoImagem.setImagem(imagem);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoImagemForm.getProcessoNome()));
		processoImagem.setProcesso(processo);

		processoImagem.setProcessoImagemMotivoOperacao(processoImagemForm.getProcessoImagemMotivoOperacao().replaceAll("\\s+"," "));
		processoImagem.setProcessoImagemStatus(processoImagemForm.getProcessoImagemStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoImagemForm.getProcessoImagemResponsavel()));
		processoImagem.setColaboradorResponsavel(colaborador);
		
		
		return processoImagem;
	}

	@Transactional
	public ProcessoImagemForm converteProcessoImagem(ProcessoImagem processoImagem) {
	
		ProcessoImagemForm processoImagemForm = new ProcessoImagemForm();
		
		processoImagemForm.setProcessoImagemPK(processoImagem.getProcessoImagemPK());
		processoImagemForm.setProcessoPK(processoImagem.getProcesso().getProcessoPK());
		processoImagemForm.setImagemPK(processoImagem.getImagem().getImagemPK());
		processoImagemForm.setProcessoNome(processoImagem.getProcesso().getProcessoPK()+"");
		processoImagemForm.setImagemNome(processoImagem.getImagem().getImagemPK()+"");

		processoImagemForm.setProcessoImagemMotivoOperacao(processoImagem.getProcessoImagemMotivoOperacao());
		processoImagemForm.setProcessoImagemStatus(AtributoStatus.valueOf(processoImagem.getProcessoImagemStatus()));
		processoImagemForm.setProcessoImagemResponsavel(processoImagem.getProcesso().getProcessoPK()+"");
		
	return processoImagemForm;
	}
	
	@Transactional
	public ProcessoImagemForm converteProcessoImagemView(ProcessoImagem processoImagem) {
	
		ProcessoImagemForm processoImagemForm = new ProcessoImagemForm();
		
		processoImagemForm.setProcessoImagemPK(processoImagem.getProcessoImagemPK());
		processoImagemForm.setProcessoPK(processoImagem.getProcesso().getProcessoPK());
		processoImagemForm.setImagemPK(processoImagem.getImagem().getImagemPK());
		processoImagemForm.setProcessoNome(processoImagem.getProcesso().getProcessoNome());
		processoImagemForm.setImagemNome(processoImagem.getImagem().getImagemNome());

		processoImagemForm.setProcessoImagemMotivoOperacao(processoImagem.getProcessoImagemMotivoOperacao());
		processoImagemForm.setProcessoImagemStatus(AtributoStatus.valueOf(processoImagem.getProcessoImagemStatus()));
		processoImagemForm.setProcessoImagemResponsavel(processoImagem.getProcesso().getProcessoNome());
		
	return processoImagemForm;
	}
	

	@Transactional
	public ProcessoImagemForm processoImagemParametros(ProcessoImagemForm processoImagemForm) {
		processoImagemForm.setProcessoImagemStatus(AtributoStatus.valueOf("ATIVO"));
	return processoImagemForm;
	}
	

}
