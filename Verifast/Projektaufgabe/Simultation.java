class Auto{
	
	public Richtung position;
	public Richtung richtung;
	
	public Auto(Richtung position,Richtung richtung){
		this.position=position;
		this.richtung=richtung;
	}
	
	public void drive(Ampelstatus ampel){
		if(ampel==Apelstatus.GRUEN){
			this.position=richtung;
		}	
	}
	public Richtung getPosition(){
		return this.position
	}
	
	public Richtung getRichtung(){
		return this.richtung;
	}
}


enum Richtung{
	NORDEN,SUEDEN,OSTEN,WESTEN;	
}

enum Ampelstatus{
	ROT;GELB,GRUEN;
}

class Ampel{
	private Ampelstatu status;
	
	
	
	public void changeStatus(Ampelstatus status){
		
	}
	
	public Ampelstatus getStatus(){
		return this.status;
	}
}

class Strasse{

}


class Keuzung{
	List<Auto> autos;
	
}

class Simuation{
	
	
}

