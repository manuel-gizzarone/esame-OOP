package com.OOPDekGiz.progettoDekGiz.service;

import java.io.File;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.filters.FiltersNuvole;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.*;
import com.OOPDekGiz.progettoDekGiz.statistics.*;

import java.io.IOException;
import java.net.MalformedURLException;


@Service
public class ServiceNuvole {

	private final String apiKey = "2300b41a61721439ff98965f79ff40db";

	/**
	 *
	 * Chiama le Api forecast 5 giorni di OpenWeather per una o più citta (i cui nomi sono inseriti nel value della key
	 * "nomiCitta" di bodyNomiCitta) e restituisce un JSONArray contenente i dati relativi alla nuvolosità di tali
	 * citta attuali e per i 5 giorni successivi alla chiamata.
	 *
	 * @param bodyNomiCitta
	 * @return il JSONArray contenente i dati relativi alla nuvolosità delle citta (i cui nomi sono inseriti nel value della key "nomiCitta" di bodyNomiCitta) per i 5 giorni da/successivi alla chiamata
	 * @throws GestisciStringaException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws DataMeteoException
	 *
	 */

	public JSONArray serviceNuvole5giorni(JSONObject bodyNomiCitta)
			throws IOException, ParseException, InserimentoException, GestisciStringaException, DataMeteoException, NomeCittaException {

		/*
		 * è il JSONArray che dovrà essere la risposta alla chiamata del metodo
		 * contiene i JSONObject con le info relative all nuvolosità delle città  ai relativi orari (scritti in formato Unix)
		 */
		JSONArray risultato = new JSONArray();
		DataBase dataBase = new DataBase("Database_Previsioni.json");

		//il vettore contiene i nomi delle città per cui sono richieste le previsioni sulla nuvolosità
		Vector<String> nomiCitta = new Vector<String>();
		
		/*
		 * è la stringa contenente i nomi delle città da separare per cui si vogliono le previsioni
		 * (ognuno è separato dall'altro dalla virgola)
		 */
		String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");
		//ATTENZIONE!!! la key associata alla stringa contenente i nom delle città separate dalla virgola deve essere "nomiCitta"
		
		if(nomiCittaDaEstrarre.isEmpty()){
			throw new InserimentoException("nomiCitta");
		}

		try{
			
			GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);
			//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
			for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
			}
		} catch (Exception e) {
			throw new GestisciStringaException();
		}

		for (int i = 0; i < nomiCitta.size(); i++) {
			try{
				OpenWeather5giorni api5 = new OpenWeather5giorni(this.apiKey, nomiCitta.get(i));
				Vector<MeteoCitta> temp = new Vector<MeteoCitta>();
				temp.addAll(api5.estraiDatiMeteo());
				dataBase.salvaSulDatabase(temp);
				for (int j = 0; j < temp.size(); j++) {
					risultato.add(temp.get(j).castToJsonObject());
				}
			} catch (IOException e) {
				throw new NomeCittaException(nomiCitta.get(i));
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
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoException eccezione che viene lanciata se l'utente non iserisce il nome della citta.
	 *
	 */

	public String salvaOgniOra(String nomeCitta)
			throws ParseException, InserimentoException, NomeCittaException {

		try{
			if(nomeCitta.isEmpty()){
				throw new InserimentoException("nomeCitta");
			} else {
				OpenWeatherCurrentMeteo openWeatherCurrentMeteo = new OpenWeatherCurrentMeteo(this.apiKey, nomeCitta);
				MeteoCitta meteoCitta = openWeatherCurrentMeteo.estraiDatiMeteo();
				DataBase dataBase = new DataBase(nomeCitta + ".json");
				dataBase.salvaSulDatabaseOgniOra(meteoCitta);
				return "Path database:  " + System.getProperty("user.dir") + "/" + dataBase.getNomeDatabase();
			}
		} catch (IOException | DataMeteoException e){
			throw  new NomeCittaException(nomeCitta);
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
		} else{
			File file = new File(System.getProperty("user.dir") + "/" + nomeDatabase + ".json");
			if(file.exists()) {
				DataBase dataBase = new DataBase(nomeDatabase + ".json");
				return dataBase.getDatabase();
			} else {
				throw new DatabaseNotFoundException(nomeDatabase);
			}
		}
	}


	//metodi per le statistiche media varianza max e min di nuvolosità giornaliere settimanali e mensili 
	
	public JSONObject statsGiornaliere (String data) {
		
		try {
			StatsNuvole stats = new StatsNuvole ();
			//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
			//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
			DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())/1000));
		
			JSONObject risultato = new JSONObject();
			risultato=stats.statisticheGiornaliere(dataMeteo);
			return risultato;
		}catch (Exception e) {
			e.printStackTrace();
			JSONObject o = new JSONObject();
			o.put("c'è", "un'eccezione");
			return o;
		}
	}
	
    public JSONObject statsSettimanali (String data) {
		
    	try {
			StatsNuvole stats = new StatsNuvole ();
			//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
			//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
			DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())/1000));
		
			JSONObject risultato = new JSONObject();
			risultato=stats.statisticheSettimanali(dataMeteo);
			return risultato;
    	}catch (Exception e) {
			e.printStackTrace();
			JSONObject o = new JSONObject();
			o.put("c'è", "un'eccezione");
			return o;
		}
	}

    public JSONObject statsMensili (String data) {
	
    	try {
	    	StatsNuvole stats = new StatsNuvole ();
			//ATTENZIONE!!! la data deve essere scritta nel formato mm/aaaa
	    	//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
	    	DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData("10/"+data).getTime())/1000));
	    	//il giorno 10 è predefinito e utilizzato esclusivamente per la creazione del relativo oggetto dataMeteo
	
	    	JSONObject risultato = new JSONObject();
	    	risultato=stats.statisticheMensili(dataMeteo);
	    	return risultato;
    	}catch (Exception e) {
			e.printStackTrace();
			JSONObject o = new JSONObject();
			o.put("c'è", "un'eccezione");
			return o;
		}
	
    }
    

    public JSONObject statsTotali () {
	
    	try {
	    	StatsNuvole stats = new StatsNuvole ();
	    	JSONObject risultato = new JSONObject();
	    	risultato=stats.statisticheTotali();
	    	return risultato;
    	}catch (Exception e) {
			e.printStackTrace();
			JSONObject o = new JSONObject();
			o.put("c'è", "un'eccezione");
			return o;
		}
	
    }
	
    //fine metodi statistiche


    //metodi per filtrare le statistiche media varianza max e min di nuvolosità giornaliere settimanali e mensili su una o più nomi di città
	
  	public JSONArray filtraStatsGiornaliere (JSONObject bodyNomiCittaData) {
  		
  		try {
  			FiltersNuvole filterStats = new FiltersNuvole ();
			//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
			//ATTENZIONE!!! la key associata alla stringa deve essere "data" e la data deve essere scritta nel formato gg/mm/aaaa
			DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())/1000));
			
			//DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())/1000));

			//è il vettore che conterrà i nomi delle città inserite
			Vector<String> nomiCitta = new Vector<String>();

			//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

			//è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
			//(ognuno è separato dall'altro dalla virgola)
			String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");

			GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

			//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
			for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
					String temp = gestisciStringa.estraiConVirgola().get(i);
					nomiCitta.add(temp);
			} //il for si potrebbe eliminare usando poi il metodo addAll

			JSONArray risultato = new JSONArray();
			JSONObject elemento = new JSONObject();

			for (String nome : nomiCitta) {
				elemento=filterStats.filtraStatisticheGiornaliere(dataMeteo,nome);
				risultato.add(elemento);
			}
			return risultato;
  		}catch (Exception e) {
  			e.printStackTrace();
  			JSONArray a= new JSONArray();
  			JSONObject o = new JSONObject();
  			o.put("c'è", "un'eccezione");
  			a.add(o);
  			return a;
  		}
  	}
  	
      public JSONArray filtraStatsSettimanali (JSONObject bodyNomiCittaData) {
  		
    	  try {
    	  		FiltersNuvole filterStats = new FiltersNuvole ();
    	  		
    	  		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
    	  	    //ATTENZIONE!!! la key associata alla stringa deve essere "data" e la data deve essere scritta nel formato gg/mm/aaaa
    	  		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())/1000));

    	  		//è il vettore che conterrà i nomi delle città inserite
    	  		Vector<String> nomiCitta = new Vector<String>();

    	  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

			  	//è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
			  	//(ognuno è separato dall'altro dalla virgola)
    			String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");
    			
    			GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

    			//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
    			for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
    					String temp = gestisciStringa.estraiConVirgola().get(i);
    					nomiCitta.add(temp);
    			} //il for si potrebbe eliminare usando poi il metodo addAll
    				
    	  		JSONArray risultato = new JSONArray();
    	  		JSONObject elemento = new JSONObject();
    	  		
    	  		for (String nome : nomiCitta) {
    	  			elemento=filterStats.filtraStatisticheSettimanali(dataMeteo,nome);
    	  			risultato.add(elemento);
    	  		}
    	  		return risultato;
    	  		}catch (Exception e) {
    	  			e.printStackTrace();
    	  			JSONArray a= new JSONArray();
    	  			JSONObject o = new JSONObject();
    	  			o.put("c'è", "un'eccezione");
    	  			a.add(o);
    	  			return a;
    	  		}
  	}

      public JSONArray filtraStatsMensili (JSONObject bodyNomiCittaData) {
  	
    	  try {

  	  		FiltersNuvole filterStats = new FiltersNuvole ();
  	  		
  	  		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
  	  	    //ATTENZIONE!!! la key associata alla stringa deve essere "data" e la data deve essere scritta nel formato gg/mm/aaaa
  	  		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData("10/"+(String)bodyNomiCittaData.get("data")).getTime())/1000));
  	  	    //il giorno 10 è predefinito e utilizzato esclusivamente per la creazione del relativo oggetto dataMeteo


		    //è il vettore che conterrà i nomi delle città inserite
  	  		Vector<String> nomiCitta = new Vector<String>();

  	  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

  			 //è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
  		     //(ognuno è separato dall'altro dalla virgola)
  			String nomiCittaDaEstrarre = (String) bodyNomiCittaData.get("nomiCitta");
  			
  			GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

  			//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta
  			for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
  					String temp = gestisciStringa.estraiConVirgola().get(i);
  					nomiCitta.add(temp);
  			} //il for si potrebbe eliminare usando poi il metodo addAll
  				
  	  		JSONArray risultato = new JSONArray();
  	  		JSONObject elemento = new JSONObject();
  	  		
  	  		for (String nome : nomiCitta) {
  	  			elemento=filterStats.filtraStatisticheMensili(dataMeteo,nome);
  	  			risultato.add(elemento);
  	  		}
  	  		return risultato;
  	  		}catch (Exception e) {
  	  			e.printStackTrace();
  	  			JSONArray a= new JSONArray();
  	  			JSONObject o = new JSONObject();
  	  			o.put("c'è", "un'eccezione");
  	  			a.add(o);
  	  			return a;
  	  		}
  	
      }
      

      public JSONArray filtraStatsTotali (JSONObject bodyNomiCitta) {
  	
    	  try {
    	  		FiltersNuvole filterStats = new FiltersNuvole ();

    	  		//è il vettore che conterrà i nomi delle città inserite
    	  		Vector<String> nomiCitta = new Vector<String>();

    	  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"

    			 //è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
    		     //(ognuno è separato dall'altro dalla virgola)
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
    	  		}catch (Exception e) {
    	  			e.printStackTrace();
    	  			JSONArray a= new JSONArray();
    	  			JSONObject o = new JSONObject();
    	  			o.put("c'è", "un'eccezione");
    	  			a.add(o);
    	  			return a;
    	  		}
      }
  	
      //fine metodi filtri
}

