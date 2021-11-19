package spaceShuttle;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Main extends Applet implements Runnable, MouseListener, MouseMotionListener  {
	double timeMult = 0;
	List <Body> ns; // all handled bodies
	List <Body> ds; // all died bodies to remove from "ns"
	static volatile List <Body> bs =  Collections.synchronizedList(new ArrayList<>()); //new particles after exploding to add to "ns"
	
	Thread t;
	boolean stop;
	
	static boolean mouseIn = false;
	static boolean mouseB1 = false;
	static boolean mouseB2 = false;
	static int mX=0;
	static int mY=0;
	

	public final static int fx=1000, fy=1000, fr=8; //pixels
	public static final double G=6.67e-11f; // gravity per ton | gravity G=6.67e-11f;
	public static final double EW=5.972e24f; //tons | kgs EW=5.972e24f
	public static final double EС=5520; //кг/м3
	public static final double ER=6.371e6f; //meters ER=6.371e6f
	public static final double MW=7.36e22f; // tons | kgs =7.36e22f
	public static final double MR=1.73814e6f; //meters MR=1.73814e6f
	public static final double EarthToMoon=384e6f; //meters EarthToMoon=384e6f
	public static final double dt=60; //sec dt=600
	

	public static final int SCALE=1_500_000; //meters a pixel

	

	
	public void init () {
		addMouseListener(this);
		addMouseMotionListener(this);
		resize(fx, fy);
		Random rnd = new Random();
		ds = Collections.synchronizedList(new ArrayList<>());
		ns = Collections.synchronizedList(new ArrayList<>());
		Body n;
			/* Moon & Earth & Sun Scale 800_000
			n = Body.newPlanet(800, 000, 0, 500, 1d, "Earth");
			ns.add(n);
			n = Body.newPlanet(880, 000, 0, 3000, 0.012d, "Moon");
			ns.add(n);
			n = Body.newPlanet(20, 20, 0, 0, 2.5d, "Sun");
			ns.add(n);
			n=Shuttle.newShuttle(500, 500, 0, 0, 5000000, 25000, 1); // (px, py, vx, vy, R, m, a)
			ns.add(n);
			*/

			///* Somewhere deep in space  1_500_000
			n = Body.newPlanet(230, 230, 1000, 300, 7d);
			ns.add(n);
			n = Body.newPlanet(500, 750, 2000, -1900, 3d);
			ns.add(n);
			n = Body.newPlanet(500, 800, -5500, -1900, 2.5d);
			ns.add(n);
			n = Body.newPlanet(550, 700, 4000, 0, 3d);
			ns.add(n);
			n=Shuttle.newShuttle(500, 500, 0, 0, 7000000, 25000, 1); // (px, py, vx, vy, R, m, a)
			ns.add(n);
			//*/
		for (Body observable : ns) {
			for (Body observer : ns) {
				if (observable!=observer) observable.addObserver(observer);
			}
		}
		t=null;
		stop=false;
	}
	
	public void start ( ) {
		t=new Thread(this);
		stop=false;
		t.start();
	}
	
	public void stop () {
		stop=true;
		t=null;
	}
	
	public synchronized void paint(Graphics g) {
	for (Body e : ns) {
			e.paint(g);
		}
	showStatus("TimeScale:" + ((int)timeMult/60) + "min in 1 sec.");

	}

	@Override
	public void run() {
		long timeAccum = 0;
		int counter = 0;
		while (!stop) {
			// calc time scale
			if (counter==0) {
				timeAccum = System.currentTimeMillis();
			}
			if (counter>=499) {
				timeAccum = System.currentTimeMillis()-timeAccum;
				double iterTime = timeAccum/500; //sec
				double hertz = 500/(timeAccum/1000);
				timeMult = dt*hertz; //sec
				counter=-1;
			}
			counter+=1;

			
			for (Body e : bs) { // add new bodies from Exploiding to array of all bodies "ns"
				addNewBody(e);
			}
			bs.clear();
			
			for (Body e : ns) {
				if (e.dead) {
					removeTrash(e); //remove references on died oblect in bodies observers
					ds.add(e); //add to delete list
					continue;
				}
				e.step();
				//System.out.println(e);
			}
			
			try {
			repaint();

			Thread.sleep(5);
			} catch (InterruptedException e) {}
			ns.removeAll(ds); //delete dead bodies
			
		}
		
	}
	
	public void removeTrash (Body trash) {
		// remove all references to dead body in bodies-observers 
		for (Body e : ns) {
			e.observers.remove(trash);
		}
	}
	public void addNewBody (Body b) {
		for (Body e : ns) {
			e.observers.add(b);
		}
		for (Body e : ns) {
			b.observers.add(e);
		}
		ns.add(b);


	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseIn=true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseIn=false;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON1) mouseB1=true;
		if(arg0.getButton()==MouseEvent.BUTTON2) mouseB2=true;
		if(arg0.getButton()==MouseEvent.BUTTON3) mouseB2=true;


	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON1) mouseB1=false;
		if(arg0.getButton()==MouseEvent.BUTTON2) mouseB2=false;
		if(arg0.getButton()==MouseEvent.BUTTON3) mouseB2=false;

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mX=arg0.getX();
		mY=arg0.getY();
		
	}
}
