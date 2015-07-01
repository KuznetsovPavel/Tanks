package BattleCity_independent_code;

import BattleCity_LibGDX.BattleCityScreen;

public class View {

	protected static final int SCREEN_HEIGHT = BattleCityScreen.SCREEN_HEIGHT;
	protected static final int SCREEN_WIDTH = BattleCityScreen.SCREEN_WIGHT;
	
	static final int TANK_SIZE_X = SCREEN_WIDTH / Map.COL;
	static final int TANK_SIZE_Y = SCREEN_HEIGHT / Map.ROW;
	private static final int BOX_SIZE_X = SCREEN_WIDTH / Map.COL;
	private static final int BOX_SIZE_Y = SCREEN_HEIGHT / Map.ROW;
	
	final static int TANK_TEXTURE_INDEX = 0;
	final static int TANK_BOT_TEXTURE_INDEX = 1;
	static final int TANK_BOT_CRASHED_TEXTURE_INDEX = 2;

	final static int PANEL_COORD_X = (Map.COL - 5) * SCREEN_WIDTH / Map.COL;
	final static int PANEL_COORD_Y = (Map.ROW - 3) * SCREEN_HEIGHT / Map.ROW;
	public final static int PANEL_SIZE_X = SCREEN_WIDTH / Map.COL * 5;
	public final static int PANEL_SIZE_Y = SCREEN_HEIGHT / Map.ROW * 3;
	
	public static final int MOVING_CONTROL_X = (int) (SCREEN_WIDTH*0.1);
	public static final int MOVING_CONTROL_Y = (int) (SCREEN_HEIGHT*0.05);
	public static final int BUTTON_FIRE_X = (int) (SCREEN_WIDTH*0.8);
	public static final int BUTTON_FIRE_Y = (int) (SCREEN_HEIGHT*0.1);

	private Graphics _graphics;
	
	public void setGraphics(final Graphics graphics) {
		_graphics = graphics;
	}

	public void draw(final State state) {

		drawMap(state._map.getData());

		int numberOfTank = 0;
		int numberOfBullet = 0;

		if (!state.bonusIsTake) {
			drawBonus(state._bonus_coordX*BOX_SIZE_X, state._bonus_coordY*BOX_SIZE_Y, true, state.bonus);
		}else {
			drawBonus(state._bonus_coordX*BOX_SIZE_X, state._bonus_coordY*BOX_SIZE_Y, false, state.bonus);
		}

		if (state.get_tank()._bullet.isLive == true) {
			drawBullet(state.get_tank()._bullet.coordX, state.get_tank()._bullet.coordY,
					numberOfBullet);
		} else if (state.get_tank()._bullet.isLive == false) {
			drawBullet(state.get_tank().coordX + TANK_SIZE_X / 2, state.get_tank().coordY
					+ TANK_SIZE_Y / 2, numberOfBullet);
		}


		drawTank(TANK_TEXTURE_INDEX, state.get_tank().coordX, state.get_tank().coordY,
				numberOfTank, state.get_tank().derection, false);

		for (Tank tankBot : state._botTanks) {
			numberOfBullet += 1;
			if (tankBot._bullet.isLive == true) {
				drawBullet(tankBot._bullet.coordX, tankBot._bullet.coordY,
						numberOfBullet);
			} else if (state.get_tank()._bullet.isLive == false) {
				drawBullet(tankBot.coordX + TANK_SIZE_X / 2, tankBot.coordY
						+ TANK_SIZE_Y / 2, numberOfBullet);
			}
		}

		for (Tank tankBot : state._botTanks) {
			numberOfTank += 1;
			if (!tankBot.isCrash) {
					drawTank(TANK_BOT_TEXTURE_INDEX, tankBot.coordX, tankBot.coordY,
							numberOfTank, tankBot.derection, state.isNewMap());				
			}else {
				drawTank(TANK_BOT_CRASHED_TEXTURE_INDEX, tankBot.coordX, tankBot.coordY,
						numberOfTank, tankBot.derection, state.isNewMap());
			}
		}
		
		if (state.isNewMap()) {
			state.setIsNewMap(false);
		}
		

		drawInfoPanel(PANEL_COORD_X, PANEL_COORD_Y, PANEL_SIZE_X, PANEL_SIZE_Y,
				0, "You destroy " + state.get_tank().getEnemyKilled() + " enemy" + '\n'
						+ "\n" + "You have "
						+ (Tank.ARMOR_PLAYER_TANK - state.get_tank().damages)
						+ " armor" + '\n' + "\n" + "Level: " + state.get_level(),
				state._gameOver);
		
		drawMovingControl(MOVING_CONTROL_X, MOVING_CONTROL_Y, state.getState_cntrl());
		drawButtonFire(BUTTON_FIRE_X, BUTTON_FIRE_Y, state.getState_butFire());
		

	}


	protected void drawMovingControl(int movCntrlX, int movCntrlY, int state_cntrl) {
		_graphics.drawTextureElement(movCntrlX, movCntrlY, BOX_SIZE_X, BOX_SIZE_Y, state_cntrl);
	}
	
	protected void drawButtonFire(int butFirelX, int butFirelY, int state_fire) {
		_graphics.drawTextureElement(butFirelX, butFirelY, BOX_SIZE_X, BOX_SIZE_Y, state_fire);
	}

	protected void drawMap(final int[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				drawBox(data[i][j], i, j);
			}
		}
	}

	protected void drawBonus(int coordX, int coordY, boolean bonusFree, int bonusIndex){
		_graphics.drawTextureElement(coordX, coordY, BOX_SIZE_X, BOX_SIZE_Y, 0);
	}
	
	protected void drawInfoPanel(int coordX, int coordY, int pannel_size_X,
			int pannel_size_Y, int textureIndex, String info, boolean gameOver) {
		_graphics.drawTextureElement(coordX, coordY, pannel_size_X,
				pannel_size_Y, textureIndex);
	}

	protected void drawBox(final int textureIndex, final int row, final int col) {
		_graphics.drawTextureElement(col * BOX_SIZE_X, row * BOX_SIZE_Y,
				BOX_SIZE_X, BOX_SIZE_Y, textureIndex);
	}

	protected void drawTank(final int textureIndex, final int coordX,
			final int coordY, int numberOfTank, int derection, boolean newMap) {
		_graphics.drawTextureElement(coordX, coordY, TANK_SIZE_X, TANK_SIZE_X,
				textureIndex);
	}

	protected void drawBullet(final int coordX, final int coordY,
			int numberOfBullet) {
		_graphics.drawTextureElement(coordX, coordY, BOX_SIZE_X, BOX_SIZE_Y, 0);
	}
}
