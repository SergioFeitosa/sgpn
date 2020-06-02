package br.com.j4business.saga.colaborador.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.j4business.saga.colaborador.message.ColaboradorResponse;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;

@RestController
public class ColaboradorRestController {

	@Autowired
	private ColaboradorService colaboradorService;

	@RequestMapping(path = "/colaboradores", method = RequestMethod.GET)
	public List<Colaborador> getAllColaboradors() {
		return colaboradorService.getColaboradorAll();
	}

	@RequestMapping(value = "/colaborador/{colaboradorPK}", method = RequestMethod.GET)
	public Colaborador getColaboradorById(@PathVariable("colaboradorPK") long colaboradorPK) {
		return colaboradorService.getColaboradorByColaboradorPK(colaboradorPK);
	}

	@RequestMapping(value = "/deleteColaborador/{colaboradorPK}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteColaborador(@PathVariable("colaboradorPK") Long colaboradorPK) {
		colaboradorService.delete(colaboradorPK);
		return colaboradorPK.toString();

	}

	@RequestMapping(value = "/getColaboradorAll", method = RequestMethod.GET)
	public ColaboradorResponse getColaboradorAll() {

		List<Colaborador> colaboradorList = new ArrayList<Colaborador>();
		colaboradorList = colaboradorService.getColaboradorAll();
		ColaboradorResponse response = new ColaboradorResponse("Done", colaboradorList);

		return response;
	}

}
