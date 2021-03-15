package com.OOPDekGiz.progettoDekGiz.statistics;

import java.io.*;

import java.util.Vector;
import java.lang.Math;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.Calcola;
import com.OOPDekGiz.progettoDekGiz.util.DataBase;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;

public class StatsNuvole {

	@SuppressWarnings("unchecked")
	public JSONObject statisticheGiornaliere (DataMeteo filtraData) throws IOException, ParseException, DataMeteoException {
	
		//Database_Raccolta.json
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();
		
		//costruisci il Vector <MeteoCitta> i cui elementi fanno tutti riferimento allo stesso identico giorno della data di misurazione del meteo
		for(int i=0;i<jsonArrayDatiMeteoLettura.size();i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();
			
			if(DATAMeteoCittaletto.confrontaData(filtraData)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se il JSONArray e vuoto significa che la data inserita non e presente nei dati del database
		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new DataMeteoException();
		}
		
		Calcola calcolaStats = new Calcola ();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public JSONObject statisticheMensili (DataMeteo filtraData) throws IOException, ParseException, DataMeteoException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();
		
		//costruisci il Vector <MeteoCitta> i cui elementi fanno tutti riferimento allo stesso identico mese (dello stesso anno) di misurazione del meteo
		for(int i=0;i<jsonArrayDatiMeteoLettura.size();i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();
			
			if(DATAMeteoCittaletto.getMese()==filtraData.getMese()&&DATAMeteoCittaletto.getAnno()==filtraData.getAnno()) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
			
		}
		//se il JSONArray e vuoto significa che la data inserita non e presente nei dati del database
		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new DataMeteoException();
		}
		
		Calcola calcolaStats = new Calcola ();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public JSONObject statisticheSettimanali (DataMeteo filtraData) throws IOException, ParseException, DataMeteoException {
		
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		
		
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();
		
		//costruisci il Vector <MeteoCitta> i cui elementi fanno tutti riferimento alla stessa identica settimana dello stesso identico mese (dello stesso anno) di misurazione del meteo
		for(int i=0;i<jsonArrayDatiMeteoLettura.size();i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();
			
			if(DATAMeteoCittaletto.getSettimana()==filtraData.getSettimana()&&DATAMeteoCittaletto.getMese()==filtraData.getMese()&&DATAMeteoCittaletto.getAnno()==filtraData.getAnno()) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
			
		}
		//se il JSONArray e vuoto significa che la data inserita non e presente nei dati del database
		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new DataMeteoException();
		}
		
		Calcola calcolaStats = new Calcola ();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject statisticheTotali () throws IOException, ParseException, DataMeteoException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();
		
		//costruisci il Vector <MeteoCitta> dei dati sul database
		for(int i=0;i<jsonArrayDatiMeteoLettura.size();i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			
		    ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
		
		}
		
		Calcola calcolaStats = new Calcola ();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
		
	}

	

}
