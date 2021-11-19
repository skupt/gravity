package spaceShuttle;

public class Shuttle extends Body {

	public Vector fJ; // jet thrust vector
	public double fJmod; // jet thrust vector
	public int rocketDelay = 0;
	
	public Shuttle(double x, double y, double vx, double vy, double R, double m) {
		super(x, y, vx, vy, R, m);
		this.fJ = new Vector (0d, 0d);
		this.s = new ShuttleLiveState();
	}
	public static Shuttle newShuttle (double px, double py, double vx, double vy, double R, double m, double jetAceler) {
		Shuttle shuttle = new Shuttle(px* Main.SCALE, py* Main.SCALE, vx, vy, R, m);
		shuttle.fJmod = m*jetAceler; //shuttle can accelerate with 1g
		return shuttle;
	}

	@Override
	void calcNewPosition() {
		double dt = Main.dt;
		if (Main.mouseB2) {
			Vector mouseP=new Vector((double) Main.mX* Main.SCALE, (double) Main.mY* Main.SCALE);
			Vector directionJet= mouseP.subtract(super.r);
			double angJet=directionJet.angXR();
			fJ=Vector.polarVR(this.fJmod, angJet);
		} else {
			fJ.x=0d;
			fJ.y=0d;
		}
		if (Main.mouseB1) {
			if (rocketDelay>50) {
			Vector mouseP=new Vector((double) Main.mX* Main.SCALE, (double) Main.mY* Main.SCALE);
			Vector directionJet= mouseP.subtract(super.r);
			double angJet=directionJet.angXR();
			Vector veapon=Vector.polarVR(this.v.hyp()*2, angJet);
			Rocket r = new Rocket(this.r.x+veapon.x*dt*30, this.r.y+veapon.y*dt*30, veapon.x, veapon.y, 3000000d, 3000d); // rocket with the same velocity as shuttle
			r.fJ=this.F.multiplyPerNumber(2d); // rocket has 2x acceleration to shuttle's one
			Main.bs.add(r);
			rocketDelay=0;
			}
		}
		rocketDelay+=1;
		
		
		Vector Fnew = this.F.add(fJ); // корректировка на силу реактивной тяги
		this.F = Fnew;
		double ax = F.x/m; // ускорение по X
		double ay = F.y/m; // ускорение по Y
		double vx2 = this.v.x + ax*dt; // скорость по Х
		double vy2 = this.v.y + ay*dt; // скорость по У
		double x2 = this.r.x + this.v.x*dt + (ax*dt*dt)/2; // новая координата Х
		double y2 = this.r.y + this.v.y*dt + (ay*dt*dt)/2; // новая координата У
		this.r.x = x2; 
		this.r.y = y2;
		this.v.x = vx2;
		this.v.y = vy2;
		this.F.x = 0;
		this.F.y = 0;
		}
	

	

}
