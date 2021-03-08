package com.OOPDekGiz.progettoDekGiz.service;

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

/**
 *
 * Questa classe rappresenta il servizio sul quale si base l'applicativo e contiene i metodi richiamati dal controller.
 *
 * @author Manuel Gizzarone
 * @author Emanuele De Caro
 *
 */

@Service
public class ServiceNuvole {

	String apiKey = "2300b41a61721439ff98965f79ff40db";

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

	public JSONArray serviceNuvole5giorni(JSONObject bodyNomiCitta) throws GestisciStringaException, MalformedURLException, IOException, ParseException, DataMeteoException {

		//è il JSONArray che dovrà essere la risposta alla chiamata del metodo
		//contiene i JSONObject con le info relative all nuvolosità delle città  ai relativi orari (scritti in formato Unix)
		JSONArray risultato = new JSONArray();
		DataBase dataBase = new DataBase("Database_Previsioni.json");

		// il vettore contiene i nomi delle città per cui sono richieste le previsioni sulla nuvolosità
		Vector<String> nomiCitta = new Vector<String>();

		try {

			//è la stringa contenente i nomi delle città da separare per cui si vogliono le previsioni
			//(ognuno è separato dall'altro dalla virgola)
			String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");

			//ATTENZIONE!!! la key associata alla stringa contenente i nom delle città separate dalla virgola deve essere "nomiCitta"

			GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

			//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta

			for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
			}   //il for si potrebbe eliminare usando poi il metodo addAll come indicato: nomiCitta.addAll(gestisciStringa.estraiConVirgola());

			//Collections.copy(nomiCitta,gestisciStringa.estraiConVirgola());

			//formatta il JSONArray da restituire come risultato
			for (int i = 0; i < nomiCitta.size(); i++) {
				OpenWeather5giorni api5 = new OpenWeather5giorni(apiKey, nomiCitta.get(i));
				Vector<MeteoCitta> temp = new Vector<MeteoCitta>();
				temp.addAll(api5.estraiDatiMeteo()); //potrebbe sostituirsi direttamente con: Vector<MeteoCitta> temp = new Vector<MeteoCitta>(api5.estraiDatiMeteo());
				dataBase.salvaSulDatabase(temp);

				for (int j = 0; j < temp.size(); j++) {
					risultato.add(temp.get(j).castToJsonObject());
				}
			}

			return risultato;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject o = new JSONObject();
			o.put("c'è", "un'eccezione");
			risultato.add(o);
			return risultato; //si puo sostituire direttamente con "return "C'è un'eccezione!" viene comunque visualizzato su postman come stringa
		}
	}

	/**
	 *
	 * Metodo che salva sul database "Database_Raccolta" i dati meteo di una citta aggiornati ogni ora.
	 *
	 * @param nomeCitta nome della citta di cui si vogliono raccogliere i dati meteo ogni ora
	 * @return Stringa contenente il path in cui il file, contenente la raccolta di dati meteo, viene salvato.
	 * @throws IOException
	 * @throws ParseException
	 * @throws InserimentoExeption eccezione che viene lanciata se l'utente non iserisce il nome della citta.
	 *
	 *
	 */

	public String salvaOgniOra(String nomeCitta) throws IOException, ParseException, InserimentoExeption {
		if(nomeCitta.isEmpty()){
			throw new InserimentoExeption("nomeCitta");
		} else{
			DataBase dataBase = new DataBase("Database_Raccolta.json");
			dataBase.salvaSulDatabaseOgniOra(nomeCitta);
			return "Path database:  " + System.getProperty("user.dir") + "\\" + dataBase.getNomeDatabase();
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
	 * @throws InserimentoExeption eccezione che viene lanciata se l'utente non inserisce il nome del database
	 * da formattare.
	 *
	 */

	public String eliminaDatabase(String nomeDatabase) throws IOException, ParseException, InserimentoExeption {
		if(nomeDatabase.isEmpty()) {
			throw new InserimentoExeption("nomeDatabase");
		} else {
			DataBase dataBase = new DataBase(nomeDatabase + ".json");
			dataBase.svuotaDatabase();
			return "Il contenuto del file " + nomeDatabase + " è stato eliminato.";
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
	 * @throws InserimentoExeption eccezione che viene lanciata se l'utente non inserisce il nome del database
	 * di cui si vogliono visualizzare i dati contenuti.
	 *
	 */

	public JSONArray getDatabase(String nomeDatabase) throws IOException, ParseException, InserimentoExeption {
		if(nomeDatabase.isEmpty()){
			throw new InserimentoExeption("nomeDatabase");
		} else{
			DataBase dataBase = new DataBase(nomeDatabase + ".json");
			return dataBase.getDatabase();
		}
	}


	//metodi per le statistiche media varianza max e min di nuvolosità giornaliere settimanali e mensili
	//NOTA!! I codici ripetuti potrebbero essere sostituiti da metodi ausiliari
	
	public JSONObject statsGiornaliere (String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		
		try {
		StatsNuvole stats = new StatsNuvole ();
		//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())*1000));
		
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
	
    public JSONObject statsSettimanali (String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		
    	try {
		StatsNuvole stats = new StatsNuvole ();
		//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())*1000));
		
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

    public JSONObject statsMensili (String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
	
    	try {
	    StatsNuvole stats = new StatsNuvole ();
		//ATTENZIONE!!! la data deve essere scritta nel formato gg/mm/aaaa
	    //salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
	    DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())*1000));
	
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
    

    public JSONObject statsTotali () throws IOException, ParseException, DataMeteoException, java.text.ParseException {
	
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
	
  	public JSONArray filtraStatsGiornaliere (JSONObject bodyNomiCittaData) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
  		
  		try {
  		FiltersNuvole filterStats = new FiltersNuvole ();
  		
  		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
  	    //ATTENZIONE!!! la key associata alla stringa deve essere "data" e la data deve essere scritta nel formato gg/mm/aaaa
  		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())*1000));
  		
  		/**
  		 * è il vettore che conterrà i nomi delle città inserite
  		 */
  		Vector<String> nomiCitta = new Vector<String>();

  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
		/**
		 *è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
	     *(ognuno è separato dall'altro dalla virgola)
		 */
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
  	
      public JSONArray filtraStatsSettimanali (JSONObject bodyNomiCittaData) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
  		
    	  try {
    	  		FiltersNuvole filterStats = new FiltersNuvole ();
    	  		
    	  		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
    	  	    //ATTENZIONE!!! la key associata alla stringa deve essere "data" e la data deve essere scritta nel formato gg/mm/aaaa
    	  		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())*1000));
    	  		
    	  		/**
    	  		 * è il vettore che conterrà i nomi delle città inserite
    	  		 */
    	  		Vector<String> nomiCitta = new Vector<String>();

    	  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
    			/**
    			 *è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
    		     *(ognuno è separato dall'altro dalla virgola)
    			 */
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

      public JSONArray filtraStatsMensili (JSONObject bodyNomiCittaData) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
  	
    	  try {
  	  		FiltersNuvole filterStats = new FiltersNuvole ();
  	  		
  	  		//estraggo la data come stringa e la converto come oggetto di tipo DataMeteo
  	  	    //ATTENZIONE!!! la key associata alla stringa deve essere "data" e la data deve essere scritta nel formato gg/mm/aaaa
  	  		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData((String)bodyNomiCittaData.get("data")).getTime())*1000));
  	  		
  	  		/**
  	  		 * è il vettore che conterrà i nomi delle città inserite
  	  		 */
  	  		Vector<String> nomiCitta = new Vector<String>();

  	  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
  			/**
  			 *è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
  		     *(ognuno è separato dall'altro dalla virgola)
  			 */
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
      

      public JSONArray filtraStatsTotali (JSONObject bodyNomiCitta) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
  	
    	  try {
    	  		FiltersNuvole filterStats = new FiltersNuvole ();
    	  		
    	  		/**
    	  		 * è il vettore che conterrà i nomi delle città inserite
    	  		 */
    	  		Vector<String> nomiCitta = new Vector<String>();

    	  		//ATTENZIONE!!! la key associata alla stringa contenente i nomi delle città separate dalla virgola deve essere "nomiCitta"
    			/**
    			 *è la stringa contenente i nomi delle città da separare per cui si vogliono le statistiche
    		     *(ognuno è separato dall'altro dalla virgola)
    			 */
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
    	  		elemento=filterStats.filtraStatisticheTotali((String)bodyNomiCitta.get("nomiCitta"));
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

