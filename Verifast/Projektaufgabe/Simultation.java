import java.util.Random;



class Auto{
	/**
	 * 
	 */
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

	@Override
	public String toString() {
		return "Auto [position=" + position + ", richtung=" + richtung + "]";
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
	
		switch (this.status) {
		case GRUEN :
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
		if(this.zaehler < 3){
			this.zaehler++;
		}else{
			this.zaehler = 0;
			changeStatus();
		}
	}

	@Override
	public String toString() {
		return "Ampel [status=" + status + ", gehtHoch=" + gehtHoch + ", zaehler=" + zaehler + "]";
	}
	
	
	
}

class Strasse{
	private Auto auto;
	private Richtung position;
	private Ampel ampel;
	
	public Strasse(Richtung position,Ampel ampel){
		this.position=position;
		this.ampel=ampel;
	}
	
	private void addAuto(){
		Richtung[] autoRichtung = new Richtung[2];
		
		switch(position){
			case NORDEN:
				autoRichtung[0]=Richtung.SUEDEN;
				autoRichtung[1]=Richtung.WESTEN;
			break;
			case SUEDEN:
				autoRichtung[0]=Richtung.NORDEN;
				autoRichtung[1]=Richtung.OSTEN;
			break;
			case WESTEN:
				autoRichtung[0]=Richtung.SUEDEN;
				autoRichtung[1]=Richtung.OSTEN;
			break;
			case OSTEN:
				autoRichtung[0]=Richtung.WESTEN;
				autoRichtung[1]=Richtung.NORDEN;
			break;
		 }
		Auto auto = new Auto(this.position, autoRichtung[new Random().nextInt(1)]);
		this.auto=auto;
	}
	
	public void tick(){
		if(this.auto==null){
			this.addAuto();
		}else if(this.auto.getPosition()!=this.position){
			this.addAuto();
		}
		this.ampel.addZaehler();
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "Strasse [auto=" + this.auto.toString() + ", position=" + position + ", ampel=" + ampel.toString() + "]";
	}
	
	
}

class Simulation{
	private Strasse[] strassen;
	public Simulation() {
		super();
		this.strassen = new Strasse[4];
		
	}
	
	public void init() {
		Ampel ampel = new Ampel(Ampelstatus.GRUEN, true, 0);
		Ampel ampel2 = new Ampel(Ampelstatus.GRUEN, true, 0);
		Ampel ampel3 = new Ampel(Ampelstatus.ROT, false, 0);
		Ampel ampel4 = new Ampel(Ampelstatus.ROT, false, 0);
		strassen[1] = new Strasse(Richtung.NORDEN, ampel2);
		strassen[3] = new Strasse(Richtung.OSTEN, ampel4);
		strassen[0] = new Strasse(Richtung.SUEDEN, ampel);
		strassen[2] = new Strasse(Richtung.WESTEN, ampel3);
	}
	
	public void start() {
		java.util.Scanner in = new java.util.Scanner(System.in);
		String stop="";
		while(!"stop".equals(stop)){
			for(Strasse s : strassen){
				s.tick();
				stop = in.nextLine();
			}
		}
	}
}

