package BattleCity_independent_code;

public class Controller implements ModelListener {

	private View _view;
	private Model _model;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	@Override
	public void onChange(State state) {
		_view.draw(state);
	}

	public void setView(final View view) {
		_view = view;
	}

	public void setModel(final Model model) {
		_model = model;
	}

	public boolean oneStep() {
		if (_model.oneStep()) {
			if (_model.get_logic().getState().get_tank().isCrash() == false) {
				if (isUp()) {
					_model.move(_model.get_logic().getState().get_tank().get_speed(), 0);
				} else if (isDown()) {
					_model.move(-_model.get_logic().getState().get_tank().get_speed(), 0);
				} else if (isLeft()) {
					_model.move(0, -_model.get_logic().getState().get_tank().get_speed());
				} else if (isRight()) {
					_model.move(0, _model.get_logic().getState().get_tank().get_speed());
				}
			}
			if (_model.get_logic().getState().get_tank().isKill() == true) {
				_model.get_logic().getState().get_tank().setKill(false);
				return true;
			}
		}
		return false;
	}

	public boolean fire() {
		if (_model.get_logic().getState().get_tank().isCrash() == false) {
			if (_model.fire()) {
				return true;
			}
		}
		return false;
	}

	public void dropBonus() {
		_model.dropBonus();
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
