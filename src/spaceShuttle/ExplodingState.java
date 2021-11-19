package spaceShuttle;

import java.awt.Color;
import java.awt.Graphics;

public class ExplodingState implements MyState {
	
	Color c = Color.red;


	@Override
	public void onStep(Body me) {
		// dividing
		double baseAn = me.v.angXD();
		double angI=baseAn+15, angI2=baseAn-15;
		double dt = Main.dt;
		double ax = me.F.x/me.m; // ускорение по X
		double ay = me.F.y/me.m; // ускорение по Y
		double vx2 = me.v.x + ax*dt; // скорость по Х
		double vy2 = me.v.y + ay*dt; // скорость по У
		double x2 = me.r.x + me.v.x*dt + (ax*dt*dt)/2; // новая координата Х
		double y2 = me.r.y + me.v.y*dt + (ay*dt*dt)/2; // новая координата У
		Vector shift= new Vector((x2-me.r.x), (y2-me.r.y));
		double shiftV=shift.hyp()*240;
		Double planetR = Math.pow(((me.m/6)/ Main.EС)/(4/3*Math.PI), (1d/3d));
		
		for (int i=0; i<6; i++) {
			angI = angI+15;
			double xI = shiftV*Math.sin(angI);
			double yI = shiftV*Math.cos(angI);
			Vector rotateI = new Vector(xI, yI);
			Vector rI = me.r.add(rotateI); // coords of new Body
			double vxI = me.v.hyp()*Math.sin(angI);
			double vyI = me.v.hyp()*Math.cos(angI);
			Vector vI = new Vector(vxI,vyI).multiplyPerNumber(1d); // velocity of new Body
			Body particle = new Body(rI.x, rI.y, vI.x, vI.y, planetR, me.m/12);
			particle.s = new SplinterState();
			Main.bs.add(particle);

		}
		//angI=baseAn;
		for (int i=0; i<6; i++) {
			angI2 = angI2-15;

			double xI = shiftV*Math.sin(angI2);
			double yI = shiftV*Math.cos(angI2);
			Vector rotateI = new Vector(xI, yI);
			Vector rI = me.r.add(rotateI); // coords of new Body
			double vxI = me.v.hyp()*Math.sin(angI2);
			double vyI = me.v.hyp()*Math.cos(angI2);
			Vector vI = new Vector(vxI,vyI); // velocity of new Body
			Body particle = new Body(rI.x, rI.y, vI.x, vI.y, planetR, me.m/12);
			particle.s = new SplinterState();
			Main.bs.add(particle);

		}
		me.dead=true;
	}
	

	@Override
	public void onCollision(Body me, Body other) {
		//throw new UnsupportedOperationException();

	}
	public synchronized void paint(Graphics g, Body e) {
		//if (!e.dead) g.drawOval((int) (e.r.x/Go.SCALE-e.R*0.5/Go.SCALE), (int) (e.r.y/Go.SCALE-e.R*0.5/Go.SCALE), (int)e.R*2/Go.SCALE, (int)e.R*2/Go.SCALE);
		g.setColor(Color.black);
		g.drawOval((int) (e.r.x/ Main.SCALE-e.R/ Main.SCALE), (int) (e.r.y/ Main.SCALE-e.R/ Main.SCALE), (int)e.R*2/ Main.SCALE, (int)e.R*2/ Main.SCALE);
		g.setColor(c);
		g.fillOval((int) (e.r.x/ Main.SCALE-e.R/ Main.SCALE +1), (int) (e.r.y/ Main.SCALE-e.R/ Main.SCALE +1), (int)e.R*2/ Main.SCALE-2, (int)e.R*2/ Main.SCALE-2);


	}

}
