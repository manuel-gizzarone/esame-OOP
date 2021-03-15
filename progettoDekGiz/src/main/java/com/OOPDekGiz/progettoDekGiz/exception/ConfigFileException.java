package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata a causa della presenza di errori nel file di configurazione.
 * In particolare verrà lanciata se il file non rispetta il formato JSON.
 *
 */

public class ConfigFileException extends Exception{

    /**
     *
     * Costruttore della classe che fa visualizzare il messaggio di errore.
     *
     */

    public ConfigFileException(){

        super("Errore formato file di configrazione! Controllare che il file rispetti il formato JSON.");
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
