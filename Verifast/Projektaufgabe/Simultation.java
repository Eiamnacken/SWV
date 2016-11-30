import java.util.List;

import ampel.Ampelstatus;
import ampel.Auto;
import ampel.Richtung;

class Auto{
	
	public Richtung position;
	public Richtung richtung;
	
	public Auto(Richtung position,Richtung richtung){
		this.position=position;
		this.richtung=richtung;
	}
	
	public void drive(Ampelstatus ampel){
		if(ampel==Ampelstatus.GRUEN){
			this.position=richtung;
		}	
	}
	public Richtung getPosition(){
		return this.position;
	}
	
	public Richtung getRichtung(){
		return this.richtung;
	}
}


enum Richtung{
	NORDEN,SUEDEN,OSTEN,WESTEN;	
}

enum Ampelstatus{
	ROT,GELB,GRUEN;
}

class Ampel{

	private Ampelstatus status;
	private boolean gehtHoch;
	private int zaehler;
	
	
	public Ampel(Ampelstatus status, boolean gehtHoch, int zaehler){
		this.status = status;
		this.gehtHoch = gehtHoch;
		this.zaehler = zaehler; 
	}
	
	public void changeStatus(){
	
		switch (status) {
		case GRUEN:
			this.status = Ampelstatus.GELB;
			this.gehtHoch = true;
			break;
		case GELB:
			if(gehtHoch == true){
			this.status = Ampelstatus.ROT;
			}else{
			this.status = Ampelstatus.GRUEN;
			}
			break;
		case ROT:
			this.status = Ampelstatus.GELB;
			this.gehtHoch = false;
			break;	
		}
	}
	
	public Ampelstatus getStatus(){
		return this.status;
	}
	
	public void addZaehler(){
		if(this.zaehler < 90){
			this.zaehler++;
		}else{
			this.zaehler = 0;
			changeStatus();
		}
	}
	
	
}

class Strasse{

}


class Kreuzung{
	List<Auto> autos;
	
}

class Simulation{
	
	
}

