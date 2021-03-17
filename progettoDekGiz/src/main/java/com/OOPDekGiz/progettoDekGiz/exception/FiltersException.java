package com.OOPDekGiz.progettoDekGiz.exception;

/**
 * Questa classe segnala l'eccezione generata a causa dell'inserimento di filtri non validi poichè non presenti
 * all'interno del "Database_Previsioni". Per filtri si intendono i nomi delle città e le date.
 */

public class FiltersException extends Exception {

    /**
     * Costruttore della classe che fa visualizzare il messaggio di errore.
     */

    public FiltersException() {

        super("ERRORE! Controllare che i valori dei filtri inseriti siano presenti nel database 'Database_Previsioni'.");
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
