package BattleCity_independent_code;

import BattleCity_Scene_Screen.BattleCityScreen;
import com.badlogic.gdx.graphics.g2d.Batch;

public class View {

	private static final int SCREEN_HEIGHT = BattleCityScreen.getScreenHeight();
	private static final int SCREEN_WIDTH = BattleCityScreen.getScreenWight();
	
	private static final int TANK_SIZE_X = getScreenWidth() / Map.getCol();
	private static final int TANK_SIZE_Y = getScreenHeight() / Map.getRow();
	private static final int BOX_SIZE_X = getScreenWidth() / Map.getCol();
	private static final int BOX_SIZE_Y = getScreenHeight() / Map.getRow();
	
	private final static int TANK_TEXTURE_INDEX = 0;
	private final static int TANK_BOT_TEXTURE_INDEX = 1;
	private static final int TANK_BOT_CRASHED_TEXTURE_INDEX = 2;

	private final static int PANEL_COORD_X = (Map.getCol() - 5) * getScreenWidth() / Map.getCol();
	private final static int PANEL_COORD_Y = (Map.getRow() - 3) * getScreenHeight() / Map.getRow();
	private final static int PANEL_SIZE_X = getScreenWidth() / Map.getCol() * 5;
	private final static int PANEL_SIZE_Y = getScreenHeight() / Map.getRow() * 3;
	
	private static final int MOVING_CONTROL_X = (int) (getScreenWidth()*0.1);
	private static final int MOVING_CONTROL_Y = (int) (getScreenHeight()*0.05);
	private static final int BUTTON_FIRE_X = (int) (getScreenWidth()*0.8);
	private static final int BUTTON_FIRE_Y = (int) (getScreenHeight()*0.1);

	private Graphics _graphics;
	private long time = 0;
	
	public void setGraphics(final Graphics graphics) {
		_graphics = graphics;
	}

	public void draw(final State state) {

		drawMap(state.get_map().getData());

		int numberOfTank = 0;
		int numberOfBullet = 0;

		if (!state.isBonusIsTake()) {
			drawBonus(state.get_bonus_coordX()*BOX_SIZE_X, state.get_bonus_coordY()*BOX_SIZE_Y, true, state.getBonus());
		}else {
			drawBonus(state.get_bonus_coordX()*BOX_SIZE_X, state.get_bonus_coordY()*BOX_SIZE_Y, false, state.getBonus());
		}

		if (state.get_tank().get_bullet().isLive() == true) {
			drawBullet(state.get_tank().get_bullet().getCoordX(), state.get_tank().get_bullet().getCoordY(),
					numberOfBullet);
		} else if (state.get_tank().get_bullet().isLive() == false) {
			drawBullet(state.get_tank().getCoordX() + TANK_SIZE_X / 2, state.get_tank().getCoordY()
					+ TANK_SIZE_Y / 2, numberOfBullet);
		}


		drawTank(TANK_TEXTURE_INDEX, state.get_tank().getCoordX(), state.get_tank().getCoordY(),
				numberOfTank, state.get_tank().getDerection(), false);

		for (Tank tankBot : state.get_botTanks()) {
			numberOfBullet += 1;
			if (tankBot.get_bullet().isLive() == true) {
				drawBullet(tankBot.get_bullet().getCoordX(), tankBot.get_bullet().getCoordY(),
						numberOfBullet);
			} else if (state.get_tank().get_bullet().isLive() == false) {
				drawBullet(0, 0, numberOfBullet);
			}
		}

		for (Tank tankBot : state.get_MyBotTanks()) {
			numberOfBullet += 1;
			if (tankBot.get_bullet().isLive() == true) {
				drawBullet(tankBot.get_bullet().getCoordX(), tankBot.get_bullet().getCoordY(),
						numberOfBullet);
			} else if (state.get_tank().get_bullet().isLive() == false) {
				drawBullet(0, 0, numberOfBullet);
			}
		}

		for (Tank tankBot : state.get_botTanks()) {
			numberOfTank += 1;
			if (!tankBot.isCrash()) {
					drawTank(TANK_BOT_TEXTURE_INDEX, tankBot.getCoordX(), tankBot.getCoordY(),
							numberOfTank, tankBot.getDerection(), state.isNewMap());				
			}else {
				drawTank(TANK_BOT_CRASHED_TEXTURE_INDEX, tankBot.getCoordX(), tankBot.getCoordY(),
						numberOfTank, tankBot.getDerection(), state.isNewMap());
			}
		}

		int count = 0;
		for (Tank tankBot : state.get_MyBotTanks()) {
			numberOfTank += 1;
			count++;
			if (!tankBot.isCrash()) {
				if (count == 2) {
					drawTank(4, tankBot.getCoordX(), tankBot.getCoordY(),
							numberOfTank, tankBot.getDerection(), state.isNewMap());
				}else {
					drawTank(3, tankBot.getCoordX(), tankBot.getCoordY(),
							numberOfTank, tankBot.getDerection(), state.isNewMap());
				}
			}else {
				drawTank(TANK_BOT_CRASHED_TEXTURE_INDEX, tankBot.getCoordX(), tankBot.getCoordY(),
						numberOfTank, tankBot.getDerection(), state.isNewMap());
			}

		}
		
		if (state.isNewMap()) {
			state.setIsNewMap(false);
		}
		

		drawInfoPanel(PANEL_COORD_X, PANEL_COORD_Y, PANEL_SIZE_X, PANEL_SIZE_Y,
			 "You destroy " + state.get_tank().getEnemyKilled() + " enemy" + '\n'
						+ "\n" + "You have "
						+ (Tank.getArmorPlayerTank() - state.get_tank().getDamages())
						+ " armor" + '\n' + "\n" + "Level: " + state.get_level(),
				state.is_gameOver());
		
		drawMovingControl(getMovingControlX(), getMovingControlY(), state.getState_cntrl());
		drawButtonFire(getButtonFireX(), getButtonFireY(), state.getState_butFire());

		if(state.isPicture()){
			if (time == 0) {
				time = System.currentTimeMillis();
			} else
			{
				if(System.currentTimeMillis() - time < 3000) {
					drawPicture();
				}
				else
				{
					state.setPicture(false);
					time = 0;
					hidePicture();
				}
			}

		}

	}

