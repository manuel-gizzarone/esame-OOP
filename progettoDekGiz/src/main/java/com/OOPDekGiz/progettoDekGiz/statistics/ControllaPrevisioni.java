package com.OOPDekGiz.progettoDekGiz.statistics;

import java.io.IOException;

import java.util.Vector;

import com.OOPDekGiz.progettoDekGiz.exception.InserimentoException;
import com.OOPDekGiz.progettoDekGiz.exception.PeriodNotValidException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.exception.SogliaErroreNotValidException;
import com.OOPDekGiz.progettoDekGiz.model.DataMeteo;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;
import com.OOPDekGiz.progettoDekGiz.util.DataBase;

/**
 * Questa classe contiene il metodo che permette il calcolo delle statistiche sulla la quantità di previsioni
 * azzeccate riguardo una città, in un dato periodo tra una data iniziale e finale e data una soglia di errore. Per
 * calcolare tali statistiche verranno sfruttati i dati meteo presenti su due database: "Database_Previsioni" contenente
 * le previsioni meteo sulla nuvolosità e "Database_Raccolta" contenente una raccolta di dati meteo attuali sulla
 * nuvolosità.
 */

public class ControllaPrevisioni {

	/**
	 * Questo metodo consente il calcolo delle statistiche sulla quantità di previsioni azzeccate riguardo una città
	 * in un dato periodo e data una soglia di errore.
	 *
	 * @param DataInizio data iniziale del periodo
	 * @param DataFine data finale del periodo
	 * @param mioNomeCitta nome della città di cui si vogliono ottenere le statistiche
	 * @param sogliaErrore errore massimo ammissibile tra il dato meteo reale e quello previsto
	 * @return JSONObject contenente le statistiche calcolate
	 * @throws DataMeteoException eccezione che viene lanciata in caso di errori con la data
	 * @throws PeriodNotValidException eccezione che viene lanciata se l'utente inserisce un perido non valido
	 * @throws SogliaErroreNotValidException eccezione che viene lanciata se l'utente inserisce una soglia non valida
	 * @throws ParseException errori durante il parsing
	 * @throws IOException errori di input/output su file
	 */

