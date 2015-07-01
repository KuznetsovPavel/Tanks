package BattleCity_LibGDX;

import Actors.ButtonFire;
import Actors.MovingControl;
import BattleCity_independent_code.Controller;
import BattleCity_independent_code.Model;
import BattleCity_independent_code.View;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ClickHandler extends InputListener {
	Model _model;
	Controller _controller;
	
	public ClickHandler(Model model, Controller controller) {
		_model = model;
		_controller = controller;
	}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			
			int cntrl_X = View.MOVING_CONTROL_X;
			int cntrl_Y = View.MOVING_CONTROL_Y;
			int bFire_X = View.BUTTON_FIRE_X;
			int bFire_Y = View.BUTTON_FIRE_Y;
			
			if (pointInArea(x, y, bFire_X, bFire_Y, ButtonFire.WIDTH_BUTTON, 
					 ButtonFire.HEIGHT_BUTTON)) {
			
				_model._logic._state.setState_butFire(1);
				if (_controller.fire()) {
					BattleCityGame.getShot().stop();
					BattleCityGame.getShot().play();
				}
				
			}else if (pointInArea(x, y, cntrl_X + MovingControl.WIDTH_CONTROL/3, 
					cntrl_Y + MovingControl.HEIGHT_CONTROL*2/3, MovingControl.WIDTH_CONTROL*2/3, 
					MovingControl.HEIGHT_CONTROL)) {
				
				_model._logic._state.setState_cntrl(1);
				_controller.up = true;
		
			}else if (pointInArea(x, y, cntrl_X + MovingControl.WIDTH_CONTROL/3, 
					cntrl_Y, MovingControl.WIDTH_CONTROL*2/3, 
					MovingControl.HEIGHT_CONTROL/3)) {
			
				_model._logic._state.setState_cntrl(2);
				_controller.down = true;
		
			}else if (pointInArea(x, y, cntrl_X, 
					cntrl_Y + MovingControl.HEIGHT_CONTROL/3, MovingControl.WIDTH_CONTROL/3, 
					MovingControl.HEIGHT_CONTROL*2/3)) {
				
				_model._logic._state.setState_cntrl(3);
				_controller.left = true;
			
			}else if (pointInArea(x, y, cntrl_X + MovingControl.WIDTH_CONTROL*2/3, 
					MovingControl.HEIGHT_CONTROL/3, cntrl_X + MovingControl.WIDTH_CONTROL, 
					MovingControl.HEIGHT_CONTROL*2/3)) {
				
				_model._logic._state.setState_cntrl(4);
				_controller.right = true;
				
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
				_controller.left = true;
				break;

			case Input.Keys.RIGHT:
				_controller.right = true;
				break;

			case Input.Keys.UP:
				_controller.up = true;
				break;

			case Input.Keys.DOWN:
				_controller.down = true;
				break;

			case Input.Keys.SPACE:
				if (_controller.fire()) {
					BattleCityGame.getShot().stop();
					BattleCityGame.getShot().play();
				}
				break;
				
			}

			return true;
		}

		@Override
		public boolean keyUp(InputEvent event, int keycode) {
			switch (keycode) {

			case Input.Keys.LEFT:
				_controller.left = false;
				break;

			case Input.Keys.RIGHT:
				_controller.right = false;
				break;

			case Input.Keys.UP:
				_controller.up = false;
				break;

			case Input.Keys.DOWN:
				_controller.down = false;
				break;

			}

			return true;
		}

	}
	

