package com.OOPDekGiz.progettoDekGiz.model;

import org.json.simple.JSONObject;


import com.OOPDekGiz.progettoDekGiz.exception.*;


/**
 * 
 * 
 * la classe definisce un oggetto MeteoCitta il quale dovra contenere i dati di nuvolosità percentuale di una certa 
 * citta ad una certa citta
 * 
 * implementa l'interfaccia InterfaceToJsonObject
 * 
 * @author emanuele
 *
 */
public class MeteoCitta {
	
	/**
	 *  nuvolosità in percentuale %
	 */
	protected int nuvolosita;
	
	/**
	 *  nome della città a cui fanno riferimento i dati meteo
	 */
	protected String nomeCitta;
	
	/**
	 * data a cui fanno riferimento i dati meteo della città secondo lo standard Unix
	 */
	protected long unixData;
	
	/**
	 * data a cui fanno riferimento i dati meteo della città come oggetto DataMeteo
	 */
	protected DataMeteo dataMeteo;

	/**
	 * costruttore per MeteoCitta : riceve tra i parametri la data ,in formato unix (UnixData),
	 * a cui fanno riferimento le previsioni di nuvolosità e istanzia il relativo oggetto DataMeteo
	 * 
	 * @param nuvolosita
	 * @param nomeCitta
	 * @param unixData
	 * @throws DataMeteoException 
	 */
	public MeteoCitta(int nuvolosita, String nomeCitta, long unixData) throws DataMeteoException {
		
		this.nuvolosita = nuvolosita;
		this.nomeCitta = nomeCitta;
		this.unixData = unixData;
		dataMeteo = new DataMeteo (unixData);
	}
	
	
	//metodi set e get di tutti campi della classe
	
	public int getNuvolosita() {
		return nuvolosita;
	}

	public void setNuvolosita(int nuvolosita) {
		this.nuvolosita = nuvolosita;
	}
	
	public String getNomeCitta() {
		return nomeCitta;
	}

	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	public long getUnixData() {
		return unixData;
	}

	public void setUnixData(long unixData) {
		this.unixData = unixData;
	}

	
	
	
	/*
	public JSONObject castToJsonObject () {
		
	}
	
	
	*/
	
	
	
	

}
