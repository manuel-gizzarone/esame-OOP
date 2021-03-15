package com.OOPDekGiz.progettoDekGiz.testService;

import static org.junit.jupiter.api.Assertions.*;

import com.OOPDekGiz.progettoDekGiz.exception.*;
import com.OOPDekGiz.progettoDekGiz.service.ServiceNuvole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 *
 * Questa classe ha lo scopo di effettuare dei test automatici su alcuni metodi della classe ServiceNuvole.
 *
 */

class TestServiceNuvole {

    private ServiceNuvole serviceNuvole;

    /**
     *
     * Inizializza un oggetto di tipo ServiceNuvole che servirà per effetture i test dei metodi.
     *
     */

    @BeforeEach
    void setUp() {
        this.serviceNuvole = new ServiceNuvole();
    }

    /**
     *
     * Serve per distruggere tutto quello che è stato inizializzato dal metodo setUp.
     *
     */

    @AfterEach
    void tearDown() {

    }

    /**
     *
     * Test corretto lancio dell'eccezione InserimentoException.
     *
     */

    @Test
    @DisplayName("Lancio corretto dell'eccezione InserimentoException.")
    void testInserimentoException() {
        String campoVuoto = "nomeCitta";
        InserimentoException e = assertThrows(InserimentoException.class, ()->this.serviceNuvole.salvaOgniOra(""));
        assertEquals("ERRORE! Il campo '" + campoVuoto + "' è vuoto. Riprovare.", e.getMessage());
    }

    /**
     *
     * Test corretto lancio dell'eccezione NomeCittaException.
     *
     */

    @Test
    @DisplayName("Lancio corretto dell'eccezione NomeCittaException.")
    void testNomeCittaException() {
        String nomeCittaErrato = "Pippo";
        NomeCittaException e = assertThrows(NomeCittaException.class, ()->this.serviceNuvole.salvaOgniOra(nomeCittaErrato));
        assertEquals("Non ho trovato nessuna città di nome '" + nomeCittaErrato + "'. Inserire una città valida!", e.getMessage());
    }

    /**
     *
     * Test corretto lancio dell'eccezione DatabaseNotFoundException.
     *
     */

    @Test
    @DisplayName("Lancio corretto dell'eccezione DatabaseNotFoundException.")
    void testDatabaseNotFoundException() {
        String nomeDatabaseInesistente = "Pluto";
        DatabaseNotFoundException e = assertThrows(DatabaseNotFoundException.class, ()->this.serviceNuvole.getDatabase(nomeDatabaseInesistente));
        assertEquals("Non ho trovato nessun database '" + nomeDatabaseInesistente + "'. Inserire il nome di un database esistente!", e.getMessage());
    }

    /**
     *
     * Test di verifica per il Path del file contenente i database creati dal metodo salvaOgniOra.
     *
     * @throws NomeCittaException
     * @throws InserimentoException
     * @throws DataMeteoException
     * @throws ConfigFileException
     * @throws org.json.simple.parser.ParseException
     * @throws IOException
     *
     */

    @Test
    @DisplayName("Path salvataggio file corretto.")
    void testCorrectPathFile()
            throws NomeCittaException, InserimentoException, DataMeteoException, ConfigFileException, org.json.simple.parser.ParseException, IOException {
        String nomeCitta = "Campobasso";
        String path ="Path database:  " + System.getProperty("user.dir") + "/" + nomeCitta + ".json";
        assertEquals(path, this.serviceNuvole.salvaOgniOra(nomeCitta));
    }
}
