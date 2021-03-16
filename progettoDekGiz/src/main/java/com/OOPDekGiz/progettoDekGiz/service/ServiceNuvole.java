package com.OOPDekGiz.progettoDekGiz.service;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.filters.FiltersNuvole;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.*;
import com.OOPDekGiz.progettoDekGiz.statistics.*;

/**
 *
 * Questa classe contiene i metodi richiamati dal Controller e rappresenta il servizio su cui si basa l'applicativo.
 *
 */


@Service
public class ServiceNuvole implements ConfigInterface {

	/**
	 *
	 * Questo metodo restituisce i dati meteo relativi alla nuvolosità attuali e per i 5 giorni successivi alla chiamata
	 * per una o più città. L'intervallo tra un dato meteo e il successivo è di 3 ore. I dati meteo verranno salvati
	 * all'interno del "Database_Previsioni" che contiene lo storico di tutte le richieste effettuate.
	 *
	 * @param bodyNomiCitta JSONObject contenente i nomi delle città di cui si vogliono visualizzare le previsioni
	 * @return JSONArray contenente le previsioni meteo relative alla nuvolosità delle città inserite
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws InserimentoException eccezione che viene lanciata se l'utente dimentica di inserire i nomi delle città
	 * nel body della richiesta
	 * @throws NomeCittaException eccezione che viene lanciata se l'utente inserisce il nome di una città inesistente
	 * o errato
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ConfigFileException eccezione che viene lanciata nel caso in cui siano presenti errori nel file di
	 * configurazione
	 *
	 */

	public JSONArray serviceNuvole5giorni(JSONObject bodyNomiCitta)
			throws IOException, ParseException, InserimentoException, GestisciStringaException, NomeCittaException, ConfigFileException, DataMeteoException {

		if(bodyNomiCitta.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		}

		JSONArray risultato = new JSONArray();
		Vector<String> nomiCitta = new Vector<>();
		String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");
		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
		nomiCitta.addAll(gestisciStringa.estraiConVirgola());

		DataBase dataBase = new DataBase("Database_Previsioni.json");
		for (String s : nomiCitta) {
			OpenWeather5giorni api5 = new OpenWeather5giorni(this.estraiApiKey(), s);
			Vector<MeteoCitta> temp = new Vector<MeteoCitta>();
			temp.addAll(api5.estraiDatiMeteo());
			dataBase.salvaSulDatabase(temp);
			for (MeteoCitta meteoCitta : temp) {
				risultato.add(meteoCitta.castToJsonObject());
			}
		}

		return risultato;
	}

	/**
	 *
	 * Questo metodo salva su un database i dati meteo attuali sulla nuvolosità di una città aggiornati ogni ora.
	 * Il file, contenente il database, prenderà il nome della città inserita dall'utente.
	 *
	 * @param nomeCitta nome della citta di cui si vogliono raccogliere i dati meteo ogni ora
	 * @return Stringa contenente il path in cui il file, contenente la raccolta di dati meteo, viene salvato
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non iserisce il nome della città
	 * @throws NomeCittaException eccezione che viene lanciata se l'utente inserisce il nome di una città inesistente
	 * o errato
	 * @throws ConfigFileException eccezione che viene lanciata nel caso in cui siano presenti errori nel file di
	 * configurazione
	 * @throws IOException errori di input/output su file
	 *
	 */

	public String salvaOgniOra(String nomeCitta)
			throws ParseException, InserimentoException, NomeCittaException, ConfigFileException, IOException {

		if(nomeCitta.isEmpty()) {
			throw new InserimentoException("nomeCitta");
		} else {
			//eseguo una chiamata di prova per controllare se effettivamente la richiesta vada a buon fine
			//se la richiesta fallisce significa che è stato inserito un nome citta non valido e quindi verrà lanciata l'eccezione
			OpenWeatherCurrentMeteo prova = new OpenWeatherCurrentMeteo(this.estraiApiKey(), nomeCitta);
			DataBase dataBase = new DataBase(nomeCitta + ".json");
			dataBase.salvaSulDatabaseOgniOra(nomeCitta);
			return "Path database:  " + System.getProperty("user.dir") + "/" + dataBase.getNomeDatabase();
		}
	}

