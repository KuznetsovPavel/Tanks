package BattleCity_Scene_Screen;

import BattleCity_independent_code.Controller;
import BattleCity_independent_code.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BattleCityStage extends Stage {

	private static final int ERROR_TIME = 100;
	private static final int FALL_TIME_BONUS = 3500;
	private static final int TIME_FOR_BONUS = 15000;
	private static final int SCREEN_WIDTH = BattleCityScreen.getScreenWight();
	private static final int SCREEN_HEIGHT = BattleCityScreen.getScreenHeight();

	SpriteBatch spriteBatch = new SpriteBatch();
	
	private long timerForDroppingBonus = 0;
	private long timerForGettingBonus = 0;
	private boolean gameIsOver = false;
	private int final_score;
	private int final_level;
	private BattleCityGame _game;
	
	public BattleCityStage(BattleCityGame game) {
		
		_game = game;
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false);
		setViewport(new ScreenViewport(camera));
	
	}

	public void init() {
		
		final Model model = new Model();
		final Controller controller = new Controller();
		model.addListener(controller);
		ClickHandler clickHandler = new ClickHandler(model, controller, _game);
		
		LibGDXView view = new LibGDXView(this, model, _game);

		controller.setView(view);
		controller.setModel(model);

		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				if (timerForDroppingBonus == 0) {
					timerForDroppingBonus = this.getExecuteTimeMillis();
				}
				
				if (this.getExecuteTimeMillis() - timerForDroppingBonus >= TIME_FOR_BONUS - ERROR_TIME && 
						this.getExecuteTimeMillis() - timerForDroppingBonus <= TIME_FOR_BONUS) {
					model.get_logic().get_state().setBonusIsTake(true);
				}else if (this.getExecuteTimeMillis() - timerForDroppingBonus >= TIME_FOR_BONUS) {
					controller.dropBonus();
					timerForGettingBonus = this.getExecuteTimeMillis();
					timerForDroppingBonus = 0;		
				}			
				
				if (this.getExecuteTimeMillis() - timerForGettingBonus  >= FALL_TIME_BONUS 
						&& this.getExecuteTimeMillis() - timerForGettingBonus  < FALL_TIME_BONUS + ERROR_TIME) {
					model.get_logic().get_state().setBonusIsDroped(true);
				}
				
				if (controller.oneStep() && !_game.music_mute) {
						_game.getBoom().play();						
				}
			}
		}, 0.01f, 0.02f);

		Gdx.input.setInputProcessor(this);

		addListener(clickHandler);

	}

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public boolean isGameIsOver() {
		return gameIsOver;
	}

	public void setGameIsOver(boolean gameIsOver) {
		this.gameIsOver = gameIsOver;
	}

	public int getFinal_score() {
		return final_score;
	}

	public void setFinal_score(int final_score) {
		this.final_score = final_score;
	}

	public int getFinal_level() {
		return final_level;
	}

	public void setFinal_level(int final_level) {
		this.final_level = final_level;
	}

}
