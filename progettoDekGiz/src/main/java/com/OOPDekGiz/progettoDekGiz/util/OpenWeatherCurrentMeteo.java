package com.OOPDekGiz.progettoDekGiz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.OOPDekGiz.progettoDekGiz.exception.NomeCittaException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;

/**
 * Questa classe estende la superclasse OpenWeatherApiUrlGen e si occupa di gestire le chiamate alle Api Current
 * Weather per il salvataggio dei dati meteo istantanei sulla nuvolosità.
 */

public class OpenWeatherCurrentMeteo extends OpenWeatherApiUrlGen {

	private String UrlCurrent = super.UrlBase + "weather?";

	BufferedReader leggiApiC;

	/**
	 * Costruttore della classe che ha il compito di effettuare la chiamata alle API. Dopo aver ottenuto l'apiKey ed il
	 * nome della città, apre il flusso di input BufferedReader per leggere la risposta.
	 *
	 * @param apiKey apiKey necessaria per la costruzione dell'URL
	 * @param nomeCitta nome della citta di cui si vogliono ottenere i dati meteo istantanei
	 * @throws NomeCittaException errore generato dall'inserimento di una città inesistente
	 */

	public OpenWeatherCurrentMeteo (String apiKey, String nomeCitta)
			throws NomeCittaException {

		super(apiKey, nomeCitta);
		try {
			URLConnection ApiOpenWeatherC = new URL(this.costruisciUrlCurrent()).openConnection();
			this.leggiApiC = new BufferedReader(new InputStreamReader(ApiOpenWeatherC.getInputStream()));
		} catch (IOException e) {
			throw new NomeCittaException(super.getNomeCitta());
		}
	}

	/**
	 * Questo metodo ausiliario costruisce l'URL corrispondente per la chiamata alle Api Current Weather.
	 *
	 * @return stringa contenente l'URL completo per la chiamata alle API
	 */

	private String costruisciUrlCurrent() {
		return (this.UrlCurrent + "q=" + super.getNomeCitta() + "&appid=" + super.getApiKey());
	}
	
	/**
	 * Questo metodo estrae i dati meteo istantanei sulla nuvolosità della città a cui fa riferimento la chiamata API.
	 *
	 * @return oggetto MeteoCitta costruito dai dati estratti dalla chiamata API
	 * @throws ParseException errori durente il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException eccezzione lanciata nel caso si verifichino problemi con la data inserita dall'utente
	 */

	public MeteoCitta estraiDatiMeteo()
			throws ParseException, IOException, DataMeteoException {

		String StringRisultatoApi = this.leggiApiC.readLine();
		JSONParser parser = new JSONParser();
		JSONObject JsonObjectRisultatoApi = (JSONObject) parser.parse(StringRisultatoApi);
		String nomeCitta = (String) JsonObjectRisultatoApi.get("name");
		long UnixTime = (long) JsonObjectRisultatoApi.get("dt");
		JSONObject JsonObjectClouds = (JSONObject) JsonObjectRisultatoApi.get("clouds");
		int nuvolosita = Integer.parseInt(JsonObjectClouds.get("all").toString());
		return new MeteoCitta(nuvolosita, nomeCitta, UnixTime);
    }

	/**
	 * Metodo get per la variabile d'istanza UrlCurrent.
	 *
	 * @return URL completo
	 */

	public String getUrlCurrent() {
		return this.UrlCurrent;
	}

	/**
	 * Metodo set per la variabile d'istanza UrlCurrent.
	 *
	 * @param urlCurrent URL chaiama API
	 */

	public void setUrlCurrent(String urlCurrent) {
		this.UrlCurrent = urlCurrent;
	}
}
