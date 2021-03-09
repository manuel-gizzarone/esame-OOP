package com.OOPDekGiz.progettoDekGiz.statistics;

import java.io.*;

import java.util.Vector;
import java.lang.Math;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.model.*;
import com.OOPDekGiz.progettoDekGiz.util.DataBase;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;

public class StatsNuvole {
	
	//NOTA:i metodi delle statistiche presentano parti di codice analogo ma ognuno esegue alcune operazioni caratteristiche solo a se stesso
	//perciò abbiamo preferito di lasciare il completo codice per ognuno senza frammentarlo con chiamate a piccoli metodi comuni al fine di migliorarne la leggibilità
	
	@SuppressWarnings("unchecked")
	public JSONObject statisticheGiornaliere (DataMeteo filtraData) throws IOException, ParseException, DataMeteoException {
		
		//statistiche disponibili sulla nuvolosità
		long min=0;
		long max=0;
		double media=0;
		double varianza=0;
		
		/*
		long giorno = filtraData.getGiorno();
		int settimana=filtraData.getSettimana();
		long mese = filtraData.getMese();
		*/
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
		
		//calcola somma e valori max e min dei dati di nuvolosità raccolti con giorno (e mese e anno) coincidente
		int somma=0;
		double sommaScartiQuadrati=0;
		
		int conta=0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
			if(analizza.getNuvolosita()>=max)
				max=analizza.getNuvolosita();
			if(analizza.getNuvolosita()<=min)
				min=analizza.getNuvolosita();
			somma+=analizza.getNuvolosita();
			conta++;         //per lo scopo di conta sarebbe stato equivalente usare ArrayDatiMeteoRisultato.size() però così mi sembrava più pulito
		}
		media=((double)somma)/conta;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
		sommaScartiQuadrati+=Math.pow((analizza.getNuvolosita()-media),2);
		}
		
		varianza = ((double)sommaScartiQuadrati)/(conta-1);
		
		//costruisco il jsonObject i cui campi sono media varianza max min di nuvolosità tra i dati su database alla data inserita(giorno mese e anno) (filtraData)
		JSONObject jsonStatsGiornaliere = new JSONObject();
		jsonStatsGiornaliere.put("max_nuvolosita",max);
		jsonStatsGiornaliere.put("min_nuvolosita",min);
		jsonStatsGiornaliere.put("media_nuvolosita",media);
		jsonStatsGiornaliere.put("varianza_nuvolosita",varianza);
		jsonStatsGiornaliere.put("data",filtraData.toString());
		
		return jsonStatsGiornaliere;
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public JSONObject statisticheMensili (DataMeteo filtraData) throws IOException, ParseException, DataMeteoException {
		
		//statistiche disponibili sulla nuvolosità
		long min=0;
		long max=0;
		double media=0;
		double varianza=0;
		
		/*
		long giorno = filtraData.getGiorno();
		int settimana=filtraData.getSettimana();
		long mese = filtraData.getMese();
		*/
		
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
		
		//calcola somma e valori max e min dei dati di nuvolosità raccolti con mese (e anno) coincidente
		int somma=0;
		double sommaScartiQuadrati=0;
		
		int conta=0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
			if(analizza.getNuvolosita()>=max)
				max=analizza.getNuvolosita();
			if(analizza.getNuvolosita()<=min)
				min=analizza.getNuvolosita();
			somma+=analizza.getNuvolosita();
			conta++;
		}
		media=((double)somma)/conta;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
		sommaScartiQuadrati+=Math.pow((analizza.getNuvolosita()-media),2);
		}
		
		varianza = ((double)sommaScartiQuadrati)/(conta-1);
		
		//costruisco il jsonObject i cui campi sono media varianza max min di nuvolosità tra i dati su database al mese (nello stesso anno) della data inserita (filtraData)
		JSONObject jsonStatsMensili = new JSONObject();
		jsonStatsMensili.put("max_nuvolosita",max);
		jsonStatsMensili.put("min_nuvolosita",min);
		jsonStatsMensili.put("media_nuvolosita",media);
		jsonStatsMensili.put("varianza_nuvolosita",varianza);
		
		return jsonStatsMensili;
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public JSONObject statisticheSettimanali (DataMeteo filtraData) throws IOException, ParseException, DataMeteoException {
		
		//statistiche disponibili sulla nuvolosità
		long min=0;
		long max=0;
		double media=0;
		double varianza=0;
		
		/*
		long giorno = filtraData.getGiorno();
		int settimana=filtraData.getSettimana();
		long mese = filtraData.getMese();
		*/
		
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
		
		//calcola somma e valori max e min dei dati di nuvolosità raccolti con settimana , mese e anno coincidenti
		int somma=0;
		double sommaScartiQuadrati=0;
		
		int conta=0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
			if(analizza.getNuvolosita()>=max)
				max=analizza.getNuvolosita();
			if(analizza.getNuvolosita()<=min)
				min=analizza.getNuvolosita();
			somma+=analizza.getNuvolosita();
			conta++;
		}
		media=((double)somma)/conta;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
		sommaScartiQuadrati+=Math.pow((analizza.getNuvolosita()-media),2);
		}
		
		varianza = ((double)sommaScartiQuadrati)/(conta-1);
		
		//costruisco il jsonObject i cui campi sono media varianza max min di nuvolosità tra i dati su database alla settimana (e mese e anno) della data inserita (filtraData)
		JSONObject jsonStatsSettimanali = new JSONObject();
		jsonStatsSettimanali.put("max_nuvolosita",max);
		jsonStatsSettimanali.put("min_nuvolosita",min);
		jsonStatsSettimanali.put("media_nuvolosita",media);
		jsonStatsSettimanali.put("varianza_nuvolosita",varianza);
		
		return jsonStatsSettimanali;
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject statisticheTotali () throws IOException, ParseException, DataMeteoException {
		
		//statistiche disponibili sulla nuvolosità
		long min=0;
		long max=0;
		double media=0;
		double varianza=0;
		
		/*
		long giorno = filtraData.getGiorno();
		int settimana=filtraData.getSettimana();
		long mese = filtraData.getMese();
		*/
		
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
		
		//calcola somma e valori max e min dei dati di nuvolosità su quelli di tutto il database
		int somma=0;
		double sommaScartiQuadrati=0;
		
		int conta=0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
			if(analizza.getNuvolosita()>=max)
				max=analizza.getNuvolosita();
			if(analizza.getNuvolosita()<=min)
				min=analizza.getNuvolosita();
			somma+=analizza.getNuvolosita();
			conta++;
		}
		media=((double)somma)/conta;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
		sommaScartiQuadrati+=Math.pow((analizza.getNuvolosita()-media),2);
		}
		
		varianza = ((double)sommaScartiQuadrati)/(conta-1);
		
		//costruisco il jsonObject i cui campi sono media varianza max min di nuvolosità tra i dati su database 
		JSONObject jsonStatsTotali = new JSONObject();
		jsonStatsTotali.put("max_nuvolosita",max);
		jsonStatsTotali.put("min_nuvolosita",min);
		jsonStatsTotali.put("media_nuvolosita",media);
		jsonStatsTotali.put("varianza_nuvolosita",varianza);
		
		return jsonStatsTotali;
		
	}

	

}
