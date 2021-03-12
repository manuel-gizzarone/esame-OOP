package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnale l'eccezione generata dall'inserimento di un periodo errato.
 *
 */

public class PeriodNotValidException extends Exception {

    /**
     *
     * Costruttore della classe che fa visualizzare il messaggio di errore.
     *
     */

    public PeriodNotValidException() {

        super("Errore con il periodo! Controllare che la data finale sia successiva a quella iniziale.");
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
