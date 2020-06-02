package br.com.j4business.saga.pais.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.pais.model.Pais;
import br.com.j4business.saga.pais.model.PaisForm;

@Service
public interface PaisService {
	
	public List<Pais> getPaisAll();
	public Page<Pais> getPaisAll(Pageable pageable);
	public Pais getPaisByPaisPK(long paisPK);
	public Pais create(PaisForm paisForm);
	public Pais save(Pais pais);
	public void delete(Long paisPK);
	
	public Pais convertePaisForm(PaisForm paisForm);
	public PaisForm convertePais(Pais pais);
	public PaisForm convertePaisView(Pais pais);

	public PaisForm paisParametros(PaisForm paisForm);

	public List<Pais> getByPaisNome(String paisNome,Pageable pageable);
	public List<Pais> getByPaisSigla(String paisSigla,Pageable pageable);
	public List<Pais> getByPaisNome(String paisNome);
	public List<Pais> getByPaisSigla(String paisSigla);

}