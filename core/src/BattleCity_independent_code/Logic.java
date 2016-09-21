package BattleCity_independent_code;

import java.util.List;
import java.util.Random;

public class Logic {
	
	protected static final int COORD_BONUS_IS_CHARGING = -10;
	protected static final int NUMB_OF_STEP_FOR_ONE_DERECTION = 40;
	protected static final int LEVEL_UP = 1;

	protected static final int SHOTING_SPEED = 2;
	protected static final int MOVING_SPEED = 1;
	protected static final int ARMOR = 0;
	protected static final int NUMB_OF_BONUS = 3;

	protected static final int VALUE_BLOCK_GROUND = 0;
	protected static final int VALUE_BLOCK_BRICK = 2;
	protected static final int VALUE_BLOCK_STONE = 3;
	protected static final int VALUE_BLOCK_WITH_HALF_ON_UP = 5;
	protected static final int VALUE_BLOCK_WITH_HALF_ON_DOWN = 6;
	protected static final int VALUE_BLOCK_WITH_HALF_ON_LEFT = 7;
	protected static final int VALUE_BLOCK_WITH_HALF_ON_RIGHT = 8;

	protected static final int BOX_SIZE_X = View.getScreenWidth() / Map.getCol();
	protected static final int BOX_SIZE_Y = View.getScreenHeight() / Map.getRow();
	protected static final int TANK_SIZE_X = View.getScreenWidth() / Map.getCol();
	protected static final int TANK_SIZE_Y = View.getScreenHeight() / Map.getRow();

	protected static final int UP = 0;
	protected static final int DOWN = 1;
	protected static final int LEFT = 2;
	protected static final int RIGHT = 3;

	protected State _state;
	protected Random random = new Random();

	public Logic(final State state) {
		set_state(state);
	}

