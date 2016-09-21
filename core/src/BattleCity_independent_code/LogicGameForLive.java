package BattleCity_independent_code;

import java.sql.Time;
import java.util.List;
import java.util.Random;

public class LogicGameForLive extends Logic{

	private long time;

	public LogicGameForLive(final State state) {
		super(state);
		set_state(state);
		time = System.currentTimeMillis();
	}

	public boolean oneStep() {

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
		
		for (Tank tank : tanks) {
			if (tank.isCrash() == true) {
				if (System.currentTimeMillis() - time >= 10000){
					tank.setCrash(false);
					tank.setKill(false);
					tank.setDamages(0);
					tank.setTank(View.getScreenWidth() / 2, (Map.getRow() - 2) * View.getScreenHeight() / Map.getRow());
					time = System.currentTimeMillis();
				}
			}
		}
		return true;
	}



}
