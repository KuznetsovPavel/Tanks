package BattleCity_independent_code;

import java.util.ArrayList;
import java.util.List;

public class State {

	private static final int  NUMB_OF_TANK_BOT = 5;
	private static final int SIZE_BOX_Y = View.getScreenHeight() / Map.getRow();
	private static final int SIZE_BOX_X = View.getScreenWidth() / Map.getCol();

	private int _level = 1;
	private Tank _tank;
	private Map _map;
	private List<Tank> _botTanks = new ArrayList<Tank>();
	private List<Tank> _myBotTanks = new ArrayList<Tank>();
	private boolean newMap = false;

	private int bonus, _bonus_coordX, _bonus_coordY;
	private boolean bonusIsDroped = false;
	private Tank tank_with_bonus;
	private boolean bonusIsTake = true;

	private boolean _gameOver = false;
	private int state_cntrl;
	private int state_butFire;
	
	private int coordX_expl = 0;
	private int coordY_expl = 0;
	private boolean isPicture = false;
	
	protected void addTank(Tank tank) {
		get_botTanks().add(tank);
	}

	protected void addMyBotTank(Tank tank) {
		get_MyBotTanks().add(tank);
	}


	protected void addRemove(Tank tank) {
		get_botTanks().remove(tank);
	}

	public void setPlayerTank() {
		set_tank(new Tank());
		get_tank().set_bullet(new Bullet(get_tank().equals(getTank_with_bonus()) && getBonus() == 2));
		get_tank().setTank(Map.getCol() / 2 * SIZE_BOX_X,  SIZE_BOX_Y);
	}

	public void setBotTank() {
		for (int i = 0; i < getNumbOfTankBot(); i++) {
			Tank tank = new Tank();
			addTank(tank);
			tank.set_bullet(new Bullet(tank.equals(getTank_with_bonus()) && getBonus() == 2));
			tank.setTank(SIZE_BOX_X * Map.getCol() / 6 + i * Map.getCol() * SIZE_BOX_X / 6, (Map.getRow() - 2) * SIZE_BOX_Y);
		}
	}

	public void setMyBotTank() {
		for (int i = 0; i < 3; i++) {
			Tank tank = new Tank();
			addMyBotTank(tank);
			tank.set_bullet(new Bullet(tank.equals(getTank_with_bonus()) && getBonus() == 2));
			tank.setTank(SIZE_BOX_X * Map.getCol() / 5 + i * Map.getCol() * SIZE_BOX_X / 5, 2 * SIZE_BOX_Y);
		}
	}

	protected void removeTank(Tank tank) {
		get_botTanks().remove(tank);
	}

	public void setMap(Map map) {
		set_map(map);
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
		isPicture = true;
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

	public boolean isBonusIsDroped() {
		return bonusIsDroped;
	}

	public void setBonusIsDroped(boolean bonusIsDroped) {
		this.bonusIsDroped = bonusIsDroped;
	}

	public boolean isBonusIsTake() {
		return bonusIsTake;
	}

	public void setBonusIsTake(boolean bonusIsTake) {
		this.bonusIsTake = bonusIsTake;
	}

	public int getCoordY_expl() {
		return coordY_expl;
	}

	public void setCoordY_expl(int coordY_expl) {
		this.coordY_expl = coordY_expl;
	}

	public int getCoordX_expl() {
		return coordX_expl;
	}

	public void setCoordX_expl(int coordX_expl) {
		this.coordX_expl = coordX_expl;
	}

	public boolean is_gameOver() {
		return _gameOver;
	}

	public void set_gameOver(boolean _gameOver) {
		this._gameOver = _gameOver;
	}

	public int get_bonus_coordX() {
		return _bonus_coordX;
	}

	public void set_bonus_coordX(int _bonus_coordX) {
		this._bonus_coordX = _bonus_coordX;
	}

	public int get_bonus_coordY() {
		return _bonus_coordY;
	}

	public int set_bonus_coordY(int _bonus_coordY) {
		this._bonus_coordY = _bonus_coordY;
		return _bonus_coordY;
	}

	public Map get_map() {
		return _map;
	}

	public void set_map(Map _map) {
		this._map = _map;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public List<Tank> get_botTanks() {
		return _botTanks;
	}

	public List<Tank> get_MyBotTanks() {
		return _myBotTanks;
	}

	public void set_botTanks(List<Tank> _botTanks) {
		this._botTanks = _botTanks;
	}

	public static int getNumbOfTankBot() {
		return NUMB_OF_TANK_BOT;
	}

	public Tank getTank_with_bonus() {
		return tank_with_bonus;
	}

	public void setTank_with_bonus(Tank tank_with_bonus) {
		this.tank_with_bonus = tank_with_bonus;
	}

	public boolean isPicture() {
		return isPicture;
	}

	public void setPicture(boolean picture) {
		isPicture = picture;
	}
}
