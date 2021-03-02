package com.OOPDekGiz.progettoDekGiz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;

public class OpenWeatherCurrentMeteo extends OpenWeatherApiUrlGen {
	
	/**
	 * contiene l'url completo per la chiamata all'api current meteo
	 */
	protected String UrlCurrent=UrlBase+"weather?";
	
	protected URLConnection ApiOpenWeatherC;
	protected BufferedReader leggiApiC;
	
	/**
	 * costruttore
	 * costruisce l'url completo per la chiamata all'api current meteo una volta ottenuti il nome della città e l'apikey
	 * apre un flusso di input BufferedReader per leggere la risposta delle api
	 */
	OpenWeatherCurrentMeteo (String apiKey, String nomeCitta) throws MalformedURLException, IOException{	
	super(apiKey,nomeCitta);
	
    ApiOpenWeatherC = new URL(this.costruisciUrlCurrent(apiKey,nomeCitta)).openConnection();
	leggiApiC = new BufferedReader(new InputStreamReader(ApiOpenWeatherC.getInputStream()));
	
	}
	
	
	/**
	 * costruisce l'url corrispondente alla chiamata api current meteo
	 * @param apiKey
	 * @param nomeCitta
	 * @return l'url completo
	 */
	public String costruisciUrlCurrent(String apiKey,String nomeCitta) {
		UrlCurrent+="q="+nomeCitta;
		UrlCurrent+="&";
		UrlCurrent+="appid="+apiKey;
		return UrlCurrent;
	}
	
	/**
	 * estrae i dati sulla nuvolosità città relativi alla chiamata api current meteo
	 * 
	 * @return l'oggetto MeteoCitta costruito dai dati estratti dalla chiamata all'api
	 * @throws ParseException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DataMeteoException
	 */
	public MeteoCitta estraiDatiMeteo () throws ParseException,MalformedURLException, IOException, DataMeteoException{
		
		JSONParser parser = new JSONParser();
		
		//Vector<MeteoCitta> vettoreMeteoCitta = new Vector<MeteoCitta>();

		String StringRisultatoApi = leggiApiC.readLine();
		
		JSONObject JsonObjectRisultatoApi = (JSONObject)parser.parse(StringRisultatoApi);
		
		String nomeCitta = (String) JsonObjectRisultatoApi.get("name");
		long UnixTime = (long) JsonObjectRisultatoApi.get("dt");
		JSONObject JsonObjectClouds =(JSONObject) JsonObjectRisultatoApi.get("clouds");
		
		//per nuvolosita si intende il numero %
		int nuvolosita = (int) JsonObjectClouds.get("all");  
		//int nuvolosita = Integer.parseInt(JsonObjectClouds.get("all").toString());
		
		MeteoCitta meteoCitta = new MeteoCitta(nuvolosita,nomeCitta,UnixTime);
		
		return meteoCitta;
    }


	//metodi get e set
	public String getUrlCurrent() {
		return UrlCurrent;
	}

    
	
	
	

}
