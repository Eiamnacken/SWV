class Auto {
	/*@ predicate auto(int p,int r)= position |-> p &*& richtung |->
	 r &*& 0 < p &*& p < 5 &*& 0 < r &*& r < 5;
	 @*/
	public int position;
	public int richtung;
	
	public Auto(int position, int richtung)
	//@ requires true;
	//@ ensures auto(?p,?r);
	 {
	 	if(0<position&&position<5)
			this.position = position;
		else this.position=1;
		if(0<richtung&&richtung<5)
			this.richtung = richtung;
		else this.richtung=1;	
	}
	
	
	/**
	 * Auto fährt an die angegebene Position
	 * Die position wird ihm über {@link #richtung} angegeben
	 * Wenn der angegebene Ampelstatus nicht {@link Ampelstatus#GRUEN} ist, wird die zielposition nicht angefahren
	 * @param ampel Ampelstatus der Ampel an der sich das Auto befindet {@link Ampelstatus}
	 */
	public void drive(int ampel)
	//@ requires auto(?p,?r);
	/*@ ensures auto(?p2,r) &*&  ampel!=Ampelstatus.GRUEN || p2!=r || ampel == Ampelstatus.GRUEN ;
	@*/  
	{
		if (ampel == Ampelstatus.GRUEN) {
			this.position = richtung;
		}
	}
	/**
	 * 
	 * @return Momentane Position des Autos auf der Krezung
	 * @see {@link Richtung}
	 */
	public int getPosition()
	//@ requires auto(?p,?r);
	//@ ensures auto(p,r) &*& result==p;
	{
		return this.position;
	}
	/**
	 * 
	 * @return Momentane Fahrtrichtung des Autos
	 * @see {@link Richtung}
	 */
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
/**
 * Wird für richtungen und Positionen verwendet
 * @author sven
 *
 */
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
/**
 * Beinhaltet die Ampelstadien
 * @author sven
 *
 */
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
/**
 * Eine Simuliert Ampel
 * @author sven
 *
 */
class Ampel {
	/*@ predicate  ampel(int s,int z) =
	status |-> s  &*& zaehler |-> z &*&
	0 < s &*& s < 5 &*&
	0 <= z &*& z < 4 ;
	@*/
	/**
	 * Momentaner Ampelzustand der Ampel
	 * @see {@link Ampelstatus}
	 */
	private int status;
	/**
	 * Zähler der Ampel um vornaschreiten der Zeit zu simulieren
	 */
	private int zaehler;

	public Ampel(int status, int zaehler) {
		this.status = status;
		this.zaehler = zaehler;
	}
	/**
	 * Wechsle den status wird anhand des momentanen {@link #status} entschieden
	 * Wurde hier als Automat relalisiert </br>
	 * <ul>
	 * <li>Auf {@link Ampelstatus#ROT} folgt {@link Ampelstatus#GELB}</li>
	 * <li>Auf {@link Ampelstatus#GELB} folgt {@link Ampelstatus#GRUEN}</li>
	 * <li>Auf {@link Ampelstatus#GRUEN} folgt {@link Ampelstatus#GELBROT}</li>
	 * <li>Auf {@link Ampelstatus#GELBROT} folgt {@link Ampelstatus#ROT}</li>
	 * </ul>
	 * Auf
	 */
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
	/**
	 * 
	 * @return Momentanen Ampelstatus
	 * @see Ampelstatus
	 */
	public int getStatus()
	//@ requires ampel(?s,?z);
	//@ ensures ampel(s,z) &*& result==s;
	{
		return this.status;
	}
	/**
	 * Vornanschreiten des Zählers um einen. Ist der Zähler bei 3 angekommen wird dieser wieder auf 0 gesetzt und die 
	 * Ampelphase gewechselt 
	 * 	 
	 */
	public void addZaehler()
	//Kann nicht verifiziert werden wegen eines Fehlers in verifast
	{
		if (this.zaehler < 3) {
			this.zaehler++;
		} else {
		
			this.zaehler = 0;
			this.changeStatus();
		}
	}
	
	public String toString() {
		return "Ampel [status=" + Ampelstatus.getName(status) + ", zaehler=" + zaehler + "]" ;
	}

}

class Strasse {
	/*@ predicate strasse(Auto a,int p,Ampel am, int r) =
	auto |-> a &*& position |-> p &*& ampel |-> am &*& richtung |-> r &*&
	a != null &*&
	0 < p &*& p < 5 &*&
	am != null &*&
	0 <= r &*& r < 2;
	@*/
	
	/*@
	predicate strasseOhneAuto(int p,Ampel am,int r)=
	position |-> p &*& ampel |-> am &*& richtung |-> r &*&
	0 < p &*& p < 5 &*&
	am != null &*&
	0 <= r &*& r < 2;
	@*/
	/**
	 * Auto das sich auf der Straße befindet
	 */
	private Auto auto;
	/**
	 * Position die diese Straße hat
	 * @see {@link Richtung}
	 */
	private int position;
	/**
	 * Ampel die sich auf der Straße befindet
	 * @see {@link Ampel}
	 */
	private Ampel ampel;
	/**
	 * In welche Richtung sich das Auto bewegen soll
	 * @see {@link Richtung}
	 */
	private int richtung;
	
	public Strasse(int position, Ampel ampel) {
		this.position = position;
		this.ampel = ampel;
		this.richtung=0;
	}
	/**
	 * Hinzufügen eines Autos auf der Straße
	 * Es ist immer nur ein Auto zur zeit auf der Straße
	 */
	private void addAuto()
	//@ requires strasseOhneAuto(?p,?am,?r) &*& this.auto |-> _;
	//@ ensures strasse(?a2,?p2,?am2,?r);
	  {
		int autoRichtung=0;
		switch (position) {
		case Richtung.NORDEN:
			autoRichtung=this.richtung==0?Richtung.SUEDEN:Richtung.WESTEN;
			break;
		case Richtung.SUEDEN:
			autoRichtung=this.richtung==0?Richtung.NORDEN:Richtung.OSTEN;
			break;
		case Richtung.WESTEN:
			autoRichtung=this.richtung==0?Richtung.SUEDEN:Richtung.OSTEN;
			break;
		case Richtung.OSTEN:
			autoRichtung=this.richtung==0?Richtung.NORDEN:Richtung.WESTEN;
			break;
		}
		Auto auto = new Auto(this.position, autoRichtung);
		assert auto != null;
		this.richtung=this.richtung==1?0:1;
		this.auto = auto;
	}
	

	//requires strasseOhneAuto(?p,?am,?lp) &*& this.auto |-> _; 
	// ensures strasse(?a,?p2,?am2,?lp2);
	/**
	 * Voranschreiten der {@link Ampelstatus} und bewegt die {@link #auto} bzw erstellt neue Autos 
	 */
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
	/*@ predicate simulation(Strasse [] s)= 
	strassen[0..strassen.length] |-> s &*&
	forall_ (int k;k <0  || k >= s.length || nth(k,s)!= null);
	@*/
	/**
	 * Die strassen die diese krezung beinhaltet
	 */
	private Strasse[] strassen;

	public Simulation() {
		super();
		this.strassen = new Strasse[4];

	}
	/**
	 * Initialisieren der Simulation. Setzen der Autos und der befüllen der Straße
	 */
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
	/**
	 * Starten der Simulation
	 */
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
