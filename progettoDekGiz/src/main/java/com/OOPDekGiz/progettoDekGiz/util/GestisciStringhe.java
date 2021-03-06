package com.OOPDekGiz.progettoDekGiz.util;

import java.util.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;

import com.OOPDekGiz.progettoDekGiz.exception.GestisciStringaException;


//questa classe permette di usare dei metodi personalizzati per lavorare su una stringa passata al costruttore
public class GestisciStringhe {
	
	protected String daLavorare;

	/**
	 * costruttore
	 * salva su daLavorare la stringa da gestire con i metodi a disposizione della classe 
	 * @param daLavorare
	 */
	public GestisciStringhe(String daLavorare) {
		this.daLavorare = daLavorare;
	}
	
	/**
	 * estrae le stringhe separate da virgola dalla stringa daLavorare
	 * e salva le stringhe estratte in un Vector<String>
	 * @return il vector di stringhe estratte
	 */
	public Vector<String> estraiConVirgola () throws GestisciStringaException {
		
		Vector<String> risultato = new Vector<String> ();
		
		String risultatoArray []= daLavorare.split(",");
		for (String s : risultatoArray) {
			risultato.add(s);
		}
		
		if (risultato.isEmpty()) {
			throw new GestisciStringaException ();
		}
		else
		return risultato;
		
	}
	
	/**
	 * il metodo estrae da una stringa del tipo dd/MM/yyyy la data come oggetto Date
	 * @param dataDaEstrarre
	 * @return la data estratta come oggetto Date
	 * @throws ParseException
	 */
	static public Date StringToData (String dataDaEstrarre) throws ParseException {
 
	    Date dataEstratta=new SimpleDateFormat("dd/MM/yyyy").parse(dataDaEstrarre); 
	    return dataEstratta;
	  
	}
	
	
	//metodi set e get
	public String getDaLavorare() {
		return daLavorare;
	}

	public void setDaLavorare(String daLavorare) {
		this.daLavorare = daLavorare;
	}

}
