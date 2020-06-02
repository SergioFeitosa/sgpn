package br.com.j4business.saga.colaborador.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.model.ColaboradorForm;
import br.com.j4business.saga.colaborador.model.ColaboradorRel002;
import br.com.j4business.saga.colaborador.model.ColaboradorRel003;
import br.com.j4business.saga.colaborador.model.ColaboradorRel004;
import br.com.j4business.saga.colaborador.model.ColaboradorRel005;

@Service
public interface ColaboradorService {
	
	public List<Colaborador> getColaboradorAll();
	public Page<Colaborador> getColaboradorAll(Pageable pageable);
	public Colaborador getColaboradorByColaboradorPK(long colaboradorPK);
	public Colaborador create(ColaboradorForm colaboradorForm);
	public Colaborador save(ColaboradorForm colaboradorForm);
	public void delete(Long colaboradorPK);
	
	public Colaborador converteColaboradorForm(ColaboradorForm colaboradorForm);
	public ColaboradorForm converteColaborador(Colaborador colaborador);
	public ColaboradorForm converteColaboradorView(Colaborador colaborador);

	public ColaboradorForm colaboradorParametros(ColaboradorForm colaboradorForm);
	public List<Colaborador> getByColaboradorApelido(String colaboradorApelido);
	public List<Colaborador> getByColaboradorApelido(String colaboradorApelido,Pageable pageable);
	public List<Colaborador> getByColaboradorNome(String colaboradorNome);
	public List<Colaborador> getByColaboradorNome(String colaboradorNome,Pageable pageable);

	public List<ColaboradorRel002> preparaRelatorio002(Pageable pageable);
	public List<ColaboradorRel003> preparaRelatorio003(Pageable pageable);
	public List<ColaboradorRel004> preparaRelatorio004(Pageable pageable);
	public List<ColaboradorRel005> preparaRelatorio005(Pageable pageable);
}