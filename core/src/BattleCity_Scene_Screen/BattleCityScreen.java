package BattleCity_Scene_Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class BattleCityScreen implements Screen {

	private static final int SCREEN_WIGHT = Gdx.graphics.getWidth();
	private static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
	private boolean isArcade;

	
	BattleCityStage _stage;
	BattleCityGame game;
	Texture texture;
	
    public BattleCityScreen(BattleCityGame newGame, boolean isArcade) {
    	game = newGame;
		this.isArcade = isArcade;
    }
	

	@Override
	public void show() {
		_stage = new BattleCityStage(game);
		_stage.init(isArcade);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		_stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
        if (_stage.isGameIsOver()) {
			game.setScreen(new GameOverScreen(game, _stage));
			game.main.stop();
		}
        
	}

	

	@Override
	public void resize(int width, int height) {
		_stage.getViewport().update(width, height, false);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}


	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}


	public static int getScreenWight() {
		return SCREEN_WIGHT;
	}

}
