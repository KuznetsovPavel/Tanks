package tanks;

import java.util.ArrayList;
import java.util.List;

public class Model {

	public static final int COLUMNS = 25;
	public static final int ROWS = 15;
	public Logic _logic;
	
	public Model() {
		State state = new State();
		_logic = new Logic(state);
		Map map = Map.randomMap();		
		state.setMap(map);
		state.setBotTank();
		state.setPlayerTank();
	}

	List<ModelListener> _listeners = new ArrayList<ModelListener>();
	
	public void addListener(final ModelListener listener){
		_listeners.add(listener);
	}
	
	public void removeListener(final ModelListener listener){
		_listeners.remove(listener);
	}
	
	void fireChangedEvent(){
		for (ModelListener modelListener : _listeners) {
			modelListener.onChange(_logic.getState());
		}
	}

	public void move(int vertical, int horizontal) {
		if (_logic.move(_logic.getState().get_tank(), vertical, horizontal)) {
			fireChangedEvent();
		}
	}

	public boolean oneStep() {
		if (_logic.oneStep()) {
			fireChangedEvent();
			return true;
		}
		return false;
	}

	public boolean fire() {
		if (_logic.fire()) {
			fireChangedEvent();
			return true;
		}
		return false;
	}

	public boolean dropBonus() {
		if(_logic.dropBonus()){
			fireChangedEvent();
			return true;
		}
		return false;
	}
	
}
