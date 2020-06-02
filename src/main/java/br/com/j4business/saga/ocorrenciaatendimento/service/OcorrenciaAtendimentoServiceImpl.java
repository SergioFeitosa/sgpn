package br.com.j4business.saga.ocorrenciaatendimento.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcesso;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimento;
import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.atendimento.service.AtendimentoService;
import br.com.j4business.saga.ocorrencia.model.Ocorrencia;
import br.com.j4business.saga.ocorrencia.service.OcorrenciaService;
import br.com.j4business.saga.ocorrenciaatendimento.model.OcorrenciaAtendimentoForm;
import br.com.j4business.saga.ocorrenciaatendimento.repository.OcorrenciaAtendimentoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("ocorrenciaAtendimentoService")
public class OcorrenciaAtendimentoServiceImpl implements OcorrenciaAtendimentoService {

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private OcorrenciaAtendimentoRepository ocorrenciaAtendimentoRepository;

	@Autowired
	private AtendimentoService atendimentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(OcorrenciaAtendimentoService.class.getName());


	@Override
	public List<OcorrenciaAtendimento> getByOcorrenciaNome(String ocorrenciaNome,Pageable pageable) {
		return ocorrenciaAtendimentoRepository.findByOcorrenciaNome(ocorrenciaNome,pageable);
	}

	@Override
	public List<OcorrenciaAtendimento> getByAtendimentoNome(String atendimentoNome,Pageable pageable) {
		return ocorrenciaAtendimentoRepository.findByAtendimentoNome(atendimentoNome,pageable);
	}

	@Override
	public List<OcorrenciaAtendimento> getByOcorrenciaNome(String ocorrenciaNome) {
		return ocorrenciaAtendimentoRepository.findByOcorrenciaNome(ocorrenciaNome);
	}

	@Override
	public List<OcorrenciaAtendimento> getByAtendimentoNome(String atendimentoNome) {
		return ocorrenciaAtendimentoRepository.findByAtendimentoNome(atendimentoNome);
	}

	@Override
	public List<OcorrenciaAtendimento> getByAtendimentoPK(long atendimentoPK,Pageable pageable) {
		return ocorrenciaAtendimentoRepository.findByAtendimentoPK(atendimentoPK,pageable);
	}

	@Override
	public List<OcorrenciaAtendimento> getByOcorrenciaPK(long ocorrenciaPK,Pageable pageable) {
		return ocorrenciaAtendimentoRepository.findByOcorrenciaPK(ocorrenciaPK,pageable);
	}

	@Override
	public List<OcorrenciaAtendimento> getOcorrenciaAtendimentoAll(Pageable pageable) {
		return ocorrenciaAtendimentoRepository.findOcorrenciaAtendimentoAll(pageable);
	}

	@Override
	public OcorrenciaAtendimento getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(long ocorrenciaAtendimentoPK) {
		return ocorrenciaAtendimentoRepository.findOne(ocorrenciaAtendimentoPK);
	}

	@Override
	public OcorrenciaAtendimento getByOcorrenciaAndAtendimento (Ocorrencia ocorrencia,Atendimento atendimento) {
		
		return ocorrenciaAtendimentoRepository.findByOcorrenciaAndAtendimento(ocorrencia,atendimento);
	}

	@Transactional
	public OcorrenciaAtendimento create(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm) {
		
		OcorrenciaAtendimento ocorrenciaAtendimento = new OcorrenciaAtendimento();
		
		ocorrenciaAtendimento = this.converteOcorrenciaAtendimentoForm(ocorrenciaAtendimentoForm);
		
		ocorrenciaAtendimento = entityManager.merge(ocorrenciaAtendimento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Atendimento Create " + "\n Usuário => " + username + 
				" // Id => "+ocorrenciaAtendimento.getOcorrenciaAtendimentoPK() + 
				" // Ocorrencia Id => "+ocorrenciaAtendimento.getOcorrencia().getOcorrenciaPK() + 
				" // Atendimento Id => "+ocorrenciaAtendimento.getAtendimento().getAtendimentoPK() + 
				" // Valor => "+ocorrenciaAtendimento.getOcorrenciaAtendimentoSequencia()); 
		
		return ocorrenciaAtendimento;
	}

