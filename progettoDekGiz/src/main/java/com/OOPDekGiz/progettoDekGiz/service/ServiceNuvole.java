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
 * @author Emanuele De Caro
 * @author Manuel Gizzarone
 *
 */


@Service
public class ServiceNuvole implements ConfigInterface {

	/**
	 *
	 * Chiama le Api forecast 5 giorni di OpenWeather per una o più citta (i cui nomi sono inseriti nel value della key
	 * "nomiCitta" di bodyNomiCitta) e restituisce un JSONArray contenente i dati relativi alla nuvolosità di tali
	 * citta attuali e per i 5 giorni successivi alla chiamata.
	 *
	 * @param bodyNomiCitta
	 * @return il JSONArray contenente i dati relativi alla nuvolosità delle citta (i cui nomi sono inseriti nel value della key "nomiCitta" di bodyNomiCitta) per i 5 giorni da/successivi alla chiamata
	 * @throws GestisciStringaException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoException
	 * @throws NomeCittaException
	 * @throws ConfigFileException
	 * @throws DataMeteoException
	 *
	 */

	public JSONArray serviceNuvole5giorni(JSONObject bodyNomiCitta)
			throws IOException, ParseException, InserimentoException, GestisciStringaException, NomeCittaException, ConfigFileException, DataMeteoException {

		 //è il JSONArray che dovrà essere la risposta alla chiamata del metodo
		JSONArray risultato = new JSONArray();

		//il vettore contiene i nomi delle città per cui sono richieste le previsioni sulla nuvolosità
		Vector<String> nomiCitta = new Vector<String>();

		 //è la stringa contenente i nomi delle città da separare per cui si vogliono le previsioni
		String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");
		if(nomiCittaDaEstrarre.isEmpty()) {
			throw new InserimentoException("nomiCitta");
		}

		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
		for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
			String temp = gestisciStringa.estraiConVirgola().get(i);
			nomiCitta.add(temp);
		}

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
	 * Metodo che salva sul database "Database_Raccolta" i dati meteo di una citta aggiornati ogni ora.
	 *
	 * @param nomeCitta nome della citta di cui si vogliono raccogliere i dati meteo ogni ora
	 * @return Stringa contenente il path in cui il file, contenente la raccolta di dati meteo, viene salvato.
	 * @throws ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non iserisce il nome della citta.
	 * @throws NomeCittaException
	 * @throws ConfigFileException
	 *
	 */

	public String salvaOgniOra(String nomeCitta)
			throws ParseException, InserimentoException, NomeCittaException, ConfigFileException, IOException {

		if(nomeCitta.isEmpty()) {
			throw new InserimentoException("nomeCitta");
		} else {
			//eseguo una chiamata di prova per controllare se effettivamente la richiesta vada a buon fine
			//se la richiesta fallisce significa che è stato inserito un nome citta non valido e quindi viene lanciata l'eccezione
			OpenWeatherCurrentMeteo prova = new OpenWeatherCurrentMeteo(this.estraiApiKey(), nomeCitta);
			DataBase dataBase = new DataBase(nomeCitta + ".json");
			dataBase.salvaSulDatabaseOgniOra(nomeCitta);
			return "Path database:  " + System.getProperty("user.dir") + "/" + dataBase.getNomeDatabase();
		}
	}

	/**
	 *
	 * Metodo che formatta il database indicato dall'utente.
	 *
	 * @param nomeDatabase nome del database che l'utente intende formattare.
	 * @return Stringa contenente un messaggio di conferma riguardo l'eliminazione dei dati dal database indicato
	 * dall'utente.
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce il nome del database
	 * da formattare.
	 * @throws DatabaseNotFoundException eccezione che viene lanciata se l'utente tenta di formattare un database
	 * inesistente.
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
	 * Metodo per visualizzare il contenuto di un database indicato dall'utente.
	 *
	 * @param nomeDatabase nome del database per il quale si vuole visualizzare il contenuto
	 * @return Un JSONArray contenente i dati presenti nel database.
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non inserisce il nome del database
	 * di cui si vogliono visualizzare i dati contenuti.
	 * @throws DatabaseNotFoundException eccezione che viene lanciata se l'utente tenta di visualizzare un database
	 * inesistente.
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
	 * Metodo per calcolare statistiche giornaliere in base ai dati presenti sul "Database_Previsioni".
	 *
	 * @param data data in formato gg/mm/AAAA di cui si vogliono calcolare le statistiche
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException
	 * @throws IOException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 *
	 */
	