	@SuppressWarnings("unchecked")
	public JSONObject controllaPrevisioniSoglia(DataMeteo DataInizio, DataMeteo DataFine, String mioNomeCitta, double sogliaErrore)
			throws DataMeteoException, ParseException, IOException, SogliaErroreNotValidException, PeriodNotValidException {
		
		//per soglia di errore si intende la differenza in valore assoluto tra la media di nuvolosita del giorno tra i dati delle previsioni e quelli attuali
		if(sogliaErrore <=0 ||sogliaErrore >= 100) {
			throw new SogliaErroreNotValidException();
		}

		DataBase databaseCorrente = new DataBase("Database_Raccolta.json");
		DataBase databasePrevisioni = new DataBase("Database_Previsioni.json");

		JSONArray jsonArrayDatiMeteoLetturaCorrente = databaseCorrente.getDatabase();
		JSONArray jsonArrayDatiMeteoLetturaPrevisioni = databasePrevisioni.getDatabase();

		Vector <MeteoCitta> ArrayDatiMeteoRisultatoCorrente = new Vector <MeteoCitta> ();
		Vector <MeteoCitta> ArrayDatiMeteoRisultatoPrevisioni = new Vector <MeteoCitta> ();

		//costruisco il l'ArrayDatiMeteoRisultatoCorrente dal database corrente contenente gli oggetti la cui data è compresa tra inizio e fine e che corrispondono alla città parametro
		for(int i = 0; i < jsonArrayDatiMeteoLetturaCorrente.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLetturaCorrente.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

			//l'or è stato inserito per un corretto confronto delle date a cavallo tra 2 mesi - manca il controllo sui giorni tra 2 anni diversi
			if(((DATAMeteoCittaletto.getGiorno() >= DataInizio.getGiorno() && DATAMeteoCittaletto.getGiorno() <= DataFine.getGiorno() &&
				DATAMeteoCittaletto.getMese() >= DataInizio.getMese() && DATAMeteoCittaletto.getMese() <= DataFine.getMese() &&
				DATAMeteoCittaletto.getAnno() >= DataInizio.getAnno() && DATAMeteoCittaletto.getAnno() <= DataFine.getAnno()) ||
					(DATAMeteoCittaletto.getGiorno() < DataInizio.getGiorno() && DATAMeteoCittaletto.getGiorno() <= DataFine.getGiorno() &&
					 DATAMeteoCittaletto.getMese() > DataInizio.getMese() && DATAMeteoCittaletto.getMese() <= DataFine.getMese() &&
					 DATAMeteoCittaletto.getAnno() >= DataInizio.getAnno() && DATAMeteoCittaletto.getAnno() <= DataFine.getAnno())) &&
					datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultatoCorrente.add(datoMeteoCittaletto);
			}
		}

		//costruisci l'ArrayDatiMeteoRisultatoPrevisioni dal database delle previsioni contenente gli oggetti la cui data è compresa tra inizio e fine e che corrispondono alla città parametro
		for(int i = 0; i < jsonArrayDatiMeteoLetturaPrevisioni.size(); i++) {
			JSONObject jsondatoMeteoCittaletto = (JSONObject)jsonArrayDatiMeteoLetturaPrevisioni.get(i);

			int nuvolosita = Integer.parseInt(jsondatoMeteoCittaletto.get("nuvolosita").toString());
			String nomeCitta = jsondatoMeteoCittaletto.get("citta").toString();
			long unixData = Long.parseLong(jsondatoMeteoCittaletto.get("unixData").toString());

			MeteoCitta datoMeteoCittaletto = new MeteoCitta(nuvolosita,nomeCitta,unixData);
			DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

			if((DATAMeteoCittaletto.getGiorno() >= DataInizio.getGiorno() && DATAMeteoCittaletto.getGiorno() <= DataFine.getGiorno() &&
				DATAMeteoCittaletto.getMese() >= DataInizio.getMese() && DATAMeteoCittaletto.getMese() <= DataFine.getMese() &&
				DATAMeteoCittaletto.getAnno() >= DataInizio.getAnno() && DATAMeteoCittaletto.getAnno() <= DataFine.getAnno()) ||
					(DATAMeteoCittaletto.getGiorno() < DataInizio.getGiorno() && DATAMeteoCittaletto.getGiorno() <= DataFine.getGiorno() &&
					 DATAMeteoCittaletto.getMese() > DataInizio.getMese() && DATAMeteoCittaletto.getMese() <= DataFine.getMese() &&
					 DATAMeteoCittaletto.getAnno() >= DataInizio.getAnno() && DATAMeteoCittaletto.getAnno() <= DataFine.getAnno()) &&
					datoMeteoCittaletto.getNomeCitta().equals(mioNomeCitta)) {
				ArrayDatiMeteoRisultatoPrevisioni.add(datoMeteoCittaletto);
			}
		}

		//calcola il numero di giorni di predizione da controllare e verifica se le date inserite sono coerenti

		if(DataFine.getMese() < DataInizio.getMese() || DataFine.getAnno() < DataInizio.getAnno() || (DataFine.getMese() == DataInizio.getMese() && DataFine.getGiorno() < DataInizio.getGiorno())) {
			throw new PeriodNotValidException();
		}

		int giorniPredizione = 0;
		if(DataFine.getMese() == DataInizio.getMese()) {
			giorniPredizione = (int) ((DataFine.getGiorno() - DataInizio.getGiorno()) + 1);
		} else if (DataFine.getMese() - DataInizio.getMese() == 1) {
			giorniPredizione = (int) (DataInizio.calcolaLimiteMese(DataInizio.getMese() - DataInizio.getGiorno() + DataFine.getGiorno()) + 1);
		}

		//calcolo per ogni giorno tra quelli fra la data di inizio e quella di fine la media
		//tra i valori di nuvolosità dei dati correnti e quelli delle previsioni e faccio il confronto
		int contaPrevisioniCorrette = 0;

		for(int k = 0; k < giorniPredizione; k++) {

			//costruisco un oggetto di tipo DataMeteo per gestire i confronti successivi
			//inizialmente è una copia di DataInizio
			DataMeteo dataLavoro = new DataMeteo(DataInizio.getUnixData());
			int sommaCorrente=0;
			int sommaPrevisioni=0;

			int contaCorrente=0;
			int contaPrevisioni=0;

			double mediaCorrente=0;
			double mediaPrevisioni=0;

			for(int i = 0; i < ArrayDatiMeteoRisultatoPrevisioni.size(); i++) {

				MeteoCitta datoMeteoCittaletto = ArrayDatiMeteoRisultatoPrevisioni.get(i);

				DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

				if(DATAMeteoCittaletto.getGiorno() == (dataLavoro.getGiorno() + k) && (dataLavoro.getGiorno() + k) <= dataLavoro.calcolaLimiteMese(dataLavoro.getMese()) && DATAMeteoCittaletto.getMese() == dataLavoro.getMese()) {
					contaPrevisioni++;
					sommaPrevisioni += datoMeteoCittaletto.getNuvolosita();
				} else if((dataLavoro.getGiorno() + k) > dataLavoro.calcolaLimiteMese(dataLavoro.getMese())) {   //corregge il giorno incrementato se rappresenta un numero che supera il numero massimo per quel mese
					dataLavoro.setGiorno((dataLavoro.getGiorno() + k) - (dataLavoro.calcolaLimiteMese(dataLavoro.getMese())));
					dataLavoro.setMese(dataLavoro.getMese() + 1);

					if(DATAMeteoCittaletto.getGiorno() == (dataLavoro.getGiorno() + k) && (dataLavoro.getGiorno() + k) <= dataLavoro.calcolaLimiteMese(dataLavoro.getMese()) && DATAMeteoCittaletto.getMese() == dataLavoro.getMese()) {
						contaPrevisioni++;
						sommaPrevisioni += datoMeteoCittaletto.getNuvolosita();
					}
				}
			 }

			 for(int i = 0; i < ArrayDatiMeteoRisultatoCorrente.size(); i++) {

				MeteoCitta datoMeteoCittaletto = ArrayDatiMeteoRisultatoCorrente.get(i);

				DataMeteo DATAMeteoCittaletto = datoMeteoCittaletto.getDataMeteo();

				if(DATAMeteoCittaletto.getGiorno() == (dataLavoro.getGiorno() + k) && (dataLavoro.getGiorno() + k) <= dataLavoro.calcolaLimiteMese(dataLavoro.getMese()) && DATAMeteoCittaletto.getMese() == dataLavoro.getMese()) {
					contaCorrente++;
					sommaCorrente += datoMeteoCittaletto.getNuvolosita();
				} else if((dataLavoro.getGiorno() + k) > dataLavoro.calcolaLimiteMese(dataLavoro.getMese())) {   //corregge il giorno incrementato se rappresenta un numero che supera il numero massimo per quel mese
					dataLavoro.setGiorno((dataLavoro.getGiorno() + k) - (dataLavoro.calcolaLimiteMese(dataLavoro.getMese())));
					dataLavoro.setMese(dataLavoro.getMese() + 1);

					if(DATAMeteoCittaletto.getGiorno() == (dataLavoro.getGiorno() + k) && (dataLavoro.getGiorno() + k) <= dataLavoro.calcolaLimiteMese(dataLavoro.getMese()) && DATAMeteoCittaletto.getMese() == dataLavoro.getMese()) {
						contaCorrente++;
						sommaCorrente += datoMeteoCittaletto.getNuvolosita();
					}
				}
			 }

			 mediaCorrente=((double)sommaCorrente)/contaCorrente;
			 mediaPrevisioni=((double)sommaPrevisioni)/contaPrevisioni;

			if(Math.abs(mediaPrevisioni - mediaCorrente) <= sogliaErrore) {
				contaPrevisioniCorrette++;
			}
		}

		JSONObject jsonRisultato = new JSONObject();
		jsonRisultato.put("citta", mioNomeCitta);
		jsonRisultato.put("giorni_di_previsione_corretti", contaPrevisioniCorrette);
		jsonRisultato.put("giorni_di_previsione_totali", giorniPredizione);
		jsonRisultato.put("data_inizio", DataInizio.toString());
		jsonRisultato.put("data_fine", DataFine.toString());
		jsonRisultato.put("soglia_errore", sogliaErrore);

		return jsonRisultato;
	}
}
