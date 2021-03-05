package com.OOPDekGiz.progettoDekGiz.model;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.OOPDekGiz.progettoDekGiz.exception.*;


/**
 * la classe gestisce la data di un certo oggetto MeteoCitta 
 * @author emanuele
 *
 */
public class DataMeteo {
	
	/**
	 * giorno
	 */
	protected long giorno;
	
	/**
	 * mese
	 */
	protected long mese;
	
	/**
	 * anno
	 */
	protected long anno;
	
	/**
	 * data completa
	 */
	protected Date data;
	
	/**
	 * istanza di calendar per gestire le date
	 */
	Calendar calendario;
	
	/**
	 * indice di numero di settimana del mese variabile tra 1 e 5
	 */
	protected int settimana=-1;
	
	/**
	 * unixData contiene i secondi trascorsi tra la data ed il 1/1/1970
	 */
	protected long unixData;  //seconds from first january 1970

	/**
	 * 
	 * @param unixData contiene i secondi trascorsi tra la data ed il 1/1/1970
	 * @throws DataMeteoException 
	 */
	public DataMeteo(long unixData) throws DataMeteoException {
		    /**
		     * unixDataMillis contiene i millisecondi trascorsi tra la data ed il 1/1/1970
		     */
			long unixDataMillis = (long)(unixData*1000);
			

		    calendario = new GregorianCalendar();
			data = new Date(unixDataMillis);
			
			calendario.setTimeInMillis(unixDataMillis);
			
			
			giorno=calendario.get(Calendar.DATE);
			mese=calendario.get(Calendar.MONTH)+1;
			anno=calendario.get(Calendar.YEAR);
			
			
			settimana = calcolaSettimana(giorno);
			
    }
	

	/**
	 * 
	 * @param giorno
	 * @return indice settimana nel mese
	 */
	public int calcolaSettimana(long giorno) throws DataMeteoException {     //throws exception
		if(giorno>=1 & giorno<=7) {
			settimana=1;
			return settimana;
		}
		else if(giorno>=8 & giorno<=14) {
			settimana=2;
			return settimana;
		}
		else if(giorno>=15 & giorno<=21) {
			settimana=3;
			return settimana;
		}
		else if(giorno>=22 & giorno<=28) {
			settimana=4;
			return settimana;
		}
		else if(giorno>=28 & giorno<=31) {
			settimana=5;
			return settimana;
		}
		
		if(settimana==-1) {
			
		throw new DataMeteoException();
		
		}
		
		return -1; //problemi 
		
	}
	
	/**
	 * 
	 * @param data2 è la data da confrontare come oggetto Date
	 * @return se è vero o falso che le due date coincidono
	 */
	public boolean confrontaData (Date data2) {
	
		if (this.data.compareTo(data2)==0)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param data2 è la data da confrontare secondo lo standard Unix Date
	 * @return se è vero o falso che le due date coincidono
	 */
	public boolean confrontaData (long unixData2) {
		
		long unixData2Millis = (long)(unixData2*1000);
		Date data2 = new Date(unixData2Millis);
		
		if (this.data.compareTo(data2)==0)
			return true;
		else
			return false;
	}

	public boolean confrontaData(DataMeteo data2) {
		boolean flag=false;
		if(data2.getAnno()==this.getAnno()&& data2.getMese()==this.getMese()&&data2.getGiorno()==this.getGiorno())
			flag=true;
		return flag;
	}
	
	public String toString () {
		return data.toString();
	}
	
	//metodi set e get di tutti campi della classe
	
	public long getGiorno() {
		return giorno;
	}

	public void setGiorno(long giorno) {
		this.giorno = giorno;
	}

	public long getMese() {
		return mese;
	}

	public void setMese(long mese) {
		this.mese = mese;
	}

	public long getAnno() {
		return anno;
	}

	public void setAnno(long anno) {
		this.anno = anno;
	}

	public long getUnixData() {
		return unixData;
	}

	public void setUnixData(long unixData) {
		this.unixData = unixData;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}


	public int getSettimana() {
		return settimana;
	}


	public void setSettimana(int settimana) {
		this.settimana = settimana;
	}
	
	
		
		

}
