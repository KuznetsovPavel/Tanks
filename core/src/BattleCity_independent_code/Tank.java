package BattleCity_independent_code;


public class Tank {
	
	private static final int ARMOM_BOT_TANK = 1;
	private static final int ARMOR_PLAYER_TANK = 10;
	private static final int SPEED = 2;
	
	
	private int coordX = 0;
	private int coordY = 0;
	
	private int _speed = getSpeed();
	
	private int damages = 0;
	private int derection = 1;
	
	private int countOfStep = 0;
	private boolean isCrash = false;
	private int enemyKilled = 0;
	private boolean isKill = false;
	
	private Bullet _bullet;
	
	protected void setTank(int x, int y) {
		setCoordY(y);
		setCoordX(x);
	}

	public int getEnemyKilled() {
		return enemyKilled;
	}

	public void setEnemyKilled(int enemyKilled) {
		this.enemyKilled = enemyKilled;
	}

	public boolean isCrash() {
		return isCrash;
	}

	public void setCrash(boolean isCrash) {
		this.isCrash = isCrash;
	}

	public int getCountOfStep() {
		return countOfStep;
	}

	public void setCountOfStep(int countOfStep) {
		this.countOfStep = countOfStep;
	}

	public int getDerection() {
		return derection;
	}

	public void setDerection(int derection) {
		this.derection = derection;
	}

	public int getDamages() {
		return damages;
	}

	public void setDamages(int damages) {
		this.damages = damages;
	}

	public int get_speed() {
		return _speed;
	}

	public void set_speed(int _speed) {
		this._speed = _speed;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public static int getArmorPlayerTank() {
		return ARMOR_PLAYER_TANK;
	}

	public static int getArmomBotTank() {
		return ARMOM_BOT_TANK;
	}

	public boolean isKill() {
		return isKill;
	}

	public void setKill(boolean isKill) {
		this.isKill = isKill;
	}

	public Bullet get_bullet() {
		return _bullet;
	}

	public void set_bullet(Bullet _bullet) {
		this._bullet = _bullet;
	}

	public static int getSpeed() {
		return SPEED;
	}
	
	
}
