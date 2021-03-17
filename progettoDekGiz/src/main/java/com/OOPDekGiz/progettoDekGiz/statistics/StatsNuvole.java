package com.OOPDekGiz.progettoDekGiz.statistics;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.Calcola;
import com.OOPDekGiz.progettoDekGiz.util.DataBase;

import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * Questa classe contiene i metodi richiamati dalla classe ServiceNuvole per il calcolo delle statistiche.
 * Tali statistiche si basano su uno storico di dati meteo contenuti all'interno del "Database_Previsioni".
 */

public class StatsNuvole {

	/**
	 * Questo metodo consente di calcolare statistiche giornaliere sulla base dei dati meteo presenti nel database.
	 *
	 * @param filtraData data in cui si vogliono calcolare le statistiche (giornaliere)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 */

	@SuppressWarnings("unchecked")
	public JSONObject statisticheGiornaliere(DataMeteo filtraData)
			throws IOException, ParseException, DataMeteoException {

		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento allo stesso identico giorno
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();
			
			if(DATAMeteoCittaletto.confrontaData(filtraData)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato e vuoto significa che la data inserita non e presente nei dati contenuti dal database
		if(ArrayDatiMeteoRisultato.isEmpty()) {
			throw new DataMeteoException();
		}
		
		Calcola calcolaStats = new Calcola();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
	}

	/**
	 * Questo metodo consente di calcolare statistiche settimanali sulla base dei dati meteo presenti nel database.
	 *
	 * @param filtraData data in cui si vogliono calcolare le statistiche (settimanali)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 */

	@SuppressWarnings("unchecked")
	public JSONObject statisticheSettimanali(DataMeteo filtraData)
			throws IOException, ParseException, DataMeteoException {

		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento alla stessa settimana del mese
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

			if(DATAMeteoCittaletto.getSettimana() == filtraData.getSettimana() && DATAMeteoCittaletto.getMese() == filtraData.getMese() && DATAMeteoCittaletto.getAnno() == filtraData.getAnno()) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato e vuoto significa che la data inserita non e presente nei dati contenuti dal database
		//e quindi non e possibile calcolare le statistiche settimanali
		if(ArrayDatiMeteoRisultato.isEmpty()) {
			throw new DataMeteoException();
		}

		Calcola calcolaStats = new Calcola();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
	}

	/**
	 * Questo metodo consente di calcolare statistiche mensili sulla base dei dati meteo presenti nel database.
	 *
	 * @param filtraData mese in cui si vogliono calcolare le statistiche (formato mm/yyyy)
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 */

	@SuppressWarnings("unchecked")
	public JSONObject statisticheMensili(DataMeteo filtraData)
			throws IOException, ParseException, DataMeteoException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento allo stesso mese dell'anno
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();
			
			if(DATAMeteoCittaletto.getMese() == filtraData.getMese() && DATAMeteoCittaletto.getAnno() == filtraData.getAnno()) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato e vuoto significa che il mese inserito non e presente nei dati contenuti dal database
		if(ArrayDatiMeteoRisultato.isEmpty()) {
			throw new DataMeteoException();
		}
		
		Calcola calcolaStats = new Calcola();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
	}

	/**
	 * Questo metodo consente di calcolare statistiche totali cioè su tutti i dati presenti nel database.
	 *
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws IOException errori di input/output su file
	 * @throws ParseException errori durante il parsing
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 */
	
	@SuppressWarnings("unchecked")
	public JSONObject statisticheTotali()
			throws IOException, ParseException, DataMeteoException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato contenente tutti i dati presenti nel database
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);
		    ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
		}
		
		Calcola calcolaStats = new Calcola();
		return calcolaStats.calcolaStats(ArrayDatiMeteoRisultato);
	}
}
