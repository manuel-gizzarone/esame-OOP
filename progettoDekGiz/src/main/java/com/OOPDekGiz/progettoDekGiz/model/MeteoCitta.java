package com.OOPDekGiz.progettoDekGiz.model;

import org.json.simple.JSONObject;

import com.OOPDekGiz.progettoDekGiz.exception.*;

/**
 * Questa classe definisce un oggetto di tipo MeteoCitta contenente i dati meteo sulla nuvolosità (in percentuale)
 * relativi ad una determinata città. Implementa l'interfaccia InterfaceToJsonObject.
 */

public class MeteoCitta implements InterfaceToJsonObject {

	private int nuvolosita;

	private String nomeCitta;

	private long unixData;

	private DataMeteo dataMeteo;

	/**
	 * Costruttore della classe che assegna i relativi valori alle variabili d'istanza.
	 * 
	 * @param nuvolosita valore percentuale di nuvolosità
	 * @param nomeCitta nome della città a cui fanno riferimento i dati meteo
	 * @param unixData data in formato Unix
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 */

	public MeteoCitta(int nuvolosita, String nomeCitta, long unixData)
			throws DataMeteoException {
		
		this.nuvolosita = nuvolosita;
		this.nomeCitta = nomeCitta;
		this.unixData = unixData;
		this.dataMeteo = new DataMeteo(unixData);
	}

	/**
	 * Implementazione del metodo castToJsonObject dell'interfaccia InterfaceToJsonObject. Esso permette di
	 * effettuare il casting in JSONObject di un oggetto di tipo MeteoCitta.
	 *
	 * @return JSONObject contenente le informazioni meteo sulla nuvolosità di una città in una certa data
	 */

	public JSONObject castToJsonObject() {
		JSONObject meteoCittaJsonObj = new JSONObject();
		meteoCittaJsonObj.put("citta", this.nomeCitta);
		meteoCittaJsonObj.put("nuvolosita", this.nuvolosita);
		meteoCittaJsonObj.put("unixData", this.unixData);
		meteoCittaJsonObj.put("Data", this.dataMeteo.toString());

		return meteoCittaJsonObj;
	}

	/**
	 * Metodo get per la variabile d'istanza nuvolosità.
	 *
	 * @return percentuale di nuvolosità
	 */
	
	public int getNuvolosita() {
		return this.nuvolosita;
	}

	/**
	 * Metodo set per la variabile d'istanza nuvolosita.
	 *
	 * @param nuvolosita nuvolosita percentuale
	 */

	public void setNuvolosita(int nuvolosita) {
		this.nuvolosita = nuvolosita;
	}

	/**
	 * Metodo get per la variabile d'istanza nomeCitta.
	 *
	 * @return nome della città a cui fanno riferimento i dati meteo
	 */
	
	public String getNomeCitta() {
		return this.nomeCitta;
	}

	/**
	 * Metodo set per la variabile d'istanza nomeCitta.
	 *
	 * @param nomeCitta nome della citta
	 */

	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * Metodo get per la variabile d'istanza unixData.
	 *
	 * @return data in formato Unix
	 */

	public long getUnixData() {
		return this.unixData;
	}

	/**
	 * Metodo set per la variabile d'istanza unixData.
	 *
	 * @param unixData data in formato Unix
	 */

	public void setUnixData(long unixData) {
		this.unixData = unixData;
	}

	/**
	 * Metodo get per la variabile d'istanza dataMeteo.
	 *
	 * @return oggetto DataMeteo contenente informazioni riguardo la data
	 */
	
	public DataMeteo getDataMeteo() {
		return this.dataMeteo;
	}

	/**
	 * Metodo set per la variabile d'istanza dataMeteo.
	 *
	 * @param dataMeteo data come oggetto DataMeteo
	 */

	public void setDataMeteo(DataMeteo dataMeteo) {
		this.dataMeteo = dataMeteo;
	}
}
