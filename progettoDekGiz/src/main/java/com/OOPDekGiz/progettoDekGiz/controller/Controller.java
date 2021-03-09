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
	 * La seguente rotta permette di ottenere le previsioni sulla nuvolosità di una o più città.
	 *
	 * @param bodyNomiCitta JSONObject contenente i nomi delle città di cui sono richieste le previsioni
	 * @return JSONArray contenente gli oggetti di tipo MeteoCitta della risposta (ognuno castato in JSONObject)
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
		return serviceNuvole.serviceNuvole5giorni(bodyNomiCitta);
		//ATTENZIONE!!! il JSONObject deve contenere 1 campo "nomiCitta"
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
	}
	
	//FINE ROTTA


	//INIZIO ROTTA

	/**
	 *
	 * La seguente rotta permette di salvare ogni ora i dati meteo sulla nuvolosità di una certa città.
	 *
	 * @param nomeCitta nome della città di cui si vogliono raccogliere i dati meteo sulla nuvolosità
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire il nome della città
	 * di cui si vogliono raccogliere i dati meteo
	 *
	 */
	
	@RequestMapping(value = "/salvaOgniOra", method = RequestMethod.GET)
	@ResponseBody
	public String salvaDati(@RequestParam String nomeCitta) throws ParseException, IOException, InserimentoException {
		return serviceNuvole.salvaOgniOra(nomeCitta);
		//La key del parametro deve essere "nomeCitta"
	}
	
	//FINE ROTTA


	//INIZIO ROTTA

	/**
	 *
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra i dati presenti nel
	 * "Database_Previsioni" al giorno della data inserita dall'utente. Le statistiche riguardano valori massimi, minimi
	 * media e varianza della nuvolosità.
	 *
	 * @param data data sulla quale si vogliono visualizzare le statistiche
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire la data
	 *
	 */

	@RequestMapping(value = "/statsGiornaliere", method = RequestMethod.GET)
	public JSONObject statsGiornaliere (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException, InserimentoException {
		return serviceNuvole.statsGiornaliere(data);
		//ATTENZIONE!!! la rotta richiede l'inserimento di un parametro "data"
	}
	
	//Fine ROTTA


	//INIZIO ROTTA

	/**
	 *
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra i dati presenti nel
	 * "Database_Previsioni" nella settimana della data inserita dall'utente. Le statistiche riguardano valori massimi, minimi
	 * media e varianza della nuvolosità.
	 *
	 * @param data data sulla quale si vogliono visualizzare le statistiche (settimanali)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire la data
	 *
	 */

	@RequestMapping(value = "/statsSettimanali", method = RequestMethod.GET)
	public JSONObject statsSettimanali (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException, InserimentoException {
		return serviceNuvole.statsSettimanali(data);
		//ATTENZIONE!!! la rotta richiede l'inserimento di un parametro "data"
	}
		
    //Fine ROTTA


	//INIZIO ROTTA

	/**
	 *
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra i dati presenti nel
	 * "Database_Previsioni" nel mese della data inserita dall'utente. Le statistiche riguardano valori massimi, minimi
	 * media e varianza della nuvolosità.
	 *
	 * @param data nel formato mm/AAAA
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire la data
	 *
	 */

	@RequestMapping(value = "/statsMensili", method = RequestMethod.GET)
	public JSONObject statsMensili(@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException, InserimentoException {
			return serviceNuvole.statsMensili(data);
			//ATTENZIONE!!! la rotta richiede l'inserimento di un parametro "data"
			//ATTENZIONE!!! il mese nel parametro data deve essere scritto come mm/AAAA
	}
		
	//FINE ROTTA

		
	//INIZIO ROTTA

	/**
	 *
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra tutti i dati presenti
	 * nel "Database_Previsioni". Le statistiche riguardano valori massimi, minimi media e varianza della nuvolosità.
	 *
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 * @throws InserimentoException
	 *
	 */

	@RequestMapping(value = "/statsTotali", method = RequestMethod.GET)
	public JSONObject statsTotali() throws IOException, ParseException, DataMeteoException, java.text.ParseException, InserimentoException {
			return serviceNuvole.statsTotali();
	}
			
	//FINE ROTTA


	//INIZIO ROTTA

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
	 *
	 */

	@RequestMapping(value = "/filtraStatsGiornaliero", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsGiornaliero(@RequestBody JSONObject bodyNomiCittaData) throws ParseException,IOException, DataMeteoException, java.text.ParseException{
		return serviceNuvole.filtraStatsGiornaliere(bodyNomiCittaData);
		//ATTENZIONE!!! il JSONObject deve contenere 2 campi "nomiCitta" e "data"
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
		//ATTENZIONE!!! la key associata alla data deve essere "data" e il value deve essere scritto come gg/mm/AAAA
	}
		
	//FINE ROTTA

	
	//INIZIO ROTTA

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
	 *
	 */

	@RequestMapping(value = "/filtraStatsSettimanale", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsSettimanale(@RequestBody JSONObject bodyNomiCittaData) throws ParseException, IOException,  DataMeteoException, java.text.ParseException{
		return serviceNuvole.filtraStatsSettimanali(bodyNomiCittaData);
		//ATTENZIONE!!! il JSONObject deve contenere 2 campi "nomiCitta" e "data"
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
		//ATTENZIONE!!! la key associata alla data deve essere "data" e il value deve essere scritto come gg/mm/AAAA
	}
			
	//FINE ROTTA

	
	//INIZIO ROTTA

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
	 *
	 */

	@RequestMapping(value = "/filtraStatsMensile", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsMensile(@RequestBody JSONObject bodyNomiCittaData) throws ParseException, IOException,  DataMeteoException, java.text.ParseException{
		return serviceNuvole.filtraStatsMensili(bodyNomiCittaData);
		//ATTENZIONE!!! il JSONObject deve contenere 2 campi "nomiCitta" e "data"
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
		//ATTENZIONE!!! la key associata alla data deve essere "data" e il value deve essere scritto come mm/AAAA
	}
				
	//FINE ROTTA

	
	//INIZIO ROTTA

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
	 *
	 */

	@RequestMapping(value = "/filtraStatsTotale", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsTotale(@RequestBody JSONObject bodyNomiCittaData) throws ParseException,MalformedURLException,IOException, GestisciStringaException, DataMeteoException, java.text.ParseException{
		return serviceNuvole.filtraStatsTotali(bodyNomiCittaData);
		//ATTENZIONE!!! il JSONObject deve contenere 1 campo "nomiCitta"
		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
		//visto che bisogna immettere una sola città sarebbe meglio inserirla come parametro?
	}
					
	//FINE ROTTA

	
	//INIZIO ROTTA

	/**
	 *
	 * La seguente rotta permette di eliminare i dati presenti all'interno di un database.
	 *
	 * @param nomeDatabase nome del database da formattare
	 * @return Stringa contenente un messaggio di avvenuta eliminazione del database
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire il nome del database
	 * da formattare
	 *
	 */

	@RequestMapping(value = "/deleteDatabase", method = RequestMethod.DELETE)
	@ResponseBody
	public String eliminaDatabase(@RequestParam String nomeDatabase) throws IOException, ParseException, InserimentoException, DatabaseNotFoundException {
		return serviceNuvole.eliminaDatabase(nomeDatabase);
		//La key del parametro deve essere "nomeDatabase"
	}

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
	 * @throws InserimentoException eccezione che viene lanciata nel caso in cui l'utente non inserisce il nome del
	 * database da visualizzare
	 *
	 */

	@RequestMapping(value = "/getDatabase", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getDatabase(@RequestParam String nomeDatabase) throws IOException, ParseException, InserimentoException, DatabaseNotFoundException {
		return serviceNuvole.getDatabase(nomeDatabase);
		//La key del parametro deve essere "nomeDatabase"
	}

	//FINE ROTTA

}
