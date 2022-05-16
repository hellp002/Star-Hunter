package logic;

import javafx.scene.shape.Rectangle;
import sharedObject.IRenderable;
import tools.Updatable;

public abstract class GameObject implements Updatable,IRenderable  {
	protected double x,y;
	protected boolean visible,deleted;
	
	
	public boolean checkCollision(GameObject other) {
		return this.getHitBox().getBoundsInParent().intersects(other.getHitBox().getBoundsInParent());
	}
	
	public abstract void onCollision(GameObject other);
	
	public abstract Rectangle getHitBox();
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
