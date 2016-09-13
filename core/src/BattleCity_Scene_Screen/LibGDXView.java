package BattleCity_Scene_Screen;


import BattleCity_Actors.Bonus;
import BattleCity_Actors.Box;
import BattleCity_Actors.Bullet;
import BattleCity_Actors.ButtonFire;
import BattleCity_Actors.InfoPanel;
import BattleCity_Actors.MovingControl;
import BattleCity_Actors.Plane;
import BattleCity_Actors.Tank;
import BattleCity_independent_code.Map;
import BattleCity_independent_code.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class LibGDXView extends BattleCity_independent_code.View {

	private static final int BASIC_POSITION = 0;
	private static final int ANGLE_270 = 270;
	private static final int ANGLE_90 = 90;
	private static final int ANGLE_180 = 180;
	private static final int ANGLE_0 = 0;
	private static final int TANK_IS_CRASHED = 2;
	private static final int RIGHT = 3;
	private static final int LEFT = 2;
	private static final int DONW = 1;
	private static final int UP = 0;
	private static final int NUMB_OF_TANK_IN_LEVEL = 9;
	
	private BattleCityStage _stage;
	private Model _model;
	private BattleCityGame _game;
	private Box[][] _boxes = new Box[Model.getRows()][Model.getColumns()];
	private Tank[] _tank = new Tank[NUMB_OF_TANK_IN_LEVEL];
	private Bullet[] _bullet = new Bullet[NUMB_OF_TANK_IN_LEVEL];
	private InfoPanel _infoPanel;
	private Bonus _bonus;
	private Plane _plane;
	private MovingControl _movingControl;
	private ButtonFire _button_fire;
	private BitmapFont font = new BitmapFont();
	private LabelStyle infoStyle = new LabelStyle(font, Color.WHITE);
	private Label _info;
	
	public LibGDXView(BattleCityStage stage, Model model, BattleCityGame game) {
		_game = game;
		_stage = stage;
		_model = model;
	}
	
	@Override
	protected void drawBonus(int coordX, int coordY, boolean bonusFree, int bonusIndex) {
		
		
		if (bonusFree) {
			
			if (_bonus == null) {	
			_plane = new Plane();
			_bonus = new Bonus(bonusIndex);
			_plane.setPosition(-_plane.get_texture().getHeight(), -_plane.get_texture().getWidth());
			_bonus.setPosition(-_bonus.get_texture().getWidth(), -_bonus.get_texture().getHeight());
			_stage.addActor(_bonus);
			_stage.addActor(_plane);
			_game.getAircraft().play();
			_plane.action(coordX, coordY, 0);
			_bonus.action(coordX, coordY);
		}			
				
			}else {
				
				if (_model.get_logic().get_state().isBonusIsDroped()) {						
					_model.get_logic().get_state().setBonusIsDroped(false);
				}

				if (_bonus != null) {
					_bonus.remove();
					_bonus = null;
				}
			
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
		_stage.addActor(box);
		box.setPosition(col * getScreenWidth() / Map.getCol(), row * getScreenHeight() / Map.getRow());
		
	}

	@Override
	protected void drawInfoPanel(int coordX, int coordY,
			int pannel_size_X, int pannel_size_Y, String message, boolean gameOver) {
		
		if (gameOver) {
			_stage.setGameIsOver(true);
			_stage.setFinal_score(_model.get_logic().get_state().get_tank().getEnemyKilled());
			_stage.setFinal_level(_model.get_logic().get_state().get_level());
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
		_stage.addActor(_infoPanel);
		_stage.addActor(_info);
		_infoPanel.setPosition(coordX, coordY);
		_info.setPosition(coordX + getScreenWidth() / Map.getCol() / 4, coordY + getScreenHeight() / Map.getRow()/2);
		
	}

	@Override
	protected void drawTank(int textureIndex, int coordY, int coordX,
			int number, int derection, boolean newMap) {

		if (newMap) {
			_tank[number].setNumbTextExpl(0);
		}
		
		if (_tank[number] == null) {
			_tank[number] = new Tank(textureIndex);
			_stage.addActor(_tank[number]);
		}
		
		if (textureIndex == TANK_IS_CRASHED) {
			
			if (_tank[number].getNumbTextExpl() < Tank.getExplosion().length) {
				_tank[number].set_texture(Tank.getExplosion()[_tank[number].getNumbTextExpl()]);
				_tank[number].setNumbTextExpl(_tank[number].getNumbTextExpl() + 1);
			}else{
				_tank[number].set_texture(Tank.getTextures()[textureIndex]);
				derection = UP;
			}
			
		}else{
			_tank[number].set_texture(Tank.getTextures()[textureIndex]);
		}
		
		if (derection == UP) {
			putInLocation(coordY, coordX, number, ANGLE_0);
		} else if (derection == DONW) {
			putInLocation(coordY + getScreenHeight() / Map.getRow(), coordX + getScreenWidth() / Map.getCol(), number, ANGLE_180);
		} else if (derection == LEFT) {
			putInLocation(coordY + getScreenHeight() / Map.getRow(), coordX, number, ANGLE_90);
		} else if (derection == RIGHT) {
			putInLocation(coordY, coordX + getScreenWidth() / Map.getCol(), number, ANGLE_270);
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
			_stage.addActor(_bullet[numberOfBullet]);
		}
		
		_bullet[numberOfBullet].setPosition(coordY, coordX);
	}
	
	@Override
	protected void drawMovingControl(int movCntrlX, int movCntrlY,	int state_cntrl) {
		
		if (_movingControl == null) {
			_movingControl = new MovingControl(BASIC_POSITION);
			_stage.addActor(_movingControl);
		}
		
		_movingControl.set_texture(MovingControl.getTextures()[state_cntrl]);
		_movingControl.setPosition(movCntrlX, movCntrlY);
		_movingControl.toFront();
	}
	
	@Override
	protected void drawButtonFire(int butFirelX, int butFirelY,	int state_fire) {
		
		if (_button_fire == null) {
			_button_fire = new ButtonFire(BASIC_POSITION);
			_stage.addActor(_button_fire);
		}
		
		_button_fire.set_texture(ButtonFire.getTextures()[state_fire]);
		_button_fire.setPosition(butFirelX, butFirelY);
		_button_fire.toFront();
	}
	
	
}
