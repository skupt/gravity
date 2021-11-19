package spaceShuttle;

public class Rocket extends Shuttle {
	
	
	public Rocket(double x, double y, double vx, double vy, double R, double m) {
		super(x, y, vx, vy, R, m);
		this.fJ = new Vector (0d, 0d);
		this.s = new ShuttleLiveState();
	}
	@Override
	void calcNewPosition() {
		double dt = Main.dt;
		
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
