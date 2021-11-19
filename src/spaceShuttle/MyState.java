package spaceShuttle;

import java.awt.Graphics;

public interface MyState {
	void onStep(Body me);
	void onCollision(Body me, Body other);
	void paint(Graphics g, Body b);
}
