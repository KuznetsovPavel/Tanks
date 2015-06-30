package com.gdx.battle_city;

import tanks.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonFire extends Actor {
	
	static final int HEIGHT_BUTTON = BattleCityScreen.SCREEN_HEIGHT/Map.ROW*2;
	static final int WIDTH_BUTTON = BattleCityScreen.SCREEN_WIGHT/Map.COL*2;
	static Texture[] textures = new Texture[2];
	Texture _texture;
	
	
	static {
			textures[0] = new Texture(Gdx.files.internal("resourse/graphics/but_fire_up.png"));
			textures[1] = new Texture(Gdx.files.internal("resourse/graphics/but_fire_down.png"));
	}

	public ButtonFire(int textureIndex) {
		_texture = textures[textureIndex];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				WIDTH_BUTTON, HEIGHT_BUTTON, this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				_texture.getWidth(), _texture.getHeight(), false, false);
	}

}
