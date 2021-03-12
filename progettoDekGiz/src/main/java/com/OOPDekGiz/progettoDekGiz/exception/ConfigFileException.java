package com.OOPDekGiz.progettoDekGiz.exception;

public class ConfigFileException extends Exception{

    public ConfigFileException(){

        super("Errore formato file di configrazione. Controllare che il file rispetti il formato JSONObject");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
