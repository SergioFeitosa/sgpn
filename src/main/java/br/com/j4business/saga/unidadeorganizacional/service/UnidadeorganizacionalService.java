package br.com.j4business.saga.unidadeorganizacional.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalForm;

@Service
public interface UnidadeorganizacionalService {
	
	public List<Unidadeorganizacional> getUnidadeorganizacionalAll();
	public Page<Unidadeorganizacional> getUnidadeorganizacionalAll(Pageable pageable);
	public Unidadeorganizacional getUnidadeorganizacionalByUnidadeorganizacionalPK(long unidadeorganizacionalPK);
	public Unidadeorganizacional create(UnidadeorganizacionalForm unidadeorganizacionalForm);
	public Unidadeorganizacional save(UnidadeorganizacionalForm unidadeorganizacionalForm);
	public void delete(Long unidadeorganizacionalPK);
	
	public Unidadeorganizacional converteUnidadeorganizacionalForm(UnidadeorganizacionalForm unidadeorganizacionalForm);
	public UnidadeorganizacionalForm converteUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional);
	public UnidadeorganizacionalForm converteUnidadeorganizacionalView(Unidadeorganizacional unidadeorganizacional);

	public UnidadeorganizacionalForm unidadeorganizacionalParametros(UnidadeorganizacionalForm unidadeorganizacionalForm);

	public List<Unidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable);
	public List<Unidadeorganizacional> getByUnidadeorganizacionalDescricao(String unidadeorganizacionalDescricao,Pageable pageable);
	public List<Unidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome);
	public List<Unidadeorganizacional> getByUnidadeorganizacionalDescricao(String unidadeorganizacionalDescricao);

}