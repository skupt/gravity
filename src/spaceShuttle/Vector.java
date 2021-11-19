package spaceShuttle;

public class Vector {
	public double x, y;
	
	public Vector (double x, double y) {
		this.x = x;
		this.y = y;
	}
	public static Vector polarVD(double hypotenusa, double angleToXInDegrees) {
		double x = hypotenusa*Math.cos(Math.toRadians(angleToXInDegrees));
		double y = hypotenusa*Math.sin(Math.toRadians(angleToXInDegrees));
		return new Vector(x,y);
	}
	public static Vector polarVR(double hypotenusa, double angleToXInRadians) {
		double x = hypotenusa*Math.cos(angleToXInRadians);
		double y = hypotenusa*Math.sin(angleToXInRadians);
		return new Vector(x,y);
	}


	public String toString () {
		String res = "Vector(" + this.x + ":" + this.y + ")" + super.hashCode(); 
		return res;
	}
	
	public double getx() { return x;}
	public double gety() { return y;}
	
	public Vector add (Vector other) {
		double newX = this.x+other.x;
		double newY = this.y+other.y;
		return new Vector (newX, newY);
	}
	public Vector subtract (Vector other) {
		double newX = this.x-other.x;
		double newY = this.y-other.y;
		return new Vector (newX, newY);
	}
	public double multiply(Vector other) {
		return this.x*other.x + this.y*other.y;
	}
	public Vector multiplyPerNumber(Double number) {
		return new Vector(this.x*number, this.y*number);
	}
	public double tgy() {
		return (this.x/this.y);
	}
	public double tgx() {
		return (this.y/this.x);
	}
	public double hyp( ) {
		return (double) Math.hypot(this.x, this.y);
		
	}
	public double sina() {
		return this.y/hyp(); 
	}
	public double cosa() {
		return this.x/hyp(); 
	}
	public double angXR() {
		return Math.atan2(this.y, this.x);
	}
	public double angXD() {
		return Math.toDegrees(Math.atan2(this.y, this.x));
	}

}
