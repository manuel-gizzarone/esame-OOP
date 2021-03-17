package com.OOPDekGiz.progettoDekGiz.testUtil;

import static org.junit.jupiter.api.Assertions.*;

import com.OOPDekGiz.progettoDekGiz.exception.GestisciStringaException;
import com.OOPDekGiz.progettoDekGiz.util.GestisciStringhe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Vector;

/**
 * Questa classe ha lo scopo di effettuare dei test automatici su alcuni metodi della classe GestisciStringhe.
 */

public class TestGestisciStrinche {

    private GestisciStringhe gestisciStringheGiusta;
    private GestisciStringhe gestisciStringheErrata;
    private Vector<String> temp;

    /**
     * Inizializza l'occorrente necessario per effettuare i test automatici.
     */

    @BeforeEach
    void setUp() {
        this.gestisciStringheGiusta = new GestisciStringhe("Pippo,Pluto,Paperino");
        this.gestisciStringheErrata = new GestisciStringhe(" Pippo,   Pluto,Paperino  ");
        this.temp = new Vector<>();
        temp.add("Pippo");
        temp.add("Pluto");
        temp.add("Paperino");
    }

    /**
     * Serve per distruggere tutto quello che è stato inizializzato dal metodo setUp.
     */

    @AfterEach
    void tearDown() {

    }

    /**
     * Test per verificare la corretta estrazione delle parole da una stringa.
     *
     * @throws GestisciStringaException eccezione lanciata in caso di errori nell'inserimento della stringa
     */

    @Test
    @DisplayName("Corretta estrazione delle città dalla stringa.")
    void testEstraiConVirgola()
            throws GestisciStringaException {
        assertEquals(this.temp, this.gestisciStringheGiusta.estraiConVirgola());
    }

    /**
     * Test corretto lancio dell'eccezione GestisciStringheException.
     */

    @Test
    @DisplayName("Lancio corretto dell'eccezione GestisciStringaException.")
    void testGestisciStringaException(){
        GestisciStringaException e = assertThrows(GestisciStringaException.class, ()->this.gestisciStringheErrata.estraiConVirgola());
        assertEquals("Errore formato stringa! Inserire le città senza lasciare spazi tra le virgole.", e.getMessage());
    }
}
