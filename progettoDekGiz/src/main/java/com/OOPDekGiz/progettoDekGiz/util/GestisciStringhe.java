package com.OOPDekGiz.progettoDekGiz.util;

import java.util.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Locale;

import com.OOPDekGiz.progettoDekGiz.exception.GestisciStringaException;

/**
 * Questa classe permette di utilizzare dei metodi personalizzati per lavorare su una stringa passata al costruttore.
 */

public class GestisciStringhe {
	
	private String daLavorare;

	/**
	 * Costruttore della classe che assegna un valore alla variabile d'istanza daLavorare, che rappresenta la stringa
	 * da gestire con i relativi metodi messi a disposizione dalla classe stessa.
	 *
	 * @param daLavorare stringa da gestire
	 */

	public GestisciStringhe(String daLavorare) {
		this.daLavorare = daLavorare;
	}
	
	/**
	 * Questo metodo estrae tutte le stringhe dalla variabile d'istanza daLavorare. Ogni singola stringa è separata
	 * dall'altra da una virgola.
	 *
	 * @return vettore contenente le stringhe estratte
	 * @throws GestisciStringaException errori di inserimento della stringa
	 */

	public Vector<String> estraiConVirgola()
			throws GestisciStringaException {

		Vector<String> risultato = new Vector<String> ();
		String[] risultatoArray = this.daLavorare.split(",");
		for (String s : risultatoArray) {
			if(s.charAt(0) == ' ' || s.endsWith(" ")) {
				throw new GestisciStringaException();
			}
			risultato.add(s);
		}

		return risultato;
	}
	
	/**
	 * 	Questo metodo estrae da una stringa contenente una data nel formato dd/MM/yyyy la data come oggetto Date.
	 *
	 * @param dataDaEstrarre stringa contenente la data da convertire in oggetto Date
	 * @return la data estratta come oggetto Date
	 * @throws ParseException errori durante il parsing
	 */

	public static Date StringToData (String dataDaEstrarre)
			throws ParseException {

	    return new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).parse(dataDaEstrarre);
	}

	/**
	 * Metodo get per la variabile d'istanza daLavorare.
	 *
	 * @return stringa da gestire
	 */

	public String getDaLavorare() {
		return this.daLavorare;
	}

	/**
	 * Metodo set per la variabile d'istanza daLavorare.
	 */

	public void setDaLavorare(String daLavorare) {
		this.daLavorare = daLavorare;
	}
}
