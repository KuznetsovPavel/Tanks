package Actors;


import BattleCity_LibGDX.BattleCityScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Headband extends Actor {
	
	static Texture[] textures = new Texture[2];
	
	Texture _texture;
	
	static{
		textures[0] = new Texture(Gdx.files.internal("resourse/graphics/handband.jpg"));
		textures[1] = new Texture(Gdx.files.internal("resourse/graphics/gameOverPanel.png"));
	}
	
	public Headband(int indexTexture) {
		_texture = textures[indexTexture];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, 0, 0, this.getOriginX(), this.getOriginY(),
				BattleCityScreen.SCREEN_WIGHT, BattleCityScreen.SCREEN_HEIGHT, this.getScaleX(), this.getScaleY(), 
				this.getRotation(), 0, 0, _texture.getWidth(), _texture.getHeight(), false, false);
	}
}