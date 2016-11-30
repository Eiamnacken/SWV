class Auto{
	
	public void drive(){
		
	}
	
	public void checkAmpel(){
	
	}

}

enum Ampelstatus{
	ROT;GELB,GRUEN;
}

class Ampel{

	private Ampelstatus status;
	private boolean gehtHoch;
	
	
	public Ampel(Ampelstatus status, boolean gehtHoch){
		this.status = status;
		this.gehtHoch = gehtHoch;
		
	}
	
	public void changeStatus(){
	
		switch (status) {
		case GRUEN:
			this.status = GELB;
			this.gehtHoch = true;
			break;
		case GELB:
			if(gehtHoch == true){
			this.status = ROT;
			}else{
			this.status = GRUEN;
			}
			break;
		case ROT:
			this.satus = GELB;
			this.gehtHoch = false;
			break;	
		}
	}
	
	public Ampelstatus getStatus(){
		return this.status;
	}
	
	
}


class Keuzung{
	List<Auto> autos;
	
	
}

class Simulation{
	
	
}

