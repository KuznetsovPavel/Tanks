package BattleCity_independent_code;

public class Bullet {

	private static final int SPEED = 10;
	
	private int coordY, coordX, targetDerection;
	private boolean isLive = false;
	private int _speed = getSpeed();
	
	public Bullet(boolean bonus) {
		if (bonus) {
			set_speed(getSpeed() * 2);			
		}else {
			set_speed(getSpeed());
		}
	}

	public int get_speed() {
		return _speed;
	}

	public void set_speed(int _speed) {
		this._speed = _speed;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public int getTargetDerection() {
		return targetDerection;
	}

	public void setTargetDerection(int targetDerection) {
		this.targetDerection = targetDerection;
	}

	public static int getSpeed() {
		return SPEED;
	}
}
