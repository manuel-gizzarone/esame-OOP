package com.OOPDekGiz.progettoDekGiz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;



public class OpenWeatherApiUrlGen {
		
		/**
		 * Stringa contenente l'Api key dell'account OpenWeather
		 */
		protected String apiKey;
		protected String nomeCitta;
		protected String UrlBase="api.openweathermap.org/data/2.5/";
		
	
		public OpenWeatherApiUrlGen(String apiKey, String nomeCitta) {
			
			this.apiKey = apiKey;
			this.nomeCitta = nomeCitta;
		
		}
		
		public String getNomeCitta() {
			return nomeCitta;
		}


		public void setNomeCitta(String nomeCitta) {
			this.nomeCitta = nomeCitta;
		}


		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}
		
	

}
