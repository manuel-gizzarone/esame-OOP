package com.OOPDekGiz.progettoDekGiz.exception;

/**
 * Questa classe segnala l'eccezione generata a causa del mancato inserimento del valore di un campo richiesto.
 */

public class InserimentoException extends Exception {

    /**
     * Costruttore della classe che fa visualizzare il messaggio di errore.
     *
     * @param campo campo il cui valore non è stato inserito
     */

    public InserimentoException(String campo){

        super("ERRORE! Il campo '" + campo + "' è vuoto. Riprovare.");
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