	/**
	 *
	 * Questo metodo consente di formattare un database indicato dall'utente. In particolare, verranno eliminati
	 * tutti i dati presenti al suo interno ma senza eliminare definitivamente il file che lo contiene.
	 *
	 * @param nomeDatabase nome del database che l'utente intende formattare.
	 * @return Stringa contenente un messaggio di conferma riguardo l'eliminazione dei dati dal database inserito
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce il nome del database
	 * da formattare
	 * @throws DatabaseNotFoundException eccezione che viene lanciata se l'utente tenta di formattare un database
	 * inesistente
	 *
	 */

	public String eliminaDatabase(String nomeDatabase)
			throws IOException, ParseException, InserimentoException, DatabaseNotFoundException {

		if(nomeDatabase.isEmpty()) {
			throw new InserimentoException("nomeDatabase");
		} else {
			File file = new File(System.getProperty("user.dir") + "/" + nomeDatabase + ".json");
			if(file.exists()) {
				DataBase dataBase = new DataBase(nomeDatabase + ".json");
				dataBase.svuotaDatabase();
				return "Il contenuto del database '" + nomeDatabase + "' è stato eliminato.";
			} else {
				throw new DatabaseNotFoundException(nomeDatabase);
			}
		}
	}

	/**
	 *
	 * Questo metodo consente di visualizzare i dati meteo presenti all'interno di un database indicato dall'utente.
	 *
	 * @param nomeDatabase nome del database per il quale si vuole visualizzare il contenuto
	 * @return JSONArray contenente i dati meteo presenti nel database
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce il nome del database
	 * di cui si vogliono visualizzare i dati contenuti
	 * @throws DatabaseNotFoundException eccezione che viene lanciata se l'utente tenta di visualizzare un database
	 * inesistente
	 *
	 */

	public JSONArray getDatabase(String nomeDatabase)
			throws IOException, ParseException, InserimentoException, DatabaseNotFoundException {

		if(nomeDatabase.isEmpty()){
			throw new InserimentoException("nomeDatabase");
		} else {
			File file = new File(System.getProperty("user.dir") + "/" + nomeDatabase + ".json");
			if(file.exists()) {
				DataBase dataBase = new DataBase(nomeDatabase + ".json");
				return dataBase.getDatabase();
			} else {
				throw new DatabaseNotFoundException(nomeDatabase);
			}
		}
	}

	//METODI STATS

	/**
	 *
	 * Questo metodo consente di calcolare statistiche giornaliere in base ad uno storico di dati presenti sul
	 * "Database_Previsioni".
	 *
	 * @param data data in formato dd/mm/yyyy di cui si vogliono calcolare le statistiche (giornaliere)
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce la data
	 *
	 */
	
