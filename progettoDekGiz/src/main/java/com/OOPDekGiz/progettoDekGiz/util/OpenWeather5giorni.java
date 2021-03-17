package com.OOPDekGiz.progettoDekGiz.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.Vector;

import com.OOPDekGiz.progettoDekGiz.exception.NomeCittaException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.*;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * Questa classe estende la superclasse OpenWeatherApiUrlGen e si occupa di gestire le chiamate alle Api 5Day/3Hour
 * Forecast per l'acquisizione dei dati meteo sulla nuvolosità ogni 3 ore fino a 5 giorni successivi alla chiamata.
 */

public class OpenWeather5giorni extends OpenWeatherApiUrlGen {

	private String Url5giorni = super.UrlBase + "forecast?";

	BufferedReader leggiApi5;

	/**
	 * Costruttore della classe che ha il compito di effettuare la chiamata alle API. Dopo aver ottenuto il nome della
	 * città e l'apiKey, apre il flusso di input BufferedReader per leggere la risposta.
	 *
	 * @param apiKey apiKey necessaria per la costruzione dell'URL
	 * @param nomeCitta nome della citta di cui si vogliono ottenere previsioni sulla nuvolosità
	 * @throws NomeCittaException errore generato dall'inserimento di una città inesistente
	 */

	public OpenWeather5giorni(String apiKey, String nomeCitta)
			throws NomeCittaException {

		super(apiKey, nomeCitta);
		try{
			URLConnection ApiOpenWeather5 = new URL(this.costruisciUrl5giorni()).openConnection();
			this.leggiApi5 = new BufferedReader(new InputStreamReader(ApiOpenWeather5.getInputStream()));
		} catch (IOException e) {
			throw new NomeCittaException(super.getNomeCitta());
		}
	}

	/**
	 * Questo metodo ausiliario costruisce l'URL corrispondente per la chiamata alle Api 5Day/3Hour Forecast.
	 *
	 * @return stringa contenente l'URL completo per la chiamata alle API
	 */

	private String costruisciUrl5giorni() {
		return (this.Url5giorni + "q=" + super.getNomeCitta() + "&appid=" + super.getApiKey());
	}
	
	/**
	 * Questo metodo estrae i dati meteo sulla nuvolosità (ogni 3 ore fino a 5 giorni successivi la chiamata)
	 * della città a cui fa riferimento la chiamata API.
	 * 
	 * @return vettore di oggetti di tipo MeteoCitta costruiti dai dati estratti dalla chiamata API
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws DataMeteoException errori relativi alla data
	 */

	public Vector<MeteoCitta> estraiDatiMeteo()
			throws ParseException, IOException, DataMeteoException {

		String StringRisultatoApi = this.leggiApi5.readLine();
		JSONParser parser = new JSONParser();
		JSONObject JsonObjectRisultatoApi = (JSONObject) parser.parse(StringRisultatoApi);
		JSONObject JsonObjectCity = (JSONObject) JsonObjectRisultatoApi.get("city");
		String nomeCitta = (String) JsonObjectCity.get("name");
		JSONArray arrayDatiCitta = (JSONArray) JsonObjectRisultatoApi.get("list");
		Vector<MeteoCitta> vettoreMeteoCitta = new Vector<MeteoCitta>();
		for(int i = 0; i < arrayDatiCitta.size(); i++) {
			JSONObject DatiCitta = (JSONObject) arrayDatiCitta.get(i);
			long UnixTime = (long) DatiCitta.get("dt");
			JSONObject JsonObjectClouds = (JSONObject) DatiCitta.get("clouds");
			int nuvolosita = Integer.parseInt(JsonObjectClouds.get("all").toString());
			MeteoCitta meteoCitta = new MeteoCitta(nuvolosita, nomeCitta, UnixTime);
			vettoreMeteoCitta.add(meteoCitta);
		}

		return vettoreMeteoCitta;
    }

	/**
	 * Metodo get per la variabile d'istanza Url5giorni.
	 *
	 * @return URL completo
	 */

	public String getUrl5giorni() {
		return this.Url5giorni;
	}

	/**
	 * Metodo set per la variabile d'istanza Url5giorni.
	 *
	 * @param url5giorni URL chiamata API
	 */

	public void setUrl5giorni(String url5giorni) {
		this.Url5giorni = url5giorni;
	}
}
