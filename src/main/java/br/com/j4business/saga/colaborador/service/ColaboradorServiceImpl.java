package br.com.j4business.saga.colaborador.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.model.ColaboradorRel002;
import br.com.j4business.saga.colaborador.model.ColaboradorRel003;
import br.com.j4business.saga.colaborador.model.ColaboradorRel004;
import br.com.j4business.saga.colaborador.model.ColaboradorRel005;
import br.com.j4business.saga.colaborador.repository.ColaboradorRepository;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;
import br.com.j4business.saga.colaboradorcertificacao.service.ColaboradorCertificacaoService;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.colaboradorcurso.service.ColaboradorCursoService;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacao;
import br.com.j4business.saga.colaboradorformacao.service.ColaboradorFormacaoService;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidade;
import br.com.j4business.saga.colaboradorhabilidade.service.ColaboradorHabilidadeService;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcesso;
import br.com.j4business.saga.colaboradorprocesso.service.ColaboradorProcessoService;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;
import br.com.j4business.saga.processocertificacao.service.ProcessoCertificacaoService;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;
import br.com.j4business.saga.processocurso.service.ProcessoCursoService;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacao;
import br.com.j4business.saga.processoformacao.service.ProcessoFormacaoService;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidade;
import br.com.j4business.saga.processohabilidade.service.ProcessoHabilidadeService;

@Service("colaboradorService")
public class ColaboradorServiceImpl implements ColaboradorService {

	@Autowired
	private ColaboradorCertificacaoService colaboradorCertificacaoService;

	@Autowired
	private ColaboradorCursoService colaboradorCursoService;


	@Autowired
	private ColaboradorFormacaoService colaboradorFormacaoService;

	@Autowired
	private ColaboradorHabilidadeService colaboradorHabilidadeService;

	@Autowired
	private ColaboradorProcessoService colaboradorProcessoService;

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private ProcessoCertificacaoService processoCertificacaoService;

	@Autowired
	private ProcessoCursoService processoCursoService;

	@Autowired
	private ProcessoHabilidadeService processoHabilidadeService;

