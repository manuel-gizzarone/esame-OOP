package com.OOPDekGiz.progettoDekGiz.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.*;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;

/**
 * legge il contenuto della risposta alle api di openweather per le previsioni meteo ogni 3 ore fino a 5 giorni successivi alla chiamata
 * estrae dalla risposta le informazioni relative alla nuvolosità di una citta ad ogni orario (tra quelli della risposta)
 * @author emanuele
 *
 */
public class OpenWeather5giorni extends OpenWeatherApiUrlGen {
	
	/**
	 * contiene l'url completo per la chiamata all'api meteo 5 giorni
	 */
	protected String Url5giorni=UrlBase+"forecast?";
	
	protected URLConnection ApiOpenWeather5;
	protected BufferedReader leggiApi5;
	
	/**
	 * costruttore
	 * costruisce l'url completo per la chiamata all'api meteo 5 giorni una volta ottenuti il nome della città e l'apikey
	 * apre un flusso di input BufferedReader per leggere la risposta delle api
	 */
	OpenWeather5giorni (String apiKey, String nomeCitta) throws MalformedURLException, IOException{	
	super(apiKey,nomeCitta);
	
    ApiOpenWeather5 = new URL(this.costruisciUrl5giorni(apiKey,nomeCitta)).openConnection();
	leggiApi5 = new BufferedReader(new InputStreamReader(ApiOpenWeather5.getInputStream()));
	
	}
	
	
	/**
	 * costruisce l'url corrispondente alla chiamata api 3 ore 5 giorni forecast
	 * @param apiKey
	 * @param nomeCitta
	 * @return l'url completo
	 */
	public String costruisciUrl5giorni(String apiKey,String nomeCitta) {
		Url5giorni+="q="+nomeCitta;
		Url5giorni+="&";
	    Url5giorni+="appid="+apiKey;
		return Url5giorni;
	}
	
	/**
	 * estrae i dati sulla nuvolosità città relativi alla chiamata api 3 ore 5 giorni forecast
	 * 
	 * @return il vettore di oggetti di tipo MeteoCitta costruiti dai dati estratti dalla chiamata all'api
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DataMeteoException
	 */
	public Vector<MeteoCitta> estraiDatiMeteo () throws ParseException,MalformedURLException, IOException, DataMeteoException{
		
		JSONParser parser = new JSONParser();
		
		Vector<MeteoCitta> vettoreMeteoCitta = new Vector<MeteoCitta>();

		String StringRisultatoApi = leggiApi5.readLine();
		
		JSONObject JsonObjectRisultatoApi = (JSONObject)parser.parse(StringRisultatoApi);
		
		JSONArray arrayDatiCitta = (JSONArray) JsonObjectRisultatoApi.get("list");
		
		for(int i=0; i<arrayDatiCitta.size(); i++)
		{
			JSONObject DatiCitta = (JSONObject) arrayDatiCitta.get(i);
			
			String nomeCitta = (String) DatiCitta.get("name");
			long UnixTime = (long) DatiCitta.get("dt");
			JSONObject JsonObjectClouds =(JSONObject) DatiCitta.get("clouds");
			
			//per nuvolosita si intende il numero %
			int nuvolosita = (int) JsonObjectClouds.get("all");  
			//int nuvolosita = Integer.parseInt(JsonObjectClouds.get("all").toString());
			
			MeteoCitta meteoCitta = new MeteoCitta(nuvolosita,nomeCitta,UnixTime);
				
			vettoreMeteoCitta.add(meteoCitta);
		}
	return vettoreMeteoCitta;
    }

    //metodi get e set
	public String getUrl5giorni() {
		return Url5giorni;
	}
	
	
	
	
}
