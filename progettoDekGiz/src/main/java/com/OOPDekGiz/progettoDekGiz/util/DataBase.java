package com.OOPDekGiz.progettoDekGiz.util;

import com.OOPDekGiz.progettoDekGiz.exception.DataMeteoException;
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
 *
 * Classe che si occupa di gestire il salvataggio su un Database dei dati forniti dalle chiamate alle API OpenWeather.
 *
 * @author Manuel Gizzarone
 * @author Emanuele De Caro
 *
 */

public class DataBase{

    private String nomeDatabase;

    private JSONArray jsonArray;

    private final File file;

    FileWriter fileWriter;

    String apiKey = "2300b41a61721439ff98965f79ff40db";

    /**
     *
     * Costruttore della classe DataBase che crea un file json ed istanzia un JSONArray in cui verranno inseriti tutti
     * i dati.
     *
     * @throws IOException
     *
     */

    //Nomi dei database
    //Database previsioni 5 giorni: "Database_Previsioni"
    //Database dati ogni ora: "Database_Raccolta"

    public DataBase(String nomeDatabase) throws IOException, ParseException {
        this.nomeDatabase = nomeDatabase;
        file = new File(System.getProperty("user.dir") + "\\" + this.nomeDatabase);
        if(!file.exists()) {
            file.createNewFile();
            jsonArray = new JSONArray();
        } else {
            jsonArray = this.getDatabase();
        }
    }

    /**
     *
     * Questo metodo salva sul database i dati meteo forniti dalle chiamate ApiCurrent.
     *
     * @param meteoCitta oggetto di tipo meteo citta contenente i dati meteo istantanei relativi ad una citta
     * @throws IOException
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     *
     */

    public void salvaSulDatabase(MeteoCitta meteoCitta) throws IOException, UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
        JSONObject nuovoDatoMeteo = meteoCitta.castToJsonObject();
        jsonArray.add(nuovoDatoMeteo);

        fileWriter = new FileWriter(file);
        fileWriter.write(jsonArray.toJSONString()); //controllare
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     *
     * Overloading del metodo SalvaSulDatabase che salva sul database i dati forniti dalle chiamate Api5Giorni.
     *
     * @param datiMeteo vettore contenente oggetti di tipo MeteoCitta
     * @throws IOException
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     *
     */

    public void salvaSulDatabase(Vector<MeteoCitta> datiMeteo) throws IOException, UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException {
        fileWriter = new FileWriter(file);
        for (MeteoCitta meteoCitta : datiMeteo) {
            JSONObject nuovoDatoMeteo = meteoCitta.castToJsonObject();
            jsonArray.add(nuovoDatoMeteo);
        }

        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     *
     * Metodo che salva ogni ora i dati meteo istantanei (nuvolosita) di una citta.
     * @param nomeCitta Nome della citta di cui si vogliono raccogliere i dati meteo
     *
     */

    public void salvaSulDatabaseOgniOra(String nomeCitta) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                OpenWeatherCurrentMeteo openWeatherCurrentMeteo = new OpenWeatherCurrentMeteo(apiKey, nomeCitta);
                MeteoCitta meteoCitta = openWeatherCurrentMeteo.estraiDatiMeteo();
                salvaSulDatabase(meteoCitta);
            } catch (IOException | ParseException | DataMeteoException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);
    }

    /**
     *
     * Metodo per visualizzare il contenuto del database formattato in JSONArray.
     *
     * @return JSONArray contenente tutti i dati meteo raccolti
     * @throws FileNotFoundException
     * @throws FileNotFoundException
     * @throws ParseException
     *
     */

    public JSONArray getDatabase() throws FileNotFoundException, ParseException {
        Scanner in = new Scanner(new BufferedReader(new FileReader(this.nomeDatabase)));
        JSONParser parser = new JSONParser();
        JSONArray output = (JSONArray) parser.parse(in.nextLine());
        return output;
    }

    /**
     *
     * Metodo per formattare il database.
     *
     */

    public void svuotaDatabase() throws IOException {
        this.jsonArray.clear();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();

    }

    //metodi setter e getter relativi al nome del file che contiene il database

    public void setNomeDatabase(String nomeDatabase) {
        this.nomeDatabase = nomeDatabase;
    }

    public String getNomeDatabase(){
        return this.nomeDatabase;
    }
}
