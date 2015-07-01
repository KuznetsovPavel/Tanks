package BattleCity_independent_code;

public class Controller implements ModelListener {

	private View _view;
	private Model _model;
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;

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
			if (_model._logic.getState().get_tank().isCrash == false) {
				if (up) {
					_model.move(_model._logic.getState().get_tank()._speed, 0);
				} else if (down) {
					_model.move(-_model._logic.getState().get_tank()._speed, 0);
				} else if (left) {
					_model.move(0, -_model._logic.getState().get_tank()._speed);
				} else if (right) {
					_model.move(0, _model._logic.getState().get_tank()._speed);
				}
			}
			if (_model._logic.getState().get_tank().isKill == true) {
				_model._logic.getState().get_tank().isKill = false;
				return true;
			}
		}
		return false;
	}

	public boolean fire() {
		if (_model._logic.getState().get_tank().isCrash == false) {
			if (_model.fire()) {
				return true;
			}
		}
		return false;
	}

	public void dropBonus() {
		_model.dropBonus();
	}

}
