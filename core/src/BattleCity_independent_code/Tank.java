package BattleCity_independent_code;


public class Tank {
	
	static final int ARMOM_BOT_TANK = 3;
	static final int ARMOR_PLAYER_TANK = 10;
	static final int SPEED = 2;
	
	
	int coordX = 0;
	int coordY = 0;
	
	int _speed = SPEED;
	
	int damages = 0;
	int derection = 1;
	
	int countOfStep = 0;
	boolean isCrash = false;
	private int enemyKilled = 0;
	boolean isKill = false;
	
	Bullet _bullet;
	
	protected void setTank(int x, int y) {
		coordY = y;
		coordX = x;
	}

	public int getEnemyKilled() {
		return enemyKilled;
	}

	public void setEnemyKilled(int enemyKilled) {
		this.enemyKilled = enemyKilled;
	}
	
	
}
