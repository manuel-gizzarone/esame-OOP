package com.OOPDekGiz.progettoDekGiz.util;

/**
 * Superclasse che contiene l'URL comune alle chiamate Api Current Weather ed Api 5Day/3Hours Forecast, l'apiKey e il
 * nome della città di cui si sono richiesti i dati meteo.
 */

public class OpenWeatherApiUrlGen {

	private String apiKey;

	private String nomeCitta;

	protected String UrlBase = "http://api.openweathermap.org/data/2.5/"; //URL comune ad entrambe le chiamate API

	/**
	 * Costruttore della superclasse che inizializza il valore dell'apiKey e il nome della citta inserita dall'utente.
	 *
	 * @param apiKey apiKey necessaria per la costruzione dell'URL
	 * @param nomeCitta nome della città di cui si sono richiesti i dati meteo
	 */

	public OpenWeatherApiUrlGen(String apiKey, String nomeCitta) {
		this.apiKey = apiKey;
		this.nomeCitta = nomeCitta;
	}

	/**
	 * Metodo get per la variabile d'istanza nomeCitta.
	 *
	 * @return nome della città
	 */

	public String getNomeCitta() {
		return this.nomeCitta;
	}

	/**
	 * Metodo set per la variabile d'istanza nomeCitta.
	 */

	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * Metodo get per la variabile d'istanza apiKey.
	 *
	 * @return stringa contenente l'apiKey
	 */

	public String getApiKey() {
		return this.apiKey;
	}

	/**
	 * Metodo set per la variabile d'istanza apiKey.
	 */

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
