package com.gdx.battle_city;

import tanks.Controller;
import tanks.Map;
import tanks.Model;
import tanks.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BattleCityStage extends Stage {

	private static final int SCREEN_WIDTH = BattleCityScreen.SCREEN_WIGHT;
	private static final int SCREEN_HEIGHT = BattleCityScreen.SCREEN_HEIGHT;

	SpriteBatch spriteBatch = new SpriteBatch();
	
	int timerForBonus = 0;
	boolean gameIsOver = false;
	int final_score;
	int final_level;
	
	public BattleCityStage() {
		
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false);
		setViewport(new ScreenViewport(camera));
	
	}

	public void init() {
		
		final Model model = new Model();
		final Controller controller = new Controller();
		model.addListener(controller);
		
		View view = new View() {

			private Box[][] _boxes = new Box[Model.ROWS][Model.COLUMNS];
			private Tank[] _tank = new Tank[4];
			private Bullet[] _bullet = new Bullet[4];
			private InfoPanel _infoPanel;
			private Bonus _bonus;
			private MovingControl _movingControl;
			private ButtonFire _button_fire;
			BitmapFont font = new BitmapFont();
			LabelStyle infoStyle = new LabelStyle(font, Color.WHITE);
			Label _info;
			
			@Override
			protected void drawBonus(int coordX, int coordY, boolean bonusFree, int bonusIndex) {
				
				if (_bonus == null) {	
					_bonus = new Bonus(bonusIndex);
				BattleCityStage.this.addActor(_bonus);
				}
				
				if (bonusFree) {
					_bonus._texture = Bonus.textures[bonusIndex];
					_bonus.toFront();
					_bonus.setPosition(coordX, coordY);	
				}else {
					_bonus.toBack();
				}
					
			}
			
			@Override
			protected void drawBox(int textureIndex, int row, int col) {
				
				if (_boxes[row][col] == null) {
					initMap(textureIndex, row, col);
				}
				
				_boxes[row][col].setTexture(textureIndex);
			}

			private void initMap(int textureIndex, int row, int col) {
				
				Box box = new Box(textureIndex);
				_boxes[row][col] = box;
				BattleCityStage.this.addActor(box);
				box.setPosition(col * SCREEN_WIDTH / Map.COL, row * SCREEN_HEIGHT / Map.ROW);
				
			}

			@Override
			protected void drawInfoPanel(int coordX, int coordY,
					int pannel_size_X, int pannel_size_Y, int textureIndex,
					String message, boolean gameOver) {
				
				if (gameOver) {
					gameIsOver = true;
					final_score = model._logic._state.get_tank().getEnemyKilled();
					final_level = model._logic._state.get_level();
				}

				if (_infoPanel == null || _info == null) {
					initInfoPanel(coordX, coordY, message);
				}
				
				_info.setText(message);
				_infoPanel.toFront();
				_info.toFront();
			}

			private void initInfoPanel(int coordX, int coordY, String message) {
				
				_infoPanel = new InfoPanel();
				_info = new Label(message, infoStyle);
				BattleCityStage.this.addActor(_infoPanel);
				BattleCityStage.this.addActor(_info);
				_infoPanel.setPosition(coordX, coordY);
				_info.setPosition(coordX + SCREEN_WIDTH / Map.COL / 4, coordY + SCREEN_HEIGHT / Map.ROW/2);
				
			}

			@Override
			protected void drawTank(int textureIndex, int coordY, int coordX,
					int number, int derection, boolean newMap) {

				if (newMap) {
					_tank[number].numbTextExpl = 0;
				}
				
				if (_tank[number] == null) {
					_tank[number] = new Tank(textureIndex);
					BattleCityStage.this.addActor(_tank[number]);
				}
				
				if (textureIndex == 2) {
					
					if (_tank[number].numbTextExpl < 33) {
						_tank[number].numbTextExpl += 1;
						_tank[number]._texture = Tank.explosion[_tank[number].numbTextExpl];
					}else{
						_tank[number]._texture = Tank.textures[textureIndex];
						derection = 0;
					}
					
				}else{
					_tank[number]._texture = Tank.textures[textureIndex];
				}
				
				if (derection == 0) {
					putInLocation(coordY, coordX, number, 0);
				} else if (derection == 1) {
					putInLocation(coordY + SCREEN_HEIGHT / Map.ROW, coordX + SCREEN_WIDTH / Map.COL, number, 180);
				} else if (derection == 2) {
					putInLocation(coordY + SCREEN_HEIGHT / Map.ROW, coordX, number, 90);
				} else if (derection == 3) {
					putInLocation(coordY, coordX + SCREEN_WIDTH / Map.COL, number, 270);
				}

			}

			private void putInLocation(int coordY, int coordX, int number,
					int angle) {
				
				_tank[number].setRotation(angle);
				_tank[number].setPosition(coordY, coordX);
				
			}

			@Override
			protected void drawBullet(int coordY, int coordX, int numberOfBullet) {
				
				if (_bullet[numberOfBullet] == null) {
					_bullet[numberOfBullet] = new Bullet();
					BattleCityStage.this.addActor(_bullet[numberOfBullet]);
				}
				
				_bullet[numberOfBullet].setPosition(coordY, coordX);
			}
			
			@Override
			protected void drawMovingControl(int movCntrlX, int movCntrlY,	int state_cntrl) {
				
				if (_movingControl == null) {
					_movingControl = new MovingControl(0);
					BattleCityStage.this.addActor(_movingControl);
				}
				
				_movingControl._texture = MovingControl.textures[state_cntrl];
				_movingControl.setPosition(movCntrlX, movCntrlY);
				_movingControl.toFront();
			}
			
			@Override
			protected void drawButtonFire(int butFirelX, int butFirelY,	int state_fire) {
				
				if (_button_fire == null) {
					_button_fire = new ButtonFire(0);
					BattleCityStage.this.addActor(_button_fire);
				}
				
				_button_fire._texture = ButtonFire.textures[state_fire];
				_button_fire.setPosition(butFirelX, butFirelY);
				_button_fire.toFront();
			}
			

		};

		controller.setView(view);
		controller.setModel(model);

		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				timerForBonus += 1;		
				
				if (timerForBonus == 1000) {
					controller.dropBonus();
					timerForBonus = 0;
				}
				
				if (controller.oneStep()) {
					BattleCityGame.boom.stop();
					BattleCityGame.boom.play();
				}
			}
		}, 0.01f, 0.013f);

		Gdx.input.setInputProcessor(this);

		addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				int cntrl_X = View.MOVING_CONTROL_X;
				int cntrl_Y = View.MOVING_CONTROL_Y;
				int bFire_X = View.BUTTON_FIRE_X;
				int bFire_Y = View.BUTTON_FIRE_Y;
				
				if (pointInArea(x, y, bFire_X, bFire_Y, ButtonFire.WIDTH_BUTTON, 
						 ButtonFire.HEIGHT_BUTTON)) {
				
					model._logic._state.setState_butFire(1);
					if (controller.fire()) {
						BattleCityGame.shot.stop();
						BattleCityGame.shot.play();
					}
					
				}else if (pointInArea(x, y, cntrl_X + MovingControl.WIDTH_CONTROL/3, 
						cntrl_Y + MovingControl.HEIGHT_CONTROL*2/3, MovingControl.WIDTH_CONTROL*2/3, 
						MovingControl.HEIGHT_CONTROL)) {
					
					model._logic._state.setState_cntrl(1);
					controller.up = true;
			
				}else if (pointInArea(x, y, cntrl_X + MovingControl.WIDTH_CONTROL/3, 
						cntrl_Y, MovingControl.WIDTH_CONTROL*2/3, 
						MovingControl.HEIGHT_CONTROL/3)) {
				
					model._logic._state.setState_cntrl(2);
					controller.down = true;
			
				}else if (pointInArea(x, y, cntrl_X, 
						cntrl_Y + MovingControl.HEIGHT_CONTROL/3, MovingControl.WIDTH_CONTROL/3, 
						MovingControl.HEIGHT_CONTROL*2/3)) {
					
					model._logic._state.setState_cntrl(3);
					controller.left = true;
				
				}else if (pointInArea(x, y, cntrl_X + MovingControl.WIDTH_CONTROL*2/3, 
						MovingControl.HEIGHT_CONTROL/3, cntrl_X + MovingControl.WIDTH_CONTROL, 
						MovingControl.HEIGHT_CONTROL*2/3)) {
					
					model._logic._state.setState_cntrl(4);
					controller.right = true;
					
				}
				
				return true;
			}
			
			private boolean pointInArea(float x, float y, double coordButPlayX, double coordButPlayY,
					int buttLenght, int buttHeight) {
				
				return x > coordButPlayX && x < coordButPlayX + buttLenght 
					&& y > coordButPlayY && y < coordButPlayY + buttHeight;
					
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				
				touchUp(event, x, y, pointer, 0);
				touchDown(event, x, y, pointer, 0);
				
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				resetControllers(model, controller);
				
			}

			private void resetControllers(final Model model, final Controller controller) {
				
				model._logic._state.setState_cntrl(0);
				model._logic._state.setState_butFire(0);
				controller.up = false;
				controller.down = false;
				controller.left = false;
				controller.right = false;
				
			}
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {

				case Input.Keys.LEFT:
					controller.left = true;
					break;

				case Input.Keys.RIGHT:
					controller.right = true;
					break;

				case Input.Keys.UP:
					controller.up = true;
					break;

				case Input.Keys.DOWN:
					controller.down = true;
					break;

				case Input.Keys.SPACE:
					if (controller.fire()) {
						BattleCityGame.shot.stop();
						BattleCityGame.shot.play();
					}
					break;
					
				}

				return true;
			}

			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				switch (keycode) {

				case Input.Keys.LEFT:
					controller.left = false;
					break;

				case Input.Keys.RIGHT:
					controller.right = false;
					break;

				case Input.Keys.UP:
					controller.up = false;
					break;

				case Input.Keys.DOWN:
					controller.down = false;
					break;

				}

				return true;
			}

		});

	}

}