	protected void hidePicture() {

	}

	protected void drawPicture() {
	}


	protected void drawMovingControl(int movCntrlX, int movCntrlY, int state_cntrl) {
		_graphics.drawMovingControl(movCntrlX, movCntrlY, BOX_SIZE_X, BOX_SIZE_Y, state_cntrl);
	}
	
	protected void drawButtonFire(int butFirelX, int butFirelY, int state_fire) {
		_graphics.drawButtonFire(butFirelX, butFirelY, BOX_SIZE_X, BOX_SIZE_Y, state_fire);
	}

	protected void drawMap(final int[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				drawBox(data[i][j], i, j);
			}
		}
	}

	protected void drawBonus(int coordX, int coordY, boolean bonusFree, int bonusIndex){
		_graphics.drawBonus(coordX, coordY, BOX_SIZE_X, BOX_SIZE_Y, bonusFree, bonusIndex);
	}
	
	protected void drawInfoPanel(int coordX, int coordY, int pannel_size_X,
			int pannel_size_Y, String info, boolean gameOver) {
		_graphics.drawInfoPanel(coordX, coordY, pannel_size_X,pannel_size_Y);
	}

	protected void drawBox(final int textureIndex, final int row, final int col) {
		_graphics.drawBox(col * BOX_SIZE_X, row * BOX_SIZE_Y,
				BOX_SIZE_X, BOX_SIZE_Y, textureIndex);
	}

	protected void drawTank(final int textureIndex, final int coordX,
			final int coordY, int numberOfTank, int derection, boolean newMap) {
		_graphics.drawTank(coordX, coordY, TANK_SIZE_X, TANK_SIZE_X,
				textureIndex, numberOfTank, derection, newMap);
	}

	protected void drawBullet(final int coordX, final int coordY,int numberOfBullet) {
		_graphics.drawBullet(coordX, coordY, BOX_SIZE_X, BOX_SIZE_Y, numberOfBullet);
	}

	public static int getMovingControlX() {
		return MOVING_CONTROL_X;
	}

	public static int getMovingControlY() {
		return MOVING_CONTROL_Y;
	}

	public static int getButtonFireX() {
		return BUTTON_FIRE_X;
	}

	public static int getButtonFireY() {
		return BUTTON_FIRE_Y;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

}
