package com.gdx.battle_city;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameOverScreen implements Screen, InputProcessor {

	OrthographicCamera camera;

	BattleCityGame game = new BattleCityGame();
	
	private static final int HEIGHT_BUT_PLAY = BattleCityScreen.SCREEN_HEIGHT/10;

	private static final int LENGHT_BUT_PLAY = BattleCityScreen.SCREEN_WIGHT/4;

	private static final double COORD_BUT_PLAY_X = BattleCityScreen.SCREEN_WIGHT*0.4;

	private static final double COORD_BUT_PLAY_Y = BattleCityScreen.SCREEN_HEIGHT*0.8;

	private static final double COORD_BUT_VOLUME_X = BattleCityScreen.SCREEN_WIGHT*0.01;

	private static final double COORD_BUT_VOLUME_Y = BattleCityScreen.SCREEN_HEIGHT*0.9;

	private static final int LENGHT_BUT_VOLUME = BattleCityScreen.SCREEN_WIGHT/10;

	private static final int HEIGHT_BUT_VOLUME = BattleCityScreen.SCREEN_HEIGHT/10;

	
	boolean isPlay = false;
	Headband headband = new Headband(1);
	
	Button play = new Button(3, (int) (BattleCityScreen.SCREEN_WIGHT * 0.4),
			(int) (BattleCityScreen.SCREEN_HEIGHT * 0.1));
	Button _volume = new Button(4, (int) (COORD_BUT_VOLUME_X), (int) (BattleCityScreen.SCREEN_HEIGHT*0.01));
	
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
			play._texture = play.textures[2];
		}else if(game.music_mute){
			_volume._texture = _volume.textures[5];
		}else if (!game.music_mute) {
			_volume._texture = _volume.textures[4];
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
			game.main.setVolume(0);
			game.boom.setVolume(0);
			game.shot.setVolume(0);
			game.music_gameOver.setVolume(0);
		}else if(!game.music_mute){
			game.main.setVolume(100);
			game.boom.setVolume(100);
			game.shot.setVolume(100);
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
