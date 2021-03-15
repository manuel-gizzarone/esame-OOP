
package com.OOPDekGiz.progettoDekGiz.util;

import java.util.Vector;

import org.json.simple.JSONObject;

import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;

/**
 * la classe contiene i metodi utii al calcolo dei valori di varianza media max e min per le classi StatsNuvole e FiltersNuvole
 * 
 */
public class Calcola {
	
	public JSONObject calcolaStats (Vector<MeteoCitta> ArrayDatiMeteoRisultato) {
		
		//statistiche disponibili sulla nuvolosità
		long min=1000;
		long max=0;
		double media=0;
		double varianza=0;
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
		
		//costruisco il jsonObject i cui campi sono media varianza max min di nuvolosità tra i dati su database alla data inserita(giorno mese e anno) (filtraData)
		JSONObject jsonStatsRisultato = new JSONObject();
		jsonStatsRisultato.put("max_nuvolosita",max);
		jsonStatsRisultato.put("min_nuvolosita",min);
		jsonStatsRisultato.put("media_nuvolosita",media);
		jsonStatsRisultato.put("varianza_nuvolosita",varianza);
		
		return jsonStatsRisultato;
	}
		
    public JSONObject calcolaFilters (Vector<MeteoCitta> ArrayDatiMeteoRisultato,String mioNomeCitta) {
		
		//statistiche disponibili sulla nuvolosità
		long min=1000;
		long max=0;
		double media=0;
		double varianza=0;
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
		
		//costruisco il jsonObject i cui campi sono media varianza max min di nuvolosità ed il nome della città
		JSONObject jsonStatsRisultato = new JSONObject();
		jsonStatsRisultato.put("max_nuvolosita",max);
		jsonStatsRisultato.put("min_nuvolosita",min);
		jsonStatsRisultato.put("media_nuvolosita",media);
		jsonStatsRisultato.put("varianza_nuvolosita",varianza);
		jsonStatsRisultato.put("citta",mioNomeCitta);
		
		return jsonStatsRisultato;
	}
    
    
		
}

