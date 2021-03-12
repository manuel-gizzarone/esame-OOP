package com.OOPDekGiz.progettoDekGiz.exception;

/**
 *
 * Questa classe segnala l'eccezione generata a causa della richiesta da parte dell'utente di eseguire operazioni su un
 * database inesistente. Per operazioni si intende visualizzazione ed eliminazione dello stesso.
 *
 */

public class DatabaseNotFoundException extends Exception {

    /**
     *
     * Costruttore della classe che fa visualizzare il messaggio di errore.
     *
     * @param nomeDatabase nome del database inesistente
     *
     */

    public DatabaseNotFoundException(String nomeDatabase){

        super("Non ho trovato nessun database '" + nomeDatabase + "'. Inserire il nome di un database esistente!");
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
