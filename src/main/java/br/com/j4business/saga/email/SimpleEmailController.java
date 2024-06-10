package br.com.j4business.saga.email;

import java.util.Date;
import java.util.Iterator;
import java.util.List;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agendacontrato.service.AgendaContratoService;
import br.com.j4business.saga.agendaevento.service.AgendaEventoService;
import br.com.j4business.saga.agendaplanejamento.service.AgendaPlanejamentoService;
import br.com.j4business.saga.agendatreinamento.service.AgendaTreinamentoService;
import br.com.j4business.saga.atividade.service.AtividadeService;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.estruturafisica.service.EstruturafisicaService;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.ocorrencia.service.OcorrenciaService;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.servico.service.ServicoService;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import jakarta.mail.internet.MimeMessage;

@Controller
@EnableScheduling
public class SimpleEmailController {
    
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private AgendaContratoService agendaContratoService;

    @Autowired
    private AgendaEventoService agendaEventoService;

    @Autowired
    private AgendaPlanejamentoService agendaPlanejamentoService;

    @Autowired
    private AgendaTreinamentoService agendaTreinamentoService;

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private EstruturafisicaService estruturafisicaService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private OcorrenciaService ocorrenciaService;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private ServicoService servicoService;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;
    
	private static Logger logger = LogManager.getLogger(SimpleEmailController.class.getName());
    
    @RequestMapping("/email")
    @ResponseBody
    //@Scheduled(cron = "0 * * * * ?")
    ModelAndView home() {
        try {
            sendEmail();
    		logger.info("Email enviado com sucesso"); 
        }catch(Exception ex) {
    		logger.info("Email Não enviado"); 
        }

    	ModelAndView mv = new ModelAndView("index");
		mv.addObject("atividadeList", atividadeService.getAtividadeAll());
		mv.addObject("cenarioList", cenarioService.getCenarioAll());
		mv.addObject("colaboradorList", colaboradorService.getColaboradorAll());
		mv.addObject("contratoList", contratoService.getContratoAll());
		mv.addObject("elementoList", elementoService.getElementoAll());
		mv.addObject("estruturaFisicaList", estruturafisicaService.getEstruturafisicaAll());
		mv.addObject("eventoList", eventoService.getEventoAll());
		mv.addObject("ocorrenciaList", ocorrenciaService.getOcorrenciaAll());
		mv.addObject("planejamentoList", planejamentoService.getPlanejamentoAll());
		mv.addObject("processoList", processoService.getProcessoAll());
		mv.addObject("servicoList", servicoService.getServicoAll());
		mv.addObject("unidadeOrganizacionalList", unidadeorganizacionalService.getUnidadeorganizacionalAll());
		mv.addObject("usuarioNome",usuarioSeguranca.getUsuarioLogado()); mv.addObject("standardDate",new Date());

    	return mv;
    }

    private void sendEmail() throws Exception{

    	List<Mensagem> agendaEventoMensagemList = agendaEventoService.getAgendaEventoMensagemAll();
        
        for (Iterator<Mensagem> iterator = agendaEventoMensagemList.iterator(); iterator.hasNext();) {
			Mensagem mensagem = (Mensagem) iterator.next();

	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        String assunto = null;
	        String corpo = null;
	        String[] destinatarios = null;
	        
			assunto = mensagem.getAssunto();
			corpo = mensagem.getCorpo();
			destinatarios = (mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));

	        helper.setTo(destinatarios);
	        helper.setText(corpo);
	        helper.setSubject(assunto);
			
	        sender.send(message);

        }
        
    	List<Mensagem> agendaContratoMensagemList = agendaContratoService.getAgendaContratoMensagemAll();
        
        for (Iterator<Mensagem> iterator = agendaContratoMensagemList.iterator(); iterator.hasNext();) {
			Mensagem mensagem = (Mensagem) iterator.next();

	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        String assunto = null;
	        String corpo = null;
	        String[] destinatarios = null;
	        
			assunto = mensagem.getAssunto();
			corpo = mensagem.getCorpo();
			destinatarios = (mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));

	        helper.setTo(destinatarios);
	        helper.setText(corpo);
	        helper.setSubject(assunto);
			
	        sender.send(message);

        }
        
    	List<Mensagem> agendaPlanejamentoMensagemList = agendaPlanejamentoService.getAgendaPlanejamentoMensagemAll();
        
        for (Iterator<Mensagem> iterator = agendaPlanejamentoMensagemList.iterator(); iterator.hasNext();) {
			Mensagem mensagem = (Mensagem) iterator.next();

	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        String assunto = null;
	        String corpo = null;
	        String[] destinatarios = null;
	        
			assunto = mensagem.getAssunto();
			corpo = mensagem.getCorpo();
			destinatarios = (mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));

	        helper.setTo(destinatarios);
	        helper.setText(corpo);
	        helper.setSubject(assunto);
			
	        sender.send(message);

        }
        
    	List<Mensagem> agendaTreinamentoMensagemList = agendaTreinamentoService.getAgendaTreinamentoMensagemAll();
        
        for (Iterator<Mensagem> iterator = agendaTreinamentoMensagemList.iterator(); iterator.hasNext();) {
			Mensagem mensagem = (Mensagem) iterator.next();

	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        String assunto = null;
	        String corpo = null;
	        String[] destinatarios = null;
	        
			assunto = mensagem.getAssunto();
			corpo = mensagem.getCorpo();
			destinatarios = (mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));

	        helper.setTo(destinatarios);
	        helper.setText(corpo);
	        helper.setSubject(assunto);
			
	        sender.send(message);

        }
        
    	List<Mensagem> colaboradorTreinamentoMensagemList = agendaTreinamentoService.getAgendaTreinamentoMensagemColaboradorAll();
        
        for (Iterator<Mensagem> iterator = colaboradorTreinamentoMensagemList.iterator(); iterator.hasNext();) {
			Mensagem mensagem = (Mensagem) iterator.next();

	        MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        String assunto = null;
	        String corpo = null;
	        String[] destinatarios = null;
	        
			assunto = mensagem.getAssunto();
			corpo = mensagem.getCorpo();
			destinatarios = (mensagem.getDestinatarios().toArray(new String[mensagem.getDestinatarios().size()]));

	        helper.setTo(destinatarios);
	        helper.setText(corpo);
	        helper.setSubject(assunto);
			
	        sender.send(message);

        }
        
    }
    
}
