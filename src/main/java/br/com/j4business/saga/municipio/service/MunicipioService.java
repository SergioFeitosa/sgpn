package br.com.j4business.saga.municipio.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.municipio.model.Municipio;
import br.com.j4business.saga.municipio.model.MunicipioForm;

@Service
public interface MunicipioService {
	
	public List<Municipio> getMunicipioAll();
	public Page<Municipio> getMunicipioAll(Pageable pageable);
	public Municipio getMunicipioByMunicipioPK(long municipioPK);
	public Municipio create(MunicipioForm municipioForm);
	public Municipio save(Municipio municipio);
	public void delete(Long municipioPK);
	
	public Municipio converteMunicipioForm(MunicipioForm municipioForm);
	public MunicipioForm converteMunicipio(Municipio municipio);
	public MunicipioForm converteMunicipioView(Municipio municipio);

	public MunicipioForm municipioParametros(MunicipioForm municipioForm);

	public List<Municipio> getByMunicipioNome(String municipioNome);
	public List<Municipio> getByMunicipioNome(String municipioNome,Pageable pageable);
	public List<Municipio> getByMunicipioCEP(String municipioCEP);
	public List<Municipio> getByMunicipioCEP(String municipioCEP,Pageable pageable);
	public List<Municipio> getByMunicipioUF(String municipioUF);
	public List<Municipio> getByMunicipioUF(String municipioUF,Pageable pageable);

}