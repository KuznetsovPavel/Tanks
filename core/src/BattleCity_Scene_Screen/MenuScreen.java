package BattleCity_Scene_Screen;

import BattleCity_Actors.Button;
import BattleCity_Actors.Headband;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen, InputProcessor{

	private static final int HEIGHT_BUT_PLAY = Button.getHeightButPlay();
	private static final int LENGHT_BUT_PLAY = Button.getWidthButPlay();
	private static final double COORD_BUT_PLAY_X = BattleCityScreen.SCREEN_WIGHT*0.4;
	private static final double COORD_BUT_PLAY_Y = BattleCityScreen.SCREEN_HEIGHT*0.8;
	private static final double COORD_BUT_VOLUME_X = BattleCityScreen.SCREEN_WIGHT*0.01;
	private static final double COORD_BUT_VOLUME_Y = BattleCityScreen.SCREEN_HEIGHT*0.85;
	private static final int LENGHT_BUT_VOLUME = Button.getWidthButVolume();
	private static final int HEIGHT_BUT_VOLUME = Button.getHeightButVolume();

	OrthographicCamera camera;
	 
	 boolean isPlay = false;
	 BattleCityGame game = new BattleCityGame();
	 Headband headband = new Headband(0);
	 Button play_game = new Button(0, (int) COORD_BUT_PLAY_X, (int) (BattleCityScreen.SCREEN_HEIGHT*0.1));
	 Button _volume = new Button(4, (int) (COORD_BUT_VOLUME_X), (int) (BattleCityScreen.SCREEN_HEIGHT*0.01));
	 
	 public MenuScreen(BattleCityGame newGame) {
		 	game = newGame;
	        camera = new OrthographicCamera();
	        camera.setToOrtho(false, BattleCityScreen.SCREEN_HEIGHT, BattleCityScreen.SCREEN_WIGHT);
	    }
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        camera.update();

	        game.batch.begin();
	        headband.draw(game.batch, 0);
	        if (isPlay) {
				play_game.set_texture(play_game.getTextures()[1]);
			}else if(game.music_mute){
				_volume.set_texture(_volume.getTextures()[5]);
			}else if (!game.music_mute) {
				_volume.set_texture(_volume.getTextures()[4]);
			}
	        play_game.draw(game.batch, 0);
	        _volume.draw(game.batch, 0);
	        game.batch.end();
	        Gdx.input.setInputProcessor(this);

	     
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointInArea(screenX, screenY, COORD_BUT_PLAY_X, COORD_BUT_PLAY_Y, LENGHT_BUT_PLAY, HEIGHT_BUT_PLAY)) {
			isPlay = true;
			return true;
		}else if (pointInArea(screenX, screenY,COORD_BUT_VOLUME_X, COORD_BUT_VOLUME_Y,
				LENGHT_BUT_VOLUME, HEIGHT_BUT_VOLUME )) {
			if (!game.music_mute) {
				game.music_mute = true;
			}else{
				game.music_mute = false;
			}
			return true;				
		}
		return false;
	}

	private boolean pointInArea(int screenX, int screenY, double coordButPlayX, double coordButPlayY,
			int buttLenght, int buttHeight) {
		return screenX > coordButPlayX && screenX < coordButPlayX + buttLenght 
			&& screenY > coordButPlayY && screenY < coordButPlayY + buttHeight;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (isPlay) {
			game.setScreen(new BattleCityScreen(game));
	        return true;
		}else if (game.music_mute) {
			game.main.setVolume(0);
			game.getBoom().setVolume(0);
			game.getShot().setVolume(0);
			game.music_gameOver.setVolume(0);
		}else if(!game.music_mute){
			game.main.setVolume(100);
			game.getBoom().setVolume(100);
			game.getShot().setVolume(100);
			game.music_gameOver.setVolume(100);
		}
		return false;
	}


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
