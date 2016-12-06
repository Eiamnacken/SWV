import java.util.Random;

class Auto {
	/*@ predicate auto(int p,int r)= position |-> p &*& richtung |->
	 r &*& 0 < p &*& p < 5 &*& 0 < r &*& r < 5;
	 @*/
	public int position;
	public int richtung;

	public Auto(int position, int richtung) {
		this.position = position;
		this.richtung = richtung;
	}
	
	

	public void drive(int ampel)
	//@ requires auto(?p,?r);
	/*@ ensures auto(?p2,r) &*&  ampel!=Ampelstatus.GRUEN || ampel == Ampelstatus.GRUEN || p2!=r;
	@*/  
	{
		if (ampel == Ampelstatus.GRUEN) {
			this.position = richtung;
		}
	}

	public int getPosition()
	//@ requires auto(?p,?r);
	//@ ensures auto(p,r) &*& result==p;
	{
		return this.position;
	}

	public int getRichtung()
	//@ requires auto(?p,?r);
	//@ ensures auto(p,r) &*& result==r;
	{
		return this.richtung;
	}

	public String toString()
	{
		return "Auto [position=" + Richtung.getName(position) + ", richtung=" + Richtung.getName(richtung) + "]";
	}

}

class Richtung {
	final static int NORDEN = 1;
	final static int SUEDEN = 2;
	final static int WESTEN = 3;
	final static int OSTEN = 4;

	public static String getName(int key) {
		switch (key) {
		case 1:
			return "NORDEN";
		case 2:
			return "SUEDEN";
		case 3:
			return "WESTEN";
		case 4:
			return "OSTEN";
		default:
			return "";
		}
	}

}

class Ampelstatus {
	final static int ROT = 1;
	final static int GELB = 2;
	final static int GRUEN = 3;
	final static int GELBROT=4;

	public static String getName(int key) {
		switch (key) {
		case 1:
			return "ROT";
		case 2:
			return "GELB";
		case 3:
			return "GRUEN";
		case 4:
			return "GELBROT";
		default:
			return "";
		}
	}
}

class Ampel {
	/*@ predicate  ampel(int s,int z) =
	status |-> s  &*& zaehler |-> z &*&
	0 < s &*& s < 5 &*&
	0 <= z &*& z < 4 ;
	@*/
	private int status;
	private int zaehler;

	public Ampel(int status, int zaehler) {
		this.status = status;
		this.zaehler = zaehler;
	}

	private void changeStatus() 
	//@ requires	ampel(?s,?z);
	/*@ ensures	ampel(?s2,z) &*& 
	0 <= z || z<4 ||
	s==Ampelstatus.GELB && s2==Ampelstatus.GRUEN  ||
	s==Ampelstatus.GRUEN && s2==Ampelstatus.GELBROT ||
	s==Ampelstatus.GELBROT && s2==Ampelstatus.ROT ||
	s==Ampelstatus.ROT && s2==Ampelstatus.GELB;
	 @*/
	{
		switch (this.status) {
		case Ampelstatus.GRUEN:
			this.status = Ampelstatus.GELBROT;
			break;
		case Ampelstatus.GELB:
			this.status = Ampelstatus.GRUEN;
			break;
		case Ampelstatus.ROT:
			this.status = Ampelstatus.GELB;
			break;
		case Ampelstatus.GELBROT:
			this.status=Ampelstatus.ROT;
			break;
		}
	}

	public int getStatus()
	//@ requires ampel(?s,?z);
	//@ ensures ampel(s,z) &*& result==s;
	{
		return this.status;
	}
	
	public void addZaehler()
	//@ requires ampel(?s,?z);
	//@ ensures ampel(s,?z2) &*& z==3 && z2==0 || z<3 && z2==z+1 ;
	{
		if (this.zaehler < 3) {
			this.zaehler++;
		} else {
		
			this.zaehler = 0;
			//@ open ampel(s,0);
			this.changeStatus();
			//@ close ampel(s,0);
		}
	}

	public String toString() {
		return "Ampel [status=" + Ampelstatus.getName(status) + ", zaehler=" + zaehler + "]" ;
	}

}

class Strasse {
	private Auto auto;
	private int position;
	private Ampel ampel;

	public Strasse(int position, Ampel ampel) {
		this.position = position;
		this.ampel = ampel;
	}

	private void addAuto() {
		int[] autoRichtung = new int[2];

		switch (position) {
		case Richtung.NORDEN:
			autoRichtung[0] = Richtung.SUEDEN;
			autoRichtung[1] = Richtung.WESTEN;
			break;
		case Richtung.SUEDEN:
			autoRichtung[0] = Richtung.NORDEN;
			autoRichtung[1] = Richtung.OSTEN;
			break;
		case Richtung.WESTEN:
			autoRichtung[0] = Richtung.SUEDEN;
			autoRichtung[1] = Richtung.OSTEN;
			break;
		case Richtung.OSTEN:
			autoRichtung[0] = Richtung.WESTEN;
			autoRichtung[1] = Richtung.NORDEN;
			break;
		}
		Auto auto = new Auto(this.position, autoRichtung[new Random().nextInt(1)]);
		this.auto = auto;
	}

	public void tick()

	{
		if (this.auto == null) {
			this.addAuto();
		} else if (this.auto.getPosition() != this.position) {
			this.addAuto();
		}
		this.ampel.addZaehler();
		this.auto.drive(this.ampel.getStatus());
		System.out.println(this.toString());
	}

	public String toString() {
		return "Strasse [auto=" + this.auto.toString() + ", position=" + Richtung.getName(position) + ", ampel="
				+ ampel.toString() + "]";
	}

}

class Simulation {

	private Strasse[] strassen;

	public Simulation() {
		super();
		this.strassen = new Strasse[4];

	}

	public void init() {
		Ampel ampel = new Ampel(Ampelstatus.GRUEN, 0);
		Ampel ampel2 = new Ampel(Ampelstatus.GRUEN, 0);
		Ampel ampel3 = new Ampel(Ampelstatus.ROT, 0);
		Ampel ampel4 = new Ampel(Ampelstatus.ROT, 0);
		strassen[1] = new Strasse(Richtung.NORDEN, ampel2);
		strassen[3] = new Strasse(Richtung.OSTEN, ampel4);
		strassen[0] = new Strasse(Richtung.SUEDEN, ampel);
		strassen[2] = new Strasse(Richtung.WESTEN, ampel3);
	}

	public void start() {
		java.util.Scanner in = new java.util.Scanner(System.in);
		String stop = "";
		while (!"stop".equals(stop)) {
			for (int i = 0; i < strassen.length; i++) {
				strassen[i].tick();
			}
			stop = in.nextLine();
		}
		in.close();
	}
}
