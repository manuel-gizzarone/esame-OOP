package com.OOPDekGiz.progettoDekGiz.model;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.OOPDekGiz.progettoDekGiz.exception.*;

/**
 *
 * la classe gestisce la data di un certo oggetto MeteoCitta 
 * @author emanuele
 *
 */

public class DataMeteo {

	private long giorno;

	private long mese;

	private long anno;

	private Date data;

	private int settimana;
	
	private long unixData;  //seconds from first january 1970

	/**
	 * 
	 * @param unixData contiene i secondi trascorsi tra la data ed il 1/1/1970
	 * @throws DataMeteoException
	 *
	 */

	public DataMeteo(long unixData) throws DataMeteoException {
		this.unixData=unixData;

		//millisecondi dal 1970
		long unixDataMillis = (this.unixData*1000);
		this.data = new Date(unixDataMillis);

		Calendar calendario = new GregorianCalendar();
		calendario.setTimeInMillis(unixDataMillis);

		this.giorno= calendario.get(Calendar.DATE);
		this.mese= calendario.get(Calendar.MONTH)+1;
		this.anno= calendario.get(Calendar.YEAR);

		this.settimana = calcolaSettimana(this.giorno);

    }

	/**
	 * 
	 * @param giorno
	 * @return indice settimana nel mese
	 *
	 */

	public int calcolaSettimana(long giorno) throws DataMeteoException {
		if(giorno>=1 & giorno<=7) {
			return 1;
		}
		else if(giorno>=8 & giorno<=14) {
			return 2;
		}
		else if(giorno>=15 & giorno<=21) {
			return 3;
		}
		else if(giorno>=22 & giorno<=28) {
			return 4;
		}
		else if(giorno>=28 & giorno<=31) {
			return 5;
		} else {
			throw new DataMeteoException();
		}
	}
	
	/**
	 * 
	 * @param data2 è la data da confrontare come oggetto Date
	 * @return se è vero o falso che le due date coincidono
	 *
	 */

	public boolean confrontaData (Date data2) {
	
		if (this.data.compareTo(data2)==0)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param unixData2 è la data da confrontare secondo lo standard Unix Date
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
	
	public String toString () {
		return this.data.toString();
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
