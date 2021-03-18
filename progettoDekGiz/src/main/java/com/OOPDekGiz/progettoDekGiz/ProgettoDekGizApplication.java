package com.OOPDekGiz.progettoDekGiz;

import com.OOPDekGiz.progettoDekGiz.exception.ConfigFileException;
import com.OOPDekGiz.progettoDekGiz.exception.InserimentoException;
import com.OOPDekGiz.progettoDekGiz.exception.NomeCittaException;
import com.OOPDekGiz.progettoDekGiz.service.ServiceNuvole;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Questa classe contiene il metodo main del programma.
 */

@SpringBootApplication
public class ProgettoDekGizApplication {

	/**
	 * Main del programma.
	 *
	 * @param args parametri passati all'avvio del programma
	 * @throws ParseException errori durante il parsing
	 * @throws InserimentoException eccezione lanciata in caso di mancato inserimento di un campo richiesto
	 * @throws NomeCittaException eccezione lanciata in caso di inserimento di un nome città inesistente o errato
	 * @throws IOException errori di input/output su file
	 * @throws ConfigFileException eccezione lanciata in caso di errori nel file di configuazione
	 */

	public static void main(String[] args)
			throws ParseException, InserimentoException, NomeCittaException, IOException, ConfigFileException {

		SpringApplication.run(ProgettoDekGizApplication.class, args);
/*
		ServiceNuvole serviceNuvole1 = new ServiceNuvole();
		serviceNuvole1.salvaOgniOra("Roma");
		ServiceNuvole serviceNuvole2 = new ServiceNuvole();
		serviceNuvole2.salvaOgniOra("Napoli");
		ServiceNuvole serviceNuvole3 = new ServiceNuvole();
		serviceNuvole3.salvaOgniOra("Milano");
*/
	}
}
