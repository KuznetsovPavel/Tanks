package com.gdx.battle_city;

import tanks.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {

	static Texture[] textures = new Texture[9];
	Texture _texture;
	
	
	static {
		for (int i = 0; i < textures.length ; i++) {
			textures[i] = new Texture(Gdx.files.internal("resourse/graphics/block_" + i + ".png"));
		}
		
	}

	public Box(int textureIndex) {
		_texture = textures[textureIndex];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(_texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(),
				BattleCityScreen.SCREEN_WIGHT/Map.COL, BattleCityScreen.SCREEN_HEIGHT/Map.ROW, 
				this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0, 
				_texture.getWidth(), _texture.getHeight(), false, false);
	}

	public void setTexture(int textureIndex) {
		_texture = textures[textureIndex];
	}
	
}