	@Autowired
	private ProcessoFormacaoService processoFormacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorService.class.getName());

	
	@Override
	public List<Colaborador> getColaboradorAll() {
		return colaboradorRepository.findAll();
	}

	@Override
	public Page<Colaborador> getColaboradorAll(Pageable pageable) {
		return colaboradorRepository.findAll(pageable);
	}

	@Override
	public Colaborador getColaboradorByColaboradorPK(long colaboradorPK) {
		
		return colaboradorRepository.findOne(colaboradorPK);
	}

	@Transactional
	public Colaborador create(ColaboradorForm colaboradorForm) {
		
		Colaborador colaborador = new Colaborador();
		
		colaborador = this.converteColaboradorForm(colaboradorForm);
		
		colaborador = entityManager.merge(colaborador);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Colaborador Create " + 	"\n Usuário => " + username + 
												" // Id => "+colaborador.getPessoaPK() + 
												" // Colaborador => "+colaborador.getPessoaNome() + 
												" // CPF  => "+ colaborador.getPessoaCPF());
		

		return colaborador;
		
	}

	@Transactional
	public Colaborador save(ColaboradorForm colaboradorForm) {
		
		Colaborador colaborador = new Colaborador();
		
		colaborador = this.converteColaboradorForm(colaboradorForm);
		
		colaborador = entityManager.merge(colaborador);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Colaborador Save " + 	"\n Usuário => " + username + 
												" // Id => "+colaborador.getPessoaPK() + 
												" // Colaborador => "+colaborador.getPessoaNome() + 
												" // CPF  => "+ colaborador.getPessoaCPF());
		

		return colaborador;
	}

	@Transactional
	public void delete(Long colaboradorId) {

		Colaborador colaborador = this.getColaboradorByColaboradorPK(colaboradorId);
		
		colaboradorRepository.delete(colaborador.getPessoaPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Colaborador Delete " + 	"\n Usuário => " + username + 
												" // Id => "+colaborador.getPessoaPK() + 
												" // Colaborador => "+colaborador.getPessoaNome() + 
												" // CPF  => "+ colaborador.getPessoaCPF());
		
			
    }

	@Transactional
	public Colaborador converteColaboradorForm(ColaboradorForm colaboradorForm) {
		
		Colaborador colaborador = new Colaborador();
		
		if (colaboradorForm.getColaboradorPK() > 0) {
			colaborador = this.getColaboradorByColaboradorPK(colaboradorForm.getColaboradorPK());
		}

		colaborador.setPessoaNome(colaboradorForm.getColaboradorNome().replaceAll("\\s+"," "));
		colaborador.setColaboradorApelido(colaboradorForm.getColaboradorApelido().replaceAll("\\s+"," "));
		colaborador.setPessoaCPF(colaboradorForm.getColaboradorCPF());
		
		colaborador.setPessoaCPF(colaboradorForm.getColaboradorCPF().replaceAll("  "," "));

		colaborador.setEnderecoCEP(colaboradorForm.getEnderecoCEP().replaceAll("\\s+"," "));
		colaborador.setEnderecoLogradouro(colaboradorForm.getEnderecoLogradouro().replaceAll("\\s+"," "));
		colaborador.setEnderecoBairro(colaboradorForm.getEnderecoBairro().replaceAll("\\s+"," "));
		colaborador.setEnderecoMunicipio(colaboradorForm.getEnderecoMunicipio().replaceAll("\\s+"," "));
		colaborador.setEnderecoUF(colaboradorForm.getEnderecoUF().replaceAll("\\s+"," "));

		colaborador.setTelefoneNumero(colaboradorForm.getTelefoneNumero().replaceAll("\\s+"," "));
		colaborador.setTelefoneOperadora(colaboradorForm.getTelefoneOperadora().replaceAll("\\s+"," "));

		colaborador.setEmailNome(colaboradorForm.getEmailNome().replaceAll("\\s+"," "));

		colaborador.setRedeSocialNome(colaboradorForm.getRedeSocialNome().replaceAll("\\s+"," "));
		colaborador.setRedeSocialIdentificacao(colaboradorForm.getRedeSocialIdentificacao().replaceAll("\\s+"," "));

		colaborador.setColaboradorMotivoOperacao(colaboradorForm.getColaboradorMotivoOperacao().replaceAll("\\s+"," "));
		colaborador.setColaboradorStatus(colaboradorForm.getColaboradorStatus()+"".replaceAll("\\s+"," "));
		Colaborador colaboradorResponsavel = this.getColaboradorByColaboradorPK(Long.parseLong(colaboradorForm.getColaboradorResponsavel()));
		colaborador.setColaboradorResponsavel(colaboradorResponsavel);
		
		return colaborador;
	}

	@Transactional
	public ColaboradorForm converteColaborador(Colaborador colaborador) {
	
		ColaboradorForm colaboradorForm = new ColaboradorForm();
		
		colaboradorForm.setColaboradorPK(colaborador.getPessoaPK());
		colaboradorForm.setColaboradorNome(colaborador.getPessoaNome());
		colaboradorForm.setColaboradorApelido(colaborador.getColaboradorApelido());
		colaboradorForm.setColaboradorCPF(colaborador.getPessoaCPF());
		
		colaboradorForm.setColaboradorResponsavel(colaborador.getColaboradorResponsavel().getPessoaPK()+"");
		
		colaboradorForm.setColaboradorCPF(colaborador.getPessoaCPF().replaceAll("  "," "));

		colaboradorForm.setEnderecoCEP(colaborador.getEnderecoCEP());
		colaboradorForm.setEnderecoLogradouro(colaborador.getEnderecoLogradouro());
		colaboradorForm.setEnderecoBairro(colaborador.getEnderecoBairro());
		colaboradorForm.setEnderecoMunicipio(colaborador.getEnderecoMunicipio());
		colaboradorForm.setEnderecoUF(colaborador.getEnderecoUF());
		
		colaboradorForm.setTelefoneNumero(colaborador.getTelefoneNumero());
		colaboradorForm.setTelefoneOperadora(colaborador.getTelefoneOperadora());

		colaboradorForm.setEmailNome(colaborador.getEmailNome());

		colaboradorForm.setRedeSocialNome(colaborador.getRedeSocialNome());
		colaboradorForm.setRedeSocialIdentificacao(colaborador.getRedeSocialIdentificacao());

		colaboradorForm.setColaboradorMotivoOperacao(colaborador.getColaboradorMotivoOperacao());
		colaboradorForm.setColaboradorStatus(AtributoStatus.valueOf(colaborador.getColaboradorStatus()));

	return colaboradorForm;
	}
	
	@Transactional
	public ColaboradorForm converteColaboradorView(Colaborador colaborador) {
	
		ColaboradorForm colaboradorForm = new ColaboradorForm();
		
		colaboradorForm.setColaboradorPK(colaborador.getPessoaPK());
		colaboradorForm.setColaboradorNome(colaborador.getPessoaNome());
		colaboradorForm.setColaboradorApelido(colaborador.getColaboradorApelido());
		colaboradorForm.setColaboradorCPF(colaborador.getPessoaCPF());
		
		colaboradorForm.setColaboradorResponsavel(colaborador.getColaboradorResponsavel().getPessoaNome());
		
		colaboradorForm.setColaboradorCPF(colaborador.getPessoaCPF().replaceAll("  "," "));

		colaboradorForm.setEnderecoCEP(colaborador.getEnderecoCEP());
		colaboradorForm.setEnderecoLogradouro(colaborador.getEnderecoLogradouro());
		colaboradorForm.setEnderecoBairro(colaborador.getEnderecoBairro());
		colaboradorForm.setEnderecoMunicipio(colaborador.getEnderecoMunicipio());
		colaboradorForm.setEnderecoUF(colaborador.getEnderecoUF());
		
		colaboradorForm.setTelefoneNumero(colaborador.getTelefoneNumero());
		colaboradorForm.setTelefoneOperadora(colaborador.getTelefoneOperadora());

		colaboradorForm.setEmailNome(colaborador.getEmailNome());

		colaboradorForm.setRedeSocialNome(colaborador.getRedeSocialNome());
		colaboradorForm.setRedeSocialIdentificacao(colaborador.getRedeSocialIdentificacao());

		colaboradorForm.setColaboradorMotivoOperacao(colaborador.getColaboradorMotivoOperacao());
		colaboradorForm.setColaboradorStatus(AtributoStatus.valueOf(colaborador.getColaboradorStatus()));

		return colaboradorForm;
	}
	

	@Transactional
	public ColaboradorForm colaboradorParametros(ColaboradorForm colaboradorForm) {
	
		colaboradorForm.setColaboradorStatus(AtributoStatus.valueOf("ATIVO"));
		
	return colaboradorForm;
	}

	@Override
	public List<Colaborador> getByColaboradorApelido(String colaboradorApelido) {
		return colaboradorRepository.findByColaboradorApelido(colaboradorApelido);
	}

	@Override
	public List<Colaborador> getByColaboradorApelido(String colaboradorApelido,Pageable pageable) {
		return colaboradorRepository.findByColaboradorApelido(colaboradorApelido,pageable);
	}

	@Override
	public List<Colaborador> getByColaboradorNome(String colaboradorNome) {
		return colaboradorRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<Colaborador> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorRepository.findByColaboradorNome(colaboradorNome,pageable);
	}
	

	@Override
	public List<ColaboradorRel002> preparaRelatorio002(Pageable pageable) {
		
		boolean existeFormacao = false;
		
		List<ColaboradorRel002> colaboradorRel002List = new ArrayList<ColaboradorRel002>();
		
		List<Colaborador> colaboradorList = this.getColaboradorAll();

		
		for (Iterator<Colaborador> iterator = colaboradorList.iterator(); iterator.hasNext();) {
			Colaborador colaborador = (Colaborador) iterator.next();

			existeFormacao = false;

			Pageable colaboradorProcessoPageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","processo.processoNome");
			List<ColaboradorProcesso> colaboradorProcessoList = colaboradorProcessoService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorProcessoPageable);

			Pageable colaboradorFormacaoTopOne = new PageRequest(0, 1);
			List<ColaboradorFormacao> colaboradorFormacaoList = colaboradorFormacaoService.getMaxNivelByColaboradorPK(colaborador.getPessoaPK(),colaboradorFormacaoTopOne);

			if ( ! colaboradorProcessoList.isEmpty()) {

				for (Iterator<ColaboradorProcesso> iterator2 = colaboradorProcessoList.iterator(); iterator2.hasNext();) {
					
					ColaboradorProcesso colaboradorProcesso = (ColaboradorProcesso) iterator2.next();
				
					Pageable processoFormacaoTopOne = new PageRequest(0, 1);
					List<ProcessoFormacao> processoFormacaoList = processoFormacaoService.getMaxNivelByProcessoPK(colaboradorProcesso.getProcesso().getProcessoPK(),processoFormacaoTopOne);

					if (processoFormacaoList.isEmpty()) {
						
						ColaboradorRel002 colaboradorRel002 = new ColaboradorRel002();
						colaboradorRel002.setColaboradorNome(colaborador.getPessoaNome());
						colaboradorRel002.setColaboradorFormacao("Colaborador sem formação vinculada");
						if ( ! colaboradorFormacaoList.isEmpty()) {
							colaboradorRel002.setColaboradorFormacao(colaboradorFormacaoList.iterator().next().getFormacao().getFormacaoNome());
						}
						colaboradorRel002.setProcessoNome(colaboradorProcesso.getProcesso().getProcessoNome());
						colaboradorRel002.setProcessoFormacao("Processo sem formação vinculada");
						colaboradorRel002List.add(colaboradorRel002);
						existeFormacao = true;
						
					} else {

						for (Iterator<ProcessoFormacao> iterator3 = processoFormacaoList.iterator(); iterator3.hasNext();) {
							
							ProcessoFormacao processoFormacao = (ProcessoFormacao) iterator3.next();
							
							if (colaboradorFormacaoList.isEmpty()) {
								
								ColaboradorRel002 colaboradorRel002 = new ColaboradorRel002();
								colaboradorRel002.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel002.setColaboradorFormacao("Colaborador sem formação vinculada");
								colaboradorRel002.setProcessoNome(processoFormacao.getProcesso().getProcessoNome());
								colaboradorRel002.setProcessoFormacao(processoFormacao.getFormacao().getFormacaoNome());
								colaboradorRel002List.add(colaboradorRel002);
								existeFormacao = true;
								
							}
								
							for (Iterator<ColaboradorFormacao> iterator4 = colaboradorFormacaoList.iterator(); iterator4.hasNext();) {
								ColaboradorFormacao colaboradorFormacao = (ColaboradorFormacao) iterator4.next();
								
								if (colaboradorFormacaoList.iterator().next().getFormacao().getFormacaoNivel() != processoFormacaoList.iterator().next().getFormacao().getFormacaoNivel()) {
									
									ColaboradorRel002 colaboradorRel002 = new ColaboradorRel002();
									colaboradorRel002.setColaboradorNome(colaborador.getPessoaNome());
									colaboradorRel002.setColaboradorFormacao(colaboradorFormacao.getFormacao().getFormacaoNome());
									colaboradorRel002.setProcessoNome(processoFormacao.getProcesso().getProcessoNome());
									colaboradorRel002.setProcessoFormacao(processoFormacao.getFormacao().getFormacaoNome());
									colaboradorRel002List.add(colaboradorRel002);
									
								} 
								
								existeFormacao = true;
								
							}
	
							if ( ! existeFormacao) {
								ColaboradorRel002 colaboradorRel002 = new ColaboradorRel002();
								colaboradorRel002.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel002.setColaboradorFormacao("Colaborador sem formação vinculada");
								colaboradorRel002.setProcessoNome(processoFormacao.getProcesso().getProcessoNome());
								colaboradorRel002.setProcessoFormacao(processoFormacao.getFormacao().getFormacaoNome());
								colaboradorRel002List.add(colaboradorRel002);
	
								existeFormacao = true;
							}
						} 
					}
				}
			}

			if ( ! existeFormacao) {
				ColaboradorRel002 colaboradorRel002 = new ColaboradorRel002();
				colaboradorRel002.setColaboradorNome(colaborador.getPessoaNome());
				colaboradorRel002.setColaboradorFormacao("Colaborador sem processo vinculado");
				if ( ! colaboradorFormacaoList.isEmpty()) {
					colaboradorRel002.setColaboradorFormacao(colaboradorFormacaoList.iterator().next().getFormacao().getFormacaoNome());
				}
				colaboradorRel002.setProcessoNome("Colaborador sem processo vinculado");
				colaboradorRel002.setProcessoFormacao("");
				colaboradorRel002List.add(colaboradorRel002);
			}			
		}
		return colaboradorRel002List;
	}

	@Override
	public List<ColaboradorRel003> preparaRelatorio003(Pageable pageable) {
		
		boolean existeCertificacao = false;
		
		List<ColaboradorRel003> colaboradorRel003List = new ArrayList<ColaboradorRel003>();
		
		List<Colaborador> colaboradorList = this.getColaboradorAll();

		
		for (Iterator<Colaborador> iterator = colaboradorList.iterator(); iterator.hasNext();) {
			Colaborador colaborador = (Colaborador) iterator.next();

			existeCertificacao = false;

			Pageable colaboradorProcessoPageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","processo.processoNome");
			List<ColaboradorProcesso> colaboradorProcessoList = colaboradorProcessoService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorProcessoPageable);

			Pageable colaboradorCertificacaoPageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","certificacao.certificacaoNome");
			List<ColaboradorCertificacao> colaboradorCertificacaoList = colaboradorCertificacaoService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorCertificacaoPageable);

			if ( ! colaboradorProcessoList.isEmpty()) {

				for (Iterator<ColaboradorProcesso> iterator2 = colaboradorProcessoList.iterator(); iterator2.hasNext();) {
					
					ColaboradorProcesso colaboradorProcesso = (ColaboradorProcesso) iterator2.next();
				
					Pageable processoCertificacaoPageable = new PageRequest(0, 200,Direction.ASC, "processo.processoNome","certificacao.certificacaoNome");
					List<ProcessoCertificacao> processoCertificacaoList = processoCertificacaoService.getByProcessoPK(colaboradorProcesso.getProcesso().getProcessoPK(), processoCertificacaoPageable);

					if (processoCertificacaoList.isEmpty()) {
						
						ColaboradorRel003 colaboradorRel003 = new ColaboradorRel003();
						colaboradorRel003.setColaboradorNome(colaborador.getPessoaNome());
						colaboradorRel003.setColaboradorCertificacao("Colaborador sem certificação vinculada");
						if ( ! colaboradorCertificacaoList.isEmpty()) {
							colaboradorRel003.setColaboradorCertificacao(colaboradorCertificacaoList.iterator().next().getCertificacao().getCertificacaoNome());
						}
						
						colaboradorRel003.setProcessoNome(colaboradorProcesso.getProcesso().getProcessoNome());
						colaboradorRel003.setProcessoCertificacao("Processo sem certificação vinculada");
						colaboradorRel003List.add(colaboradorRel003);
						existeCertificacao = true;
						
					} else {

						for (Iterator<ProcessoCertificacao> iterator3 = processoCertificacaoList.iterator(); iterator3.hasNext();) {
							
							ProcessoCertificacao processoCertificacao = (ProcessoCertificacao) iterator3.next();
							
							if (colaboradorCertificacaoList.isEmpty()) {
								
								ColaboradorRel003 colaboradorRel003 = new ColaboradorRel003();
								colaboradorRel003.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel003.setColaboradorCertificacao("Colaborador sem certificação vinculada");
								colaboradorRel003.setProcessoNome(processoCertificacao.getProcesso().getProcessoNome());
								colaboradorRel003.setProcessoCertificacao(processoCertificacao.getCertificacao().getCertificacaoNome());
								colaboradorRel003List.add(colaboradorRel003);
								existeCertificacao = true;
								
							}
								
							for (Iterator<ColaboradorCertificacao> iterator4 = colaboradorCertificacaoList.iterator(); iterator4.hasNext();) {
								ColaboradorCertificacao colaboradorCertificacao = (ColaboradorCertificacao) iterator4.next();
								
								if (colaboradorCertificacao.getCertificacao().getCertificacaoPK() != processoCertificacao.getCertificacao().getCertificacaoPK()) {
									
									ColaboradorRel003 colaboradorRel003 = new ColaboradorRel003();
									colaboradorRel003.setColaboradorNome(colaborador.getPessoaNome());
									colaboradorRel003.setColaboradorCertificacao(colaboradorCertificacao.getCertificacao().getCertificacaoNome());
									colaboradorRel003.setProcessoNome(processoCertificacao.getProcesso().getProcessoNome());
									colaboradorRel003.setProcessoCertificacao(processoCertificacao.getCertificacao().getCertificacaoNome());
									colaboradorRel003List.add(colaboradorRel003);
	
								} 
								existeCertificacao = true;
								
							}
	
							if ( ! existeCertificacao) {
								ColaboradorRel003 colaboradorRel003 = new ColaboradorRel003();
								colaboradorRel003.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel003.setColaboradorCertificacao("Colaborador sem certificação vinculada");
								colaboradorRel003.setProcessoNome(processoCertificacao.getProcesso().getProcessoNome());
								colaboradorRel003.setProcessoCertificacao(processoCertificacao.getCertificacao().getCertificacaoNome());
								colaboradorRel003List.add(colaboradorRel003);
	
								existeCertificacao = true;
							}
						} 
					}
				}
			}

			if ( ! existeCertificacao) {
				ColaboradorRel003 colaboradorRel003 = new ColaboradorRel003();
				colaboradorRel003.setColaboradorNome(colaborador.getPessoaNome());
				colaboradorRel003.setColaboradorCertificacao("Colaborador sem certificação vinculada");
				if ( ! colaboradorCertificacaoList.isEmpty()) {
					colaboradorRel003.setColaboradorCertificacao(colaboradorCertificacaoList.iterator().next().getCertificacao().getCertificacaoNome());
				}
				colaboradorRel003.setProcessoNome("Colaborador sem processo vinculado");
				colaboradorRel003.setProcessoCertificacao("");
				colaboradorRel003List.add(colaboradorRel003);
			}			
		}
		return colaboradorRel003List;
	}


	@Override
	public List<ColaboradorRel004> preparaRelatorio004(Pageable pageable) {
		
		boolean existeCurso = false;
		
		List<ColaboradorRel004> colaboradorRel004List = new ArrayList<ColaboradorRel004>();
		
		List<Colaborador> colaboradorList = this.getColaboradorAll();

		
		for (Iterator<Colaborador> iterator = colaboradorList.iterator(); iterator.hasNext();) {
			Colaborador colaborador = (Colaborador) iterator.next();

			existeCurso = false;

			Pageable colaboradorProcessoPageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","processo.processoNome");
			List<ColaboradorProcesso> colaboradorProcessoList = colaboradorProcessoService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorProcessoPageable);

			Pageable colaboradorCursoPageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","curso.cursoNome");
			List<ColaboradorCurso> colaboradorCursoList = colaboradorCursoService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorCursoPageable);

			if ( ! colaboradorProcessoList.isEmpty()) {

				for (Iterator<ColaboradorProcesso> iterator2 = colaboradorProcessoList.iterator(); iterator2.hasNext();) {
					
					ColaboradorProcesso colaboradorProcesso = (ColaboradorProcesso) iterator2.next();
				
					Pageable processoCursoPageable = new PageRequest(0, 200,Direction.ASC, "processo.processoNome","curso.cursoNome");
					List<ProcessoCurso> processoCursoList = processoCursoService.getByProcessoPK(colaboradorProcesso.getProcesso().getProcessoPK(), processoCursoPageable);

					if (processoCursoList.isEmpty()) {
						
						ColaboradorRel004 colaboradorRel004 = new ColaboradorRel004();
						colaboradorRel004.setColaboradorNome(colaborador.getPessoaNome());
						colaboradorRel004.setColaboradorCurso("Colaborador sem curso vinculado");
						if ( ! colaboradorCursoList.isEmpty()) {
							colaboradorRel004.setColaboradorCurso(colaboradorCursoList.iterator().next().getCurso().getCursoNome());
						}

						colaboradorRel004.setProcessoNome(colaboradorProcesso.getProcesso().getProcessoNome());
						colaboradorRel004.setProcessoCurso("Processo sem curso vinculado");
						colaboradorRel004List.add(colaboradorRel004);
						existeCurso = true;
						
					} else {

						for (Iterator<ProcessoCurso> iterator3 = processoCursoList.iterator(); iterator3.hasNext();) {
							
							ProcessoCurso processoCurso = (ProcessoCurso) iterator3.next();
							
							if (colaboradorCursoList.isEmpty()) {
								
								ColaboradorRel004 colaboradorRel004 = new ColaboradorRel004();
								colaboradorRel004.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel004.setColaboradorCurso("Colaborador sem curso vinculado");
								colaboradorRel004.setProcessoNome(processoCurso.getProcesso().getProcessoNome());
								colaboradorRel004.setProcessoCurso(processoCurso.getCurso().getCursoNome());
								colaboradorRel004List.add(colaboradorRel004);
								existeCurso = true;
								
							}
								
							for (Iterator<ColaboradorCurso> iterator4 = colaboradorCursoList.iterator(); iterator4.hasNext();) {
								ColaboradorCurso colaboradorCurso = (ColaboradorCurso) iterator4.next();
								
								if (colaboradorCurso.getCurso().getCursoPK() != processoCurso.getCurso().getCursoPK()) {
									
									ColaboradorRel004 colaboradorRel004 = new ColaboradorRel004();
									colaboradorRel004.setColaboradorNome(colaborador.getPessoaNome());
									colaboradorRel004.setColaboradorCurso(colaboradorCurso.getCurso().getCursoNome());
									colaboradorRel004.setProcessoNome(processoCurso.getProcesso().getProcessoNome());
									colaboradorRel004.setProcessoCurso(processoCurso.getCurso().getCursoNome());
									colaboradorRel004List.add(colaboradorRel004);
	
								} 

								existeCurso = true;
								
							}
	
							if ( ! existeCurso) {
								ColaboradorRel004 colaboradorRel004 = new ColaboradorRel004();
								colaboradorRel004.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel004.setColaboradorCurso("Colaborador sem curso vinculado");
								colaboradorRel004.setProcessoNome(processoCurso.getProcesso().getProcessoNome());
								colaboradorRel004.setProcessoCurso(processoCurso.getCurso().getCursoNome());
								colaboradorRel004List.add(colaboradorRel004);
	
								existeCurso = true;
							}
						} 
					}
				}
			}

			if ( ! existeCurso) {
				ColaboradorRel004 colaboradorRel004 = new ColaboradorRel004();
				colaboradorRel004.setColaboradorNome(colaborador.getPessoaNome());
				colaboradorRel004.setColaboradorCurso("Colaborador sem curso vinculado");
				if ( ! colaboradorCursoList.isEmpty()) {
					colaboradorRel004.setColaboradorCurso(colaboradorCursoList.iterator().next().getCurso().getCursoNome());
				}
				colaboradorRel004.setProcessoNome("Colaborador sem processo vinculado");
				colaboradorRel004.setProcessoCurso("");
				colaboradorRel004List.add(colaboradorRel004);
			}			
		}
		return colaboradorRel004List;
	}


	@Override
	public List<ColaboradorRel005> preparaRelatorio005(Pageable pageable) {
		
		boolean existeHabilidade = false;
		
		List<ColaboradorRel005> colaboradorRel005List = new ArrayList<ColaboradorRel005>();
		
		List<Colaborador> colaboradorList = this.getColaboradorAll();

		
		for (Iterator<Colaborador> iterator = colaboradorList.iterator(); iterator.hasNext();) {
			Colaborador colaborador = (Colaborador) iterator.next();

			existeHabilidade = false;

			Pageable colaboradorProcessoPageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","processo.processoNome");
			List<ColaboradorProcesso> colaboradorProcessoList = colaboradorProcessoService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorProcessoPageable);

			Pageable colaboradorHabilidadePageable = new PageRequest(0, 200,Direction.ASC, "colaborador.pessoaNome","habilidade.habilidadeNome");
			List<ColaboradorHabilidade> colaboradorHabilidadeList = colaboradorHabilidadeService.getByColaboradorPK(colaborador.getPessoaPK(), colaboradorHabilidadePageable);

			if ( ! colaboradorProcessoList.isEmpty()) {

				for (Iterator<ColaboradorProcesso> iterator2 = colaboradorProcessoList.iterator(); iterator2.hasNext();) {
					
					ColaboradorProcesso colaboradorProcesso = (ColaboradorProcesso) iterator2.next();
				
					Pageable processoHabilidadePageable = new PageRequest(0, 200,Direction.ASC, "processo.processoNome","habilidade.habilidadeNome");
					List<ProcessoHabilidade> processoHabilidadeList = processoHabilidadeService.getByProcessoPK(colaboradorProcesso.getProcesso().getProcessoPK(), processoHabilidadePageable);

					if (processoHabilidadeList.isEmpty()) {
						
						ColaboradorRel005 colaboradorRel005 = new ColaboradorRel005();
						colaboradorRel005.setColaboradorNome(colaborador.getPessoaNome());
						colaboradorRel005.setColaboradorHabilidade("Colaborador sem habilidade vinvulada");
						if ( ! colaboradorHabilidadeList.isEmpty()) {
							colaboradorRel005.setColaboradorHabilidade(colaboradorHabilidadeList.iterator().next().getHabilidade().getHabilidadeNome());
						}
						
						colaboradorRel005.setProcessoNome(colaboradorProcesso.getProcesso().getProcessoNome());
						colaboradorRel005.setProcessoHabilidade("Processo sem habilidade vinculada");
						colaboradorRel005List.add(colaboradorRel005);
						existeHabilidade = true;
						
					} else {

						for (Iterator<ProcessoHabilidade> iterator3 = processoHabilidadeList.iterator(); iterator3.hasNext();) {
							
							ProcessoHabilidade processoHabilidade = (ProcessoHabilidade) iterator3.next();
							
							if (colaboradorHabilidadeList.isEmpty()) {
								
								ColaboradorRel005 colaboradorRel005 = new ColaboradorRel005();
								colaboradorRel005.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel005.setColaboradorHabilidade("Colaborador sem habilidade vinvulada");
								colaboradorRel005.setProcessoNome(processoHabilidade.getProcesso().getProcessoNome());
								colaboradorRel005.setProcessoHabilidade(processoHabilidade.getHabilidade().getHabilidadeNome());
								colaboradorRel005List.add(colaboradorRel005);
								existeHabilidade = true;
								
							}
								
							for (Iterator<ColaboradorHabilidade> iterator4 = colaboradorHabilidadeList.iterator(); iterator4.hasNext();) {
								ColaboradorHabilidade colaboradorHabilidade = (ColaboradorHabilidade) iterator4.next();
								
								if (colaboradorHabilidade.getHabilidade().getHabilidadePK() != processoHabilidade.getHabilidade().getHabilidadePK()) {
									
									ColaboradorRel005 colaboradorRel005 = new ColaboradorRel005();
									colaboradorRel005.setColaboradorNome(colaborador.getPessoaNome());
									colaboradorRel005.setColaboradorHabilidade(colaboradorHabilidade.getHabilidade().getHabilidadeNome());
									colaboradorRel005.setProcessoNome(processoHabilidade.getProcesso().getProcessoNome());
									colaboradorRel005.setProcessoHabilidade(processoHabilidade.getHabilidade().getHabilidadeNome());
									colaboradorRel005List.add(colaboradorRel005);
	
								} 
								existeHabilidade = true;
								
							}
	
							if ( ! existeHabilidade) {
								ColaboradorRel005 colaboradorRel005 = new ColaboradorRel005();
								colaboradorRel005.setColaboradorNome(colaborador.getPessoaNome());
								colaboradorRel005.setColaboradorHabilidade("Colaborador sem habilidade vinculada");
								colaboradorRel005.setProcessoNome(processoHabilidade.getProcesso().getProcessoNome());
								colaboradorRel005.setProcessoHabilidade(processoHabilidade.getHabilidade().getHabilidadeNome());
								colaboradorRel005List.add(colaboradorRel005);
	
								existeHabilidade = true;
							}
						} 
					}
				}
			}

			if ( ! existeHabilidade) {
				ColaboradorRel005 colaboradorRel005 = new ColaboradorRel005();
				colaboradorRel005.setColaboradorNome(colaborador.getPessoaNome());
				colaboradorRel005.setColaboradorHabilidade("Colaborador sem habilidade vinculada");
				if ( ! colaboradorHabilidadeList.isEmpty()) {
					colaboradorRel005.setColaboradorHabilidade(colaboradorHabilidadeList.iterator().next().getHabilidade().getHabilidadeNome());
				}
				colaboradorRel005.setProcessoNome("Colaborador sem processo vinculado");
				colaboradorRel005.setProcessoHabilidade("");
				colaboradorRel005List.add(colaboradorRel005);
			}			
		}
		return colaboradorRel005List;
	}




}
