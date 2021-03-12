package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata dall'inserimento di una data in un formato non valido.
 *
 */

public class DataMeteoException extends Exception {

	/**
	 *
	 * Costruttore della classe che fa visualizzare il messaggio di errore relativo.
	 *
	 */

	public DataMeteoException() {

		super("Errore! La data inserita non è presente nei dati contenuti dal database.");
	}

	/**
	 *
	 * Metodo getter per ottenere il messaggio di errore.
	 *
	 * @return messaggio di errore
	 *
	 */

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
