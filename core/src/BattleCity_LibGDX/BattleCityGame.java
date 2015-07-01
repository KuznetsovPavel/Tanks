package BattleCity_LibGDX;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class BattleCityGame extends Game {
	Music main;
	Music music_gameOver;
	 SpriteBatch batch;
	 BitmapFont font;
	 boolean music_mute = false;
	 
	private static Music shot;
	private static Music boom;
	 
	@Override
	public void create () {
		
	batch = new SpriteBatch();
	font = new BitmapFont();
		this.setScreen(new MenuScreen(this));
		music_gameOver = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/gameOver.mp3"));
		main = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/main.mp3"));
		setShot(Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/shot.mp3")));
		setBoom(Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/boom.mp3")));
		main.play();
	}

	public static Music getBoom() {
		return boom;
	}

	public static void setBoom(Music boom) {
		BattleCityGame.boom = boom;
	}

	public static Music getShot() {
		return shot;
	}

	public static void setShot(Music shot) {
		BattleCityGame.shot = shot;
	}

}
