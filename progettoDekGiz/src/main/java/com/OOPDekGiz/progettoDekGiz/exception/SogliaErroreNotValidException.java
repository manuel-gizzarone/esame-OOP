package com.OOPDekGiz.progettoDekGiz.exception;

public class SogliaErroreNotValidException extends Exception {
	
	/**
	 * Costruttore per GestisciStringaException con messaggio di errore
	 */
	public SogliaErroreNotValidException() {
		super("la soglia di errore inserita non è valida");
	}

}
