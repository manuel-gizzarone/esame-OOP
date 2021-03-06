package com.OOPDekGiz.progettoDekGiz.controller;

import java.io.IOException;


import java.net.MalformedURLException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.parser.ParseException;

import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.service.*;



@RestController
public class Controller {

	@Autowired
	private ServiceNuvole serviceNuvole;

	//INIZIO ROTTA
	/**
	 *
	 * la rotta permette di ottenere le previsioni sulla nuvolosità di una o più città 
	 * @param bodyNomiCitta è il JSONObject contenente i nomi delle città di cui sono richieste le previsioni
	 * @return è il JSONArray contenente gli oggetti di tipo MeteoCitta (ognuno castato in JSONObject) della risposta 
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws GestisciStringaException 
	 * @throws DataMeteoException
	 *
	 */

	@RequestMapping(value = "/nuvoleCitta5giorni", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray nuvole5giorni(@RequestBody JSONObject bodyNomiCitta) throws ParseException,MalformedURLException,IOException, GestisciStringaException, DataMeteoException{
		
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

		return serviceNuvole.serviceNuvole5giorni(bodyNomiCitta);
	
	}
	
	//FINE ROTTA
	
	//INIZIO ROTTA
	@RequestMapping(value = "/statsGiornaliere", method = RequestMethod.GET)
	public JSONObject statsGiornaliere (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		return serviceNuvole.statsGiornaliere(data);
	}
	
	//Fine ROTTA
	
	//INIZIO ROTTA
	@RequestMapping(value = "/statsSettimanali", method = RequestMethod.GET)
	public JSONObject statsSettimanali (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		return serviceNuvole.statsSettimanali(data);
	}
		
    //Fine ROTTA
		
	//INIZIO ROTTA
	@RequestMapping(value = "/statsMensili", method = RequestMethod.GET)
	public JSONObject statsMensili(@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
			return serviceNuvole.statsMensili(data);
	}
		
	//Fine ROTTA
		
		
	

}
