package com.OOPDekGiz.progettoDekGiz.filters;

import java.io.*;

import java.util.Vector;
import java.lang.Math;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.Calcola;
import com.OOPDekGiz.progettoDekGiz.util.DataBase;

import org.json.simple.parser.ParseException;


public class FiltersNuvole {

	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheGiornaliere (DataMeteo filtraData,String mioNomeCitta) throws DataMeteoException, ParseException, IOException, NomeCittaException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");

		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisci il Vector <MeteoCitta> i cui elementi fanno tutti riferimento allo stesso identico giorno della data di misurazione del meteo
		for(int i=0;i<jsonArrayDatiMeteoLettura.size();i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);

			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

			if(DATAMeteoCittaletto.confrontaData(filtraData)&&datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}

		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new DataMeteoException();
		}

		Calcola calcolaFilters = new Calcola ();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheMensili (DataMeteo filtraData,String mioNomeCitta) throws IOException, ParseException, DataMeteoException {
		
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
			
			if(DATAMeteoCittaletto.getMese()==filtraData.getMese()&&DATAMeteoCittaletto.getAnno()==filtraData.getAnno()&&datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}

		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new DataMeteoException();
		}
		
		Calcola calcolaFilters = new Calcola ();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheSettimanali (DataMeteo filtraData,String mioNomeCitta) throws IOException, ParseException, DataMeteoException {
		
		//statistiche disponibili sulla nuvolosità
		long min=1000;
		long max=0;
		double media=0;
		double varianza=0;
		
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
			
			if(DATAMeteoCittaletto.getSettimana()==filtraData.getSettimana()&&DATAMeteoCittaletto.getMese()==filtraData.getMese()&&DATAMeteoCittaletto.getAnno()==filtraData.getAnno()&&datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}

		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new DataMeteoException();
		}
		
		Calcola calcolaFilters = new Calcola ();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheTotali (String mioNomeCitta) throws IOException, ParseException, DataMeteoException {

		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();
		
		//costruisci il Vector <MeteoCitta> dei dati riferiti alla stessa città sul database
		for(int i=0;i<jsonArrayDatiMeteoLettura.size();i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);

			if(datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}

		Calcola calcolaFilters = new Calcola ();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}
}
