package com.OOPDekGiz.progettoDekGiz.util;

import com.OOPDekGiz.progettoDekGiz.exception.ConfigFileException;
import com.OOPDekGiz.progettoDekGiz.exception.GestisciStringaException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 *
 * Interfaccia che verrà implementata dalle classi che richiedono l'acquisizione dell'apiKey per effettuare le chiamate
 * alle API relative.
 *
 */

public interface ConfigInterface {

    /**
     *
     * Metodo per estrarre l'apiKey dal file di configurazione.
     *
     * @return stringa contenente l'apiKey
     * @throws ParseException errori durente il parsing
     * @throws IOException errori durante input/output su file
     * @throws GestisciStringaException errori di inserimento di una stringa
     * @throws ConfigFileException errori presenti nel file di configurazione (se non rispetta il formato JSON)
     *
     */

    String estraiApiKey() throws ParseException, IOException, GestisciStringaException, ConfigFileException;
}
