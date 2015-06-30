package com.gdx.battle_city;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BattleCityGame extends Game {
	Music main;
	Music music_gameOver;
	 SpriteBatch batch;
	 BitmapFont font;
	 boolean music_mute = false;
	 
	static Music shot;
	static Music boom;
	 
	@Override
	public void create () {
		
	batch = new SpriteBatch();
	font = new BitmapFont();
		this.setScreen(new MenuScreen(this));
		music_gameOver = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/gameOver.mp3"));
		main = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/main.mp3"));
		shot = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/shot.mp3"));
		boom = Gdx.audio.newMusic(Gdx.files.internal("resourse/sounds/boom.mp3"));
		main.play();
	}

}
