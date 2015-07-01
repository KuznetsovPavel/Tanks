package BattleCity_independent_code;

import java.util.ArrayList;
import java.util.List;

public class State {

	static final int NUMB_OF_TANK_BOT = 3;
	static final int SIZE_BOX_Y = View.SCREEN_HEIGHT / Map.ROW;
	static final int SIZE_BOX_X = View.SCREEN_WIDTH / Map.COL;

	private int _level = 1;
	private Tank _tank;
	Map _map;
	List<Tank> _botTanks = new ArrayList<Tank>();
	private boolean newMap = false;

	int bonus, _bonus_coordX = -10, _bonus_coordY = -10;
	Tank tank_with_bonus;
	public boolean bonusIsTake = true;

	boolean _gameOver = false;
	private int state_cntrl;
	private int state_butFire;
	
	int coordX_expl = 0;
	int coordY_expl = 0;
	
	protected void addTank(Tank tank) {
		_botTanks.add(tank);
	}

	protected void addRemove(Tank tank) {
		_botTanks.remove(tank);
	}

	public void setPlayerTank() {
		set_tank(new Tank());
		get_tank()._bullet = new Bullet(get_tank().equals(tank_with_bonus) && bonus == 2);
		get_tank().setTank(Map.COL / 2 * SIZE_BOX_X, 2 * SIZE_BOX_Y);
	}

	public void setBotTank() {
		for (int i = 0; i < NUMB_OF_TANK_BOT; i++) {
			Tank tank = new Tank();
			addTank(tank);
			tank._bullet = new Bullet(tank.equals(tank_with_bonus) && bonus == 2);
			tank.setTank(SIZE_BOX_X * Map.COL / 4 + i * Map.COL * SIZE_BOX_X / 4, (Map.ROW - 2) * SIZE_BOX_Y);
		}
	}

	protected void removeTank(Tank tank) {
		_botTanks.remove(tank);
	}

	public void setMap(Map map) {
		_map = map;
	}

	@Override
	protected State clone() throws CloneNotSupportedException {
		return this;
	}

	public int get_level() {
		return _level;
	}

	public void set_level(int _level) {
		this._level = _level;
	}

	public Tank get_tank() {
		return _tank;
	}

	public void set_tank(Tank _tank) {
		this._tank = _tank;
	}

	public boolean isNewMap() {
		return newMap;
	}

	public void setIsNewMap(boolean newMap) {
		this.newMap = newMap;
	}

	public int getState_butFire() {
		return state_butFire;
	}

	public void setState_butFire(int state_butFire) {
		this.state_butFire = state_butFire;
	}

	public int getState_cntrl() {
		return state_cntrl;
	}

	public void setState_cntrl(int state_cntrl) {
		this.state_cntrl = state_cntrl;
	}

}
