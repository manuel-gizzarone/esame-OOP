package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata dall'inserimento di un nome di una città non valido o non presente
 * nei database di OpenWeather
 *
 */

public class NomeCittaException extends Exception {

    /**
     *
     * Costruttore della classe che richiama il costruttore della superclasse Exception per visualizzare il messaggio
     * di errore.
     *
     * @param nomeCittaErrato nome della città che ha causato il lancio dell'eccezione
     *
     */

    public NomeCittaException(String nomeCittaErrato){

        super("Non ho trovato nessuna città di nome '" + nomeCittaErrato + "'. Inserire una città valida!");
    }
}
