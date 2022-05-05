package logic;

import javafx.scene.shape.Rectangle;
import sharedObject.IRenderable;
import tools.Updatable;

public abstract class GameObject implements Updatable,IRenderable  {
	protected double x,y;
	protected double radius; 
	protected boolean visible,deleted;
	
	
	public boolean checkCollision(GameObject other) {
		return this.getHitBox().getBoundsInParent().intersects(other.getHitBox().getBoundsInParent());
	}
	
	public abstract void onCollision(GameObject other);
	
	public abstract Rectangle getHitBox();

}
