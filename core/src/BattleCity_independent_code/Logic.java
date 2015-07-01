package BattleCity_independent_code;

import java.util.List;
import java.util.Random;

import BattleCity_independent_code.State;

public class Logic {
	
	private static final int VALUE_BLOCK_GROUND = 0;
	private static final int VALUE_BLOCK_BRICK = 2;
	private static final int VALUE_BLOCK_STONE = 3;
	private static final int VALUE_BLOCK_WITH_HALF_ON_UP = 5;
	private static final int VALUE_BLOCK_WITH_HALF_ON_DOWN = 6;
	private static final int VALUE_BLOCK_WITH_HALF_ON_LEFT = 7;
	private static final int VALUE_BLOCK_WITH_HALF_ON_RIGHT = 8;
	
	private static final int BOX_SIZE_X = View.SCREEN_WIDTH / Map.COL;
	private static final int BOX_SIZE_Y = View.SCREEN_HEIGHT / Map.ROW;
	private static final int TANK_SIZE_X = View.SCREEN_WIDTH / Map.COL;
	private static final int TANK_SIZE_Y = View.SCREEN_HEIGHT / Map.ROW;

	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;

	public State _state;
	Random random = new Random();

	public Logic(final State state) {
		_state = state;
	}

	public State getState() {
		try {
			return _state.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean oneStep() {

		if (_state.get_tank().damages == 10) {
			_state._gameOver = true;
			return true;
		}

		takeBonus();
		
		List<Tank> tanks = _state._botTanks;
		oneStepForBotTanks(tanks);
		oneStepForPlayerTank();
		
		boolean allDestroy = true;
		
		for (Tank tank : tanks) {
			if (tank.isCrash == false) {
				allDestroy = false;
			}
		}
		
		initNewMap(allDestroy);
		return true;
	}

	private void initNewMap(boolean allDestroy) {
		if (allDestroy) {
			for (int tank = 0; tank < State.NUMB_OF_TANK_BOT; tank++) {
				_state.removeTank(_state._botTanks.get(0));
			}
			
			_state.setBotTank();
			Map map = Map.randomMap();
			_state.bonusIsTake = true;
			_state.setIsNewMap(true);
			_state.setMap(map);
			_state.get_tank().setTank(Map.COL / 2 * BOX_SIZE_X, 2 * BOX_SIZE_Y);
			_state.set_level(_state.get_level() + 1);

		}
	}

	private void oneStepForPlayerTank() {
		if (_state.get_tank()._bullet.isLive) {
			
			Tank hunter = _state.get_tank();
			Bullet bullet = hunter._bullet;
			List<Tank> victims = _state._botTanks;
			fire();
			
			for (Tank victim : victims) {	
				if (hit(hunter, bullet, victim)) {
					_state.get_tank().isKill = true;
					break;	
				}
			}
			
		}
	}

	private void oneStepForBotTanks(List<Tank> tanks) {
		
		for (Tank hunter : tanks) {
			
			if (hunter.isCrash == false) {
				moveBotTank(hunter);
				hunter.countOfStep += 1;
				
				if (hunter.countOfStep == 50) {
					hunter.countOfStep = 0;
				}
				
				fireBot(hunter);
				Bullet bullet = hunter._bullet;
				Tank victim = _state.get_tank();
				hit(hunter, bullet, victim);
				
				for (Tank tank : tanks) {
					if (hunter.equals(tank)) {
						continue;
					}
					
					victim = tank;
					hit(hunter, bullet, victim);
					
				}
			}
		}
	}

	public boolean move(Tank tank, int vertical_speed, int horizontal_speed) {

		if (vertical_speed == tank._speed) {
			tank.derection = UP;
		} else if (vertical_speed == -tank._speed) {
			tank.derection = DOWN;
		} else if (horizontal_speed == tank._speed) {
			tank.derection = RIGHT;
		} else if (horizontal_speed == -tank._speed) {
			tank.derection = LEFT;
		}

		tank.coordY += vertical_speed;
		tank.coordX += horizontal_speed;

		if (!isTankFitField(tank) || isTankOnOtherTank(tank)) {
			tank.coordY -= vertical_speed;
			tank.coordX -= horizontal_speed;

			return false;
		}

		return true;

	}

	private boolean isTankOnOtherTank(Tank tank) {
		
		boolean cross = false;
		
		for (Tank otherTank : _state._botTanks) {
			if (!tank.equals(otherTank)) {
				if (cross = checkCrossing(tank.coordX, tank.coordY, otherTank.coordX, otherTank.coordY)) {
					return cross;
				}
			}
		}
		
		if (!tank.equals(_state.get_tank())) {
			if (cross = checkCrossing(tank.coordX, tank.coordY, _state.get_tank().coordX, _state.get_tank().coordY)) {
				return cross;
			}
		}
		return cross;
	}

	private boolean checkCrossing(int fObjct_X, int fObjct_Y, int sObjct_X, int sObjct_Y) {
		
		if (sObjct_X <= fObjct_X
				&& sObjct_X + TANK_SIZE_X >= fObjct_X
				&& sObjct_Y >= fObjct_Y
				&& sObjct_Y <= fObjct_Y + TANK_SIZE_Y
				
				|| sObjct_X <= fObjct_X + TANK_SIZE_X
				&& sObjct_X + TANK_SIZE_X >= fObjct_X + TANK_SIZE_X
				&& sObjct_Y >= fObjct_Y
				&& sObjct_Y <= fObjct_Y + TANK_SIZE_Y
				
				|| sObjct_X <= fObjct_X + TANK_SIZE_X
				&& sObjct_X + TANK_SIZE_X >= fObjct_X + TANK_SIZE_X
				&& sObjct_Y - TANK_SIZE_Y <= fObjct_Y - TANK_SIZE_Y
				&& sObjct_Y >= fObjct_Y - TANK_SIZE_Y
				
				|| sObjct_X <= fObjct_X
				&& sObjct_X + TANK_SIZE_X >= fObjct_X
				&& sObjct_Y - TANK_SIZE_Y <= fObjct_Y - TANK_SIZE_Y
				&& sObjct_Y >= fObjct_Y - TANK_SIZE_Y) {

			return true;
		}
		return false;
	}

	private boolean isTankFitField(Tank tank) {
		int leftUpAngle_Y = (tank.coordY + 1) / (TANK_SIZE_Y);
		int leftUpAngle_X = (tank.coordX + 1) / (TANK_SIZE_X);

		int rightUpAngle_Y = (tank.coordY + 1) / (TANK_SIZE_Y);
		int rightUpAngle_X = (tank.coordX + TANK_SIZE_X - 1) / (TANK_SIZE_X);

		int rightDownAngle_Y = (tank.coordY + TANK_SIZE_Y - 1) / (TANK_SIZE_Y);
		int rightDownAngle_X = (tank.coordX + TANK_SIZE_X - 1) / (TANK_SIZE_X);

		int leftDownAngle_Y = (tank.coordY + TANK_SIZE_Y - 1) / (TANK_SIZE_Y);
		int leftDownAngle_X = (tank.coordX + 1) / (TANK_SIZE_X);

		if (objectIsNotInMap(tank.coordY, tank.coordX)
			|| !locationIsFree(leftUpAngle_Y, leftUpAngle_X)
			|| objectIsNotInMap(tank.coordY, tank.coordX + TANK_SIZE_X)
			|| !locationIsFree(rightUpAngle_Y, rightUpAngle_X)
			|| objectIsNotInMap(tank.coordY + TANK_SIZE_Y, tank.coordX + TANK_SIZE_X)
			|| !locationIsFree(rightDownAngle_Y, rightDownAngle_X)
			|| objectIsNotInMap(tank.coordY + TANK_SIZE_Y, tank.coordX)
			|| !locationIsFree(leftDownAngle_Y, leftDownAngle_X)) {

			return false;
		}
		return true;
	}

	protected boolean objectIsNotInMap(int coordY, int coordX) {
		return coordY < 0 || coordX < 0 || coordY >= _state._map.getHeight()*TANK_SIZE_Y
				|| coordX >= _state._map.getWidth()*TANK_SIZE_X;
	}

	protected boolean locationIsFree(int row, int col) {
		return (_state._map._data[row][col] == 0 || _state._map._data[row][col] == 1);
	}

	protected void moveBotTank(Tank tank) {
		
		int victim_coordX = _state.get_tank().coordX;
		int victim_coordY = _state.get_tank().coordY;

		int differ_coordX = victim_coordX - tank.coordX;
		int differ_coordY = victim_coordY - tank.coordY;

		if (tank.countOfStep == 0) {
			if (random.nextBoolean()) {

				if (differ_coordY > 0) {
					tank.derection = UP;
					return;
				} else {
					tank.derection = DOWN;
					return;
				}

			} else {

				if (differ_coordX > 0) {
					tank.derection = RIGHT;
					return;
				} else {
					tank.derection = LEFT;
					return;
				}
			}
		}

		int direction = tank.derection;

		switch (direction) {
		case UP:
			move(tank, tank._speed / 2, 0);
			break;

		case DOWN:
			move(tank, -tank._speed / 2, 0);
			break;

		case LEFT:
			move(tank, 0, -tank._speed / 2);
			break;

		case RIGHT:
			move(tank, 0, tank._speed / 2);
			break;

		default:
			break;
		}
	}

	protected void fireBot(Tank tank) {
		if (isFireAlready(tank)) {
			moveBullet(tank);
			return;
		}

//		if (tankHaveTarget(tank)) {
			tank._bullet = new Bullet(tank.equals(_state.tank_with_bonus) && _state.bonus == 3);
			tank._bullet.isLive = true;
			tank._bullet.coordY = tank.coordY + TANK_SIZE_Y / 2;
			tank._bullet.coordX = tank.coordX + TANK_SIZE_X / 2;
			tank._bullet.targetDerection = tank.derection;
//		}
	}
	
	

/*
 	private boolean tankHaveTarget(Tank tank) {
		
		int victim_coordX = _state.get_tank().coordX;
		int victim_coordY = _state.get_tank().coordY;

		int differ_coordX = victim_coordX - tank.coordX;
		int differ_coordY = victim_coordY - tank.coordY;

		if (differ_coordX / BOX_SIZE_X == 0 || differ_coordY / BOX_SIZE_Y == 0) {
			return true;
		}
		return false;
	}
*/
	
	
	protected boolean fire() {
		Tank hunter = _state.get_tank();
		
		if (isFireAlready(hunter)) {
			moveBullet(hunter);
			return false;
		}
		
		hunter._bullet = new Bullet(hunter.equals(_state.tank_with_bonus) && _state.bonus == 2);
		hunter._bullet.isLive = true;
		hunter._bullet.coordY = hunter.coordY + TANK_SIZE_Y / 2;
		hunter._bullet.coordX = hunter.coordX + TANK_SIZE_X / 2;
		hunter._bullet.targetDerection = hunter.derection;
		return true;
		
	}

	protected void moveBullet(Tank tank) {
		switch (tank._bullet.targetDerection) {
		case UP:
			tank._bullet.coordY +=  tank._bullet._speed;
			break;

		case DOWN:
			tank._bullet.coordY -=  tank._bullet._speed;
			break;

		case LEFT:
			tank._bullet.coordX -=  tank._bullet._speed;
			break;

		case RIGHT:
			tank._bullet.coordX += tank._bullet._speed;
			break;

		default:
			break;
		}
	}

	private boolean isFireAlready(Tank tank) {
		if (tank._bullet.isLive == true) {
			return true;
		}
		return false;
	}

	protected boolean hit(Tank hunter, Bullet bullet, Tank victim) {
		
		int tankCoordY = victim.coordY;
		int tankCoordX = victim.coordX;
		int bulletCoordY = (hunter._bullet.coordY * 2 + 5) / 2;
		int bulletCoordX = (hunter._bullet.coordX * 2 + 5) / 2;

		if (tankCoordY <= bulletCoordY
				&& tankCoordY + TANK_SIZE_Y >= bulletCoordY
				&& tankCoordX <= bulletCoordX
				&& tankCoordX + TANK_SIZE_X >= bulletCoordX) {

			return hitOfTank(hunter, bullet, victim);
		
		} 
		
		if (objectIsNotInMap(bulletCoordY, bulletCoordX)
				|| _state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_STONE ) {
		
			return missShooting(hunter, bullet);
		
		}else if(_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_BRICK ){
		
			int bullet_direction = hunter._bullet.targetDerection;
			return hitInFullBrickBlock(hunter, bullet, bulletCoordY, bulletCoordX, bullet_direction);
		
		}
		
		return hitInHalfBrickBlock(hunter, bullet, bulletCoordY, bulletCoordX);
	}

	private boolean missShooting(Tank hunter, Bullet bullet) {
		
		hunter._bullet.coordX = (hunter.coordX * 2 + TANK_SIZE_X) / 2;
		hunter._bullet.coordY = (hunter.coordY * 2 + TANK_SIZE_Y) / 2;
		bullet.isLive = false;
		return false;
		
	}

	private boolean hitOfTank(Tank hunter, Bullet bullet, Tank victim) {
		
		if (hunter.equals(_state.get_tank())) {
			victim.damages += 1;
		}
		
		if (victim.equals(_state.get_tank())) {
			victim.damages += 1;
			
			if (victim.damages == Tank.ARMOR_PLAYER_TANK) {
				victim.isCrash = true;
			}
			
		}

		for (Tank tankBot : _state._botTanks) {
			if (victim.damages == Tank.ARMOM_BOT_TANK) {
				if (victim.equals(tankBot)) {
					
					victim._bullet.coordX = (victim.coordX * 2 + TANK_SIZE_X) / 2;
					victim._bullet.coordY = (victim.coordY * 2 + TANK_SIZE_Y) / 2;
					victim.isCrash = true;
					hunter.setEnemyKilled(hunter.getEnemyKilled() + 1);
					_state.coordX_expl = victim.coordX;
					_state.coordY_expl = victim.coordY;
					bullet.isLive = false;
					return true;
					
				}
			}
		}

		bullet.isLive = false;
		return false;
	}

	private boolean hitInHalfBrickBlock(Tank hunter, Bullet bullet,
			int bulletCoordY, int bulletCoordX) {
		
		if (_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_UP
				|| _state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_DOWN
				|| _state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_RIGHT
				|| _state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_LEFT) {
			
			_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_GROUND;
			hunter._bullet.coordX = (hunter.coordX * 2 + TANK_SIZE_X) / 2;
			hunter._bullet.coordY = (hunter.coordY * 2 + TANK_SIZE_Y) / 2;
			bullet.isLive = false;
			return true;
			
		}
		return false;
	}

	private boolean hitInFullBrickBlock(Tank hunter, Bullet bullet,
			int bulletCoordY, int bulletCoordX, int bullet_direction) {
		
		if (bullet_direction == 0) {
			_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_UP;
		}else if (bullet_direction == 1) {
			_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_DOWN;
		}else if (bullet_direction == 2) {
			_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_RIGHT;
		}else if (bullet_direction == 3) {
			_state._map._data[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_LEFT;
		}
		
		hunter._bullet.coordX = (hunter.coordX * 2 + TANK_SIZE_X) / 2;
		hunter._bullet.coordY = (hunter.coordY * 2 + TANK_SIZE_Y) / 2;
		bullet.isLive = false;
		return true;
		
	}


	public boolean dropBonus() {
		int bonus_tmpX;
		int bonus_tmpY;
		
		if (_state.tank_with_bonus != null) {
			_state.tank_with_bonus._speed = Tank.SPEED;  
			_state.tank_with_bonus._bullet._speed = Bullet.SPEED;
			_state.tank_with_bonus = null;
		}
		
		_state.bonusIsTake = false;
		while (true) {
			
			_state.bonus = random.nextInt(3);
			bonus_tmpX = random.nextInt(24);
			bonus_tmpY = random.nextInt(14);
			
			if (_state._map._data[bonus_tmpY][bonus_tmpX] == 0) {
				_state._bonus_coordX = bonus_tmpX;
				_state._bonus_coordY = bonus_tmpY;
				break;
			}
			
		}
		return true;
	}


	public void takeBonus() {
		for (Tank tank : _state._botTanks) {
			if (checkCrossing(tank.coordX, tank.coordY, _state._bonus_coordX*BOX_SIZE_X, _state._bonus_coordY*BOX_SIZE_Y)) {
				
				whichBonusIsTake(tank);
				_state.tank_with_bonus = tank;

				return;
			}
		}
		if (checkCrossing(_state.get_tank().coordX, _state.get_tank().coordY,
				_state._bonus_coordX*BOX_SIZE_X, _state._bonus_coordY*BOX_SIZE_Y)) {

			whichBonusIsTake(_state.get_tank());
			_state.tank_with_bonus = _state.get_tank();
			
		}
	}

	private void whichBonusIsTake(Tank tank) {
		
		if (_state.bonus == 0) {
			tank.damages -= 1;
		}else if (_state.bonus == 1) {
			tank._speed += 2;
		}else if (_state.bonus == 2) {
			tank._bullet._speed += 6;
		}
		
		_state._bonus_coordX = -10;
		_state._bonus_coordY = -10;
		_state.bonusIsTake = true;
		
	}

}