	public JSONObject statsGiornaliere(String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {

		if(data.isEmpty()){
			throw new InserimentoException("data");
		}
		StatsNuvole stats = new StatsNuvole();
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData(data).getTime())/1000));
		return stats.statisticheGiornaliere(dataMeteo);
	}

	/**
	 *
	 * Questo metodo consente di calcolare statistiche settimanali  in base ad uno storico di dati presenti sul
	 * "Database_Previsioni". In particolare inserita una data, calcolerà le statistiche della settimana a cui tale
	 * data appartiene.
	 *
	 * @param data data in formato dd/mm/yyyy di cui si vogliono calcolare le statistiche (settimanali)
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce la data
	 *
	 */
	
    public JSONObject statsSettimanali(String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {

    	if(data.isEmpty()){
    		throw new InserimentoException("data");
		}
		StatsNuvole stats = new StatsNuvole();
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData(data).getTime())/1000));
		return stats.statisticheSettimanali(dataMeteo);
	}

	/**
	 *
	 * Questo metodo consente di calcolare statistiche mensili in base ad uno storico di dati presenti sul
	 * "Database_Previsioni".

	 * @param data data in formato mm/yyyy che indica il mese di cui si vogliono ottenere statistiche
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce la data
	 *
	 */

    public JSONObject statsMensili(String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {

		if(data.isEmpty()){
			throw new InserimentoException("data");
		}
		StatsNuvole stats = new StatsNuvole();
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		//il giorno 10 è predefinito e utilizzato esclusivamente per la creazione del relativo oggetto dataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData("10/" + data).getTime())/1000));
		return stats.statisticheMensili(dataMeteo);
    }

	/**
	 *
	 * Questo metodo consente di calcolare statistiche totali su uno storico di dati presenti sul "Database_Previsioni".
	 * Non richiede l'inserimento di alcun patametro o body.
	 *
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 *
	 */

	public JSONObject statsTotali ()
			throws ParseException, IOException, DataMeteoException {

		StatsNuvole stats = new StatsNuvole();
		return stats.statisticheTotali();
    }

	//METODI FILTERS PER FILTRARE LE STATISTICHE

	/**
	 *
	 * Questo metodo consente di filtrare le statistiche giornaliere in base ai nomi delle città inserite e alla data.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi: "nomiCitta" e "data" formato dd/mm/yyyy
	 * @return JSONArray contenente i valori delle statistiche filtrate
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce i nomi delle città o la data
	 *
	 */
	
  	public JSONArray filtraStatsGiornaliere (JSONObject bodyNomiCittaData)
			throws GestisciStringaException, java.text.ParseException, DataMeteoException, IOException, ParseException,  InserimentoException, FiltersException {

		if(bodyNomiCittaData.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		} else if(bodyNomiCittaData.get("data").toString().isEmpty()) {
			throw new InserimentoException("data");
		}
		FiltersNuvole filterStats = new FiltersNuvole ();

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())/1000));

		Vector<String> nomiCitta = new Vector<String>();
		String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");
		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		nomiCitta.addAll(gestisciStringa.estraiConVirgola());

		JSONArray risultato = new JSONArray();

		for (String nome : nomiCitta) {
			JSONObject elemento = filterStats.filtraStatisticheGiornaliere(dataMeteo, nome);
			risultato.add(elemento);
		}

		return risultato;
  	}

	/**
	 *
	 * Questo metodo consente di filtrare le statistiche settimanali in base ai nomi delle città inserite e alla data.
	 * In particolare inseriti i nomi delle città e una data, calcolerà le statistiche della settimana a cui tale
	 * data appartiene.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi: "nomiCitta" e "data" formato dd/mm/yyyy
	 * @return JSONArray contenente i valori delle statistiche filtrate
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce i nomi delle città o la data
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 *
	 */
  	
  	public JSONArray filtraStatsSettimanali (JSONObject bodyNomiCittaData)
			throws java.text.ParseException, GestisciStringaException, ParseException, IOException, DataMeteoException, InserimentoException, FiltersException {

		if(bodyNomiCittaData.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		} else if(bodyNomiCittaData.get("data").toString().isEmpty()) {
			throw new InserimentoException("data");
		}
		FiltersNuvole filterStats = new FiltersNuvole();

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())/1000));
		Vector<String> nomiCitta = new Vector<String>();
		String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");
		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		nomiCitta.addAll(gestisciStringa.estraiConVirgola());

		JSONArray risultato = new JSONArray();

		for (String nome : nomiCitta) {
			JSONObject elemento = filterStats.filtraStatisticheSettimanali(dataMeteo,nome);
			risultato.add(elemento);
		}

		return risultato;
  	}

	/**
	 *
	 * Questo metodo consente di filtrare le statistiche mensili in base ai nomi delle città inserite e alla data.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi: "nomiCitta" e "data" formato mm/yyyy
	 * @return JSONArray contenente i valori delle statistiche filtrate
	 * @throws java.text.ParseException errori dutante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce i nomi delle città o la data
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 *
	 */

  	public JSONArray filtraStatsMensili (JSONObject bodyNomiCittaData)
			throws java.text.ParseException, DataMeteoException, GestisciStringaException, IOException, ParseException, InserimentoException, FiltersException {

		if(bodyNomiCittaData.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		} else if(bodyNomiCittaData.get("data").toString().isEmpty()) {
			throw new InserimentoException("data");
		}
		FiltersNuvole filterStats = new FiltersNuvole ();

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		//il giorno 10 è predefinito e utilizzato esclusivamente per la creazione del relativo oggetto dataMeteo
		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData("10/" + bodyNomiCittaData.get("data")).getTime())/1000));
		Vector<String> nomiCitta = new Vector<String>();
		String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");
		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		nomiCitta.addAll(gestisciStringa.estraiConVirgola());

		JSONArray risultato = new JSONArray();

		for (String nome : nomiCitta) {
			JSONObject elemento = filterStats.filtraStatisticheMensili(dataMeteo, nome);
			risultato.add(elemento);
		}

		return risultato;
	}

	/**
	 * Questo metodo consente di filtrare le statistiche totali in base ai nomi delle città inserite.
	 *
	 * @param bodyNomiCitta JSONObject contenente in campo "nomiCitta"
	 * @return JSONArray contenente i valori delle statistiche filtrate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws GestisciStringaException eccezione che viene lanciata se l'utente inserisce una stringa non valida
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce i nomi delle città
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 *
	 */

	public JSONArray filtraStatsTotali (JSONObject bodyNomiCitta)
			throws GestisciStringaException, ParseException, IOException, DataMeteoException, InserimentoException, FiltersException {

  		if(bodyNomiCitta.get("nomiCitta").toString().isEmpty()) {
  			throw new InserimentoException("nomiCitta");
		}
		FiltersNuvole filterStats = new FiltersNuvole ();
		Vector<String> nomiCitta = new Vector<String>();
		String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");
		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		nomiCitta.addAll(gestisciStringa.estraiConVirgola());

		JSONArray risultato = new JSONArray();

		for (String nome : nomiCitta) {
			JSONObject elemento = filterStats.filtraStatisticheTotali(nome);
			risultato.add(elemento);
		}

		return risultato;
  	}

	//METODO STATISTICHE PREVISIONI AZZECCATE

	/**
	 *
	 * Questo metodo consente di ottenere statistiche sulla quantità di previsioni azzeccate di una città in un certo
	 * periodo. L'utente dovrà inserire un body contenente 4 campi: "nomeCitta","dataInizio" cioè l'inizio del
	 * periodo,"dataFine" cioè la fine del periodo e "sogliaErrore" cioè l'errore massimo ammissibile tra il dato meteo
	 * reale e quello previsto.
	 *
	 * @param bodyInizioFineCittaSoglia JSONObject contenente quattro campi: "nomeCitta","dataInizio","dataFine"
	 * e "sogliaErrore"
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws PeriodNotValidException eccezione che viene lanciata se l'utente inserisce un perido non valido
	 * @throws SogliaErroreNotValidException eccezione che viene lanciata se l'utente inserisce una soglia non valida
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws java.text.ParseException errori durante il parsing
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce uno tra i 4 campi richiesti
	 *
	 */
      
	public JSONObject controllaPrevisioniSoglia(JSONObject bodyInizioFineCittaSoglia)
			  throws DataMeteoException, PeriodNotValidException, SogliaErroreNotValidException, ParseException, IOException, java.text.ParseException, InserimentoException {

		if(bodyInizioFineCittaSoglia.get("nomeCitta").toString().isEmpty()) {
			throw new InserimentoException("nomeCitta");
		} else if(bodyInizioFineCittaSoglia.get("dataInizio").toString().isEmpty()) {
			throw new InserimentoException("dataInizio");
		} else if(bodyInizioFineCittaSoglia.get("dataFine").toString().isEmpty()) {
			throw new InserimentoException("dataFine");
		} else if(bodyInizioFineCittaSoglia.get("sogliaErrore").toString().isEmpty()) {
			throw new InserimentoException("sogliaErrore");
		}

		ControllaPrevisioni calcola = new ControllaPrevisioni();

		//estraggo le date di inizio e fine come stringa e le converto come oggetti di tipo DataMeteo
		DataMeteo dataInizio = new DataMeteo ((GestisciStringhe.StringToData((String)bodyInizioFineCittaSoglia.get("dataInizio")).getTime())/1000);
		DataMeteo dataFine = new DataMeteo ((GestisciStringhe.StringToData((String)bodyInizioFineCittaSoglia.get("dataFine")).getTime())/1000);

		double sogliaErrore = Double.parseDouble(bodyInizioFineCittaSoglia.get("sogliaErrore").toString());
		String nomeCitta = bodyInizioFineCittaSoglia.get("nomeCitta").toString();

		return calcola.controllaPrevisioniSoglia(dataInizio, dataFine, nomeCitta, sogliaErrore);
  	}

	/**
	 *
	 * Implementazione del metodo estraiApiKey dell'interfaccia ConfigInterface. Esso permette l'estrazione dell'apiKey
	 * dal file di configurazione.
	 *
	 * @throws ConfigFileException errori presenti nel file di configurazione (se non rispetta il formato JSON)
	 *
	 */

	public String estraiApiKey()
			throws ConfigFileException {

		try {
			File file = new File(System.getProperty("user.dir") + "/config.json");
			Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
			String inputLine = in.nextLine();
			in.close();
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(inputLine);
			return (String) jsonObject.get("apiKey");
		} catch (Exception e) {
			throw new ConfigFileException();
		}
	}
}

