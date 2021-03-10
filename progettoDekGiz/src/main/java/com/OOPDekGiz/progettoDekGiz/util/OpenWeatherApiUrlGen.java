package com.OOPDekGiz.progettoDekGiz.util;

/**
 *
 * Superclasse che contiene l'URL comune alle chiamate ApiCurrent ed Api5Giorni, l'apiKey e il nome della città di cui
 * si sono richiesti i dati meteo.
 *
 */

public class OpenWeatherApiUrlGen {

		private String apiKey;

		private String nomeCitta;

		protected String UrlBase = "http://api.openweathermap.org/data/2.5/";

		/**
		 *
		 * Costruttore della superclasse che contiene il valore dell'apiKey e il nome della citta inserita dall'utente.
		 *
		 * @param apiKey apiKey necessaria per la costruzione dell'URL
		 * @param nomeCitta nome della città di cui si sono richiesti i dati meteo
		 *
		 */

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