	@Transactional
	public OcorrenciaAtendimento save(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm) {

		OcorrenciaAtendimento ocorrenciaAtendimento = new OcorrenciaAtendimento();
		
		ocorrenciaAtendimento = this.converteOcorrenciaAtendimentoForm(ocorrenciaAtendimentoForm);
		
		ocorrenciaAtendimento = entityManager.merge(ocorrenciaAtendimento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("OcorrenciaAtendimento Save " + "\n Usuário => " + username + 
										" // Id => "+ocorrenciaAtendimento.getOcorrenciaAtendimentoPK() + 
										" // Ocorrencia Id => "+ocorrenciaAtendimento.getOcorrencia().getOcorrenciaPK() + 
										" // Atendimento Id => "+ocorrenciaAtendimento.getAtendimento().getAtendimentoPK() + 
										" // Valor => "+ocorrenciaAtendimento.getOcorrenciaAtendimentoSequencia()); 
		return ocorrenciaAtendimento;
	}

	@Transactional
	public void delete(Long ocorrenciaAtendimentoPK) {

		OcorrenciaAtendimento ocorrenciaAtendimentoTemp = this.getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(ocorrenciaAtendimentoPK);

		ocorrenciaAtendimentoRepository.delete(ocorrenciaAtendimentoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("OcorrenciaAtendimento Save " + "\n Usuário => " + username + 
										" // Id => "+ocorrenciaAtendimentoTemp.getOcorrenciaAtendimentoPK() + 
										" // Ocorrencia Id => "+ocorrenciaAtendimentoTemp.getOcorrencia().getOcorrenciaPK() + 
										" // Atendimento Id => "+ocorrenciaAtendimentoTemp.getAtendimento().getAtendimentoPK() + 
										" // Valor => "+ocorrenciaAtendimentoTemp.getOcorrenciaAtendimentoSequencia()); 
    }

	@Transactional
	public void deleteByAtendimento(Atendimento atendimento) {
		
		List<OcorrenciaAtendimento> ocorrenciaAtendimentoList = ocorrenciaAtendimentoRepository.findByAtendimento(atendimento);

		ocorrenciaAtendimentoRepository.delete(ocorrenciaAtendimentoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		ocorrenciaAtendimentoList.forEach((OcorrenciaAtendimento ocorrenciaAtendimento) -> {
			
			logger.info("OcorrenciaAtendimento Delete2 " + "\n Usuário => " + username + 
											" // Id => "+ocorrenciaAtendimento.getOcorrenciaAtendimentoPK() + 
											" // Ocorrencia Id => "+ocorrenciaAtendimento.getOcorrencia().getOcorrenciaPK() + 
											" // Atendimento Id => "+ocorrenciaAtendimento.getAtendimento().getAtendimentoPK() + 
											" // Valor => "+ocorrenciaAtendimento.getOcorrenciaAtendimentoSequencia()); 
		});
		
    }

	@Transactional
	public OcorrenciaAtendimento converteOcorrenciaAtendimentoForm(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm) {
		
		OcorrenciaAtendimento ocorrenciaAtendimento = new OcorrenciaAtendimento();
		
		if (ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoPK() > 0) {
			ocorrenciaAtendimento = this.getOcorrenciaAtendimentoByOcorrenciaAtendimentoPK(ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoPK());
		}
		
		Atendimento atendimento = atendimentoService.getAtendimentoByAtendimentoPK(Long.parseLong(ocorrenciaAtendimentoForm.getAtendimentoNome()));
		ocorrenciaAtendimento.setAtendimento(atendimento);

		Ocorrencia ocorrencia = ocorrenciaService.getOcorrenciaByOcorrenciaPK(Long.parseLong(ocorrenciaAtendimentoForm.getOcorrenciaNome()));
		ocorrenciaAtendimento.setOcorrencia(ocorrencia);

		ocorrenciaAtendimento.setOcorrenciaAtendimentoSequencia(Integer.parseInt(ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoSequencia()+"".replaceAll("\\s+"," ")));

		ocorrenciaAtendimento.setOcorrenciaAtendimentoMotivoOperacao(ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoMotivoOperacao().replaceAll("\\s+"," "));
		ocorrenciaAtendimento.setOcorrenciaAtendimentoStatus(ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(ocorrenciaAtendimentoForm.getOcorrenciaAtendimentoResponsavel()));
		ocorrenciaAtendimento.setColaboradorResponsavel(colaborador);
		
		return ocorrenciaAtendimento;
	}

	@Transactional
	public OcorrenciaAtendimentoForm converteOcorrenciaAtendimento(OcorrenciaAtendimento ocorrenciaAtendimento) {
	
		OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm = new OcorrenciaAtendimentoForm();
		
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoPK(ocorrenciaAtendimento.getOcorrenciaAtendimentoPK());
		ocorrenciaAtendimentoForm.setOcorrenciaNome(ocorrenciaAtendimento.getOcorrencia().getOcorrenciaPK()+"");
		ocorrenciaAtendimentoForm.setAtendimentoNome(ocorrenciaAtendimento.getAtendimento().getAtendimentoPK()+"");
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoSequencia(ocorrenciaAtendimento.getOcorrenciaAtendimentoSequencia()+"");

		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoMotivoOperacao(ocorrenciaAtendimento.getOcorrenciaAtendimentoMotivoOperacao());
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoStatus(AtributoStatus.valueOf(ocorrenciaAtendimento.getOcorrenciaAtendimentoStatus()));
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoResponsavel(ocorrenciaAtendimento.getColaboradorResponsavel().getPessoaNome());
		
	return ocorrenciaAtendimentoForm;
	}
	
	@Transactional
	public OcorrenciaAtendimentoForm converteOcorrenciaAtendimentoView(OcorrenciaAtendimento ocorrenciaAtendimento) {
	
		OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm = new OcorrenciaAtendimentoForm();
		
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoPK(ocorrenciaAtendimento.getOcorrenciaAtendimentoPK());
		ocorrenciaAtendimentoForm.setOcorrenciaNome(ocorrenciaAtendimento.getOcorrencia().getOcorrenciaNome());
		ocorrenciaAtendimentoForm.setAtendimentoNome(ocorrenciaAtendimento.getAtendimento().getAtendimentoNome());
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoSequencia(ocorrenciaAtendimento.getOcorrenciaAtendimentoSequencia()+"");

		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoMotivoOperacao(ocorrenciaAtendimento.getOcorrenciaAtendimentoMotivoOperacao());
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoStatus(AtributoStatus.valueOf(ocorrenciaAtendimento.getOcorrenciaAtendimentoStatus()));
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoResponsavel(ocorrenciaAtendimento.getColaboradorResponsavel().getPessoaNome());
		
	return ocorrenciaAtendimentoForm;
	}
	

	@Transactional
	public OcorrenciaAtendimentoForm ocorrenciaAtendimentoParametros(OcorrenciaAtendimentoForm ocorrenciaAtendimentoForm) {
	
		ocorrenciaAtendimentoForm.setOcorrenciaAtendimentoStatus(AtributoStatus.valueOf("ATIVO"));
		
	return ocorrenciaAtendimentoForm;
	}
	

}
