package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata a causa dell'inserimento di una stringa vuota.
 *
 */

public class InserimentoException extends Exception {

    /**
     *
     * Costruttore della classe che richiama il costruttore della superclasse Exeption per visualizzare il messaggio di
     * errore.
     *
     */

    public InserimentoException(String campo){

        super("ERRORE! Il campo '" + campo + "' è vuoto. Riprovare!");
    }
}
