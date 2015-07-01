package BattleCity_LibGDX;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class BattleCityScreen implements Screen {

	BattleCityStage _stage;
	BattleCityGame game;
	Texture texture;
	
	
	
	public static final int SCREEN_WIGHT = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight(); 
    
    public BattleCityScreen(BattleCityGame newGame) {
    	game = newGame;
    }
	

	@Override
	public void show() {
		_stage = new BattleCityStage();
		_stage.init();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _stage.draw();
        if (_stage.gameIsOver) {
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

}
