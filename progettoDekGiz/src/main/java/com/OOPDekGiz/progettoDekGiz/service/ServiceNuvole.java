package com.OOPDekGiz.progettoDekGiz.service;

import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.*;
import com.OOPDekGiz.progettoDekGiz.statistics.*;

import java.io.IOException;
import java.net.MalformedURLException;


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

		/**
		 * è il JSONArray che dovrà essere la risposta alla chiamata del metodo
		 * contiene i JSONObject con le info relative all nuvolosità delle città  ai relativi orari (scritti in formato Unix)
		 */

		JSONArray risultato = new JSONArray();
		DataBase dataBase = new DataBase("Database_Previsioni.json");

		/**
		 *
		 * il vettore contiene i nomi delle città per cui sono richieste le previsioni sulla nuvolosità
		 *
		 */

		Vector<String> nomiCitta = new Vector<String>();

		try {

			/**
			 * è la stringa contenente i nomi delle città da separare per cui si vogliono le previsioni
			 * (ognuno è separato dall'altro dalla virgola)
			 */

			String nomiCittaDaEstrarre = (String) bodyNomiCitta.get("nomiCitta");
			//ATTENZIONE!!! la key associata alla stringa contenente i nom delle città separate dalla virgola deve essere "nomiCitta"

			GestisciStringhe gestisciStringa = new GestisciStringhe(nomiCittaDaEstrarre);

			//inserisce i nomi delle citta estratti dal bodyNomiCitta nel vettore di stringhe nomiCitta

			for (int i = 0; i < gestisciStringa.estraiConVirgola().size(); i++) {
				String temp = gestisciStringa.estraiConVirgola().get(i);
				nomiCitta.add(temp);
			} //il for si potrebbe eliminare usando poi il metodo addAll

			//Collections.copy(nomiCitta,gestisciStringa.estraiConVirgola());

			//formatta il JSONArray da restituire come risultato

			for (int i = 0; i < nomiCitta.size(); i++) {
				OpenWeather5giorni api5 = new OpenWeather5giorni(apiKey, nomiCitta.get(i));
				Vector<MeteoCitta> temp = new Vector<MeteoCitta>();
				temp.addAll(api5.estraiDatiMeteo());
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
			return risultato;
		}
		//fine metodo relativo alla rotta api5 giorni
	}
	
	
	
	//metodi per le statistiche media varianza max e min di nuvolosità giornaliere settimanali e mensili 
	
	public JSONObject statsGiornaliere (String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		
		StatsNuvole stats = new StatsNuvole ();
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())*1000));
		
		JSONObject risultato = new JSONObject();
		risultato=stats.statisticheGiornaliere(dataMeteo);
		return risultato;
		
	}
	
    public JSONObject statsSettimanali (String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
		
		StatsNuvole stats = new StatsNuvole ();
		//salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
		DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())*1000));
		
		JSONObject risultato = new JSONObject();
		risultato=stats.statisticheSettimanali(dataMeteo);
		return risultato;
		
	}

    public JSONObject statsMensili (String data) throws IOException, ParseException, DataMeteoException, java.text.ParseException {
	
	    StatsNuvole stats = new StatsNuvole ();
	    //salvo la data ottenuta come stringa come oggetto di tipo DataMeteo
	    DataMeteo dataMeteo = new DataMeteo ((long)((GestisciStringhe.StringToData(data).getTime())*1000));
	
	    JSONObject risultato = new JSONObject();
	    risultato=stats.statisticheMensili(dataMeteo);
	    return risultato;
	
    }
	
    //fine metodi statistiche
}

