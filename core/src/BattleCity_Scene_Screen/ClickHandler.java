package BattleCity_Scene_Screen;

import BattleCity_Actors.ButtonFire;
import BattleCity_Actors.MovingControl;
import BattleCity_independent_code.Controller;
import BattleCity_independent_code.Model;
import BattleCity_independent_code.View;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ClickHandler extends InputListener {
	private Model _model;
	private Controller _controller;
	private BattleCityGame _game;
	
	public ClickHandler(Model model, Controller controller, BattleCityGame game) {
		_model = model;
		_controller = controller;
		_game = game;
	}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			
			int cntrl_X = View.getMovingControlX();
			int cntrl_Y = View.getMovingControlY();
			int bFire_X = View.getButtonFireX();
			int bFire_Y = View.getButtonFireY();
			
			if (pointInArea(x, y, bFire_X, bFire_Y, ButtonFire.getWidthButton(), 
					 ButtonFire.getHeightButton())) {
			
				_model.get_logic().get_state().setState_butFire(1);
				if (_controller.fire()) {
					_game.getShot().stop();
					_game.getShot().play();
				}
				
			}else if (pointInArea(x, y, cntrl_X + MovingControl.getWidthControl()/3, 
					cntrl_Y + MovingControl.getHeightControl()*2/3, MovingControl.getWidthControl()*2/3, 
					MovingControl.getHeightControl())) {
				
				_model.get_logic().get_state().setState_cntrl(1);
				_controller.setUp(true);
		
			}else if (pointInArea(x, y, cntrl_X + MovingControl.getWidthControl()/3, 
					cntrl_Y, MovingControl.getWidthControl()*2/3, 
					MovingControl.getHeightControl()/3)) {
			
				_model.get_logic().get_state().setState_cntrl(2);
				_controller.setDown(true);
		
			}else if (pointInArea(x, y, cntrl_X, 
					cntrl_Y + MovingControl.getHeightControl()/3, MovingControl.getWidthControl()/3, 
					MovingControl.getHeightControl()*2/3)) {
				
				_model.get_logic().get_state().setState_cntrl(3);
				_controller.setLeft(true);
			
			}else if (pointInArea(x, y, cntrl_X + MovingControl.getWidthControl()*2/3, 
					MovingControl.getHeightControl()/3, cntrl_X + MovingControl.getWidthControl(), 
					MovingControl.getHeightControl()*2/3)) {
				
				_model.get_logic().get_state().setState_cntrl(4);
				_controller.setRight(true);
				
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
			
			resetControllers(_model, _controller);
			
		}

		private void resetControllers(final Model model, final Controller controller) {
			
			model.get_logic().get_state().setState_cntrl(0);
			model.get_logic().get_state().setState_butFire(0);
			controller.setUp(false);
			controller.setDown(false);
			controller.setLeft(false);
			controller.setRight(false);
			
		}
		
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			switch (keycode) {

			case Input.Keys.LEFT:
				_controller.setLeft(true);
				break;

			case Input.Keys.RIGHT:
				_controller.setRight(true);
				break;

			case Input.Keys.UP:
				_controller.setUp(true);
				break;

			case Input.Keys.DOWN:
				_controller.setDown(true);
				break;

			case Input.Keys.SPACE:
				if (_controller.fire() && !_game.music_mute) {
					_game.getShot().play();
				}
				break;
				
			}

			return true;
		}

		@Override
		public boolean keyUp(InputEvent event, int keycode) {
			switch (keycode) {

			case Input.Keys.LEFT:
				_controller.setLeft(false);
				break;

			case Input.Keys.RIGHT:
				_controller.setRight(false);
				break;

			case Input.Keys.UP:
				_controller.setUp(false);
				break;

			case Input.Keys.DOWN:
				_controller.setDown(false);
				break;

			}

			return true;
		}

	}
	

