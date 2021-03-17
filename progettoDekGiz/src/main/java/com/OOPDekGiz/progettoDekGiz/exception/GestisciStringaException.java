package com.OOPDekGiz.progettoDekGiz.exception;

/**
 * Questa classe segnala l'eccezione generata dall'inserimento di una stringa (contenente i nomi delle città) in un
 * formato non valido.
 */

public class GestisciStringaException extends Exception {

	/**
	 * Costruttore della classe che fa visualizzare il messaggio di errore.
	 */

	public GestisciStringaException() {

		super("Errore formato stringa! Inserire le città senza lasciare spazi tra le virgole.");
	}

	/**
	 * Metodo get per ottenere il messaggio di errore.
	 *
	 * @return messaggio di errore
	 */

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
