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
		//ATTENZIONE!!! il JSONObject deve contenere 1 campo "nomiCitta"
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

		return serviceNuvole.serviceNuvole5giorni(bodyNomiCitta);
	
	}
	
	//FINE ROTTA
	
	//INIZIO ROTTA
	
	@RequestMapping(value = "/salvaOgniOra", method = RequestMethod.GET)
	@ResponseBody
	public String salvaDati(@RequestParam String nomeCitta) throws ParseException, IOException, InserimentoExeption {
		return serviceNuvole.salvaOgniOra(nomeCitta);
	}   //La key del parametro deve essere "nomeCitta"
	
	//FINE ROTTA
	
	//INIZIO ROTTA
	/**
	 * la rotta permette di ricevere le statistiche di media varianza e valori max e min sulla nuvolosità tra i dati sul database al giorno della data inserita
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	@RequestMapping(value = "/statsGiornaliere", method = RequestMethod.GET)
	public JSONObject statsGiornaliere (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		//ATTENZIONE!!! la rotta richiede l'inserimento di un parametro "data"
		return serviceNuvole.statsGiornaliere(data);
	}
	
	//Fine ROTTA
	
	//INIZIO ROTTA
	/**
	 * la rotta permette di ricevere le statistiche di media varianza e valori max e min sulla nuvolosità tra i dati sul database nella settimana della data inserita
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	@RequestMapping(value = "/statsSettimanali", method = RequestMethod.GET)
	public JSONObject statsSettimanali (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		//ATTENZIONE!!! la rotta richiede l'inserimento di un parametro "data"
		return serviceNuvole.statsSettimanali(data);
	}
		
    //Fine ROTTA
		
	//INIZIO ROTTA
	/**
	 * la rotta permette di ricevere le statistiche di media varianza e valori max e min sulla nuvolosità tra i dati sul database al mese della data inserita
	 * @param data nel formato mm/AAAA
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	@RequestMapping(value = "/statsMensili", method = RequestMethod.GET)
	public JSONObject statsMensili(@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
			return serviceNuvole.statsMensili(data);
			//ATTENZIONE!!! la rotta richiede l'inserimento di un parametro "data"
			//ATTENZIONE!!! il mese nel parametro data deve essere scritto come mm/AAAA
	}
		
	//Fine ROTTA
		
	//INIZIO ROTTA
	/**
	 * la rotta permette di ricevere le statistiche di media varianza e valori max e min sulla nuvolosità tra i dati sul database alla data inserita
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	@RequestMapping(value = "/statsTotali", method = RequestMethod.GET)
	public JSONObject statsTotali() throws IOException, ParseException, DataMeteoException, java.text.ParseException {
			return serviceNuvole.statsTotali();
	}
			
	//Fine ROTTA
		
	//INIZIO ROTTA

	@RequestMapping(value = "/filtraStatsGiornaliero", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	 * @param bodyNomiCittaData
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws GestisciStringaException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	public JSONArray filtraStatsGiornaliero(@RequestBody JSONObject bodyNomiCittaData) throws ParseException,MalformedURLException,IOException, GestisciStringaException, DataMeteoException, java.text.ParseException{
	//ATTENZIONE!!! il JSONObject deve contenere 2 campi "nomiCitta" e "data"		
	//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
	//ATTENZIONE!!! la key associata alla data deve essere "data" e il value deve essere scritto come gg/mm/AAAA

	return serviceNuvole.filtraStatsGiornaliere(bodyNomiCittaData);
		
	}
		
	//FINE ROTTA
	
	//INIZIO ROTTA

	@RequestMapping(value = "/filtraStatsSettimanale", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	 * @param bodyNomiCittaData
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws GestisciStringaException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	public JSONArray filtraStatsSettimanale(@RequestBody JSONObject bodyNomiCittaData) throws ParseException,MalformedURLException,IOException, GestisciStringaException, DataMeteoException, java.text.ParseException{
	//ATTENZIONE!!! il JSONObject deve contenere 2 campi "nomiCitta" e "data"					
	//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
	//ATTENZIONE!!! la key associata alla data deve essere "data" e il value deve essere scritto come gg/mm/AAAA

	return serviceNuvole.filtraStatsSettimanali(bodyNomiCittaData);
			
	}
			
	//FINE ROTTA
	
	//INIZIO ROTTA

	@RequestMapping(value = "/filtraStatsMensile", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	 * @param bodyNomiCittaData
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws GestisciStringaException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	public JSONArray filtraStatsMensile(@RequestBody JSONObject bodyNomiCittaData) throws ParseException,MalformedURLException,IOException, GestisciStringaException, DataMeteoException, java.text.ParseException{
	//ATTENZIONE!!! il JSONObject deve contenere 2 campi "nomiCitta" e "data"				
	//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
	//ATTENZIONE!!! la key associata alla data deve essere "data" e il value deve essere scritto come mm/AAAA

	return serviceNuvole.filtraStatsMensili(bodyNomiCittaData);
				
	}
				
	//FINE ROTTA
	
	//INIZIO ROTTA

	@RequestMapping(value = "/filtraStatsTotale", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	 * @param bodyNomiCittaData
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws GestisciStringaException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 */
	public JSONArray filtraStatsTotale(@RequestBody JSONObject bodyNomiCittaData) throws ParseException,MalformedURLException,IOException, GestisciStringaException, DataMeteoException, java.text.ParseException{
	//ATTENZIONE!!! il JSONObject deve contenere 1 campo "nomiCitta"			
	//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

	return serviceNuvole.filtraStatsTotali(bodyNomiCittaData);
					
	}
					
	//FINE ROTTA
	
	//INIZIO ROTTA
	@RequestMapping(value = "/deleteDatabase", method = RequestMethod.DELETE)
	@ResponseBody
	public String eliminaDatabase(@RequestParam String nomeDatabase) throws IOException, ParseException, InserimentoExeption {
		 return serviceNuvole.eliminaDatabase(nomeDatabase);
	}    //La key del parametro deve essere "nomeDatabase"

	//FINE ROTTA
	
	//INIZIO ROTTA
	/**
	 *
	 * La seguente rotta permette all'utente di visualizzare il contenuto di un database.
	 *
	 * @param nomeDatabase nome del database che si desidera visualizzare
	 * @return Il JSONArray contenente i dati meteo presenti nel database indicato
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoExeption eccezione che viene lanciata nel caso in cui l'utente non inserisce il nome del
	 * database da visualizzare
	 *
	 */

	@RequestMapping(value = "/getDatabase", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getDatabase(@RequestParam String nomeDatabase) throws IOException, ParseException, InserimentoExeption {
		return serviceNuvole.getDatabase(nomeDatabase);
	}   //La key del parametro deve essere "nomeDatabase"

	//FINE ROTTA

}
