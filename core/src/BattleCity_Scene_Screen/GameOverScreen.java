package BattleCity_Scene_Screen;

import BattleCity_Actors.Button;
import BattleCity_Actors.Headband;
import BattleCity_Actors.InfoPanel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameOverScreen implements Screen, InputProcessor {

	private static final int VOLUME_MAX = 100;

	private static final int VOLUME_OFF = 0;

	private static final int GAME_OVER_HEADBAND = 1;

	private static final int SOUND_IS_ON = 4;
	private static final int SOUND_IS_OFF = 5;
	private static final int BUTTON_PLAY_IS_NOT_PUT = 3;
	private static final int BUTTON_PLAY_IS_PUT = 2;
	
	private static final int HEIGHT_BUT_PLAY = Button.getHeightButPlay();
	private static final int LENGHT_BUT_PLAY = Button.getWidthButPlay();
	private static final double COORD_BUT_PLAY_X = BattleCityScreen.SCREEN_WIGHT*0.4;
	private static final double COORD_BUT_PLAY_Y = BattleCityScreen.SCREEN_HEIGHT*0.8;
	private static final double COORD_BUT_VOLUME_X = BattleCityScreen.SCREEN_WIGHT*0.01;
	private static final double COORD_BUT_VOLUME_Y = BattleCityScreen.SCREEN_HEIGHT*0.85;
	private static final int LENGHT_BUT_VOLUME = Button.getWidthButVolume();
	private static final int HEIGHT_BUT_VOLUME = Button.getHeightButVolume();

	BattleCityGame game = new BattleCityGame();
	OrthographicCamera camera;
	
	boolean isPlay = false;
	Headband headband = new Headband(GAME_OVER_HEADBAND);
	
	Button play = new Button(BUTTON_PLAY_IS_NOT_PUT, (int) (BattleCityScreen.SCREEN_WIGHT * 0.4),
			(int) (BattleCityScreen.SCREEN_HEIGHT * 0.1));
	Button _volume = new Button(SOUND_IS_ON, (int) (COORD_BUT_VOLUME_X), (int) (BattleCityScreen.SCREEN_HEIGHT*0.01));
	
	InfoPanel gameOverInfoPanel = new InfoPanel();
	BitmapFont gameOverInfo = new BitmapFont();
	String message = new String();
	
	
	public GameOverScreen(BattleCityGame newGame, BattleCityStage stage) {
		game = newGame;
		message = "You are killed " + stage.final_score + " enemy " + '\n' 
				+ '\n' + "You could clean up " + stage.final_level + " level";
		gameOverInfo.setColor(Color.WHITE);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, BattleCityScreen.SCREEN_HEIGHT,
				BattleCityScreen.SCREEN_WIGHT);
	}

	@Override
	public void show() {
		game.music_gameOver.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		gameOverInfoPanel.setPosition((float) (BattleCityScreen.SCREEN_WIGHT*0.43), 
				(float) (BattleCityScreen.SCREEN_HEIGHT*0.4));
		
		game.batch.begin();
		headband.draw(game.batch, 0);
		
		if (isPlay) {
			play.set_texture(play.getTextures()[BUTTON_PLAY_IS_PUT]);
		}else if(game.music_mute){
			_volume.set_texture(_volume.getTextures()[SOUND_IS_OFF]);
		}else if (!game.music_mute) {
			_volume.set_texture(_volume.getTextures()[SOUND_IS_ON]);
		}
		
		play.draw(game.batch, 0);
		 _volume.draw(game.batch, 0);
		gameOverInfoPanel.setScale(1);
		gameOverInfoPanel.draw(game.batch, 0);
		gameOverInfo.draw(game.batch, message, (float) (BattleCityScreen.SCREEN_WIGHT*0.45), 
				(float) (BattleCityScreen.SCREEN_HEIGHT*0.55));
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
			game.music_gameOver.stop();
			game.main.play();
	        return true;
		}else if (game.music_mute) {
			game.main.setVolume(VOLUME_OFF);
			game.getBoom().setVolume(VOLUME_OFF);
			game.getShot().setVolume(VOLUME_OFF);
			game.music_gameOver.setVolume(VOLUME_OFF);
		}else if(!game.music_mute){
			game.main.setVolume(VOLUME_MAX);
			game.getBoom().setVolume(VOLUME_MAX);
			game.getShot().setVolume(VOLUME_MAX);
			game.music_gameOver.setVolume(VOLUME_MAX);
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