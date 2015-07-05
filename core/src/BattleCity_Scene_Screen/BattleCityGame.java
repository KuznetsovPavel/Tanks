package BattleCity_Scene_Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleCityGame extends Game {
	Music main;
	Music music_gameOver;
	private Music _aircraft;
	SpriteBatch batch;
	BitmapFont font;
	boolean music_mute = false;

	private Sound _shot;
	private Sound _boom;

	@Override
	public void create() {

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MenuScreen(this));
		music_gameOver = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/gameOver.mp3"));
		main = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/main.mp3"));
		setAircraft(Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/aircraft.mp3")));
		setShot(Gdx.audio.newSound(Gdx.files.internal("resourse/sounds/shot.mp3")));
		setBoom(Gdx.audio.newSound(Gdx.files.internal("resourse/sounds/boom.mp3")));
		main.play();
	}

	public Sound getBoom() {
		return _boom;
	}

	public void setBoom(Sound boom) {
		_boom = boom;
	}

	public Sound getShot() {
		return _shot;
	}

	public void setShot(Sound shot) {
		_shot = shot;
	}

	public Music getAircraft() {
		return _aircraft;
	}

	public void setAircraft(Music aircraft) {
		_aircraft = aircraft;
	}

}
