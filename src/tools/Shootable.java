package tools;

public interface Shootable {
	
	boolean isCoolDown();
	Vector2D getFiringDirection();
	void setAttackSpeed();
	void shoot();
	
}
