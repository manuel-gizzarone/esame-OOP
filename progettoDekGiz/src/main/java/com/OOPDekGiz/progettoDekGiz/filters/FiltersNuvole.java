package com.OOPDekGiz.progettoDekGiz.filters;

import java.io.*;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.Calcola;
import com.OOPDekGiz.progettoDekGiz.util.DataBase;

import org.json.simple.parser.ParseException;

/**
 * Questa classe contiene i metodi richiamati dalla classe ServiceNuvole per il calcolo delle statistiche filtrate per
 * periodo e città. Tali statistiche si basano su uno storico di dati meteo contenuti all'interno del
 * "Database_Previsioni".
 */

public class FiltersNuvole {

	/**
	 * Questo metodo consente di calcolare statistiche giornaliere filtrate per data e città.
	 *
	 * @param filtraData data in cui si vogliono calcolare le statistiche (giornaliere)
	 * @param mioNomeCitta città di cui si vogliono calcolare le statistiche
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 */

	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheGiornaliere(DataMeteo filtraData, String mioNomeCitta)
			throws DataMeteoException, ParseException, IOException, FiltersException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento allo stesso identico giorno e alla stessa citta
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

			if(DATAMeteoCittaletto.confrontaData(filtraData) && datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato è vuoto significa che ci sono stati errori nell'inserimento dei filtri
		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new FiltersException();
		}

		Calcola calcolaFilters = new Calcola ();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}

	/**
	 * Questo metodo consente di calcolare statistiche settimanali filtrate per data (in base alla quale verrà calcolato
	 * l'indice della settimana nel mese) e città.
	 *
	 * @param filtraData data in cui si vogliono calcolare le statistiche (settimanali)
	 * @param mioNomeCitta città di cui si vogliono calcolare le statistiche
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 */

	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheSettimanali (DataMeteo filtraData, String mioNomeCitta)
			throws IOException, ParseException, DataMeteoException, FiltersException {

		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento alla stessa identica settimana del mese (e dell'anno) e alla stessa citta
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

			if(DATAMeteoCittaletto.getSettimana() == filtraData.getSettimana() && DATAMeteoCittaletto.getMese() == filtraData.getMese() && DATAMeteoCittaletto.getAnno() == filtraData.getAnno() && datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato è vuoto significa che ci sono stati errori nell'inserimento dei filtri
		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new FiltersException();
		}

		Calcola calcolaFilters = new Calcola();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}

	/**
	 * Questo metodo consente di calcolare statistiche mensili filtrate per mese dell'anno e città.
	 *
	 * @param filtraData mese in cui si vogliono calcolare le statistiche (formato mm/yyyy)
	 * @param mioNomeCitta città di cui si vogliono calcolare le statistiche
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 */
	
	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheMensili(DataMeteo filtraData, String mioNomeCitta)
			throws IOException, ParseException, DataMeteoException, FiltersException {
		
		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento allo stesso identico mese dell'anno e alla stessa citta
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);
			
			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());
			
			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();
			
			if(DATAMeteoCittaletto.getMese() == filtraData.getMese() && DATAMeteoCittaletto.getAnno() == filtraData.getAnno() && datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato è vuoto significa che ci sono stati errori nell'inserimento dei filtri
		if(ArrayDatiMeteoRisultato.isEmpty()){
			throw new FiltersException();
		}
		
		Calcola calcolaFilters = new Calcola();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}

	/**
	 * Questo metodo consente di calcolare statistiche totali (su tutti i dati meteo presenti nel database) filtrate
	 * per città.
	 *
	 * @param mioNomeCitta città di cui si vogliono calcolare le statistiche
	 * @return JSONObject contenente i valori delle statistiche calcolate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 * @throws FiltersException eccezione che viene lanciata in caso di errori con i filtri
	 */
	
	@SuppressWarnings("unchecked")
	public JSONObject filtraStatisticheTotali (String mioNomeCitta)
			throws IOException, ParseException, DataMeteoException, FiltersException {

		DataBase mioDataBase = new DataBase("Database_Previsioni.json");
		JSONArray jsonArrayDatiMeteoLettura = mioDataBase.getDatabase();
		Vector <MeteoCitta> ArrayDatiMeteoRisultato = new Vector <MeteoCitta> ();

		//costruisco l'ArrayDatiMeteoRisultato i cui elementi fanno tutti riferimento alla stessa citta
		for(int i = 0; i < jsonArrayDatiMeteoLettura.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLettura.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita, nomeCitta, unixData);

			if(datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultato.add(datoMeteoCittaletto);
			}
		}
		//se l'ArrayDatiMeteoRisultato è vuoto significa che ci sono stati errori nell'inserimento dei filtri
		if(ArrayDatiMeteoRisultato.isEmpty()) {
			throw new FiltersException();
		}

		Calcola calcolaFilters = new Calcola ();
		return calcolaFilters.calcolaFilters(ArrayDatiMeteoRisultato, mioNomeCitta);
	}
}
