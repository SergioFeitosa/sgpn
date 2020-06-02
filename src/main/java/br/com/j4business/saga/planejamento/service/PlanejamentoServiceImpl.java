package br.com.j4business.saga.planejamento.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.acao.service.AcaoService;
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoCusto;
import br.com.j4business.saga.atributo.enumeration.AtributoPrazo;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamento.model.PlanejamentoAcaoBean;
import br.com.j4business.saga.planejamento.repository.PlanejamentoRepository;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.planejamento.model.PlanejamentoForm;

@Service("planejamentoService")
public class PlanejamentoServiceImpl implements PlanejamentoService {

	@Autowired
	private AcaoService acaoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private PlanejamentoRepository planejamentoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(PlanejamentoService.class.getName());

	@Override
	public Page<Planejamento> getPlanejamentoAll(Pageable pageable) {
		return planejamentoRepository.findAll(pageable);
	}

	@Override
	public List<Planejamento> getPlanejamentoAll() {
		return planejamentoRepository.findAll();
	}

	@Override
	public Planejamento getPlanejamentoByPlanejamentoPK(long planejamentoPK) {
		
		return planejamentoRepository.findOne(planejamentoPK);
	}

	@Transactional
	public Planejamento create(PlanejamentoForm planejamentoForm) {
		
		Planejamento planejamento = new Planejamento();
		
		planejamento = this.convertePlanejamentoForm(planejamentoForm);
		
		planejamento = entityManager.merge(planejamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Planejamento Create " + "\n Usuário => " + username + 
										" // Id => "+planejamento.getPlanejamentoPK() + 
										" // Planejamento => "+planejamento.getPlanejamentoNome() + 
										" // Descrição => "+ planejamento.getPlanejamentoDescricao());
		
		return planejamento;
	}

	@Transactional
	public Planejamento save(PlanejamentoForm planejamentoForm) {
		
		Planejamento planejamento = new Planejamento();
		
		planejamento = this.convertePlanejamentoForm(planejamentoForm);
		
		planejamento = entityManager.merge(planejamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Planejamento Save " + "\n Usuário => " + username + 
										" // Id => "+planejamento.getPlanejamentoPK() + 
										" // Planejamento => "+planejamento.getPlanejamentoNome() + 
										" // Descrição => "+ planejamento.getPlanejamentoDescricao());
		

		return planejamento;
		
	}

	@Transactional
	public void delete(Long planejamentoId) {

		Planejamento planejamento = this.getPlanejamentoByPlanejamentoPK(planejamentoId);
		
		planejamentoRepository.delete(planejamento.getPlanejamentoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Planejamento Delete " + "\n Usuário => " + username + 
										" // Id => "+planejamento.getPlanejamentoPK() + 
										" // Planejamento => "+planejamento.getPlanejamentoNome() + 
										" // Descrição => "+ planejamento.getPlanejamentoDescricao());
		

    }

	@Transactional
	public Planejamento convertePlanejamentoForm(PlanejamentoForm planejamentoForm) {
		
		Planejamento planejamento = new Planejamento();
		
		if (planejamentoForm.getPlanejamentoPK() > 0) {
			planejamento = this.getPlanejamentoByPlanejamentoPK(planejamentoForm.getPlanejamentoPK());
		}
		planejamento.setPlanejamentoNome(planejamentoForm.getPlanejamentoNome().replaceAll("\\s+"," "));
		planejamento.setPlanejamentoDescricao(planejamentoForm.getPlanejamentoDescricao().replaceAll("\\s+"," "));
		planejamento.setPlanejamentoAprovacao(planejamentoForm.getPlanejamentoAprovacao()+"");

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(planejamentoForm.getPlanejamentoGestor()));
		planejamento.setColaboradorGestor(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(planejamentoForm.getPlanejamentoDono()));
		planejamento.setColaboradorDono(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(planejamentoForm.getPlanejamentoResponsavel()));
		planejamento.setColaboradorResponsavel(colaborador);

		if (planejamentoForm.getPlanejamentoDataRealInicio() != "") {
			planejamento.setPlanejamentoDataRealInicio(planejamentoForm.getPlanejamentoDataRealInicio()+"");
		}
		
		if (planejamentoForm.getPlanejamentoDataRealTermino() != "") {
			planejamento.setPlanejamentoDataRealTermino(planejamentoForm.getPlanejamentoDataRealTermino()+"");
		}
		
