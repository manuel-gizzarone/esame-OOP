package com.OOPDekGiz.progettoDekGiz.util;

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

    private final File file;

    private static JSONArray jsonArray; //static e necessario altrimenti lancia un eccezione NullPointerException

    FileWriter fileWriter;

    /**
     *
     * Costruttore della classe DataBase che crea un file json ed istanzia un JSONArray in cui verranno inseriti tutti
     * i dati.
     *
     * @throws IOException
     *
     */

    public DataBase() throws IOException {
        this.nomeDatabase = "DATABASE.json";
        file = new File(System.getProperty("user.dir") + "/" + this.nomeDatabase);
        if(!file.exists()) {
            file.createNewFile();
            jsonArray = new JSONArray();
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
        fileWriter.write(nuovoDatoMeteo.toJSONString());
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
        fileWriter.flush(); //pulisce lo stream di input
        fileWriter.close();
    }

    /**
     *
     * Metodo che salva ogni ora i dati meteo istantanei di un oggetto MeteoCitta, ottenuti dalle chiamate alle
     * ApiCurrent.
     *
     * @param meteoCitta oggetto di tipo meteo citta contenente i dati meteo istantanei relativi ad una citta
     *
     */

    public void salvaSulDatabaseOgniOra(MeteoCitta meteoCitta) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        //uso una lambda expression
        scheduler.scheduleAtFixedRate(() -> {
            try {
                salvaSulDatabase(meteoCitta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);
    }

    /**
     *
     * Metodo per visualizzare il contenuto del database.
     *
     * @return JSONArray contenente tutti i dati meteo raccolti
     * @throws FileNotFoundException
     * @throws FileNotFoundException
     * @throws ParseException
     *
     */

    public JSONArray getDatabase() throws FileNotFoundException, ParseException {
        Scanner in = new Scanner(new BufferedReader(new FileReader(this.nomeDatabase + ".json")));
        JSONParser parser = new JSONParser();
        JSONArray output = (JSONArray) parser.parse(in.nextLine());
        return output;
    }

    /**
     *
     * Metodo per eliminare il database
     *
     */

    public void eliminaDatabase(){
        this.file.delete();
    }

    //metodi setter e getter relativi al nome del file che contiene il database

    public void setNomeDatabase(String nomeDatabase) {
        this.nomeDatabase = nomeDatabase;
    }

    public String getNomeDatabase(){
        return this.nomeDatabase;
    }
}
