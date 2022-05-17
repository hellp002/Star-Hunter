package tools;


public class Vector2D {
	private double x,y;
	
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2D(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D copyOne) {
		this.x = copyOne.x;
		this.y = copyOne.y;
	}
	
	public double getMagnitude() {
		return Math.hypot(x, y);
	}
	
	public Vector2D getUnitVector() {
		if (this.getMagnitude() == 0) {
			return new Vector2D();
		}
		return new Vector2D((this.x/this.getMagnitude()),(this.y/this.getMagnitude()));
	}
	
	
	public void setRotatingVector(double degree) {
		double newX = this.x * Math.cos(Math.toRadians(degree)) - this.y * Math.sin(Math.toRadians(degree));
		double newY = this.x * Math.sin(Math.toRadians(degree)) + this.y * Math.cos(Math.toRadians(degree));
		this.setX(newX);
		this.setY(newY);
		
	}

	public void reset() {
		setX(0);
		setY(0);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
}
