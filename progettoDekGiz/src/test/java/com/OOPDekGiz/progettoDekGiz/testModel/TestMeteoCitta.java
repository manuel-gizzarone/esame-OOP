package com.OOPDekGiz.progettoDekGiz.testModel;

import static org.junit.jupiter.api.Assertions.*;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Questa classe ha lo scopo di effettuare un test automatico sul metodo per il casting in JSONObject.
 */

class TestMeteoCitta {

    private MeteoCitta meteoCitta;

    /**
     * Inizializza l'occorrente necessario per effettuare il test.
     */

    @BeforeEach
    void setUp()
            throws DataMeteoException {
        int nuvolosita = 50;
        String nomeCitta = "Ferrazzano";
        long unixData = 1616011236;
        this.meteoCitta = new MeteoCitta(nuvolosita, nomeCitta, unixData);
    }

    /**
     * Serve per distruggere tutto quello che è stato inizializzato dal metodo setUp.
     */

    @AfterEach
    void tearDown() {

    }

    /**
     * Test casting corretto in JSONObject.
     */

    @Test
    @DisplayName("Controllo corretto cast in JSONObject.")
    void testCastToJsonObject() {
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("citta", "Ferrazzano");
        jsonObject.put("nuvolosita", 50);
        jsonObject.put("unixData", 1616011236);
        jsonObject.put("Data", "Wed Mar 17 21:00:36 CET 2021");

        assertEquals(jsonObject.toString(), this.meteoCitta.castToJsonObject().toString());
    }
}
