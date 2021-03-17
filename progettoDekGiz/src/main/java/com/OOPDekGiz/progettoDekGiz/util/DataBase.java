package com.OOPDekGiz.progettoDekGiz.util;

import com.OOPDekGiz.progettoDekGiz.exception.ConfigFileException;
import com.OOPDekGiz.progettoDekGiz.model.MeteoCitta;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Questa classe si occupa di gestire il salvataggio su un Database dei dati provenienti dalle chiamate alle
 * API di OpenWeather. Iplementa l'interfaccia ConfigInterface per l'acquisizione dell'apiKey.
 */

public class DataBase implements ConfigInterface {

    private String nomeDatabase;

    private JSONArray jsonArray;

    private final File file;

    FileWriter fileWriter;

    /**
     * Costruttore della classe che assegna alla variabile d'istanza nomeDatabase il nome dell'oggetto DataBase
     * appena creato. Se il database è già esistente leggerà i dati contenuti e li salverà su un JSONArray
     * momentaneo. Se invece è insesistente crea il nuovo database, inizializzando al suo interno un JSONArray vuoto.
     *
     * @param nomeDatabase nome del database
     * @throws IOException errori di input/output su file
     * @throws ParseException errori durante il parsing
     */

    public DataBase(String nomeDatabase)
            throws IOException, ParseException {

        this.nomeDatabase = nomeDatabase;
        this.file = new File(System.getProperty("user.dir") + "/" + this.nomeDatabase);
        if (!this.file.exists()) {
            file.createNewFile();
            this.jsonArray = new JSONArray();
            FileWriter fileWriter = new FileWriter(this.file);
            fileWriter.write(this.jsonArray.toJSONString());
            fileWriter.flush(); //pulisce lo stream di output
            fileWriter.close();
        } else {
            this.jsonArray = this.getDatabase();
        }
    }

    /**
     * Questo metodo salva sul database relativo i dati meteo forniti dalle chiamate Api Current Weather.
     *
     * @param meteoCitta oggetto di tipo MeteoCitta contenente i dati meteo istantanei relativi ad una città
     * @throws IOException errori di input/output su file
     * @throws ClassCastException   errore lanciato per indicare che il codice ha tentato di eseguire il cast di un
     *         oggetto in una sottoclasse di cui non è un'istanza
     * @throws NullPointerException errore lanciato quando si tenta di utilizzare null in un caso in cui è richiesto
     *         un oggetto
     */

    public void salvaSulDatabase(MeteoCitta meteoCitta)
            throws IOException, ClassCastException, NullPointerException {

        JSONObject nuovoDatoMeteo = meteoCitta.castToJsonObject();
        this.jsonArray.add(nuovoDatoMeteo);
        fileWriter = new FileWriter(this.file);
        fileWriter.write(this.jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Overloading del metodo SalvaSulDatabase che salva sul database i dati forniti dalle chiamate Api 5Day/3Hour
     * Forecast.
     *
     * @param datiMeteo vettore contenente oggetti di tipo MeteoCitta
     * @throws IOException errori di input/output su file
     * @throws ClassCastException   errore lanciato per indicare che il codice ha tentato di eseguire il cast di un
     *         oggetto in una sottoclasse di cui non è un'istanza
     * @throws NullPointerException errore lanciato quando si tenta di utilizzare null in un caso in cui è richiesto
     *         un oggetto
     */

    public void salvaSulDatabase(Vector<MeteoCitta> datiMeteo)
            throws IOException, ClassCastException, NullPointerException {

        fileWriter = new FileWriter(this.file);
        for (MeteoCitta meteoCitta : datiMeteo) {
            JSONObject nuovoDatoMeteo = meteoCitta.castToJsonObject();
            this.jsonArray.add(nuovoDatoMeteo);
        }
        fileWriter.write(this.jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Questo metodo salva ogni ora sul database relativo i dati meteo forniti dalle chiamate Api Current Weather.
     *
     * @param nomeCitta nome della città a cui fanno riferimento i dati meteo
     */

    public void salvaSulDatabaseOgniOra(String nomeCitta) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                OpenWeatherCurrentMeteo openWeatherCurrentMeteo = new OpenWeatherCurrentMeteo(this.estraiApiKey(), nomeCitta);
                salvaSulDatabase(openWeatherCurrentMeteo.estraiDatiMeteo());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);
    }

    /**
     * Metodo per visualizzare il contenuto del relativo database formattato in JSONArray.
     *
     * @return JSONArray contenente tutti i dati meteo presenti sul database
     * @throws FileNotFoundException errore che verrà generato dal FileInputStream quando un file con il percorso
     *         specificato non esiste.
     * @throws ParseException errori durante il parsing
     */

    public JSONArray getDatabase()
            throws FileNotFoundException, ParseException {

        Scanner in = new Scanner(new BufferedReader(new FileReader(this.nomeDatabase)));
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(in.nextLine());
    }

    /**
     * Metodo per formattare il database. Una volta invocato, cancellerà tutti i dati presenti al suo interno ma senza
     * eliminare il relativo file.
     *
     * @throws IOException errori di input/output su file
     */

    public void svuotaDatabase()
            throws IOException {

        this.jsonArray.clear();
        FileWriter fileWriter = new FileWriter(this.file);
        fileWriter.write(this.jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Implementazione del metodo estraiApiKey dell'interfaccia ConfigInterface. Esso permette l'estrazione dell'apiKey
     * dal file di configurazione.
     *
     * @throws ConfigFileException errori presenti nel file di configurazione (se non rispetta il formato JSON)
     */

    public String estraiApiKey()
            throws ConfigFileException {

        try {
            File file = new File(System.getProperty("user.dir") + "/config.json");
            Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
            String inputLine = in.nextLine();
            in.close();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(inputLine);
            return (String) jsonObject.get("apiKey");
        } catch (Exception e) {
            throw new ConfigFileException();
        }
    }

    /**
     * Metodo get per ottenere il nome del database.
     *
     * @return nome del database
     */

    public String getNomeDatabase() {
        return this.nomeDatabase;
    }

    /**
     * Metodo set per la variabile d'istanza nomeDatabase.
     */

    public void setNomeDatabase(String nomeDatabase) {
        this.nomeDatabase = nomeDatabase;
    }
}

