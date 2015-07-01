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

	private static final int TIME_FOR_BONUS = 1000;
	private static final int SCREEN_WIDTH = BattleCityScreen.SCREEN_WIGHT;
	private static final int SCREEN_HEIGHT = BattleCityScreen.SCREEN_HEIGHT;

	SpriteBatch spriteBatch = new SpriteBatch();
	
	int timerForBonus = 0;
	protected boolean gameIsOver = false;
	protected int final_score;
	protected int final_level;
	
	public BattleCityStage() {
		
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false);
		setViewport(new ScreenViewport(camera));
	
	}

	public void init() {
		
		final Model model = new Model();
		final Controller controller = new Controller();
		model.addListener(controller);
		ClickHandler clickHandler = new ClickHandler(model, controller);
		
		LibGDXView view = new LibGDXView(this, model);

		controller.setView(view);
		controller.setModel(model);

		Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				timerForBonus += 1;		
				
				if (timerForBonus == TIME_FOR_BONUS) {
					controller.dropBonus();
					timerForBonus = 0;
				}
				
				if (controller.oneStep()) {
					BattleCityGame.getBoom().stop();
					BattleCityGame.getBoom().play();
				}
			}
		}, 0.01f, 0.013f);

		Gdx.input.setInputProcessor(this);

		addListener(clickHandler);

	}

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

}
