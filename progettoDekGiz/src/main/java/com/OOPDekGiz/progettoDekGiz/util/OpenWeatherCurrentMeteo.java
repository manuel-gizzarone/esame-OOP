package com.OOPDekGiz.progettoDekGiz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.OOPDekGiz.progettoDekGiz.exception.NomeCittaException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;


/**
 *
 * Questa classe estende la superclasse OpenWeatherApiUrlGen e si occupa di gestire le chiamate alle ApiCurrent
 * per il salvataggio dei dati meteo attuali sulla nuvolosità.
 *
 * @author Manuel Gizzarone
 * @author Emanuele De Caro
 *
 */


public class OpenWeatherCurrentMeteo extends OpenWeatherApiUrlGen {

	private String UrlCurrent = super.UrlBase + "weather?";

	BufferedReader leggiApiC;

	/**
	 *
	 * Costruttore della classe che ha il compito di costruire l'URL completo per la chiamata alle ApiCurrent. Dopo aver
	 * ottenuto il nome della città e l'apiKey, apre il flusso di input BufferedReader per leggere la risposta.
	 *
	 * @param apiKey apiKey necessaria per la costruzione dell'URL
	 * @param nomeCitta nome della citta di cui si vogliono ottenere i dati meteo istantanei
	 * @throws IOException
	 * @throws ParseException
	 *
	 */

	public OpenWeatherCurrentMeteo (String apiKey, String nomeCitta) throws NomeCittaException {
		super(apiKey, nomeCitta);
		try {
			URLConnection ApiOpenWeatherC = new URL(this.costruisciUrlCurrent()).openConnection();
			leggiApiC = new BufferedReader(new InputStreamReader(ApiOpenWeatherC.getInputStream()));
		} catch (IOException e) {
			throw new NomeCittaException(super.getNomeCitta());
		}
	}

	/**
	 *
	 * Questo metodo costruisce l'URL corrispondente alla chiamata alle ApiCurrent.
	 *
	 * @return URL completo per la chiamata alle ApiCurrent
	 *
	 */

	public String costruisciUrlCurrent() {
		return (UrlCurrent + "q=" + super.getNomeCitta() + "&appid=" + super.getApiKey());
	}
	
	/**
	 *
	 * Questo metodo estrae i dati meteo attuali sulla nuvolosità della città inserita dall'utente.
	 * 
	 * @return l'oggetto MeteoCitta costruito dai dati estratti dalla chiamata all'ApiCurrent
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DataMeteoException
	 *
	 */

	public MeteoCitta estraiDatiMeteo () throws ParseException, MalformedURLException, IOException, DataMeteoException {
		String StringRisultatoApi = leggiApiC.readLine();

		JSONParser parser = new JSONParser();
		JSONObject JsonObjectRisultatoApi = (JSONObject) parser.parse(StringRisultatoApi);
		String nomeCitta = (String) JsonObjectRisultatoApi.get("name");
		long UnixTime = (long) JsonObjectRisultatoApi.get("dt");
		JSONObject JsonObjectClouds = (JSONObject) JsonObjectRisultatoApi.get("clouds");
		int nuvolosita = Integer.parseInt(JsonObjectClouds.get("all").toString());
		return new MeteoCitta(nuvolosita, nomeCitta, UnixTime);
    }

    //metodi setter e getter per ottenere l'URL

	public String getUrlCurrent() {
		return UrlCurrent;
	}

	public void setUrlCurrent(String urlCurrent) {
		UrlCurrent = urlCurrent;
	}
}
