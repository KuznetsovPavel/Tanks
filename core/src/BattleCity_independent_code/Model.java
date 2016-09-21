package BattleCity_independent_code;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private static final int COLUMNS = 25;
	private static final int ROWS = 15;
	private Logic _logic;
	
	public Model(boolean isArcade) {
		State state = new State();
		if (isArcade)
			set_logic(new Logic(state));
		else
			set_logic(new LogicGameForLive(state));
		Map map = Map.randomMap();		
		state.setMap(map);
		state.setBotTank();
		state.setMyBotTank();
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
			modelListener.onChange(get_logic().getState());
		}
	}

	public void move(int vertical, int horizontal) {
		if (get_logic().move(get_logic().getState().get_tank(), vertical, horizontal)) {
			fireChangedEvent();
		}
	}

	public boolean oneStep() {
		if (get_logic().oneStep()) {
			fireChangedEvent();
			return true;
		}
		return false;
	}

	public boolean fire() {
		if (get_logic().fire(_logic._state.get_tank())) {
			fireChangedEvent();
			return true;
		}
		return false;
	}

	public boolean dropBonus() {
		if(get_logic().dropBonus()){
			fireChangedEvent();
			return true;
		}
		return false;
	}

	public static int getColumns() {
		return COLUMNS;
	}

	public static int getRows() {
		return ROWS;
	}

	public Logic get_logic() {
		return _logic;
	}

	public void set_logic(Logic _logic) {
		this._logic = _logic;
	}
	
}
