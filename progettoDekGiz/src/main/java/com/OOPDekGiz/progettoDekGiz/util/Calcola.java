package com.OOPDekGiz.progettoDekGiz.util;

import java.util.Vector;

import org.json.simple.JSONObject;

import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;

/**
 * Questa classe contiene i metodi utili al calcolo delle statistiche per le classi StatsNuvole e FiltersNuvole.
 * Per statistiche si intendono valori massimi e minimi di nuvolosità, media e varianza.
 */

public class Calcola {

	private long min;

	private long max;

	private double media;

	private double varianza;

	/**
	 * Costruttore della classe che assegna dei valori di default alle variabili d'istanza che rappresentano le
	 * statistiche disponibili sulla nuvolosità.
	 */

	public Calcola() {
		this.min = 1000;
		this.max = 0;
		this.media = 0;
		this.varianza = 0;
	}

	/**
	 * Questo metodo verrà richiamato dalla classe StatsNuvole per il calcolo delle statistiche.
	 *
	 * @param ArrayDatiMeteoRisultato vettore contenente i dati meteo su cui calcolare le statistiche
	 * @return JSONObject contenente le statistiche calcolate
	 */
	
	public JSONObject calcolaStats(Vector<MeteoCitta> ArrayDatiMeteoRisultato) {
		int somma = 0;
		int conta = 0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato) {
			if(analizza.getNuvolosita() >= this.max) {
				this.max = analizza.getNuvolosita();
			}
			if(analizza.getNuvolosita() <= this.min) {
				this.min = analizza.getNuvolosita();
			}
			somma += analizza.getNuvolosita();
			conta ++;
		}

		this.media = ((double)somma) / conta;

		double sommaScartiQuadrati = 0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato) {
			sommaScartiQuadrati += Math.pow((analizza.getNuvolosita() - this.media), 2);
		}
		
		this.varianza = sommaScartiQuadrati / (conta - 1);
		
		//costruisco il jsonObject che conterrà i valori delle statistiche calcolate
		JSONObject jsonStatsRisultato = new JSONObject();
		jsonStatsRisultato.put("max_nuvolosita", this.max);
		jsonStatsRisultato.put("min_nuvolosita", this.min);
		jsonStatsRisultato.put("media_nuvolosita", this.media);
		jsonStatsRisultato.put("varianza_nuvolosita", this.varianza);
		
		return jsonStatsRisultato;
	}

	/**
	 * Questo metodo verrà richiamato dalla classe FiltersNuvole per il calcolo delle statistiche. Nel risultato
	 * verrà inserito anche il nome della città a cui tali statistiche fanno riferimento.
	 *
	 * @param ArrayDatiMeteoRisultato vettore contenente i dati meteo su cui calcolare le statistiche
	 * @param mioNomeCitta nome della città a cui tali statistiche si riferiscono
	 * @return JSONObject contenente i valori delle statistiche calcolate e la città relativa a cui esse si riferiscono
	 */
		
    public JSONObject calcolaFilters(Vector<MeteoCitta> ArrayDatiMeteoRisultato, String mioNomeCitta) {
		int somma=0;
		int conta=0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato) {
			if(analizza.getNuvolosita() >= this.max) {
				this.max = analizza.getNuvolosita();
			}
			if(analizza.getNuvolosita() <= this.min) {
				this.min = analizza.getNuvolosita();
			}
			somma += analizza.getNuvolosita();
			conta ++;
		}

		this.media=((double)somma) / conta;

		double sommaScartiQuadrati = 0;
		for(MeteoCitta analizza : ArrayDatiMeteoRisultato ) {
			sommaScartiQuadrati += Math.pow((analizza.getNuvolosita() - this.media), 2);
		}
		
		this.varianza = sommaScartiQuadrati / (conta - 1);
		
		//costruisco il jsonObject che conterrà i valori delle statistiche calcolate e la relativa città a cui esse fanno riferimento
		JSONObject jsonStatsRisultato = new JSONObject();
		jsonStatsRisultato.put("max_nuvolosita", this.max);
		jsonStatsRisultato.put("min_nuvolosita", this.min);
		jsonStatsRisultato.put("media_nuvolosita", this.media);
		jsonStatsRisultato.put("varianza_nuvolosita", this.varianza);
		jsonStatsRisultato.put("citta", mioNomeCitta);
		
		return jsonStatsRisultato;
	}
}

