package BattleCity_independent_code;

public class Bullet {

	static final int SPEED = 6;
	
	int coordY, coordX, targetDerection;
	boolean isLive = false;
	int _speed = SPEED;
	
	public Bullet(boolean bonus) {
		if (bonus) {
			_speed = SPEED * 2;			
		}else {
			_speed = SPEED;
		}
	}
}
