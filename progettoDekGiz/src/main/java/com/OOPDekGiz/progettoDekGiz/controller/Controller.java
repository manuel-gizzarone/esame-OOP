package com.OOPDekGiz.progettoDekGiz.controller;

import java.io.IOException;

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

/**
 * Questa classe rappresenta il controller del programma. Essa permette di gestire tutte le possibili chiamate al server
 * che l'utente (client) può fare.
 */

@RestController
public class Controller {

	@Autowired
	private ServiceNuvole serviceNuvole;

	/**
	 * La seguente rotta permette di ottenere le previsioni sulla nuvolosità di una o più città.
	 *
	 * @param bodyNomiCitta JSONObject contenente un campo "nomiCitta" in cui saranno inseriti i nomi delle città
	 *        separati da una virgola
	 * @return JSONArray contenente gli oggetti di tipo MeteoCitta della risposta (ognuno castato in JSONObject)
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire i nomi delle città
	 * 		   nel body della richiesta
	 * @throws NomeCittaException eccezione che viene lanciata se l'utente inserisce il nome di una città inesistente
	 * 		   o errato
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ConfigFileException eccezione che viene lanciata nel caso in cui siano presenti errori nel file di
	 * 		   configurazione
	 */

	@RequestMapping(value = "/nuvoleCitta5giorni", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray nuvole5giorni(@RequestBody JSONObject bodyNomiCitta)
			throws ParseException, IOException, InserimentoException, NomeCittaException, GestisciStringaException, DataMeteoException, ConfigFileException {
		return serviceNuvole.serviceNuvole5giorni(bodyNomiCitta);
	}

	/**
	 * La seguente rotta permette di salvare ogni ora i dati meteo sulla nuvolosità di una certa città.
	 *
	 * @param nomeCitta nome della città di cui si vogliono raccogliere i dati meteo sulla nuvolosità
	 * @return stringa contenente il path in cui è stato salvato il database
	 * @throws ParseException errori durente il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire il nome della città
	 * 		   di cui si vogliono raccogliere i dati meteo
	 * @throws NomeCittaException eccezione che viene lanciata se l'utente inserisce il nome di una città inesistente
	 * 		   o errato
	 * @throws IOException errori di input/output su file
	 * @throws ConfigFileException eccezione che viene lanciata nel caso in cui siano presenti errori nel file di
	 * 		   configurazione
	 */
	
	@RequestMapping(value = "/salvaOgniOra", method = RequestMethod.GET)
	public String salvaDati(@RequestParam String nomeCitta)
			throws ParseException, InserimentoException, NomeCittaException, IOException, ConfigFileException {
		return serviceNuvole.salvaOgniOra(nomeCitta);
	}

	/**
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra i dati presenti nel
	 * "Database_Previsioni" al giorno della data inserita dall'utente. Le statistiche riguardano valori massimi, minimi,
	 * media e varianza della nuvolosità.
	 *
	 * @param data data nel formato dd/mm/yyyy sulla quale si vogliono visualizzare le statistiche (giornaliere)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire la data
	 */

	@RequestMapping(value = "/statsGiornaliere", method = RequestMethod.GET)
	public JSONObject statsGiornaliere (@RequestParam String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {
		return serviceNuvole.statsGiornaliere(data);
	}

	/**
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra i dati presenti nel
	 * "Database_Previsioni" nella settimana della data inserita dall'utente. Le statistiche riguardano valori massimi,
	 *  minimi, media e varianza della nuvolosità.
	 *
	 * @param data data nel formato dd/mm/yyyy sulla quale si vogliono visualizzare le statistiche (settimanali)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire la data
	 */

	@RequestMapping(value = "/statsSettimanali", method = RequestMethod.GET)
	public JSONObject statsSettimanali (@RequestParam String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {
		return serviceNuvole.statsSettimanali(data);
	}

	/**
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra i dati presenti nel
	 * "Database_Previsioni" nel mese della data inserita dall'utente. Le statistiche riguardano valori massimi, minimi
	 * media e varianza della nuvolosità.
	 *
	 * @param data data nel formato mm/yyyy sulla quale si vogliono visualizzare le statistiche (mensili)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire la data
	 */

	@RequestMapping(value = "/statsMensili", method = RequestMethod.GET)
	public JSONObject statsMensili(@RequestParam String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {
		return serviceNuvole.statsMensili(data);
	}


	/**
	 * La seguente rotta permette di calcolare e visualizzare le statistiche sulla nuvolosità tra tutti i dati presenti
	 * nel "Database_Previsioni". Le statistiche riguardano valori massimi, minimi, media e varianza della nuvolosità.
	 *
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 */

	@RequestMapping(value = "/statsTotali", method = RequestMethod.GET)
	public JSONObject statsTotali()
			throws ParseException, IOException, DataMeteoException {
		return serviceNuvole.statsTotali();
	}

	/**
	 * La seguente rotta permette di filtrare le statistiche giornaliere per nome della città e per data. E' possibile
	 * inserire contemporaneamente più nomi.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi "nomiCitta" separati da una virgola e "data" nel
	 *        formato dd/mm/yyyy
	 * @return JSONArray contente i valori della statistiche filtrate
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire i nomi delle città
	 * 		   o la data
	 */

	@RequestMapping(value = "/filtraStatsGiornaliero", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsGiornaliero(@RequestBody JSONObject bodyNomiCittaData)
			throws GestisciStringaException, java.text.ParseException, DataMeteoException, IOException, ParseException, InserimentoException, FiltersException {
		return serviceNuvole.filtraStatsGiornaliere(bodyNomiCittaData);
	}

	/**
	 * La seguente rotta permette di filtrare le statistiche settimanali per nome della città e per data. E' possibile
	 * inserire contemporaneamente più nomi.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi "nomiCitta" separati da una virgola e "data" nel
	 *        formato dd/mm/yyyy
	 * @return JSONArray contente i valori della statistiche filtrate
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire i nomi delle città
	 * 		   o la data
	 */

	@RequestMapping(value = "/filtraStatsSettimanale", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsSettimanale(@RequestBody JSONObject bodyNomiCittaData)
			throws GestisciStringaException, DataMeteoException, ParseException, java.text.ParseException, IOException, InserimentoException, FiltersException {
		return serviceNuvole.filtraStatsSettimanali(bodyNomiCittaData);
	}

	/**
	 * La seguente rotta permette di filtrare le statistiche mensili per nome della città e per data. E' possibile
	 * inserire contemporaneamente più nomi.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi "nomiCitta" separati da un virgola  e "data" nel
	 *        formato mm/yyyy
	 * @return JSONArray contente i valori della statistiche filtrate
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire i nomi delle città
	 *         o la data
	 */

	@RequestMapping(value = "/filtraStatsMensile", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsMensile(@RequestBody JSONObject bodyNomiCittaData)
			throws GestisciStringaException, DataMeteoException, ParseException, java.text.ParseException, IOException, InserimentoException, FiltersException {
		return serviceNuvole.filtraStatsMensili(bodyNomiCittaData);
	}

	/**
	 * La seguente rotta permette di filtrare le statistiche totali per nome della città. E' possibile
	 * inserire contemporaneamente più nomi.
	 *
	 * @param bodyNomiCitta JSONObject contenente un campo "nomiCitta" separati da una virgola
	 * @return JSONArray contente i valori della statistiche filtrate
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire i nomi delle città
	 */

	@RequestMapping(value = "/filtraStatsTotale", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray filtraStatsTotale(@RequestBody JSONObject bodyNomiCitta)
			throws GestisciStringaException, DataMeteoException, ParseException, IOException, InserimentoException, FiltersException {
		return serviceNuvole.filtraStatsTotali(bodyNomiCitta);
	}

	/**
	 * La seguente rotta permette di eliminare i dati presenti all'interno di un database.
	 *
	 * @param nomeDatabase nome del database da formattare
	 * @return Stringa contenente un messaggio di avvenuta eliminazione del database
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire il nome del database
	 * 		   da formattare
	 * @throws DatabaseNotFoundException eccezione che viene lanciata se l'utente tenta di formattare un database
	 * 		   inesistente
	 */

	@RequestMapping(value = "/deleteDatabase", method = RequestMethod.DELETE)
	public String eliminaDatabase(@RequestParam String nomeDatabase)
			throws IOException, ParseException, InserimentoException, DatabaseNotFoundException {
		return serviceNuvole.eliminaDatabase(nomeDatabase);
	}

	/**
	 * La seguente rotta permette all'utente di visualizzare il contenuto di un database.
	 *
	 * @param nomeDatabase nome del database da visualizzare
	 * @return JSONArray contenente i dati meteo presenti nel database indicato
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata nel caso in cui l'utente non inserisce il nome del
	 * 		   database da visualizzare
	 * @throws DatabaseNotFoundException eccezione che viene lanciata se l'utente tenta di visualizzare un database
	 * 		   inesistente
	 */

	@RequestMapping(value = "/getDatabase", method = RequestMethod.GET)
	public JSONArray getDatabase(@RequestParam String nomeDatabase)
			throws IOException, ParseException, InserimentoException, DatabaseNotFoundException {
		return serviceNuvole.getDatabase(nomeDatabase);
	}

	/**
	 * La seguente rotta permette di generare statistiche sulla quantità previsioni azzeccate riguardo una città, in un
	 * dato periodo tra una data iniziale e finale e data una soglia di errore. In particolare richiede l'inserimento di
	 * un body contenente 4 campi: "nomeCitta"(uno solo),"dataInizio"(formato dd/mm/yyyy),"dataFine"(formato dd/mm/yyyy)
	 * ,"sogliaErrore"(numero compreso tra 1 e 99).
	 *
	 * @param bodyInizioFineCittaSoglia JSONObject contenente contenente 4 campi: "nomeCitta","dataInizio","dataFine",
	 * "sogliaErrore"
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws PeriodNotValidException eccezione che viene lanciata se l'utente inserisce una periodo non valido
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws SogliaErroreNotValidException eccezione che viene lanciata se l'utente inserisce una soglia di errore
	 * 		   non valida
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws InserimentoException eccezione che viene lanciata nel caso in cui l'utente non inserisce uno tra i 4
	 *     	   campi richiesti
	 */

	@RequestMapping(value = "/previsioniSoglia", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject controllaPrevisioniSoglia(@RequestBody JSONObject bodyInizioFineCittaSoglia)
			throws PeriodNotValidException, DataMeteoException, ParseException, SogliaErroreNotValidException, java.text.ParseException, IOException, InserimentoException {
		return serviceNuvole.controllaPrevisioniSoglia(bodyInizioFineCittaSoglia);
	}
}
