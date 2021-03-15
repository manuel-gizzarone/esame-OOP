package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata dall'inserimento di una soglia di errore non valida.
 *
 */

public class SogliaErroreNotValidException extends Exception {

	/**
	 *
	 * Costruttore della classe che fa visualizzare il messaggio di errore.
	 *
	 */

	public SogliaErroreNotValidException() {

		super("La soglia di errore inserita non è valida! Inserire un numero compreso tra 1 e 99.");
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
