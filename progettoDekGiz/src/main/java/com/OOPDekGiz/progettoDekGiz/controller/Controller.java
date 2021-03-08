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

	/**
	 *
	 * La seguente rotta permette di ottenere le previsioni sulla nuvolosità di una o più città.
	 *
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
		return serviceNuvole.serviceNuvole5giorni(bodyNomiCitta);
	}   //ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

	/**
	 * La seguente rotta permette di avviare una raccolta di dati meteo sulla nuvolosita di una citta inserita
	 * dall'utente.
	 *
	 * @param nomeCitta nome della citta
	 * @return Stringa contenente il path in cui il file, contenente la raccolta di dati meteo, viene salvato.
	 * @throws ParseException
	 * @throws IOException
	 * @throws InserimentoExeption eccezione che viene lanciata nel caso in cui l'utente non inserisce il nome
	 * della citta.
	 *
	 */

	@RequestMapping(value = "/salvaOgniOra", method = RequestMethod.GET)
	@ResponseBody
	public String salvaDati(@RequestParam String nomeCitta) throws ParseException, IOException, InserimentoExeption {
		return serviceNuvole.salvaOgniOra(nomeCitta);
	}   //La key del parametro deve essere "nomeCitta"


	//rotte per le stats (giornaliere-settimanali-mensili)
	@RequestMapping(value = "/statsGiornaliere", method = RequestMethod.GET)
	public JSONObject statsGiornaliere (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		return serviceNuvole.statsGiornaliere(data);
	}


	@RequestMapping(value = "/statsSettimanali", method = RequestMethod.GET)
	public JSONObject statsSettimanali (@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		return serviceNuvole.statsSettimanali(data);
	}


	@RequestMapping(value = "/statsMensili", method = RequestMethod.GET)
	public JSONObject statsMensili(@RequestParam String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
			return serviceNuvole.statsMensili(data);
	}
	//fine rotte stats

	/**
	 * La seguente rotta permette di eliminare i dati presenti in un database.
	 *
	 * @param nomeDatabase nome del database di cui si vogliono eliminare tutti i dati
	 * @return Stringa contenente un messaggio di conferma riguardo l'eliminazione dei dati dal database indicato
	 * dall'utente.
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoExeption eccezione che viene lanciata nel caso in cui l'utente non inserisce il nome del
	 * database da formattare
	 *
	 */

	@RequestMapping(value = "/deleteDatabase", method = RequestMethod.DELETE)
	@ResponseBody
	public String eliminaDatabase(@RequestParam String nomeDatabase) throws IOException, ParseException, InserimentoExeption {
		 return serviceNuvole.eliminaDatabase(nomeDatabase);
	}    //La key del parametro deve essere "nomeDatabase"

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

}
