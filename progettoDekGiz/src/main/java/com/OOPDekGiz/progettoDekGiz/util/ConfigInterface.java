package com.OOPDekGiz.progettoDekGiz.util;

import com.OOPDekGiz.progettoDekGiz.exception.ConfigFileException;
import com.OOPDekGiz.progettoDekGiz.exception.GestisciStringaException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ConfigInterface {

    String estraiApiKey() throws ParseException, IOException, GestisciStringaException, ConfigFileException;
}
