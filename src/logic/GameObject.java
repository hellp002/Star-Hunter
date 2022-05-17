package logic;


import sharedObject.IRenderable;
import tools.Hitable;
import tools.Updatable;

public abstract class GameObject implements Updatable,IRenderable,Hitable  {
	protected double x,y;
	protected boolean visible,deleted;
	
	
	public boolean checkCollision(Hitable other) {
		return this.getHitBox().getBoundsInParent().intersects(other.getHitBox().getBoundsInParent());
	}
	

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	@Override
	public double getX() {
		return x;
	}

}
