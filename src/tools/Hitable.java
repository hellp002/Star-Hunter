package tools;

import javafx.scene.shape.Rectangle;

public interface Hitable {
	void onCollision(Hitable other);
	boolean checkCollision(Hitable other);
	Rectangle getHitBox();
}
