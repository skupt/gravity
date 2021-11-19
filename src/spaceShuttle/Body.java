package spaceShuttle;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Body implements MyObservable, MyObserver {
	//public double x, y, vx, vy; 

	
	List<MyObserver> observers;

	public double R;
	public double m;
	public Vector r;
	public Vector v;
	public Vector F;
	public MyState s;
	public boolean dead;
	String name;
	
	public Body(double x, double y, double vx, double vy, double R, double m) {
		
		this.R=R;
		this.m=m;
		this.r=new Vector(x,y);
		this.v=new Vector(vx, vy);
		this.F=new Vector(0,0);
		this.s = new LiveState();
		dead=false;
		observers = new ArrayList<>();
	}
	
	public static Body newPlanet(double px, double py, double vx, double vy, double multyplyEarth) {
		//Double multR = Math.pow((multyplyEarth/(4/3*Math.PI)), (1d/3d));
		Double planetR = Math.pow((Main.EW/ Main.EС*multyplyEarth)/(4/3*Math.PI), (1d/3d));
		Body planet = new Body(px* Main.SCALE, py* Main.SCALE, vx, vy, planetR, Main.EW*multyplyEarth);

		return planet;
	}
	public static Body newPlanet(double px, double py, double vx, double vy, double multyplyEarth, String name) {
		Double planetR = Math.pow((Main.EW/ Main.EС*multyplyEarth)/(4/3*Math.PI), (1d/3d));
		Body planet = new Body(px* Main.SCALE, py* Main.SCALE, vx, vy, planetR, Main.EW*multyplyEarth);
		planet.name=name;
		return planet;
	}

	
	
	@Override
	public void update(Body o) {
		if (checkColl(o)) s.onCollision(this, o);
		Vector dis = o.r.subtract(this.r); // вектор от текущего тела к 2-му
		double dist = dis.hyp(); //дистанция между 2-мя телами
		double fGm = Main.G*(this.m*o.m)/(dist*dist); // модуль силы гравит взаимодействия
		double fGx = fGm * (dis.x/dis.hyp());
		double fGy = fGm * (dis.y/dis.hyp());
		Vector fG = new Vector(fGx, fGy); //вектор силы гравит взаимодействия 2-мя телами
		Vector Fnew = this.F.add(fG); // корректировка на силу  взаимодействия со 2-м телом суммы всех сил уже действующих на тело 
		this.F = Fnew;
	}

	@Override
	public void addObserver(MyObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(MyObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (MyObserver o : observers) {
			o.update(this);
		}
	}
	
	boolean checkColl(Body o) {
		if ((Math.abs((o.r.x-this.r.x))<((o.R+this.R)/2))&&(Math.abs((o.r.y-this.r.y))<((o.R+this.R)/2))) return true;
		
		return false;
	}
	
	void step() {
		s.onStep(this);
	}
	void paint(Graphics g) {
		s.paint(g, this);
	}
	
	void calcNewPosition() {
		double dt = Main.dt;
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
	
	
	public String toString() {
		return name+"("+r.x+","+r.y+"):v("+v.x+","+v.y+"):"+"m="+ m + ", R=" + R+ "dead="+dead;
	}
	

}
