package BattleCity_independent_code;

public interface Graphics {

	void drawBox(int x, int y, int width, int height, int textureIndex);
	
	void drawTank(int x, int y, int width, int height, int textureIndex,
			int numberOfTank, int derection, boolean newMap);
	
	void drawInfoPanel(int x, int y, int width, int height);

	void drawBullet(int coordX, int coordY, int boxSizeX, int boxSizeY,
			int numberOfBullet);

	void drawBonus(int coordX, int coordY, int boxSizeX, int boxSizeY,
			boolean bonusFree, int bonusIndex);

	void drawMovingControl(int movCntrlX, int movCntrlY, int boxSizeX,
			int boxSizeY, int state_cntrl);

	void drawButtonFire(int butFirelX, int butFirelY, int boxSizeX,
			int boxSizeY, int state_fire);

}
