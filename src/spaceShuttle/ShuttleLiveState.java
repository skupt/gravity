package spaceShuttle;

import java.awt.Color;
import java.awt.Graphics;

public class ShuttleLiveState extends LiveState {
	
	int jetPulsCount = 0;
	
	public ShuttleLiveState () {
		super();
		super.c = Color.blue;
	}

	@Override
	public synchronized void paint(Graphics g, Body e) {
		Shuttle sh = (Shuttle) e;
		g.setColor(Color.black);
		g.drawOval((int) (e.r.x/ Main.SCALE-e.R/ Main.SCALE), (int) (e.r.y/ Main.SCALE-e.R/ Main.SCALE), (int)e.R*2/ Main.SCALE, (int)e.R*2/ Main.SCALE);
		g.setColor(c);
		g.fillOval((int) (e.r.x/ Main.SCALE-e.R/ Main.SCALE +1), (int) (e.r.y/ Main.SCALE-e.R/ Main.SCALE +1), (int)e.R*2/ Main.SCALE-2, (int)e.R*2/ Main.SCALE-2);
		// jet current
		jetPulsCount+=1;
		g.setColor(Color.red);
		g.drawLine((int) e.r.x/ Main.SCALE,
					(int) e.r.y/ Main.SCALE,
					(int) (e.r.x/ Main.SCALE + sh.fJ.x*(-1)/ Main.SCALE*70*jetPulsCount),
					(int) (e.r.y/ Main.SCALE + sh.fJ.y*(-1)/ Main.SCALE*70*jetPulsCount)
					);
		if (jetPulsCount>10) jetPulsCount=0;
		
	}

	
}
