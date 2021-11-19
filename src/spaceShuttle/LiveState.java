package spaceShuttle;

import java.awt.Color;
import java.awt.Graphics;

public class LiveState implements MyState {
	Color c = Color.green;
	
	public LiveState () {}

	@Override
	public void onStep(Body b) {
		b.calcNewPosition();
		b.notifyObservers();
	}

	@Override
	public void onCollision (Body me, Body other) {
		//check velocity vectors direction
		double cos = me.v.multiply(other.v)/(me.v.hyp()*other.v.hyp());
		if (cos>-0.48) { // velocities angle is less then 90 degrees
			if (me.m>=other.m) { // I am more weight
				Vector myP = me.v.multiplyPerNumber(me.m);
				Vector otherP = other.v.multiplyPerNumber(other.m);
				Vector myNewP = myP.add(otherP);
				double myNewM = me.m+other.m;
				Vector myNewV = myNewP.multiplyPerNumber(1/myNewM);
				Double rationWeight = myNewM/me.m; 
				Double planetR = Math.pow((myNewM/ Main.EС)/(4/3*Math.PI), (1d/3d));
				me.m=myNewM;
				me.v=myNewV;
				me.R=planetR;
				me.s = new LiveState();//StickState
				other.dead=true;
			} else { // Me // I am less weight
				Vector myP = me.v.multiplyPerNumber(me.m);
				Vector otherP = other.v.multiplyPerNumber(other.m);
				Vector otherNewP = myP.add(otherP);
				double otherNewM = me.m+other.m;
				Vector otherNewV = otherNewP.multiplyPerNumber(1/otherNewM);
				other.m=otherNewM;
				me.v=otherNewV;
				Double rationWeight = otherNewM/other.m; 
				Double planetR = Math.pow((otherNewM/ Main.EС)/(4/3*Math.PI), (1d/3d));
				other.R=planetR;
				other.s = new LiveState();//StickState
	
				me.dead=true;
			};
		} else {
			if (me.m>=other.m&&(other.m/me.m>0.13d) ) { // I explode, other give weight, impuls and die
				Vector myP = me.v.multiplyPerNumber(me.m);
				Vector otherP = other.v.multiplyPerNumber(other.m);
				Vector myNewP = myP.add(otherP);
				double myNewM = me.m+other.m;
				Vector myNewV = myNewP.multiplyPerNumber(1/myNewM);
				Double rationWeight = myNewM/me.m; 
				Double planetR = Math.pow((myNewM/ Main.EС)/(4/3*Math.PI), (1d/3d));
				me.m=myNewM;
				me.v=myNewV;
				me.R=planetR;
				me.s = new ExplodingState(); //ExplodingState;
				other.dead=true;
			} else {
				Vector myP = me.v.multiplyPerNumber(me.m);
				Vector otherP = other.v.multiplyPerNumber(other.m);
				Vector otherNewP = myP.add(otherP);
				double otherNewM = me.m+other.m;
				Vector otherNewV = otherNewP.multiplyPerNumber(1/otherNewM);
				other.m=otherNewM;
				other.v=otherNewV;
				Double rationWeight = otherNewM/other.m; 
				Double planetR = Math.pow((otherNewM/ Main.EС)/(4/3*Math.PI), (1d/3d));
				other.R=planetR;
				other.s = new LiveState();//StickState
	
				me.dead=true;
			}
		}
	}
	
	public synchronized void paint(Graphics g, Body e) {
		//if (!e.dead) g.drawOval((int) (e.r.x/Go.SCALE-e.R*0.5/Go.SCALE), (int) (e.r.y/Go.SCALE-e.R*0.5/Go.SCALE), (int)e.R*2/Go.SCALE, (int)e.R*2/Go.SCALE);
		g.setColor(Color.black);
		g.drawOval((int) (e.r.x/ Main.SCALE-e.R/ Main.SCALE), (int) (e.r.y/ Main.SCALE-e.R/ Main.SCALE), (int)e.R*2/ Main.SCALE, (int)e.R*2/ Main.SCALE);
		g.setColor(c);
		g.fillOval((int) (e.r.x/ Main.SCALE-e.R/ Main.SCALE +1), (int) (e.r.y/ Main.SCALE-e.R/ Main.SCALE +1), (int)e.R*2/ Main.SCALE-2, (int)e.R*2/ Main.SCALE-2);
		
	}
}
