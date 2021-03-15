package com.OOPDekGiz.progettoDekGiz.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.OOPDekGiz.progettoDekGiz.exception.*;

/**
 *
 * Questa classe contiene i metodi per la gestione di una certa data.
 *
 */

public class DataMeteo {

	private long giorno;

	private long mese;

	private long anno;

	private Date data;

	private int settimana;
	
	private long unixData;  //secondi dal 01/01/1970

	/**
	 *
	 * Costruttore della classe
	 *
	 * @param unixData contiene i secondi trascorsi tra la data ed il 01/01/1970
	 * @throws DataMeteoException eccezzione lanciata nel caso si verifichino problemi con la data inserita dall'utente
	 *
	 */

	public DataMeteo(long unixData)
			throws DataMeteoException {

		this.unixData = unixData;
		long unixDataMillis = (this.unixData * 1000);
		this.data = new Date(unixDataMillis);
		Calendar calendario = new GregorianCalendar();
		calendario.setTimeInMillis(unixDataMillis);
		this.giorno = calendario.get(Calendar.DATE);
		this.mese = calendario.get(Calendar.MONTH) + 1; //l'indice dei mesi parte dallo 0
		this.anno = calendario.get(Calendar.YEAR);
		this.settimana = this.calcolaSettimana(this.giorno);
    }

	/**
	 *
	 * Questo metodo consente di calcolare l'indice della settimana in cui appartiene un dato giorno di un mese.
	 *
	 * @param giorno indice giorno nel mese
	 * @return indice settimana nel mese. In caso di un indice del giorno superiore a 31 ritorna un valore di default
	 *
	 */

	public int calcolaSettimana(long giorno) {
		if(giorno>=1 && giorno<=7) {
			return 1;
		}
		else if(giorno>=8 && giorno<=14) {
			return 2;
		}
		else if(giorno>=15 && giorno<=21) {
			return 3;
		}
		else if(giorno>=22 && giorno<=28) {
			return 4;
		}
		else if(giorno>=28 && giorno<=31) {
			return 5;
		} else {
			return 0;
		}
	}


	public boolean confrontaData (Date data2) {
	
		if (this.data.compareTo(data2)==0)
			return true;
		else
			return false;
	}

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


	/**
	 *
	 * Questo metodo consente di calcolare il numero di giorni presenti in un mese.
	 *
	 * @param mese indice del mese
	 * @return numero di giorni presenti nel mese
	 *
	 */

	public long calcolaLimiteMese(long mese) {
		long limite = 0;

		if(mese==1)
			limite=31;
		if(mese==2)
			limite=28;
		if(mese==3)
			limite=31;
		if(mese==4)
			limite=30;
		if(mese==5)
			limite=31;
		if(mese==6)
			limite=30;
		if(mese==7)
			limite=31;
		if(mese==8)
			limite=31;
		if(mese==9)
			limite=30;
		if(mese==10)
			limite=31;
		if(mese==11)
			limite=30;
		if(mese==12)
			limite=31;

		return limite;
	}

	/**
	 *
	 * Override del metodo toString che consente di convertire un oggetto di tipo Date in una stringa contenente la
	 * data della relativa istanza della classe.
	 *
	 * @return istanza di Date sotto forma di stringa
	 *
	 */

	public String toString () {
		return this.data.toString();
	}

	/**
	 *
	 * Metodo get per la variabile d'istanza giorno.
	 *
	 * @return indice del giorno
	 *
	 */

	public long getGiorno() {
		return giorno;
	}

	/**
	 *
	 * Metodo set per la variabile d'istanza giorno.
	 *
	 */

	public void setGiorno(long giorno) {
		this.giorno = giorno;
	}

	/**
	 *
	 * Metodo get per la variabile d'istanza settimana.
	 *
	 * @return indice della settimana
	 *
	 */

	public int getSettimana() {
		return settimana;
	}

	/**
	 *
	 * Metodo set per la variabile d'istanza settimana.
	 *
	 */

	public void setSettimana(int settimana) {
		this.settimana = settimana;
	}

	/**
	 *
	 * Metodo get per la variabile d'istanza mese.
	 *
	 * @return indice del mese
	 *
	 */

	public long getMese() {
		return mese;
	}

	/**
	 *
	 * Metodo set per la variabile d'istanza mese.
	 *
	 */

	public void setMese(long mese) {
		this.mese = mese;
	}

	/**
	 *
	 * Metodo get per la variabile d'istanza anno.
	 *
	 * @return indice dell'anno
	 *
	 */

	public long getAnno() {
		return anno;
	}

	/**
	 *
	 * Metodo set per la variabile d'istanza anno.
	 *
	 */

	public void setAnno(long anno) {
		this.anno = anno;
	}

	/**
	 *
	 * Metodo get per la variabile d'istanza unixData.
	 *
	 * @return data in formato Unix
	 *
	 */

	public long getUnixData() {
		return unixData;
	}

	/**
	 *
	 * Metodo set per la variabile d'istanza unixData.
	 *
	 */

	public void setUnixData(long unixData) {
		this.unixData = unixData;
	}

	/**
	 *
	 * Metodo get per la variabile d'istanza data.
	 *
	 * @return oggetto di tipo Date contenente la relativa data
	 *
	 */

	public Date getData() {
		return data;
	}

	/**
	 *
	 * Metodo set per la variabile d'istanza data.
	 *
	 */

	public void setData(Date data) {
		this.data = data;
	}
}
