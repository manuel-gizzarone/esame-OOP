package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata dall'inserimento di una data non presente all'interno del database
 * relativo.
 *
 */

public class DataMeteoException extends Exception {

	/**
	 *
	 * Costruttore della classe che fa visualizzare il messaggio di errore.
	 *
	 */

	public DataMeteoException() {

		super("ERRORE! La data inserita non è presente nei dati contenuti dal database.");
	}

	/**
	 *
	 * Metodo get per ottenere il messaggio di errore.
	 *
	 * @return messaggio di errore
	 *
	 */

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