		planejamento.setPlanejamentoPrazoStatus(planejamentoForm.getPlanejamentoPrazoStatus()+"");

		planejamento.setPlanejamentoCustoPrevisto(planejamentoForm.getPlanejamentoCustoPrevisto());

		if (planejamentoForm.getPlanejamentoCustoFinal()+"" != "") {
			planejamento.setPlanejamentoCustoFinal(planejamentoForm.getPlanejamentoCustoFinal());
		}
		planejamento.setPlanejamentoCustoStatus(planejamentoForm.getPlanejamentoCustoStatus()+"");

		
		planejamento.setPlanejamentoMotivoOperacao(planejamentoForm.getPlanejamentoMotivoOperacao().replaceAll("\\s+"," "));
		planejamento.setPlanejamentoStatus(planejamentoForm.getPlanejamentoStatus()+"".replaceAll("\\s+"," "));

		return planejamento;
		

	}


	@Transactional
	public PlanejamentoForm convertePlanejamento(Planejamento planejamento) {
	
		PlanejamentoForm planejamentoForm = new PlanejamentoForm();
		
		planejamentoForm.setPlanejamentoPK(planejamento.getPlanejamentoPK());
		planejamentoForm.setPlanejamentoNome(planejamento.getPlanejamentoNome());
		planejamentoForm.setPlanejamentoDescricao(planejamento.getPlanejamentoDescricao());
		planejamentoForm.setPlanejamentoAprovacao(AtributoAprovacao.valueOf(planejamento.getPlanejamentoAprovacao()+""));

		planejamentoForm.setPlanejamentoGestor(planejamento.getColaboradorGestor().getPessoaPK()+"");

		planejamentoForm.setPlanejamentoDono(planejamento.getColaboradorDono().getPessoaPK()+"");

		planejamentoForm.setPlanejamentoDataPrevistaInicio(planejamento.getPlanejamentoDataPrevistaInicio()+"");
		planejamentoForm.setPlanejamentoDataPrevistaTermino(planejamento.getPlanejamentoDataPrevistaTermino()+"");
		planejamentoForm.setPlanejamentoDataRealInicio(planejamento.getPlanejamentoDataRealInicio()+"");
		planejamentoForm.setPlanejamentoDataRealTermino(planejamento.getPlanejamentoDataRealTermino()+"");

		planejamentoForm.setPlanejamentoPrazoStatus(AtributoPrazo.valueOf(planejamento.getPlanejamentoPrazoStatus()+""));
		planejamentoForm.setPlanejamentoCustoStatus(AtributoCusto.valueOf(planejamento.getPlanejamentoCustoStatus()+""));

		planejamentoForm.setPlanejamentoPrazoStatus(AtributoPrazo.valueOf(planejamento.getPlanejamentoPrazoStatus()+""));

		planejamentoForm.setPlanejamentoCustoPrevisto(planejamento.getPlanejamentoCustoPrevisto());
		planejamentoForm.setPlanejamentoCustoFinal(planejamento.getPlanejamentoCustoFinal());
		
		planejamentoForm.setPlanejamentoCustoStatus(AtributoCusto.valueOf(planejamento.getPlanejamentoCustoStatus()+""));

		planejamentoForm.setPlanejamentoResponsavel(planejamento.getColaboradorResponsavel().getPessoaPK()+"");

		planejamentoForm.setPlanejamentoMotivoOperacao(planejamento.getPlanejamentoMotivoOperacao());
		planejamentoForm.setPlanejamentoStatus(AtributoStatus.valueOf(planejamento.getPlanejamentoStatus()));

		List<Acao> acaoList = acaoService.getAcaoAll();
		List<Acao> planejamentoAcaoList = planejamento.getAcaoList();
		List<PlanejamentoAcaoBean> planejamentoAcaoBeanList = new ArrayList<PlanejamentoAcaoBean>();
		
		
		acaoList.forEach((Acao acao) -> {
			
			PlanejamentoAcaoBean planejamentoAcaoBean = new PlanejamentoAcaoBean();
			planejamentoAcaoBean.setAcaoPK(acao.getAcaoPK());
			planejamentoAcaoBean.setAcaoNome(acao.getAcaoNome());
			planejamentoAcaoBean.setPlanejamentoAcaoChecked(false);
			
			planejamentoAcaoList.forEach((Acao planejamentoAcao) -> {
				
				if (acao.getAcaoPK() == planejamentoAcao.getAcaoPK()) {
					
					planejamentoAcaoBean.setPlanejamentoAcaoChecked(true);
					
				}
			});
			
			planejamentoAcaoBeanList.add(planejamentoAcaoBean);
			
		});

		planejamentoForm.setPlanejamentoAcaoBeanList(planejamentoAcaoBeanList);

		//logger.info("1" + planejamento.getAcaoList().listIterator().next().getAcaoNome());
		
		//planejamentoForm.setAcaoList(planejamento.getAcaoList());

		//logger.info("2" + planejamentoForm.getAcaoList().listIterator().next().getAcaoNome());
		
		
	return planejamentoForm;
	}
	

	@Transactional
	public PlanejamentoForm convertePlanejamentoView(Planejamento planejamento) {
	
		PlanejamentoForm planejamentoForm = new PlanejamentoForm();
		
		planejamentoForm.setPlanejamentoPK(planejamento.getPlanejamentoPK());
		planejamentoForm.setPlanejamentoNome(planejamento.getPlanejamentoNome());
		planejamentoForm.setPlanejamentoDescricao(planejamento.getPlanejamentoDescricao());
		planejamentoForm.setPlanejamentoAprovacao(AtributoAprovacao.valueOf(planejamento.getPlanejamentoAprovacao()+""));

		planejamentoForm.setPlanejamentoGestor(planejamento.getColaboradorGestor().getPessoaNome()+"");

		planejamentoForm.setPlanejamentoDono(planejamento.getColaboradorDono().getPessoaNome()+"");

		planejamentoForm.setPlanejamentoDataPrevistaInicio(planejamento.getPlanejamentoDataPrevistaInicio()+"");
		planejamentoForm.setPlanejamentoDataPrevistaTermino(planejamento.getPlanejamentoDataPrevistaTermino()+"");
		planejamentoForm.setPlanejamentoDataRealInicio(planejamento.getPlanejamentoDataRealInicio()+"");
		planejamentoForm.setPlanejamentoDataRealTermino(planejamento.getPlanejamentoDataRealTermino()+"");

		planejamentoForm.setPlanejamentoPrazoStatus(AtributoPrazo.valueOf(planejamento.getPlanejamentoPrazoStatus()+""));
		planejamentoForm.setPlanejamentoCustoStatus(AtributoCusto.valueOf(planejamento.getPlanejamentoCustoStatus()+""));

		planejamentoForm.setPlanejamentoPrazoStatus(AtributoPrazo.valueOf(planejamento.getPlanejamentoPrazoStatus()+""));

		planejamentoForm.setPlanejamentoCustoPrevisto(planejamento.getPlanejamentoCustoPrevisto());
		planejamentoForm.setPlanejamentoCustoFinal(planejamento.getPlanejamentoCustoFinal());
		
		planejamentoForm.setPlanejamentoCustoStatus(AtributoCusto.valueOf(planejamento.getPlanejamentoCustoStatus()+""));

		planejamentoForm.setPlanejamentoResponsavel(planejamento.getColaboradorResponsavel().getPessoaNome()+"");
		
	return planejamentoForm;
	}
	

	@Transactional
	public PlanejamentoForm planejamentoParametros(PlanejamentoForm planejamentoForm) {
	
		planejamentoForm.setPlanejamentoStatus(AtributoStatus.valueOf("ATIVO"));
		
	return planejamentoForm;
	}

	@Override
	public List<Planejamento> getByPlanejamentoDescricao(String planejamentoDescricao,Pageable pageable) {
		return planejamentoRepository.findByPlanejamentoDescricao(planejamentoDescricao,pageable);
	}

	@Override
	public List<Planejamento> getByPlanejamentoNome(String planejamentoNome,Pageable pageable) {
		return planejamentoRepository.findByPlanejamentoNome(planejamentoNome,pageable);
	}


	@Override
	public List<Planejamento> getByPlanejamentoDescricao(String planejamentoDescricao) {
		return planejamentoRepository.findByPlanejamentoDescricao(planejamentoDescricao);
	}

	@Override
	public List<Planejamento> getByPlanejamentoNome(String planejamentoNome) {
		return planejamentoRepository.findByPlanejamentoNome(planejamentoNome);
	}



}
