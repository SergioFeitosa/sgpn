package br.com.j4business.saga.treinamento.service;

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
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoCusto;
import br.com.j4business.saga.atributo.enumeration.AtributoPrazo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.model.TreinamentoForm;
import br.com.j4business.saga.treinamento.repository.TreinamentoRepository;

@Service("treinamentoService")
public class TreinamentoServiceImpl implements TreinamentoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private TreinamentoRepository treinamentoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(TreinamentoService.class.getName());

	@Override
	public List<Treinamento> getTreinamentoAll() {
		return treinamentoRepository.findAll();
	}

	@Override
	public Page<Treinamento> getTreinamentoAll(Pageable pageable) {
		return treinamentoRepository.findAll(pageable);
	}

	@Override
	public Treinamento getTreinamentoByTreinamentoPK(long treinamentoPK) {
		
		Optional<Treinamento> treinamento = treinamentoRepository.findById(treinamentoPK);
		return treinamento.get();
	}

	@Transactional
	public Treinamento create(TreinamentoForm treinamentoForm) {
		
		Treinamento treinamento = new Treinamento();
		
		treinamento = this.converteTreinamentoForm(treinamentoForm);
		
		treinamento = entityManager.merge(treinamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Treinamento Create " + "\n Usuário => " + username + 
										" // Id => "+treinamento.getTreinamentoPK() + 
										" // Treinamento => "+treinamento.getTreinamentoNome() + 
										" // Descrição => "+ treinamento.getTreinamentoDescricao());
		
		return treinamento;
	}

	@Transactional
	public Treinamento save(TreinamentoForm treinamentoForm) {
		
		Treinamento treinamento = new Treinamento();
		
		treinamento = this.converteTreinamentoForm(treinamentoForm);
		
		treinamento = entityManager.merge(treinamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();

		logger.info("Treinamento Save " + "\n Usuário => " + username + 
										" // Id => "+treinamento.getTreinamentoPK() + 
										" // Treinamento => "+treinamento.getTreinamentoNome() + 
										" // Descrição => "+ treinamento.getTreinamentoDescricao());
		

		return treinamento;
		
	}

	@Transactional
	public void delete(Long treinamentoId) {

		Treinamento treinamento = this.getTreinamentoByTreinamentoPK(treinamentoId);
		
		treinamentoRepository.delete(treinamento);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Treinamento Delete " + "\n Usuário => " + username + 
										" // Id => "+treinamento.getTreinamentoPK() + 
										" // Treinamento => "+treinamento.getTreinamentoNome() + 
										" // Descrição => "+ treinamento.getTreinamentoDescricao());
		

    }

	@Transactional
	public Treinamento converteTreinamentoForm(TreinamentoForm treinamentoForm) {
		
		Treinamento treinamento = new Treinamento();
		
		if (treinamentoForm.getTreinamentoPK() > 0) {
			treinamento = this.getTreinamentoByTreinamentoPK(treinamentoForm.getTreinamentoPK());
		}
		treinamento.setTreinamentoNome(treinamentoForm.getTreinamentoNome().replaceAll("\\s+"," "));
		treinamento.setTreinamentoDescricao(treinamentoForm.getTreinamentoDescricao().replaceAll("\\s+"," "));

		treinamento.setTreinamentoMotivoOperacao(treinamentoForm.getTreinamentoMotivoOperacao().replaceAll("\\s+"," "));
		treinamento.setTreinamentoStatus(treinamentoForm.getTreinamentoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(treinamentoForm.getTreinamentoResponsavel()));
		treinamento.setColaboradorResponsavel(colaborador);
		
		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(treinamentoForm.getTreinamentoDono()));
		treinamento.setColaboradorDono(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(treinamentoForm.getTreinamentoResponsavel()));
		treinamento.setColaboradorResponsavel(colaborador);

		if (treinamentoForm.getTreinamentoDataRealInicio() != "") {
			treinamento.setTreinamentoDataRealInicio(treinamentoForm.getTreinamentoDataRealInicio()+"");
		}
		
		if (treinamentoForm.getTreinamentoDataRealTermino() != "") {
			treinamento.setTreinamentoDataRealTermino(treinamentoForm.getTreinamentoDataRealTermino()+"");
		}
		
		treinamento.setTreinamentoPrazoStatus(treinamentoForm.getTreinamentoPrazoStatus()+"");

		treinamento.setTreinamentoCustoPrevisto(treinamentoForm.getTreinamentoCustoPrevisto());

		if (treinamentoForm.getTreinamentoCustoFinal()+"" != "") {
			treinamento.setTreinamentoCustoFinal(treinamentoForm.getTreinamentoCustoFinal());
		}
		treinamento.setTreinamentoCustoStatus(treinamentoForm.getTreinamentoCustoStatus()+"");

		
		treinamento.setTreinamentoMotivoOperacao(treinamentoForm.getTreinamentoMotivoOperacao().replaceAll("\\s+"," "));
		treinamento.setTreinamentoStatus(treinamentoForm.getTreinamentoStatus()+"".replaceAll("\\s+"," "));

		return treinamento;
	}

	@Transactional
	public TreinamentoForm converteTreinamento(Treinamento treinamento) {
	
		TreinamentoForm treinamentoForm = new TreinamentoForm();
		
		treinamentoForm.setTreinamentoPK(treinamento.getTreinamentoPK());
		treinamentoForm.setTreinamentoNome(treinamento.getTreinamentoNome());
		treinamentoForm.setTreinamentoDescricao(treinamento.getTreinamentoDescricao());

		treinamentoForm.setTreinamentoMotivoOperacao(treinamento.getTreinamentoMotivoOperacao());
		treinamentoForm.setTreinamentoStatus(AtributoStatus.valueOf(treinamento.getTreinamentoStatus()));

		treinamentoForm.setTreinamentoResponsavel(treinamento.getColaboradorResponsavel().getPessoaPK()+"");
		
		treinamentoForm.setTreinamentoAprovacao(AtributoAprovacao.valueOf(treinamento.getTreinamentoAprovacao()+""));

		treinamentoForm.setTreinamentoGestor(treinamento.getColaboradorGestor().getPessoaPK()+"");

		treinamentoForm.setTreinamentoDono(treinamento.getColaboradorDono().getPessoaPK()+"");

		treinamentoForm.setTreinamentoDataPrevistaInicio(treinamento.getTreinamentoDataPrevistaInicio()+"");
		treinamentoForm.setTreinamentoDataPrevistaTermino(treinamento.getTreinamentoDataPrevistaTermino()+"");
		treinamentoForm.setTreinamentoDataRealInicio(treinamento.getTreinamentoDataRealInicio()+"");
		treinamentoForm.setTreinamentoDataRealTermino(treinamento.getTreinamentoDataRealTermino()+"");

		treinamentoForm.setTreinamentoPrazoStatus(AtributoPrazo.valueOf(treinamento.getTreinamentoPrazoStatus()+""));
		treinamentoForm.setTreinamentoCustoStatus(AtributoCusto.valueOf(treinamento.getTreinamentoCustoStatus()+""));

		treinamentoForm.setTreinamentoPrazoStatus(AtributoPrazo.valueOf(treinamento.getTreinamentoPrazoStatus()+""));

		treinamentoForm.setTreinamentoCustoPrevisto(treinamento.getTreinamentoCustoPrevisto());
		treinamentoForm.setTreinamentoCustoFinal(treinamento.getTreinamentoCustoFinal());
		
		treinamentoForm.setTreinamentoCustoStatus(AtributoCusto.valueOf(treinamento.getTreinamentoCustoStatus()+""));

	return treinamentoForm;
	}
	
	@Transactional
	public TreinamentoForm converteTreinamentoView(Treinamento treinamento) {
	
		TreinamentoForm treinamentoForm = new TreinamentoForm();
		
		treinamentoForm.setTreinamentoPK(treinamento.getTreinamentoPK());
		treinamentoForm.setTreinamentoNome(treinamento.getTreinamentoNome());
		treinamentoForm.setTreinamentoDescricao(treinamento.getTreinamentoDescricao());

		treinamentoForm.setTreinamentoMotivoOperacao(treinamento.getTreinamentoMotivoOperacao());
		treinamentoForm.setTreinamentoStatus(AtributoStatus.valueOf(treinamento.getTreinamentoStatus()));

		treinamentoForm.setTreinamentoResponsavel(treinamento.getColaboradorResponsavel().getPessoaNome());
		treinamentoForm.setTreinamentoMotivoOperacao(treinamento.getTreinamentoMotivoOperacao());
		treinamentoForm.setTreinamentoStatus(AtributoStatus.valueOf(treinamento.getTreinamentoStatus()));

		treinamentoForm.setTreinamentoResponsavel(treinamento.getColaboradorResponsavel().getPessoaPK()+"");
		
		treinamentoForm.setTreinamentoAprovacao(AtributoAprovacao.valueOf(treinamento.getTreinamentoAprovacao()+""));

		treinamentoForm.setTreinamentoGestor(treinamento.getColaboradorGestor().getPessoaPK()+"");

		treinamentoForm.setTreinamentoDono(treinamento.getColaboradorDono().getPessoaPK()+"");

		treinamentoForm.setTreinamentoDataPrevistaInicio(treinamento.getTreinamentoDataPrevistaInicio()+"");
		treinamentoForm.setTreinamentoDataPrevistaTermino(treinamento.getTreinamentoDataPrevistaTermino()+"");
		treinamentoForm.setTreinamentoDataRealInicio(treinamento.getTreinamentoDataRealInicio()+"");
		treinamentoForm.setTreinamentoDataRealTermino(treinamento.getTreinamentoDataRealTermino()+"");

		treinamentoForm.setTreinamentoPrazoStatus(AtributoPrazo.valueOf(treinamento.getTreinamentoPrazoStatus()+""));
		treinamentoForm.setTreinamentoCustoStatus(AtributoCusto.valueOf(treinamento.getTreinamentoCustoStatus()+""));

		treinamentoForm.setTreinamentoPrazoStatus(AtributoPrazo.valueOf(treinamento.getTreinamentoPrazoStatus()+""));

		treinamentoForm.setTreinamentoCustoPrevisto(treinamento.getTreinamentoCustoPrevisto());
		treinamentoForm.setTreinamentoCustoFinal(treinamento.getTreinamentoCustoFinal());
		
		treinamentoForm.setTreinamentoCustoStatus(AtributoCusto.valueOf(treinamento.getTreinamentoCustoStatus()+""));

	return treinamentoForm;
	}
	

	@Transactional
	public TreinamentoForm treinamentoParametros(TreinamentoForm treinamentoForm) {
	
		
		treinamentoForm.setTreinamentoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return treinamentoForm;
	}

	@Override
	public List<Treinamento> getByTreinamentoDescricao(String treinamentoDescricao,Pageable pageable) {
		return treinamentoRepository.findByTreinamentoDescricao(treinamentoDescricao,pageable);
	}

	@Override
	public List<Treinamento> getByTreinamentoNome(String treinamentoNome,Pageable pageable) {
		return treinamentoRepository.findByTreinamentoNome(treinamentoNome,pageable);
	}

	@Override
	public List<Treinamento> getByTreinamentoDescricao(String treinamentoDescricao) {
		return treinamentoRepository.findByTreinamentoDescricao(treinamentoDescricao);
	}

	@Override
	public List<Treinamento> getByTreinamentoNome(String treinamentoNome) {
		return treinamentoRepository.findByTreinamentoNome(treinamentoNome);
	}



}