	public JSONObject statsGiornaliere (String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {

		if(data.isEmpty()){
			throw new InserimentoException("data");
		}
		StatsNuvole stats = new StatsNuvole ();
		//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData(data).getTime())/1000));
		return stats.statisticheGiornaliere(dataMeteo);
	}

	/**
	 *
	 * Metodo per calcolare statistiche settimanali in base ai dati presenti sul "Database_Previsioni". In particolare
	 * inserita una data, calcolerà le statistiche della settimana a cui tale data appartiene.
	 *
	 * @param data data in formato gg/mm/AAAA di cui si vogliono calcolare le statistiche
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException
	 * @throws IOException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 *
	 */
	
    public JSONObject statsSettimanali (String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {

    	if(data.isEmpty()){
    		throw new InserimentoException("data");
		}
		StatsNuvole stats = new StatsNuvole ();
		//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData(data).getTime())/1000));
		return stats.statisticheSettimanali(dataMeteo);

	}

	/**
	 *
	 * Metodo per calcolare statistiche mensili in base ai dati presenti sul "Database_Previsioni".

	 * @param data data in formato mm/AAAA che indica il mese di cui si vogliono ottenere statistiche
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException
	 * @throws IOException
	 * @throws DataMeteoException
	 * @throws java.text.ParseException
	 *
	 */

    public JSONObject statsMensili (String data)
			throws ParseException, IOException, DataMeteoException, java.text.ParseException, InserimentoException {

		if(data.isEmpty()){
			throw new InserimentoException("data");
		}
		StatsNuvole stats = new StatsNuvole ();
		//ATTENZIONE!!! la data deve essere scritta nel formato mm/aaaa
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData("10/"+data).getTime())/1000));
		//il giorno 10 è predefinito e utilizzato esclusivamente per la creazione del relativo oggetto dataMeteo
		return stats.statisticheMensili(dataMeteo);
    }

	/**
	 *
	 * Metodo per calcolare statistiche totali su tutti i dati presenti nel "Database_Previsioni".
	 *
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws ParseException
	 * @throws IOException
	 * @throws DataMeteoException
	 */

	public JSONObject statsTotali ()
			throws ParseException, IOException, DataMeteoException {

		StatsNuvole stats = new StatsNuvole ();
		return stats.statisticheTotali();
    }

    //FINE METODI STATS


	//METODI FILTERS PER FILTRARE LE STATISTICHE

	/**
	 *
	 * Metodo per filtrare le statistiche giornaliere in base ai nomi delle città inserite.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi: "nomiCitta" e "data"
	 * @return
	 * @throws GestisciStringaException
	 * @throws java.text.ParseException
	 * @throws DataMeteoException
	 * @throws IOException
	 * @throws ParseException
	 * @throws NomeCittaException
	 * @throws InserimentoException
	 *
	 */
	
  	public JSONArray filtraStatsGiornaliere (JSONObject bodyNomiCittaData)
			throws GestisciStringaException, java.text.ParseException, DataMeteoException, IOException, ParseException, NomeCittaException, InserimentoException {

		if(bodyNomiCittaData.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		} else if(bodyNomiCittaData.get("data").toString().isEmpty()){
			throw new InserimentoException("data");
		}

		FiltersNuvole filterStats = new FiltersNuvole ();

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())/1000));

		//è il vettore che conterrà i nomi delle città inserite
		Vector<String> nomiCitta = new Vector<String>();

		//è la stringa contenente i nomi delle città da separare per cui si vogliono ottenere le statistiche
		String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");

		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
		}

		JSONArray risultato = new JSONArray();
		JSONObject elemento;

		for (String nome : nomiCitta) {
			elemento = filterStats.filtraStatisticheGiornaliere(dataMeteo, nome);
			risultato.add(elemento);
		}

		return risultato;
  	}

	/**
	 *
	 * Metodo per filtrare le statistiche settimanali in base ai nomi delle città inserite.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi: "nomiCitta" e "data"
	 * @return
	 * @throws java.text.ParseException
	 * @throws GestisciStringaException
	 * @throws ParseException
	 * @throws IOException
	 * @throws DataMeteoException
	 * @throws InserimentoException
	 */
  	
  	public JSONArray filtraStatsSettimanali (JSONObject bodyNomiCittaData)
			throws java.text.ParseException, GestisciStringaException, ParseException, IOException, DataMeteoException, InserimentoException {

		if(bodyNomiCittaData.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		} else if(bodyNomiCittaData.get("data").toString().isEmpty()) {
			throw new InserimentoException("data");
		}

		FiltersNuvole filterStats = new FiltersNuvole ();

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo (((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())/1000));

		//è il vettore che conterrà i nomi delle città inserite
		Vector<String> nomiCitta = new Vector<String>();

		//è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
		String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");

		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
		}

		JSONArray risultato = new JSONArray();
		JSONObject elemento;

		for (String nome : nomiCitta) {
			elemento = filterStats.filtraStatisticheSettimanali(dataMeteo,nome);
			risultato.add(elemento);
		}

		return risultato;
  	}

	/**
	 *
	 * Metodo per filtrare le statistiche mensili in base ai nomi delle città inserite.
	 *
	 * @param bodyNomiCittaData JSONObject contenente due campi: "nomiCitta" e "data" formato mm/AAAA
	 * @return
	 * @throws java.text.ParseException
	 * @throws DataMeteoException
	 * @throws GestisciStringaException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoException
	 *
	 */

  	public JSONArray filtraStatsMensili (JSONObject bodyNomiCittaData)
			throws java.text.ParseException, DataMeteoException, GestisciStringaException, IOException, ParseException, InserimentoException {

		if(bodyNomiCittaData.get("nomiCitta").toString().isEmpty()) {
			throw new InserimentoException("nomiCitta");
		} else if(bodyNomiCittaData.get("data").toString().isEmpty()) {
			throw new InserimentoException("data");
		}

		FiltersNuvole filterStats = new FiltersNuvole ();

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData("10/"+(String)bodyNomiCittaData.get("data")).getTime())/1000));
		//il giorno 10 è predefinito e utilizzato esclusivamente per la creazione del relativo oggetto dataMeteo

		//è il vettore che conterrà i nomi delle città inserite
		Vector<String> nomiCitta = new Vector<String>();

		 //è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
		String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");

		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
		}

		JSONArray risultato = new JSONArray();
		JSONObject elemento;

		for (String nome : nomiCitta) {
			elemento=filterStats.filtraStatisticheMensili(dataMeteo,nome);
			risultato.add(elemento);
		}

		return risultato;
	}

	/**
	 *
	 * Metodo per filtrare le statistiche totali in base ai nomi della città inserite.
	 *
	 * @param bodyNomiCitta JSONObject contenente in campo "nomiCitta"
	 * @return
	 * @throws GestisciStringaException
	 * @throws ParseException
	 * @throws IOException
	 * @throws DataMeteoException
	 * @throws InserimentoException
	 *
	 */

	public JSONArray filtraStatsTotali (JSONObject bodyNomiCitta)
			throws GestisciStringaException, ParseException, IOException, DataMeteoException, InserimentoException {

  		if(bodyNomiCitta.get("nomiCitta").toString().isEmpty()) {
  			throw new InserimentoException("nomiCitta");
		}
		FiltersNuvole filterStats = new FiltersNuvole ();

		//è il vettore che conterrà i nomi delle città inserite
		Vector<String> nomiCitta = new Vector<String>();

		//è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
		String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");

		GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

		//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
		for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
		} //il for si potrebbe eliminare usando poi il metodo addAll

		JSONArray risultato = new JSONArray();
		JSONObject elemento = new JSONObject();

		for (String nome : nomiCitta) {
			elemento=filterStats.filtraStatisticheTotali(nome);
			risultato.add(elemento);
		}

		return risultato;
  	}
  	
  	//FINE METODI FILTERS


	//METODO SOGLIA ERRORE

	/**
	 *
	 * Metodo per ottenere statistiche sulle previsioni azzeccate.
	 *
	 * @param bodyInizioFineCittaSoglia JSONObject contenente quattro campi: "nomeCitta" , "dataInizio" ,
	 *                                  "dataFine" e "sogliaErrore"
	 * @return
	 * @throws DataMeteoException
	 * @throws PeriodNotValidException
	 * @throws SogliaErroreNotValidException
	 * @throws ParseException
	 * @throws IOException
	 * @throws java.text.ParseException
	 * @throws InserimentoException
	 *
	 */
      
	public JSONObject controllaPrevisioniSoglia (JSONObject bodyInizioFineCittaSoglia)
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

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataInizio = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyInizioFineCittaSoglia.get("dataInizio")).getTime())/1000));

		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
		DataMeteo dataFine = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyInizioFineCittaSoglia.get("dataFine")).getTime())/1000));

		double sogliaErrore = Double.parseDouble(bodyInizioFineCittaSoglia.get("sogliaErrore").toString());

		String nomeCitta = bodyInizioFineCittaSoglia.get("nomeCitta").toString();

		return calcola.controllaPrevisioniSoglia(dataInizio,dataFine,nomeCitta,sogliaErrore);
  	}

	/**
	 *
	 * Metodo per l'estrazione dell'apiKey dal file di configurazione.
	 *
	 * @throws ParseException
	 * @throws IOException
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

