package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata dall'inserimento di un nome di una città non valido o non presente
 * nei database di OpenWeather.
 *
 */

public class NomeCittaException extends Exception {

    /**
     *
     * Costruttore della classe che fa visualizzare il messaggio di errore.
     *
     * @param nomeCittaErrato nome della città che ha causato il lancio dell'eccezione
     *
     */

    public NomeCittaException(String nomeCittaErrato){

        super("Non ho trovato nessuna città di nome '" + nomeCittaErrato + "'. Inserire una città valida!");
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