	public State getState() {
		try {
			return get_state().clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected boolean oneStep() {

		if (get_state().get_tank().getDamages() == Tank.getArmorPlayerTank()) {
			get_state().set_gameOver(true);
			return true;
		}

		takeBonus();
		
		List<Tank> tanks = get_state().get_botTanks();
		List<Tank> myBots = get_state().get_MyBotTanks();
		oneStepForBotTanks(tanks);
		oneStepForMyBotTanks(myBots);
		oneStepForPlayerTank();
		
		boolean allDestroy = true;
		
		for (Tank tank : tanks) {
			if (tank.isCrash() == false) {
				allDestroy = false;
			}
		}
		
		initNewMap(allDestroy);
		return true;
	}

	protected void initNewMap(boolean allDestroy) {
		if (allDestroy) {

			get_state().get_botTanks().clear();
			get_state().get_MyBotTanks().clear();

			get_state().setBotTank();
			get_state().setMyBotTank();
			Map map = Map.randomMap();
			get_state().setBonusIsTake(true);
			get_state().setIsNewMap(true);
			get_state().set_bonus_coordX(get_state().set_bonus_coordY(COORD_BONUS_IS_CHARGING));
			get_state().setMap(map);
			get_state().get_tank().setTank(Map.getCol() / 2 * BOX_SIZE_X, 2 * BOX_SIZE_Y);
			get_state().set_level(get_state().get_level() + LEVEL_UP);

		}
	}

	protected void oneStepForPlayerTank() {
		if (get_state().get_tank().get_bullet().isLive()) {
			
			Tank hunter = get_state().get_tank();
			Bullet bullet = hunter.get_bullet();
			List<Tank> victims = get_state().get_botTanks();
			fire(hunter);
			
			for (Tank victim : victims) {	
				if (hit(hunter, bullet, victim)) {
					get_state().get_tank().setKill(true);
					break;	
				}
			}
			
		}
	}

	protected void oneStepForBotTanks(List<Tank> tanks) {
		
		for (Tank hunter : tanks) {
			
			if (!hunter.isCrash()) {
				moveBotTank(hunter);
				hunter.setCountOfStep(hunter.getCountOfStep() + 1);
				
				if (hunter.getCountOfStep() == NUMB_OF_STEP_FOR_ONE_DERECTION) {
					hunter.setCountOfStep(0);
				}
				
				fire(hunter);
				Bullet bullet = hunter.get_bullet();
				Tank victim = get_state().get_tank();
				hit(hunter, bullet, victim);

				for (Tank tank : _state.get_MyBotTanks()) {
					hit(hunter, bullet, tank);

				}
			}
		}
	}

	protected void oneStepForMyBotTanks (List<Tank> tanks){
		int count = 0;
		for (Tank tank : tanks) {
			if (tank.getDamages() == Tank.getArmomBotTank()) {
				tank.setCrash(true);
			}
			if (!tank.isCrash()) {
				if (count == 0) {
					moveHunter(tank);
					tank.setCountOfStep(tank.getCountOfStep() + 1);
					if (tank.getCountOfStep() == NUMB_OF_STEP_FOR_ONE_DERECTION)
						tank.setCountOfStep(0);
				} else {
					if (count == 1) {
						moveHealer(tank);
						tank.setCountOfStep(tank.getCountOfStep() + 1);
						if (tank.getCountOfStep() == NUMB_OF_STEP_FOR_ONE_DERECTION * 2)
							tank.setCountOfStep(0);

					} else {
						if (count == 2) {
							moveGuard(tank);
						}
					}
				}
				fire(tank);

				Bullet bullet = tank.get_bullet();
				for (Tank bot : _state.get_botTanks()) {
					hit(tank, bullet, bot);
				}

				if(count == 1){
					hit(tank, bullet, _state.get_tank());
				}

				count++;
			}
		}
	}

	private void moveGuard(Tank tank) {

		int myTank_coordX = get_state().get_tank().getCoordX();
		int myTank_coordY = get_state().get_tank().getCoordY();
		int myDirection = get_state().get_tank().getDerection();
		int differ = BOX_SIZE_X * 2;

		int differ_coordX = myTank_coordX - tank.getCoordX();
		int differ_coordY = myTank_coordY - tank.getCoordY();

		if ((Math.abs(differ_coordX) > differ) || (Math.abs(differ_coordY) > differ)) {

			switch (myDirection) {
				case UP:
					if (differ_coordX > 0) {
						tank.setDerection(RIGHT);
						changeMove(tank);
						break;
					}
					if (differ_coordX < 0) {
						tank.setDerection(LEFT);
						changeMove(tank);
						break;
					}
					if (differ_coordY > 0) {
						tank.setDerection(UP);
						break;
					}
					if (differ_coordY < 0) {
						tank.setDerection(DOWN);
						break;
					}
					break;

				case DOWN:
					if (differ_coordX > 0) {
						tank.setDerection(RIGHT);
						changeMove(tank);
						break;
					}
					if (differ_coordX < 0) {
						tank.setDerection(LEFT);
						changeMove(tank);
						break;
					}
					if (differ_coordY > 0) {
						tank.setDerection(UP);
						break;
					}
					if (differ_coordY < 0) {
						tank.setDerection(DOWN);
						break;
					}
					break;

				case RIGHT:
					if (differ_coordY > 0) {
						tank.setDerection(UP);
						changeMove(tank);
						break;
					}
					if (differ_coordY < 0) {
						tank.setDerection(DOWN);
						changeMove(tank);
						break;
					}

					if (differ_coordX > 0) {
						tank.setDerection(RIGHT);
						break;
					}
					if (differ_coordX < 0) {
						tank.setDerection(LEFT);
						break;
					}
					break;

				case LEFT:
					if (differ_coordY > 0) {
						tank.setDerection(UP);
						changeMove(tank);
						break;
					}
					if (differ_coordY < 0) {
						tank.setDerection(DOWN);
						changeMove(tank);
						break;
					}

					if (differ_coordX > 0) {
						tank.setDerection(RIGHT);
						break;
					}
					if (differ_coordX < 0) {
						tank.setDerection(LEFT);
						break;
					}
					break;


			}
		}

	}

	private void changeMove (Tank tank) {
		int direction = tank.getDerection();
		switch (direction) {
			case UP:
				move(tank, tank.get_speed(), 0);
				break;

			case DOWN:
				move(tank, -tank.get_speed(), 0);
				break;

			case LEFT:
				move(tank, 0, -tank.get_speed());
				break;

			case RIGHT:
				move(tank, 0, tank.get_speed());
				break;

			default:
				break;
		}
	}

	private void moveHealer(Tank tank) {
		if (tank.getCountOfStep() == 0) {
			if (random.nextBoolean()) {

				if (random.nextBoolean()) {
					tank.setDerection(UP);
					return;
				} else {
					tank.setDerection(DOWN);
					return;
				}

			} else {

				if (random.nextBoolean()) {
					tank.setDerection(RIGHT);
					return;
				} else {
					tank.setDerection(LEFT);
					return;
				}
			}
		}

		changeMove(tank);
	}

	private void moveHunter(Tank tank) {

		List<Tank> tanks = get_state().get_botTanks();
		int bot_coordX;
		int bot_coordY;
		int differ_coordX = Integer.MAX_VALUE;
		int differ_coordY = Integer.MAX_VALUE;

		for (Tank bot : tanks) {
			bot_coordX = bot.getCoordX();
			bot_coordY = bot.getCoordY();
			if ((tank.getCoordX() - bot_coordX < differ_coordX) || (tank.getCoordY() - bot_coordY < differ_coordY)){
				differ_coordX = - tank.getCoordX() + bot_coordX;
				differ_coordY = - tank.getCoordY() + bot_coordY;
			}

		}

		if (tank.getCountOfStep() == 0) {
			if (random.nextBoolean()) {

				if (differ_coordY > 0) {
					tank.setDerection(UP);
					return;
				} else {
					tank.setDerection(DOWN);
					return;
				}

			} else {

				if (differ_coordX > 0) {
					tank.setDerection(RIGHT);
					return;
				} else {
					tank.setDerection(LEFT);
					return;
				}
			}
		}

		changeMove(tank);
	}

	public boolean move(Tank tank, int vertical_speed, int horizontal_speed) {

		if (vertical_speed == tank.get_speed()) {
			tank.setDerection(UP);
		} else if (vertical_speed == -tank.get_speed()) {
			tank.setDerection(DOWN);
		} else if (horizontal_speed == tank.get_speed()) {
			tank.setDerection(RIGHT);
		} else if (horizontal_speed == -tank.get_speed()) {
			tank.setDerection(LEFT);
		}

		tank.setCoordY(tank.getCoordY() + vertical_speed);
		tank.setCoordX(tank.getCoordX() + horizontal_speed);

		if (!isTankFitField(tank) || isTankOnOtherTank(tank)) {
			tank.setCoordY(tank.getCoordY() - vertical_speed);
			tank.setCoordX(tank.getCoordX() - horizontal_speed);
			if(!tank.equals(_state.get_tank())) {
				if (tank.getStandStill() == 15) {
					tank.setDerection((int) (Math.random() * 4));
					tank.setStandStill(0);
				}
				tank.setStandStill(tank.getStandStill() + 1);
			}
			return false;
		}
		return true;

	}

	private boolean isTankOnOtherTank(Tank tank) {
		
		boolean cross = false;
		
		for (Tank otherTank : get_state().get_botTanks()) {
			if (!tank.equals(otherTank)) {
				if (cross = checkCrossing(tank.getCoordX(), tank.getCoordY(), otherTank.getCoordX(), otherTank.getCoordY())) {
					return cross;
				}
			}
		}

		for (Tank otherTank : get_state().get_MyBotTanks()) {
			if (!tank.equals(otherTank)) {
				if (cross = checkCrossing(tank.getCoordX(), tank.getCoordY(), otherTank.getCoordX(), otherTank.getCoordY())) {
					return cross;
				}
			}
		}
		
		if (!tank.equals(get_state().get_tank())) {
			if (cross = checkCrossing(tank.getCoordX(), tank.getCoordY(), get_state().get_tank().getCoordX(), get_state().get_tank().getCoordY())) {
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
		int leftUpAngle_Y = (tank.getCoordY() + 1) / (TANK_SIZE_Y);
		int leftUpAngle_X = (tank.getCoordX() + 1) / (TANK_SIZE_X);

		int rightUpAngle_Y = (tank.getCoordY() + 1) / (TANK_SIZE_Y);
		int rightUpAngle_X = (tank.getCoordX() + TANK_SIZE_X - 1) / (TANK_SIZE_X);

		int rightDownAngle_Y = (tank.getCoordY() + TANK_SIZE_Y - 1) / (TANK_SIZE_Y);
		int rightDownAngle_X = (tank.getCoordX() + TANK_SIZE_X - 1) / (TANK_SIZE_X);

		int leftDownAngle_Y = (tank.getCoordY() + TANK_SIZE_Y - 1) / (TANK_SIZE_Y);
		int leftDownAngle_X = (tank.getCoordX() + 1) / (TANK_SIZE_X);

		if (objectIsNotInMap(tank.getCoordY(), tank.getCoordX())
			|| !locationIsFree(leftUpAngle_Y, leftUpAngle_X)
			|| objectIsNotInMap(tank.getCoordY(), tank.getCoordX() + TANK_SIZE_X)
			|| !locationIsFree(rightUpAngle_Y, rightUpAngle_X)
			|| objectIsNotInMap(tank.getCoordY() + TANK_SIZE_Y, tank.getCoordX() + TANK_SIZE_X)
			|| !locationIsFree(rightDownAngle_Y, rightDownAngle_X)
			|| objectIsNotInMap(tank.getCoordY() + TANK_SIZE_Y, tank.getCoordX())
			|| !locationIsFree(leftDownAngle_Y, leftDownAngle_X)) {

			return false;
		}
		return true;
	}

	protected boolean objectIsNotInMap(int coordY, int coordX) {
		return coordY < 0 || coordX < 0 || coordY >= get_state().get_map().getHeight()*TANK_SIZE_Y
				|| coordX >= get_state().get_map().getWidth()*TANK_SIZE_X;
	}

	protected boolean locationIsFree(int row, int col) {
		return (get_state().get_map().get_data()[row][col] == 0 || get_state().get_map().get_data()[row][col] == 1);
	}

	protected void moveBotTank(Tank tank) {
		
		int victim_coordX = get_state().get_tank().getCoordX();
		int victim_coordY = get_state().get_tank().getCoordY();

		int differ_coordX = victim_coordX - tank.getCoordX();
		int differ_coordY = victim_coordY - tank.getCoordY();

		if (tank.getCountOfStep() == 0) {
			if (random.nextBoolean()) {

				if (differ_coordY > 0) {
					tank.setDerection(UP);
					return;
				} else {
					tank.setDerection(DOWN);
					return;
				}

			} else {

				if (differ_coordX > 0) {
					tank.setDerection(RIGHT);
					return;
				} else {
					tank.setDerection(LEFT);
					return;
				}
			}
		}

		changeMove(tank);
	}

	protected boolean fire(Tank tank) {
		if (isFireAlready(tank)) {
			moveBullet(tank);
			return false;
		}
		tank.get_bullet().setCoordY(0);
		tank.get_bullet().setCoordX(0);
		if(System.currentTimeMillis() - tank.get_bullet().getTime() > 1500) {
			tank.set_bullet(new Bullet(tank.equals(get_state().getTank_with_bonus()) && get_state().getBonus() == SHOTING_SPEED));
			tank.get_bullet().setLive(true);
			tank.get_bullet().setTime(System.currentTimeMillis());
			tank.get_bullet().setCoordY(tank.getCoordY() + TANK_SIZE_Y / 2);
			tank.get_bullet().setCoordX(tank.getCoordX() + TANK_SIZE_X / 2);
			tank.get_bullet().setTargetDerection(tank.getDerection());
			return true;
		}
		return  false;
	}

	protected void moveBullet(Tank tank) {
		switch (tank.get_bullet().getTargetDerection()) {
		case UP:
			tank.get_bullet().setCoordY(tank.get_bullet().getCoordY() + tank.get_bullet().get_speed());
			break;

		case DOWN:
			tank.get_bullet().setCoordY(tank.get_bullet().getCoordY() - tank.get_bullet().get_speed());
			break;

		case LEFT:
			tank.get_bullet().setCoordX(tank.get_bullet().getCoordX() - tank.get_bullet().get_speed());
			break;

		case RIGHT:
			tank.get_bullet().setCoordX(tank.get_bullet().getCoordX() + tank.get_bullet().get_speed());
			break;

		default:
			break;
		}
	}

	private boolean isFireAlready(Tank tank) {
		if (tank.get_bullet().isLive() == true) {
			return true;
		}
		return false;
	}

	protected boolean hit(Tank hunter, Bullet bullet, Tank victim) {
		
		int tankCoordY = victim.getCoordY();
		int tankCoordX = victim.getCoordX();
		int bulletCoordY = hunter.get_bullet().getCoordY() ;
		int bulletCoordX = hunter.get_bullet().getCoordX();

		if (tankCoordY <= bulletCoordY
				&& tankCoordY + TANK_SIZE_Y >= bulletCoordY
				&& tankCoordX <= bulletCoordX
				&& tankCoordX + TANK_SIZE_X >= bulletCoordX) {

			return hitOfTank(hunter, bullet, victim);
		
		} 
		
		if (objectIsNotInMap(bulletCoordY, bulletCoordX)
				|| get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_STONE ) {
		
			return missShooting(hunter, bullet);
		
		}else if(get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_BRICK ){
		
			int bullet_direction = hunter.get_bullet().getTargetDerection();
			return hitInFullBrickBlock(hunter, bullet, bulletCoordY, bulletCoordX, bullet_direction);
		
		}
		
		return hitInHalfBrickBlock(hunter, bullet, bulletCoordY, bulletCoordX);
	}

	private boolean missShooting(Tank hunter, Bullet bullet) {
		
		hunter.get_bullet().setCoordX(0);
		hunter.get_bullet().setCoordY(0);
		bullet.setLive(false);
		return false;
		
	}

	private boolean hitOfTank(Tank hunter, Bullet bullet, Tank victim) {
		
		victim.setDamages(victim.getDamages() + 1);

		if (victim.equals(get_state().get_tank()) && get_state().get_MyBotTanks().contains(hunter)) {
			if (victim.getDamages() != Tank.getArmorPlayerTank()) {
				victim.setDamages(victim.getDamages() - 2);
				bullet.setLive(false);
			}
			return true;
		}


		if (victim.equals(get_state().get_tank())) {
			if (victim.getDamages() == Tank.getArmorPlayerTank()) {
				victim.setCrash(true);
			}

		}

		for (Tank tankBot : get_state().get_botTanks()) {
			if (victim.getDamages() == Tank.getArmomBotTank()) {
				if (victim.equals(tankBot)) {

					victim.get_bullet().setCoordX((victim.getCoordX() * 2 + TANK_SIZE_X) / 2);
					victim.get_bullet().setCoordY((victim.getCoordY() * 2 + TANK_SIZE_Y) / 2);
					victim.setCrash(true);
					hunter.setEnemyKilled(hunter.getEnemyKilled() + 1);
					get_state().setCoordX_expl(victim.getCoordX());
					get_state().setCoordY_expl(victim.getCoordY());
					bullet.setLive(false);
					return true;

				}
			}
		}

		bullet.setLive(false);
		return false;
	}

	private boolean hitInHalfBrickBlock(Tank hunter, Bullet bullet,
			int bulletCoordY, int bulletCoordX) {
		
		if (get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_UP
				|| get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_DOWN
				|| get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_RIGHT
				|| get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] == VALUE_BLOCK_WITH_HALF_ON_LEFT) {
			
			get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_GROUND;
			hunter.get_bullet().setCoordX((hunter.getCoordX() * 2 + TANK_SIZE_X) / 2);
			hunter.get_bullet().setCoordY((hunter.getCoordY() * 2 + TANK_SIZE_Y) / 2);
			bullet.setLive(false);
			return true;
			
		}
		return false;
	}

	private boolean hitInFullBrickBlock(Tank hunter, Bullet bullet,
			int bulletCoordY, int bulletCoordX, int bullet_direction) {
		
		if (bullet_direction == 0) {
			get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_UP;
		}else if (bullet_direction == 1) {
			get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_DOWN;
		}else if (bullet_direction == 2) {
			get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_RIGHT;
		}else if (bullet_direction == 3) {
			get_state().get_map().get_data()[bulletCoordY / BOX_SIZE_Y][bulletCoordX / BOX_SIZE_X] = VALUE_BLOCK_WITH_HALF_ON_LEFT;
		}
		
		hunter.get_bullet().setCoordX(hunter.getCoordX() + TANK_SIZE_X / 2);
		hunter.get_bullet().setCoordY(hunter.getCoordY() + TANK_SIZE_Y / 2);
		bullet.setLive(false);
		return true;
		
	}


	public boolean dropBonus() {
		
		
		int bonus_tmpX;
		int bonus_tmpY;
		
		if (get_state().getTank_with_bonus() != null) {
			get_state().getTank_with_bonus().set_speed(Tank.getSpeed());  
			get_state().getTank_with_bonus().get_bullet().set_speed(Bullet.getSpeed());
			get_state().setTank_with_bonus(null);
		}
		
		get_state().setBonusIsTake(false);
		while (true) {
			
			get_state().setBonus(random.nextInt(NUMB_OF_BONUS));
			bonus_tmpX = random.nextInt(Map.getCol() - 1);
			bonus_tmpY = random.nextInt(Map.getRow() - 1);
			
			if (get_state().get_map().get_data()[bonus_tmpY][bonus_tmpX] == VALUE_BLOCK_GROUND) {
				get_state().set_bonus_coordX(bonus_tmpX);
				get_state().set_bonus_coordY(bonus_tmpY);
				break;
			}
			
		}
		return true;
	}


	public void takeBonus() {
		for (Tank tank : get_state().get_botTanks()) {
			if (get_state().isBonusIsDroped() && checkCrossing(tank.getCoordX(), tank.getCoordY(), 
					get_state().get_bonus_coordX()*BOX_SIZE_X, get_state().get_bonus_coordY()*BOX_SIZE_Y)) {
				
				whichBonusIsTake(tank);
				get_state().setTank_with_bonus(tank);
				bonusIsTake();
				return;
				
			}
		}
		if (get_state().isBonusIsDroped() && checkCrossing(get_state().get_tank().getCoordX(), get_state().get_tank().getCoordY(),
				get_state().get_bonus_coordX()*BOX_SIZE_X, get_state().get_bonus_coordY()*BOX_SIZE_Y)) {

			whichBonusIsTake(get_state().get_tank());
			get_state().setTank_with_bonus(get_state().get_tank());
			bonusIsTake();
			
		}
	}

	private void whichBonusIsTake(Tank tank) {
		
		if (get_state().getBonus() == ARMOR) {
			tank.setDamages(tank.getDamages() - 1);
		}else if (get_state().getBonus() == MOVING_SPEED) {
			tank.set_speed(tank.get_speed() + Tank.getSpeed());
		}else if (get_state().getBonus() == SHOTING_SPEED) {
			tank.get_bullet().set_speed(tank.get_bullet().get_speed() + Bullet.getSpeed());
		}
		
	}

	private void bonusIsTake() {
		get_state().set_bonus_coordX(get_state().set_bonus_coordY(COORD_BONUS_IS_CHARGING));
		get_state().setBonusIsDroped(false);
		get_state().setBonusIsTake(true);
	}

	public State get_state() {
		return _state;
	}

	public void set_state(State _state) {
		this._state = _state;
	}

}
