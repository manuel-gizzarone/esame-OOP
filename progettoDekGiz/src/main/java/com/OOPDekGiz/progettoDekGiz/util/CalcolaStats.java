package com.OOPDekGiz.progettoDekGiz.util;

import java.io.IOException;

import java.util.Vector;

import org.json.simple.JSONObject;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.DataMeteo;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;

public class CalcolaStats {
	
	JSONObject calcola (Vector<MeteoCitta> ArrayDatiMeteoRisultato,DataMeteo filtraData) {
		//statistiche disponibili sulla nuvolosità
		long min=1000;
		long max=0;
		double media=0;
		double varianza=0;
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
		JSONObject jsonStatsRisultato = new JSONObject();
		jsonStatsRisultato.put("max_nuvolosita",max);
		jsonStatsRisultato.put("min_nuvolosita",min);
		jsonStatsRisultato.put("media_nuvolosita",media);
		jsonStatsRisultato.put("varianza_nuvolosita",varianza);
		jsonStatsRisultato.put("data",filtraData.toString());
		
		return jsonStatsRisultato;
	}
		
		
}

