package com.OOPDekGiz.progettoDekGiz.model;

import org.json.simple.JSONObject;

/**
 * Interfaccia che verrà implementata dalle classi che definiscono un metodo per il casting di un oggetto in JSONObject.
 */

public interface InterfaceToJsonObject {

	/**
	 * Metodo per il casting in JSONObject.
	 *
	 * @return oggetto della classe relativa convertito in formato JSONObject
	 */
	
	JSONObject castToJsonObject ();
}
